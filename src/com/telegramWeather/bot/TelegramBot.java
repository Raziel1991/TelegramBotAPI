package com.telegramWeather.bot;
import com.telegramWeather.util.WeatherHandler;
import com.telegramWeather.weather.CityWeather;
import com.telegramWeather.weather.WeatherResponse;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.telegramWeather.util.ReadKeyFromFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class TelegramBot extends TelegramLongPollingBot {
    // Store user preferences, including ZIP codes, return from work time.
    private Map<Long, String> userZipCodes = new HashMap<>();
    private Map<Long, Integer> userReturnTime = new HashMap<>();

    // Default map values
    private final String defaultZipCode = "M5T2Y4"; // Default ZIP code
    private final int defaultReturnTime = 18;

    private Map<Long, Boolean> awaitingZipCode = new HashMap<>(); // Track if the user is expected to enter a ZIP code


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TelegramBot() {
        scheduleNightlyWeatherUpdates(); // Schedule nightly weather updates
    }

    @Override
    public String getBotUsername() {
        return "BotPrepareForWeatherBot";
    }

    @Override
    public String getBotToken() {
        String tokenFilePath = "C:\\Users\\hampt\\IdeaProjects\\botWeatherKey.txt";
        return ReadKeyFromFile.getKeyFromFile(tokenFilePath);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String userInput = message.getText();
            userZipCodes.putIfAbsent(chatId, defaultZipCode); // Default ZIP code if not set
            userReturnTime.putIfAbsent(chatId, defaultReturnTime);

            System.out.println("User: " + message.getFrom().getFirstName() + " " + message.getFrom().getLastName() +
                    " said " + userInput);

            // Check if we are awaiting a ZIP code input
            if (awaitingZipCode.getOrDefault(chatId, false)) {
                // Validate and update the ZIP code
                if (isZipCode(userInput)) {
                    userZipCodes.replace(chatId, userInput);
                    sendText(chatId, "Your ZIP code has been updated to: " + userInput);
                } else {
                    sendText(chatId, "Please enter a valid ZIP code.");
                }
                awaitingZipCode.put(chatId, false); // Reset the state after handling the ZIP code
            } else if (message.isCommand()) {
                // Handle commands
                if (message.getText().equals("/start")) {
                    sendTextWithButton(chatId, "Click the button below to get the weather:");
                } else if (message.getText().equals("/weather")) {
                    sendWeatherUpdate(chatId, userZipCodes.get(chatId), userReturnTime.get(chatId));
                } else if (message.getText().equals("/zipcode")) {
                    sendText(chatId, "Enter the ZIP code:");
                    awaitingZipCode.put(chatId, true); // Set state to expect a ZIP code input next
                }else if (message.getText().equals("/returntime")) {
                    sendTimeSelection(chatId);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("get_weather")) {
                String zipCode = userZipCodes.getOrDefault(chatId, defaultZipCode);
                sendWeatherUpdate(chatId, userZipCodes.get(chatId), userReturnTime.get(chatId));
            }else if (callbackData.startsWith("time_")) {
                int selectedHour = Integer.parseInt(callbackData.split("_")[1]);
                userReturnTime.put(chatId, selectedHour);
                sendText(chatId, "You've selected " + selectedHour + ":00 PM for weather updates.");

                // Now you can schedule weather updates for this user at the selected time
                scheduleWeatherUpdateAtSelectedTime(chatId, selectedHour);
            }
        }
    }

    // Helper method to validate if user input is a valid U.S., Canadian, or Mexican postal code
    private boolean isZipCode(String input) {
        return input.matches("\\d{5}") || // U.S. or Mexican postal code
                input.matches("[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d"); // Canadian postal code
    }

    // Helper method to send weather update
    private void sendWeatherUpdate(long chatId, String zipCode, int time) {
        WeatherResponse weatherResponse = WeatherHandler.getWeatherByZipCode(zipCode);

        if (weatherResponse != null) {
            sendText(chatId, "Current Weather: " +
                            "\nTimezone: " + weatherResponse.getTimezone() +
                            "\nTemperature: " + weatherResponse.getTemperature() +
                            "\nFeels Like: " + weatherResponse.getFeelsLike() +
                            "\n\nWeather for time: " + time + ":00 " +
                            "\nTimezone: " + weatherResponse.getTimezone() +
                            "\nTemperature: " + weatherResponse.getTemperatureHourly(time) +
                            "\nFeels Like: " + weatherResponse.getFeelsLikeHourly(time)
                    );
        } else {
            sendText(chatId, "Sorry, couldn't retrieve the weather for ZIP code: " + zipCode);
        }
    }

    // Send message
    private void sendText(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Send message with button
    private void sendTextWithButton(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Get Weather");
        button.setCallbackData("get_weather");
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);

        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Schedule weather updates
    private void scheduleNightlyWeatherUpdates() {
        long delay = calculateDelayUntilNight(9); //the target time
        long period = 24 * 60 * 60; // 24 hours in seconds

        scheduler.scheduleAtFixedRate(() -> {
            for (Long chatId : userZipCodes.keySet()) {
                String zipCode = userZipCodes.getOrDefault(chatId, "M5T2Y4");
                sendWeatherUpdate(chatId, userZipCodes.get(chatId), userReturnTime.get(chatId));
            }
        }, delay, period, TimeUnit.SECONDS);
    }

    // Helper to calculate delay until the target time (9 PM)
    private long calculateDelayUntilNight(int targetHour) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // If it's past 9 PM, schedule for the next day
        if (currentHour >= targetHour) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Set to 9 PM
        calendar.set(Calendar.HOUR_OF_DAY, targetHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long currentTime = System.currentTimeMillis();
        long targetTime = calendar.getTimeInMillis();

        return (targetTime - currentTime) / 1000; // Return delay in seconds
    }
    private void scheduleWeatherUpdateAtSelectedTime(Long chatId, int hour) {
        long delay = calculateDelayUntilNight(hour);

        scheduler.scheduleAtFixedRate(() -> {
            String zipCode = userZipCodes.getOrDefault(chatId, defaultZipCode);
            sendWeatherUpdate(chatId, userZipCodes.get(chatId), userReturnTime.get(chatId));
        }, delay, 24 * 60 * 60, TimeUnit.SECONDS); // Schedule daily updates at the selected time
    }


    private void sendTimeSelection(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Select the time you want to receive weather updates:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Create time options
        for (int hour = 18; hour <= 24; hour++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(hour + ":00 PM");
            button.setCallbackData("time_" + hour); // Store hour in callback data
            row.add(button);
            rows.add(row);
        }

        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}

//Placeholder to change the commands in BotFather
/*

start - Starts the bot
weather - Get current weather
zipcode - Set the default zipcode
returntime - Set the returning time from work
*/
