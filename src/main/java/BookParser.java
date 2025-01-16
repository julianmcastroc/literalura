import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BookParser {
    public List<Book> parseBooks(String jsonResponse) {
        List<Book> books = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (int i = 0; i < results.size(); i++) {
                JsonObject bookObject = results.get(i).getAsJsonObject();
                String title = bookObject.get("title").getAsString();

                List<String> authors = new ArrayList<>();
                bookObject.getAsJsonArray("authors").forEach(author ->
                        authors.add(author.getAsJsonObject().get("name").getAsString()));

                List<String> subjects = new ArrayList<>();
                bookObject.getAsJsonArray("subjects").forEach(subject ->
                        subjects.add(subject.getAsString()));

                books.add(new Book(title, authors, subjects));
            }
        } catch (Exception e) {
            System.out.println("Error al analizar los datos: " + e.getMessage());
        }
        return books;
    }
}
