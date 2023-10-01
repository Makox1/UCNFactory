import java.util.List;
interface PurchaseReport {
    // Method to get the client with the most purchases
    Client clientWithMostPurchases(List<Client> clients);

    // Method to get the best-selling product
    Product bestSellingProduct(List<Product> products);

    // Method to get the least-selling product
    Product leastSellingProduct(List<Product> products);

    // Method to get the client who has spent the most money on purchases
    Client clientWithHighestTotalSpending(List<Client> clients);
}
