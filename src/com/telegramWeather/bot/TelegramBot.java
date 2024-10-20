package com.telegramWeather.bot;

import com.telegramWeather.weather.CityWeather;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.telegramWeather.util.ReadKeyFromFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    CityWeather cityWeather;

    public TelegramBot(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
    }

    @Override
    public String getBotUsername() {
        return "BotPrepareForWeatherBot";
    }

    @Override
    public String getBotToken() {
        // Provide the path to your token file here
        String tokenFilePath = "C:\\Users\\hampt\\IdeaProjects\\botWeatherKey.txt";
        return ReadKeyFromFile.getKeyFromFile(tokenFilePath);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Handle updates from Telegram here
        System.out.println(update);
        Message message = update.getMessage();
        String messageText = message.getText();
        String userName = message.getFrom().getFirstName() + " " + message.getFrom().getLastName();
        long id = message.getChatId();
        System.out.println(id + " "  + userName + " wrote!: " + messageText);

        sendText(id, cityWeather.toString());
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
