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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Main {
    private static final String sessionId = UUID.randomUUID().toString();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmss"); // Change the pattern
    private static BufferedWriter logWriter;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PurchaseReportImplement impl = new PurchaseReportImplement();
        Connection connection = null;


        // Establece la URL de la base de datos SQLite (reemplaza "mydatabase.db" con el nombre de tu base de datos)
        SQLiteDBSingleton dbSingleton = SQLiteDBSingleton.getInstance();
        connection = dbSingleton.getConnection();
        System.out.println("Conexión a la base de datos SQLite establecida.");
        openLogFile();

        while (true) {
            String menu=
                    "\nMain Menu\n" +
                    "1. Register a Product\n" +
                    "2. Register a Client\n" +
                    "3. Register a Purchase\n" +
                    "4. Customer with the Most Purchases\n" +
                    "5. Best Selling Product\n" +
                    "6. Least Sold Product\n" +
                    "7. Customer Who Spent the Most\n" +
                    "8. Exit\n" +
                    "Enter your choice: ";
            System.out.print(menu);
            logToFile(menu);


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
                    String val4 =impl.clientWithMostPurchases(connection);
                    System.out.println(val4);
                    logToFile(val4);
                    break;
                case 5:
                    System.out.println("You Chose 5");
                    String val5= impl.bestSellingProduct(connection);
                    System.out.println(val5);
                    logToFile(val5);
                    break;
                case 6:
                    System.out.println("You Chose 6");
                    String val6= impl.worseSellingProduct(connection);
                    System.out.println(val6);
                    logToFile(val6);
                    break;
                case 7:
                    System.out.println("You Chose 7");
                    String val7= impl.clientWithHighestTotalSpending(connection);
                    System.out.println(val7);
                    logToFile(val7);
                    break;
                case 8:

                    try {
                        System.out.println("Conexión cerrada.");
                        connection.close();
                        logToFile("Existing the system.");
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





    private static void openLogFile() {
        String currentTime = dateFormat.format(new Date());
        String logFileName = "transaction" + currentTime + ".log";

        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName, true));
        } catch (IOException e) {
            System.err.println("Error opening log file: " + e.getMessage());
        }
    }

    private static void logToFile(String message) {
        if (logWriter != null) {
            String currentTime = dateFormat.format(new Date());
            String logMessage = "[" + currentTime + "] " + message;

            try {
                logWriter.write(logMessage);
                logWriter.newLine();
                logWriter.flush(); // Ensure that the message is written immediately
            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        }
    }

    private static void closeLogFile() {
        try {
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing log file: " + e.getMessage());
        }
    }

}

