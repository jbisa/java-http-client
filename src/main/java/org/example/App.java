package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class App {
    private static final String POSTS_API_URL = "https://jsonplaceholder.typicode.com/posts";

    public static void main( String[] args ) {
        // Build the client and request objects
        HttpClient client = HttpClient.newHttpClient();

        try {
            // HTTP GET request -> Fetch /posts/1
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(POSTS_API_URL + "/1"))
                    .build();

            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            // Parse JSON into objects
            ObjectMapper mapper = new ObjectMapper();
            //List<Post> posts = mapper.readValue(response.body(), new TypeReference<>(){});
            Post post = mapper.readValue(response.body(), new TypeReference<>(){});

            // Output the post id and title
            //posts.forEach(System.out::println);
            System.out.println(post);

            // HTTP POST request -> Add /posts/12
            Post newPost = new Post(12, 1, "Foo Message", "Here is some message...");
            String requestPayload = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(newPost);

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestPayload))
                    .header("Content-Type", "application/json")
                    .uri(URI.create(POSTS_API_URL))
                    .build();

            response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
