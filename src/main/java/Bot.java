import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot {
    public static void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Хочу получить список пород"));
        keyboardRowList.add(keyboardFirstRow);
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
                setText("Все доступные породы").
                setReplyMarkup(inlineKeyboardMarkup);
    }
}
