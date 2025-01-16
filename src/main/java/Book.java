import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private List<String> subjects;

    public Book(String title, List<String> authors, List<String> subjects) {
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
