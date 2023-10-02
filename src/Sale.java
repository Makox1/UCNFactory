import java.util.Date;
import java.util.List;

public class Sale {
    private int transactionID;
    private Date transactionDate;
    private Client client;
    private Product products;



    public Sale(Client client, Product products, int transactionID, Date transactionDate) {
        this.client = client;
        this.products = products;
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}

