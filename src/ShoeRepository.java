import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoeRepository {
  public static List<Shoe> getAllShoes() throws SQLException, IOException {
    List<Shoe> shoes = new ArrayList<>();
    DBConnection dbConnection = DBConnection.getInstance();
    try (Connection connection = dbConnection.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select id,size,brand,color,price,quantity from WebShop.Shoes")

    ) {
      while (resultSet.next()) {
        Shoe temp = new Shoe();
        int id = resultSet.getInt("id");
        temp.setId(id);

        String size = resultSet.getString("size");
        temp.setSize(size);

        String brand = resultSet.getString("brand");
        temp.setBrand(brand);

        String color = resultSet.getString("color");
        temp.setColor(color);

        int price = resultSet.getInt("price");
        temp.setPrice(price);

        int quantity = resultSet.getInt("quantity");
        temp.setQuantity(quantity);

        shoes.add(temp);
      }
    }
    return shoes;
  }
  public static List<Shoe> getAllShoesByCategory(String category) throws SQLException, IOException {
    List<Shoe> shoes = new ArrayList<>();
    String query = "select Shoes.id, Shoes.size, Shoes.brand, Shoes.color, Shoes.price, Shoes.quantity from Shoes " +
        "inner join categoryMapping on Shoes.id = categoryMapping.shoeId " +
        "inner join shoeCategory on categoryMapping.categoryId = shoeCategory.id" +
        " where shoeCategory.typeOfShoe = ?";
    DBConnection dbConnection = DBConnection.getInstance();
    try (Connection connection = dbConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(query))
    {
      ps.setString(1, category);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        shoes.add(new Shoe(rs.getInt("id"),rs.getString("size"),
            rs.getString("brand"), rs.getString("color"),
            rs.getInt("price"), rs.getInt("quantity")));
      }
    }
    return shoes;
  }

  public static Customer getCustomerByLogin(String name, String password) throws SQLException, IOException {
    String query = "select Customer.id, Customer.firstName, Customer.lastName " +
        "from Customer where Customer.firstName = ?" +
        " and Customer.passwords = ?";
    DBConnection dbConnection = DBConnection.getInstance();
    try (Connection connection = dbConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1,name);
      ps.setString(2, password);
      ResultSet rs = ps.executeQuery();
      if(rs.next()){
        return new Customer(rs.getInt("id"), rs.getString("firstName"),
            rs.getString("lastName"));
      }
    }
    return null;
  }
}
