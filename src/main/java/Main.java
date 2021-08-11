import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Main());
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "DogBreedImageBot";
    }

    @Override
    public String getBotToken() {
        return "1949704232:AAEiHBRoLuKtlnq-wNpFdZHjZqn9PgTyhj0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "Хочу получить список пород":
                    try {
                        execute(Bot.sendInlineKeyBoardMessage(update.getMessage().getChatId(), Breed.getBreeds()));
                    } catch (IOException | TelegramApiException exception) {
                        exception.printStackTrace();
                    }
                    break;
                default:
                    sendMsg(message, "Собаки такой породы не найдено!");
                    break;

            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new SendMessage()
                        .setText(Breed.getRandomBreedDog(update.getCallbackQuery().getData()))
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException | IOException exception) {
                exception.printStackTrace();
            }

        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            Bot.setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }
}
