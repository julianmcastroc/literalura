import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final int BOOKS_PER_PAGE = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiClient apiClient = new ApiClient();
        BookParser parser = new BookParser();

        while (true) {
            System.out.println("======================================");
            System.out.println("          Bienvenido a LiterAlura     ");
            System.out.println("======================================");
            System.out.println("1. Buscar libros");
            System.out.println("2. Salir");
            System.out.print("Selecciona una opción: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    searchBooks(scanner, apiClient, parser);
                    break;
                case "2":
                    System.out.println("¡Gracias por usar LiterAlura! Hasta luego.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intenta nuevamente.");
            }
        }
    }

    private static void searchBooks(Scanner scanner, ApiClient apiClient, BookParser parser) {
        System.out.print("\nIngrese el término de búsqueda (ejemplo: ciencia, historia): ");
        String query = scanner.nextLine().trim();

        System.out.print("Ingrese el idioma (ejemplo: en, es): ");
        String language = scanner.nextLine().trim();

        System.out.println("\nBuscando libros...");
        String response = apiClient.fetchBooks(query, language);

        if (response == null) {
            System.out.println("Error: No se pudo conectar con la API. Inténtalo más tarde.");
            return;
        }

        List<Book> books = parser.parseBooks(response);

        if (books.isEmpty()) {
            System.out.println("\nNo se encontraron libros para la búsqueda: " + query);
        } else {
            displayBooks(scanner, books);
        }
    }

    private static void displayBooks(Scanner scanner, List<Book> books) {
        int totalPages = (int) Math.ceil((double) books.size() / BOOKS_PER_PAGE);
        int currentPage = 1;

        while (true) {
            System.out.println("\n=== Resultados de la Búsqueda (Página " + currentPage + " de " + totalPages + ") ===");
            int start = (currentPage - 1) * BOOKS_PER_PAGE;
            int end = Math.min(start + BOOKS_PER_PAGE, books.size());

            for (int i = start; i < end; i++) {
                Book book = books.get(i);
                System.out.println((i + 1) + ". Título: " + book.getTitle());
                System.out.println("   Autor(es): " + String.join(", ", book.getAuthors()));
                System.out.println("   Temas: " + String.join(", ", book.getSubjects()));
                System.out.println("   -----------------------------------");
            }

            System.out.println("\nAcciones:");
            System.out.println("N - Página siguiente");
            System.out.println("P - Página anterior");
            System.out.println("Q - Salir a menú principal");

            System.out.print("\nSeleccione una opción: ");
            String action = scanner.nextLine().trim().toUpperCase();

            if (action.equals("N") && currentPage < totalPages) {
                currentPage++;
            } else if (action.equals("P") && currentPage > 1) {
                currentPage--;
            } else if (action.equals("Q")) {
                break;
            } else {
                System.out.println("Opción no válida. Por favor, intenta nuevamente.");
            }
        }
    }
}


