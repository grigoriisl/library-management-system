package library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        
        library.addBook(new Book(1, "Война и мир", 
            "Л.Н. Толстой", 1869, "978-5-17-090335-2"));
        library.addBook(new Book(2, "Преступление и наказание",
            "Ф.М. Достоевский", 1866, "978-5-17-090336-9"));
        library.addBook(new Book(3, "Анна Каренина",
            "Л.Н. Толстой", 1877, "978-5-17-090337-6"));
        
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ БИБЛИОТЕКИ ===\n");
        
        System.out.println("Все книги в библиотеке:");
        for (Book book : library.getAllBooks()) {
            System.out.println(book);
            System.out.println();
        }
        
        System.out.println("\nВыдача книг:");
        library.borrowBook(1);
        library.borrowBook(2);
        
        System.out.println("\nДоступные книги после выдачи:");
        for (Book book : library.getAvailableBooks()) {
            System.out.println(book);
        }
        
        System.out.println("\nВозврат книги ID 1:");
        library.returnBook(1);
        
        System.out.println("\nКниги Л.Н. Толстого:");
        for (Book book : library.findBooksByAuthor("Л.Н. Толстой")) {
            System.out.println(book);
        }
        
        System.out.println("\nЖурнал операций:");
        library.printOperationLog();
    }
    public boolean removeBook(int id) {
    Book book = findBookById(id);
    if (book != null) {
        books.remove(book);
        operationLog.addEntry(
            OperationLog.OperationType.ADD_BOOK, // или создайте новый тип
            String.format("Удалена книга: \"%s\" (ID: %d)", 
                book.getTitle(), book.getId())
        );
        return true;
    }
    return false;
    }
}