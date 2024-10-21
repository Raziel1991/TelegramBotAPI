package com.telegramWeather.bot;

import com.telegramWeather.util.WeatherHandler;
import com.telegramWeather.weather.CityWeather;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.telegramWeather.util.ReadKeyFromFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        String zipCode = "M5T2Y4"; // Default ZIP code for now; can be replaced with user input later
        Message message = update.getMessage();
        long chatId = message.getChatId();

        System.out.println(chatId + " " + message.getFrom().getFirstName() + " wrote: " + message.getText());

        CityWeather cityWeather = WeatherHandler.getWeatherByZipCode(zipCode);

        if (cityWeather != null) {
            sendText(chatId, cityWeather.toString());
        } else {
            sendText(chatId, "Sorry, couldn't retrieve the weather for ZIP code: " + zipCode);
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
}
