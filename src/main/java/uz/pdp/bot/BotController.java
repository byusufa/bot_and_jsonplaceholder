package uz.pdp.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static uz.pdp.bot.BotService.*;

public class BotController {
    ExecutorService executorService = Executors.newFixedThreadPool(10);


    public void start() {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                executorService.execute(() -> {
                    handleUpdate(update);
                });
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });


    }

    private void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            TgUser tgUser = getOrCreateUser(message.chat().id());
            if (message.text() != null) {
               if(message.text().equals("/start")){
                   acceptStartAskUser(tgUser);
               }
            }

        } else if (update.callbackQuery() != null) {
            CallbackQuery callbackQuery = update.callbackQuery();
            String data = callbackQuery.data();
            TgUser tgUser = getOrCreateUser(callbackQuery.from().id());
            if(tgUser.getState().equals(State.USERS)){
                acceptUserAskPost(tgUser,data);
            }
        }
    }
}
