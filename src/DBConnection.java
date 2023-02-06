import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
  private static DBConnection instance;
  private Connection connection;

  private DBConnection() throws SQLException, IOException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Get Database Connection details from settings.properties file
      Properties properties = new Properties();
      properties.load(new FileInputStream("src/Settings.properties"));
      this.connection = DriverManager.getConnection(
          properties.getProperty("connectionString"),
          properties.getProperty("name"),
          properties.getProperty("password"));
    } catch (ClassNotFoundException ex) {
      System.out.println("Database Connection Creation Failed : " + ex.getMessage());
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public static DBConnection getInstance() throws SQLException, IOException {
    if (instance == null) {
      instance = new DBConnection();
    } else if (instance.getConnection().isClosed()) {

      instance = new DBConnection();
    }

    return instance;
  }
}
