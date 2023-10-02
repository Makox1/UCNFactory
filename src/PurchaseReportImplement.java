import java.sql.*;
import java.util.List;

public class PurchaseReportImplement implements PurchaseReport {

    @Override
    public boolean addProduct(Product product, Connection connection) {
        try {
            PreparedStatement stmt = null;

            if (product instanceof Computer) {
                Computer computer = (Computer) product;

                // Insert into the Product table
                String insertProductQuery = "INSERT INTO Product (brand, model, price) VALUES (?, ?, ?)";
                stmt = connection.prepareStatement(insertProductQuery);
                stmt.setString(1, computer.getBrand());
                stmt.setString(2, computer.getModel());
                stmt.setDouble(3, computer.getPrice());

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    // Retrieve the last inserted ID using a separate query
                    int productId = getLastInsertedId(connection);

                    // Insert into the Computer table
                    String insertComputerQuery = "INSERT INTO Computer (id, cpu, ram, hdd) VALUES (?, ?, ?, ?)";
                    stmt = connection.prepareStatement(insertComputerQuery);
                    stmt.setInt(1, productId);
                    stmt.setString(2, computer.getCpu());
                    stmt.setString(3, computer.getRam());
                    stmt.setString(4, computer.getHdd());
                    stmt.executeUpdate();
                }
            }
            if (product instanceof Monitor) {
                Monitor monitor= (Monitor) product;

                // Insert into the Product table
                String insertProductQuery = "INSERT INTO Product (brand, model, price) VALUES (?, ?, ?)";
                stmt = connection.prepareStatement(insertProductQuery);
                stmt.setString(1, monitor.getBrand());
                stmt.setString(2, monitor.getModel());
                stmt.setDouble(3, monitor.getPrice());

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    // Retrieve the last inserted ID using a separate query
                    int productId = getLastInsertedId(connection);

                    // Insert into the Computer table
                    String insertComputerQuery = "INSERT INTO Monitor (id, ResolutionX, ResolutionY) VALUES (?, ?, ?)";
                    stmt = connection.prepareStatement(insertComputerQuery);
                    stmt.setInt(1, productId);
                    stmt.setInt(2, monitor.getResolutionX());
                    stmt.setInt(3, monitor.getResolutionY());
                    stmt.executeUpdate();
                }
            }if (product instanceof Mouse) {
                Mouse mouse= (Mouse) product;

                // Insert into the Product table
                String insertProductQuery = "INSERT INTO Product (brand, model, price) VALUES (?, ?, ?)";
                stmt = connection.prepareStatement(insertProductQuery);
                stmt.setString(1, mouse.getBrand());
                stmt.setString(2, mouse.getModel());
                stmt.setDouble(3, mouse.getPrice());

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    // Retrieve the last inserted ID using a separate query
                    int productId = getLastInsertedId(connection);

                    // Insert into the Computer table
                    String insertComputerQuery = "INSERT INTO Mouse (id, isWired) VALUES (?, ?)";
                    stmt = connection.prepareStatement(insertComputerQuery);
                    stmt.setInt(1, productId);
                    stmt.setBoolean(2, mouse.isWired());
                    stmt.executeUpdate();
                }
            }


            // Similar logic for other product types (Monitor and Mouse) can be added here.

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to retrieve the last inserted ID
    private int getLastInsertedId(Connection connection) throws SQLException {
        String sql = "SELECT last_insert_rowid() AS lastId";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("lastId");
            } else {
                throw new SQLException("Failed to retrieve last inserted ID.");
            }
        }
    }

    @Override
    public void products(Connection connection){
        try {
            // Realiza una consulta SQL para obtener todos los clientes
            String query = "SELECT * FROM Product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String price = resultSet.getString("price");

                String query2 = "SELECT COUNT(productId) as x FROM Sale WHERE productId = '" + id + "'";
                Statement preparedStatement2 = connection.createStatement();
                ResultSet rs = preparedStatement2.executeQuery(query2);
                if(rs.getInt(1)!=1){
                    System.out.println("ID: " + id);
                    System.out.println("Brand: " + brand);
                    System.out.println("Model: " + model);
                    System.out.println("Price: " + price);
                    System.out.println();
                }

            }
            // Cierra los recursos
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
        }


    }
    @Override
    public boolean addClient(Connection connection, Client client){
        try{

            String query = "INSERT INTO Client (rut, name, address, phoneNumber, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Establece los valores de los parámetros
            preparedStatement.setString(1, client.getRut());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setString(4, client.getPhoneNumber());
            preparedStatement.setString(5, client.getEmail());

            // Ejecuta la consulta de inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Client added successfully");
            } else {
                System.err.println("The client couldnt' be added");
                return false;
            }

            // Cierra el recurso
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Error al agregar el cliente: " + e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public boolean makeSale(Connection connection, String rut, String idProduct) {
        try {
            String query = "SELECT COUNT(rut) as x FROM Client WHERE rut = '"+rut+"'";
            Statement preparedStatement = connection.createStatement();

            ResultSet rs = preparedStatement.executeQuery(query);

        }catch (SQLException e){
            System.err.println("The Client does not exist in the database. " + e.getMessage());
        }
        try{
            String query = "SELECT COUNT(id) as x FROM Product WHERE id = '"+idProduct+"'";
            Statement preparedStatement = connection.createStatement();

            ResultSet rs = preparedStatement.executeQuery(query);

        }catch (SQLException e){
            System.err.println("The Product does not exist in the database. " + e.getMessage());
        }
        try{
            String query = "INSERT INTO Sale (clientRut,productId) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, rut);
            preparedStatement.setString(2, idProduct);


            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Sale added successfully.");
            } else {
                System.err.println("The sale couldn't be done.");
                return false;
            }

            // Cierra el recurso
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("There was an error during the sale " + e.getMessage());
            return false;

        }
        return true;
    }

    @Override
    public String clientWithMostPurchases(Connection connection) {

        try {

            String sql = "SELECT clientRut, COUNT(*) AS purchaseCount " +
                    "FROM Sales " +
                    "GROUP BY clientRut " +
                    "ORDER BY purchaseCount DESC " +
                    "LIMIT 1";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String clientRut = resultSet.getString("clientRut");
                    int maxPurchaseCount = resultSet.getInt("purchaseCount");

                    System.out.println("Client with the most purchases: " + clientRut);
                    System.out.println("Number of purchases: " + maxPurchaseCount);
                    return clientRut;
                } else {
                    System.out.println("No purchases found in the database.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }

    @Override
    // Method to get the best-selling product
    public Product bestSellingProduct(Connection connection){
        try {
            String sql = "SELECT p.brand, p.model, COUNT(s.productId) AS total_sales FROM Sale s JOIN Product p ON s.productId = p.id GROUP BY p.brand, p.model ORDER BY total_sales DESC LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String total_sales = resultSet.getString("total_sales");
                System.out.println("The best selling product is: ");
                System.out.println("Brand: "+brand);
                System.out.println("Model: "+model);
                System.out.println("Total Sales: "+total_sales);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


        @Override
    // Method to get the worse-selling product
    public Product worseSellingProduct(Connection connection){
            try {
                String sql = "SELECT p.brand, p.model, COUNT(s.productId) AS total_sales FROM Sale s JOIN Product p ON s.productId = p.id GROUP BY p.brand, p.model ORDER BY total_sales DESC LIMIT 1;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    String total_sales = resultSet.getString("total_sales");
                    System.out.println("The worse selling product is: ");
                    System.out.println("Brand: "+brand);
                    System.out.println("Model: "+model);
                    System.out.println("Total Sales: "+total_sales);
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
    }

    @Override
    // Method to get the client who has spent the most money on purchases
    public Client clientWithHighestTotalSpending(Connection connection){
        try {
            String sql = "SELECT s.clientRut, SUM(p.price) AS total_amount FROM Sale s JOIN Product p ON s.productId = p.id GROUP BY s.clientRut ORDER BY total_amount DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String rut = resultSet.getString("clientRut");
                String total_amount = resultSet.getString("total_amount");
                System.out.println("Customer Who Spent the Most: ");
                System.out.println("Rut: "+rut);
                System.out.println("Total Amount: "+total_amount);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
