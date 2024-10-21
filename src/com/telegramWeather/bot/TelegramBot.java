package com.telegramWeather.bot;

import com.telegramWeather.util.WeatherHandler;
import com.telegramWeather.weather.CityWeather;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.telegramWeather.util.ReadKeyFromFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {


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

            if (message.isCommand()) {
                if (message.getText().equals("/start")) {
                    sendTextWithButton(chatId, "Click the button below to get the weather:");
                } else if (message.getText().equals("/weather")) {
                    String zipCode = "M5T2Y4"; // You can replace this with user input later
                    CityWeather cityWeather = WeatherHandler.getWeatherByZipCode(zipCode);
                    if (cityWeather != null) {
                        sendText(chatId, cityWeather.toString());
                    } else {
                        sendText(chatId, "Sorry, couldn't retrieve the weather.");
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("get_weather")) {
                String zipCode = "M5T2Y4"; // Default ZIP code for now
                CityWeather cityWeather = WeatherHandler.getWeatherByZipCode(zipCode);
                if (cityWeather != null) {
                    sendText(chatId, cityWeather.toString());
                } else {
                    sendText(chatId, "Sorry, couldn't retrieve the weather.");
                }
            }
        }
    }


    private void sendText(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendTextWithButton(Long chatId, String text) {
        // Create the message
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();

        // Create the inline keyboard markup
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Get Weather");
        button.setCallbackData("get_weather"); // Callback data for handling button press
        row.add(button);
        rows.add(row);
        markup.setKeyboard(rows);

        // Attach the keyboard to the message
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
