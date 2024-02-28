import java.sql.*;
import java.util.Scanner;

public class EventManagementSystem {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/event_management?characterEncoding=utf8&useSSL=false&useUnicode=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Establish the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_management?characterEncoding=utf8&useSSL=false&useUnicode=true","root","");
            System.out.println("Connected to the database.");

            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Register\n2. Login");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("Enter username:");
                String username = scanner.next();
                System.out.println("Enter password:");
                String password = scanner.next();
                registerUser(connection, username, password);
            } else if (choice == 2) {
                System.out.println("Enter username:");
                String username = scanner.next();
                System.out.println("Enter password:");
                String password = scanner.next();
                boolean loginStatus = loginUser(connection, username, password);
                if (loginStatus) {
                    bookEvent(connection);
                } else {
                    System.out.println("Invalid username or password.");
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void registerUser(Connection connection, String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        System.out.println("User registered successfully.");
    }

    private static boolean loginUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static void bookEvent(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter event name:");
        String eventName = scanner.nextLine();
       // System.out.println("Enter event date:");
        //String eventDate = scanner.nextLine();
        System.out.println("Enter event location:");
        String eventLocation = scanner.nextLine();
        System.out.println("Enter number of tickets:");
        int tickets = scanner.nextInt();

        String query = "INSERT INTO events (eventName, eventLocation, eventCapacity, eventBooked) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, eventName);
        //preparedStatement.setString(2, eventDate);
        preparedStatement.setString(2, eventLocation);
        preparedStatement.setInt(3, tickets);
        preparedStatement.setInt(4, 0); // Initially no tickets are booked
        preparedStatement.executeUpdate();
        System.out.println("Event booked successfully.");
    }
}
