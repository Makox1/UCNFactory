import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PurchaseReportImplement impl = new PurchaseReportImplement();
        Connection connection = null;


        // Establece la URL de la base de datos SQLite (reemplaza "mydatabase.db" con el nombre de tu base de datos)
        SQLiteDBSingleton dbSingleton = SQLiteDBSingleton.getInstance();
        connection = dbSingleton.getConnection();
        System.out.println("Conexión a la base de datos SQLite establecida.");


        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. Register a Product");
            System.out.println("2. Register a Client");
            System.out.println("3. Register a Purchase");
            System.out.println("4. Customer with the Most Purchases");
            System.out.println("5. Best Selling Product");
            System.out.println("6. Least Sold Product");
            System.out.println("7. Customer Who Spent the Most");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    Product product = null;
                    System.out.println("You chose 1");
                    logToFile("User chose 1");
                    System.out.println("¿Qué tipo de producto quiere ingresar?\n" +
                                        "1) Computador\n" +
                                        "2) Monitor\n" +
                                        "3) Mouse\n" +
                                        "Ingrese 1, 2 o 3: "
                    );

                    int option= scanner.nextInt();
                    while (option != 1 && option != 2 && option != 3 ){
                        System.out.println("Error, intente nuevamente\n" +
                                "¿Qué tipo de producto quiere ingresar?\n" +
                                "1) Computador\n" +
                                "2) Monitor\n" +
                                "3) Mouse\n" +
                                "Ingrese 1, 2 o 3: "
                        );
                        option= scanner.nextInt();
                    }
                    System.out.print("Brand: ");
                    scanner.nextLine();
                    String brand = scanner.nextLine();

                    System.out.print("Model: ");
                    String model = scanner.nextLine();

                    System.out.print("Price: ");
                    int price = scanner.nextInt();

                    if(option == 1){
                        System.out.print("CPU: ");
                        scanner.nextLine();
                        String cpu = scanner.nextLine();

                        System.out.print("RAM: ");
                        String ram = scanner.nextLine();

                        System.out.print("HDD: ");
                        String hdd = scanner.nextLine();
                        Computer computer= new Computer(brand,model,price,cpu,ram,hdd);
                        impl.addProduct(computer,connection);
                    }
                    if(option == 2){
                        System.out.print("Resolution X: ");
                        int resX = scanner.nextInt();

                        System.out.print("Resolution Y: ");
                        int resY = scanner.nextInt();
                        Monitor monitor= new Monitor(brand,model,price,resX,resY);
                        impl.addProduct(monitor,connection);
                    }
                    if(option == 3){
                        boolean isWired = false;

                        while (true) {
                            System.out.print("¿Es inalámbrico? (si/sí/no): ");
                            String wireOption = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison

                            if (wireOption.equals("si") || wireOption.equals("sí")) {
                                isWired = true;
                                break;// Exit the loop if "si" or "sí" is entered
                            } else if (wireOption.equals("no")) {
                                isWired = false;
                                break;// Exit the loop if "no" is entered
                            } else {
                                System.out.println("Entrada no válida. Por favor, ingrese 'si', 'sí' o 'no'.");
                            }
                        }
                        Mouse mouse = new Mouse(brand,model,price,isWired);
                        impl.addProduct(mouse,connection);
                    }



                    //impl.products(connection);

                    break;
                case 2:
                    System.out.println("You chose 2");
                    logToFile("User chose 2");

                    System.out.print("Rut: ");
                    String rut = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("PhoneNumber: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    Client client = new Client(rut, name, address, phoneNumber, email);
                    impl.addClient(connection,client);
                    break;
                case 3:
                    System.out.println("You chose 3");
                    logToFile("User chose 3");
                    impl.products(connection);
                    System.out.print("Client rut: ");
                    String rut2 = scanner.nextLine();
                    System.out.print("ID Product: ");
                    String product2 = scanner.nextLine();

                    impl.makeSale(connection,rut2,product2);
                    break;
                case 4:
                    System.out.println("You Chose 4");
                    impl.clientWithMostPurchases(connection);
                    break;
                case 5:

                case 8:

                    try {
                        System.out.println("Conexión cerrada.");
                        connection.close();
                        System.exit(0);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    logToFile("Invalid choice: " + choice);
                    System.exit(0);
                    break;
            }
        }
    }

    private static void logToFile(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String currentTime = dateFormat.format(new Date());

        String logFileName = "transaction_" + currentTime + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            String logMessage = "[" + currentTime + "] " + message;
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}

