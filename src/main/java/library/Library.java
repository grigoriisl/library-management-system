package library;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private OperationLog operationLog;
    
    public static class OperationLog {
        public class LogEntry {
            private OperationType type;
            private LocalDateTime timestamp;
            private String description;
            
            public LogEntry(OperationType type, String description) {
                this.type = type;
                this.description = description;
                this.timestamp = LocalDateTime.now();
            }
            
            public OperationType getType() {
                return type;
            }
            
            public LocalDateTime getTimestamp() {
                return timestamp;
            }
            
            public String getDescription() {
                return description;
            }
            
            @Override
            public String toString() {
                return String.format("[%s] %s - %s", 
                    timestamp, type, description);
            }
        }
        
        public enum OperationType {
            ADD_BOOK("Добавление книги"),
            BORROW("Выдача книги"),
            RETURN("Возврат книги");
            
            private final String description;
            
            OperationType(String description) {
                this.description = description;
            }
            
            @Override
            public String toString() {
                return description;
            }
        }
        
        private List<LogEntry> entries;
        
        public OperationLog() {
            this.entries = new ArrayList<>();
        }
        
        public void addEntry(OperationType type, String description) {
            LogEntry entry = new LogEntry(type, description);
            entries.add(entry);
        }
        
        public List<LogEntry> getEntries() {
            return new ArrayList<>(entries);
        }
        
        public void printLog() {
            if (entries.isEmpty()) {
                System.out.println("Журнал операций пуст.");
                return;
            }
            
            System.out.println("=== ЖУРНАЛ ОПЕРАЦИЙ БИБЛИОТЕКИ ===");
            for (LogEntry entry : entries) {
                System.out.println(entry);
            }
            System.out.println("=================================");
        }
    }
    
    // Конструктор
    public Library() {
        this.books = new ArrayList<>();
        this.operationLog = new OperationLog();
    }
    
    public void addBook(Book book) {
        books.add(book);
        operationLog.addEntry(
            OperationLog.OperationType.ADD_BOOK,
            String.format("Добавлена книга: \"%s\" (ID: %d)", 
                book.getTitle(), book.getId())
        );
    }
    
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    
    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                   .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                   .collect(Collectors.toList());
    }
    
    public boolean borrowBook(int id) {
        Book book = findBookById(id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            operationLog.addEntry(
                OperationLog.OperationType.BORROW,
                String.format("Выдана книга: \"%s\" (ID: %d)", 
                    book.getTitle(), book.getId())
            );
            return true;
        }
        return false;
    }
    
    public boolean returnBook(int id) {
        Book book = findBookById(id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            operationLog.addEntry(
                OperationLog.OperationType.RETURN,
                String.format("Возвращена книга: \"%s\" (ID: %d)", 
                    book.getTitle(), book.getId())
            );
            return true;
        }
        return false;
    }
    
    public List<Book> getAvailableBooks() {
        return books.stream()
                   .filter(Book::isAvailable)
                   .collect(Collectors.toList());
    }
    
    public void printOperationLog() {
        operationLog.printLog();
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public String getStatistics() {
    int total = books.size();
    int available = (int) books.stream()
                               .filter(Book::isAvailable)
                               .count();
    int borrowed = total - available;
    
    return String.format(
        "Общая статистика:\n" +
        "Всего книг: %d\n" +
        "Доступно: %d\n" +
        "Выдано: %d",
        total, available, borrowed
    );
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