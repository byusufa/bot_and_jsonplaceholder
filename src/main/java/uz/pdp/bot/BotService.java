package uz.pdp.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.DB;

import java.util.List;
import java.util.Optional;

import static uz.pdp.bot.JsonService.getPost;
import static uz.pdp.bot.JsonService.getUser;

public class BotService {
    public static TelegramBot telegramBot = new TelegramBot("7222362176:AAEg43tz0Jv-Nt0N6lO4moAL16vxCF2ZZJw");


    public static TgUser getOrCreateUser(Long chatId) {
        Optional<TgUser> first = DB.TG_USERS.stream().filter(tgUser -> tgUser.getChatId().equals(chatId)).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            TgUser tgUser = new TgUser();
            tgUser.setChatId(chatId);
            DB.TG_USERS.add(tgUser);
            return tgUser;
        }
    }

    public static void acceptStartAskUser(TgUser tgUser) {
        List<User> USERS = getUser();
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), "USERS: ");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        USERS.forEach(user ->
                inlineKeyboardMarkup.addRow(
                        new InlineKeyboardButton(user.getName()).callbackData(String.valueOf(user.getId())),
                        new InlineKeyboardButton("posts").callbackData(String.valueOf(user.getId()))
                )
        );
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
        tgUser.setState(State.USERS);

    }

    public static void acceptUserAskPost(TgUser tgUser, String data) {
        List<Post> POSTS = getPost();
        List<User> users = getUser();
        User selectedUser = users.stream().filter(user -> user.getId().equals(Integer.parseInt(data))).findFirst().get();
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), "POSTS of %s: ".formatted(selectedUser.getName()));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        POSTS.stream().filter(post -> post.getUserId().equals(Integer.parseInt(data))).
                forEach(post ->
                        inlineKeyboardMarkup.addRow(

                                new InlineKeyboardButton(post.getTitle()).callbackData(String.valueOf(post.getId())),
                                new InlineKeyboardButton("body").callbackData(String.valueOf(post.getId()))

                        ));
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
        tgUser.setState(State.POSTS);
    }

    public static void acceptPostAskBody(TgUser tgUser, String data) {
        List<Post> POSTS = getPost();
        SendMessage sendMessage = new SendMessage(tgUser.getChatId(), "BODY");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        POSTS.stream().filter(post -> post.getUserId().equals((Integer.parseInt(data))))
                .forEach(post ->
                        inlineKeyboardMarkup.addRow(
                                new InlineKeyboardButton(post.getBody()).callbackData(String.valueOf(post.getId())))
                );
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        telegramBot.execute(sendMessage);
        tgUser.setState(State.BODIES);
    }


}
