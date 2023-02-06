import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
  List<Customer> getCustomer() throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("name"),
        properties.getProperty("password"),
        properties.getProperty("connectionString"));
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select id, firstName,lastName, personNumber," +
             "telefonNumber,postCode, city,street,passwords from Customer")
    ) {

      List<Customer> customer = new ArrayList<>();

      while (resultSet.next()) {
        Customer temp = new Customer(resultSet.getInt("id"), resultSet.getString("firstName"), resultSet.getString("lastName"),
            resultSet.getString("personNumber"), resultSet.getString("telefonNumber"), resultSet.getString("postCode"),
            resultSet.getString("city"), resultSet.getString("street"), resultSet.getString("passwords"));
        customer.add(temp);
      }
      return customer;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  List<Customer> getCustomerByName(String firstName) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("connectionString"),
        properties.getProperty("name"),
        properties.getProperty("password"));

         PreparedStatement statement = connection.prepareStatement(
             "SELECT id, firstName,lastName, personNumber,telefonNumber,postCode, " +
                 "city,street,passwords FROM Customer WHERE firstName = ?")

    ) {

      statement.setString(1, firstName);
      ResultSet resultSet = statement.executeQuery();
      List<Customer> customer = new ArrayList<>();

      while (resultSet.next()) {
        Customer temp = new Customer(resultSet.getInt("id"), resultSet.getString("firstName"),
            resultSet.getString("lastName"),
            resultSet.getString("personNumber"), resultSet.getString("telefonNumber"),
            resultSet.getString("postCode"),
            resultSet.getString("city"), resultSet.getString("street"),
            resultSet.getString("passwords"));
        customer.add(temp);
      }
      return customer;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  void insertCustomer(int id, String firstName, String lastName, String personNumber, String telefonNumber, String postCode,
                      String city, String street, String passwords) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("connectionString"),
        properties.getProperty("user"),
        properties.getProperty("password"));

         PreparedStatement statement = connection.prepareStatement(
             "INSERT INTO Customer(id, firstName, lastName, personNumber,telefonNumber, " +
                 "postCode, city,street,passwords) VALUES(?,?,?,?,?,?,?,?,?)")

    ) {

      statement.setInt(1, id);
      statement.setString(2, firstName);
      statement.setString(3, lastName);
      statement.setString(4, personNumber);
      statement.setString(5, telefonNumber);
      statement.setString(6, postCode);
      statement.setString(7, city);
      statement.setString(8, street);
      statement.setString(9, passwords);


      int res = statement.executeUpdate();
      System.out.println(res + "antalet rader uppdaterades");


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  void addToCart(int customerId, int orderId, int productId) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("connectionString"),
        properties.getProperty("user"),
        properties.getProperty("password"));

         CallableStatement statement = connection.prepareCall(
             "call AddToCart(?,?,?)")

    ) {


      statement.setInt(1, customerId);
      statement.setInt(2, orderId);
      statement.setInt(3, productId);
      statement.executeQuery();


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  void addToCartWithOutParameter(int customerId, int orderId, int productId) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("connectionString"),
        properties.getProperty("user"),
        properties.getProperty("password"));

         CallableStatement statement = connection.prepareCall(
             "call AddToCart(?,?,?,?)")

    ) {


      statement.setInt(1, customerId);
      statement.setInt(2, orderId);
      statement.setInt(3, productId);
      statement.registerOutParameter(4, Types.VARCHAR);
      statement.executeQuery();

      System.out.println(statement.getString(4));


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  void addToCartWithSelect(int customerId, int orderId, int productId) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("src/settings.properties"));

    try (Connection connection = DriverManager.getConnection(
        properties.getProperty("connectionString"),
        properties.getProperty("user"),
        properties.getProperty("password"));

         CallableStatement statement = connection.prepareCall(
             "call AddToCart(?,?,?,?)")

    ) {


      statement.setInt(1, customerId);
      statement.setInt(2, orderId);
      statement.setInt(3, productId);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        System.out.println(rs.getString("brand"));
      }
    } catch (SQLException e) {

      System.out.println(e.getMessage());
      System.out.println(e.getErrorCode());
      //throw new RuntimeException(e);
    }
  }
}
