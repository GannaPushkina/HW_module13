package mytry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtilities {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User postUser(URI uri, User user) throws IOException, InterruptedException {
        String userString = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(userString))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status of postUser: " + response.statusCode());
        return GSON.fromJson(response.body(), User.class);
    }


    public static User putUser(URI uri, User user) throws IOException, InterruptedException {
        String userString = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(userString))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status of putUser: " + response.statusCode());
        return GSON.fromJson(response.body(), User.class);
    }

    public static String deleteUser(URI uri, User user) throws IOException, InterruptedException {
        String userString = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(userString))
                .build();
        return "Status of deleteUser: " + CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).statusCode();
    }

    public static List<User> printListOfUsers(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    public static User getUserById(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response);

        return GSON.fromJson(response.body(), User.class);
    }

    private static int getMaxPostID(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + userId + "/posts"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Post> posts = GSON.fromJson(response.body(), new TypeToken<List<Post>>() {
        }.getType());
        return posts.stream()
                .map(Post::getId)
                .max(Integer::compare)
                .get();
    }

    private static void writeCommentsToJson(List<Comment> comments, int userId, int maxPostId) {
        try (PrintWriter out = new PrintWriter(new FileWriter("user-" + userId + "-post-" + maxPostId + "-comments.json"))) {
            out.write(GSON.toJson(comments));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getUserComments(int userId) throws IOException, InterruptedException {
        int maxPostId = getMaxPostID(userId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + maxPostId + "/comments"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Comment> comments = GSON.fromJson(response.body(), new TypeToken<List<Comment>>() {
        }.getType());
        writeCommentsToJson(comments, userId, maxPostId);
    }

    public static List<Todo> getUserTodos(URI uri, int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + userId + "/todos?completed=false"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<Todo>>() {
        }.getType());
    }


}
