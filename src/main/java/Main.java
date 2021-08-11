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
        return "1949704232:AAG6PMnZDJORHGugljUS3GqP0EVzcl3tcUo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Model model = new Model();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Чем вам помочь?");
                    break;
                case "Хочу получить список пород":
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), Breed.getBreeds()));
//                        sendMsg(message, Breed.getBreeds());
                    } catch (IOException | TelegramApiException exception) {
                        exception.printStackTrace();
                    }
                    break;
                case "/start":
                    sendMsg(message, "Меня зовут Чат-Бот, чем я могу Вам помочь?");
                default:
                    //                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), Breed.getBreeds()));
//                        sendMsg(message, Breed.getRandomBreedDog(message.getText().toLowerCase(Locale.ROOT)));
//                    break;

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
            setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ReplyKeyboardMarkup replyKeyboardMarkup1 = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        List<KeyboardRow> keyboardRowList2 = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/start"));
        keyboardSecondRow.add(new KeyboardButton("Хочу получить список пород"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


    public static SendMessage sendInlineKeyBoardMessage(long chatId, List<String> breeds) throws IOException {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList1 = new ArrayList<>();


        for (int i = 0; i < breeds.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

            inlineKeyboardButton1.setText(breeds.get(i));
            inlineKeyboardButton1.setCallbackData(inlineKeyboardButton1.getText());
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

            keyboardButtonsRow1.add(inlineKeyboardButton1);
            rowList1.add(keyboardButtonsRow1);
        }

        inlineKeyboardMarkup.setKeyboard(rowList1);
        return new SendMessage().setChatId(chatId).
                setText("Все породы").
                setReplyMarkup(inlineKeyboardMarkup);
    }
}
