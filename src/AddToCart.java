import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class AddToCart {
  int addToCart(int customerId, int orderId, int productId) throws IOException {
    try {
      Connection connection = DBConnection.getInstance().getConnection();
      CallableStatement statement = connection.prepareCall(
          "call spAddToCart1(?,?,?,?)");
      statement.setInt(1,customerId);
      statement.setInt(2,orderId);
      statement.setInt(3,productId);
      statement.registerOutParameter(4, Types.INTEGER);
      statement.executeQuery();
      return statement.getInt(4);


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
