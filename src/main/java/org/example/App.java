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
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();

        try {
            // Send HTTP request and wait for a response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON into objects
            ObjectMapper mapper = new ObjectMapper();
            List<Post> posts = mapper.readValue(response.body(), new TypeReference<>(){});

            // Output the post id and title
            posts.forEach(System.out::println);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
