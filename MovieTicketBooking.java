import java.util.ArrayList;
import java.util.Scanner;

class Movie {
    private String title;
    private int availableSeats;

    public Movie(String title, int availableSeats) {
        this.title = title;
        this.availableSeats = availableSeats;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        if (numSeats <= availableSeats) {
            availableSeats -= numSeats;
        }
    }
}

class BookingSystem {
    private ArrayList<Movie> movies;
    private ArrayList<String> bookings;
    private static final int TICKET_COST = 200;

    public BookingSystem() {
        movies = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addMovie(String title, int availableSeats) {
        movies.add(new Movie(title, availableSeats));
    }

    public void viewMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }
        System.out.println("---------------------------------------");
        System.out.println("           Available movies            ");
        System.out.println("---------------------------------------");
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println((i + 1) + ". " + movie.getTitle() + " (Available seats: " + movie.getAvailableSeats() + ")");
        }
    }

    public void bookTicket(int movieIndex, int numSeats) {
        if (movieIndex < 1 || movieIndex > movies.size()) {
            System.out.println("Invalid movie selection.");
            return;
        }

        Movie movie = movies.get(movieIndex - 1);

        if (movie.getAvailableSeats() < numSeats) {
            System.out.println("Only " + movie.getAvailableSeats() + " seats available for " + movie.getTitle() + ".");
            return;
        }

        int totalCost = numSeats * TICKET_COST;
        System.out.println("Total cost: " + totalCost + " RS.");

        if (processPayment(totalCost)) {
            movie.bookSeats(numSeats);
            bookings.add(numSeats + " seats booked for " + movie.getTitle() + " at a cost of " + totalCost + " units.");
            System.out.println(numSeats + " seats booked for " + movie.getTitle());
        } else {
            System.out.println("Payment failed. Booking not confirmed.");
        }
    }

    private boolean processPayment(int amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cash amount: ");
        int cash = scanner.nextInt();

        if (cash < amount) {
            System.out.println("Insufficient amount. Payment failed.");
            return false;
        } else if (cash == amount) {
            System.out.println("...............Payment successful..............");
            return true;
        } else {
            int balance = cash - amount;
            System.out.println("Payment successful. Balance to return: " + balance + " RS.");
            return true;
        }
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("---------------------------------------");
            System.out.println("               Bookings                ");
            System.out.print("---------------------------------------");
            System.out.println("No bookings made.");
            return;
        }
        System.out.println("---------------------------------------");
        System.out.println("               Bookings                ");
        System.out.println("---------------------------------------");
        for (String booking : bookings) {
            System.out.println(booking);
        }
    }
}

public class MovieTicketBooking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();

        bookingSystem.addMovie("MARVEL", 50);
        bookingSystem.addMovie("VENOM", 30);
        bookingSystem.addMovie("JOHN WICK", 20);

        while (true) {
            System.out.println("---------------------------------------");
            System.out.println("         MOVIE TICKET BOOKING          ");
            System.out.print("---------------------------------------");
            System.out.println("\n1. View Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookingSystem.viewMovies();
                    break;
                case 2:
                    System.out.print("Enter movie number: ");
                    int movieIndex = scanner.nextInt();
                    System.out.print("Enter number of seats: ");
                    int numSeats = scanner.nextInt();
                    bookingSystem.bookTicket(movieIndex, numSeats);
                    break;
                case 3:
                    bookingSystem.viewBookings();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}