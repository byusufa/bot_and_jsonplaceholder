package uz.pdp;

import uz.pdp.bot.Post;
import uz.pdp.bot.TgUser;
import uz.pdp.bot.User;

import java.util.ArrayList;
import java.util.List;

public interface DB {


    List<TgUser> TG_USERS = new ArrayList<>();
    List<User> USERS = new ArrayList<>();
    List<Post> POSTS = new ArrayList<>();
}
