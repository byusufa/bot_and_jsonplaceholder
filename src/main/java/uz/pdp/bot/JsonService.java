package uz.pdp.bot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import uz.pdp.DB;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JsonService {

    @SneakyThrows
    public static List<User> getUser() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().
                uri(URI.create("https://jsonplaceholder.typicode.com/users")).
                GET().
                build();
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String str = send.body();
        Gson gson = new Gson();
        List<User> users = gson.fromJson(str, new TypeToken<List<User>>() {
        }.getType());
        DB.USERS.addAll(users);
        return users;


    }

    @SneakyThrows
    public static List<Post> getPost() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().
                uri(URI.create("https://jsonplaceholder.typicode.com/posts")).
                GET().
                build();
        HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String str = send.body();
        Gson gson = new Gson();
        List<Post> posts = gson.fromJson(str, new TypeToken<List<Post>>() {
        }.getType());
        DB.POSTS.addAll(posts);
        return posts;

    }
}
