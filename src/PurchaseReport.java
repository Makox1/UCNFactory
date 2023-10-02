import java.sql.Connection;
import java.util.List;
interface PurchaseReport {
    //void clients(Connection connection);
    void products(Connection connection);

    boolean addProduct(Product product,Connection connection);
    boolean addClient(Connection connection, Client client);
    // Method to get the client with the most purchases

    boolean makeSale(Connection connection, String rut, String idProduct);
    String clientWithMostPurchases(Connection connection);
    //List<Client> clients

    // Method to get the best-selling product
    String bestSellingProduct(Connection connection);

    // Method to get the worse-selling product
    String worseSellingProduct(Connection connection);

    // Method to get the client who has spent the most money on purchases
    String clientWithHighestTotalSpending(Connection connection);
}
