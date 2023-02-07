import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Login {

  public static void main(String[] args) throws SQLException, IOException {
    Order order = new Order();
    Shoe s = new Shoe();
    Customer customer;
    AddToCart addToCart = new AddToCart();
    Scanner scanner = new Scanner(System.in);
    System.out.println("ENTER YOUR FIRSTNAME:");
    String firstName = scanner.nextLine();
    System.out.println("ENTER PASSWORD:");
    String password = scanner.nextLine();
    customer = ShoeRepository.getCustomerByLogin(firstName, password);


    try {
      DBConnection dbConnection = DBConnection.getInstance();
      try (Connection connection = dbConnection.getConnection();
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("select firstName,passwords from Customer")) {

        boolean isValidUser = false;
        while (resultSet.next()) {
          if (firstName.equals(resultSet.getString("firstName")) &&
              password.equals(resultSet.getString("passwords"))) {
            isValidUser = true;
            break;
          }
        }

        if (isValidUser) {
          System.out.println("Welcome" + " " + firstName);
          List<Shoe> shoeList;
          boolean exitMenu = false;
          while (!exitMenu) {
            System.out.println("Choose one option: ");
            System.out.println("1. Boots");
            System.out.println("2. Crocs");
            System.out.println("3. Sandals");
            System.out.println("4. Sneakers");
            System.out.println("5. Uggs");
            System.out.println("6. Show Order");
            System.out.println("7. Sort by brand");
            System.out.println("8. Sort by price");
            System.out.println("9. Sort by color");
            System.out.println("10. Exit");

            int choice = scanner.nextInt();
            String category = "";
            switch (choice) {
              case 1 -> category = "Boots";

              case 2 -> category = "Crocs";

              case 3 -> category = "Sandals";

              case 4 -> category = "Sneakers";

              case 5 -> category = "Uggs";

              case 6 -> {
                if (order.getShoes().size() > 0) {
                  order.getShoes().forEach(e -> System.out.println(e.toStringWithoutQuantity()));

                } else {
                  System.out.println("You have not added any shoes to your order yet.");
                }
              }

              case 7 -> {
                brandSearch(scanner);
              }

              case 8 -> {
                maxPrice(scanner);
              }

              case 9 -> {
                colorSearch(scanner);
              }
              case 10 -> {
                exitMenu = true;
                System.out.println("Thank you for shopping with us,come back soon " + firstName + "!");
              }
              default -> System.out.println("Not a valid choice!");
            }
            if (choice > 0 && choice < 7) {
              shoeList = ShoeRepository.getAllShoesByCategory(category);
              AtomicInteger b = new AtomicInteger(1);
              shoeList.forEach(ae -> {
                System.out.println(b + ". " + ae.toString());
                b.getAndIncrement();

              });
              choice = scanner.nextInt();
              if (choice - 1 < 0 || choice - 1 > shoeList.size()) {
                System.out.println("Not a valid choice! Select Shoe again!");
              } else {
                order.addShoe(shoeList.get(choice - 1));
                int orderId = addToCart.addToCart(customer.getId(), order.getId(), shoeList.get(choice - 1).getId());
                if (orderId > 0) {
                  order.setId(orderId);
                  System.out.println("You have added " + shoeList.get(choice - 1).toString() + " to your order.");
                  System.out.println("Select another shoe or exit the program.");
                }
              }
            }
          }

        } else {
          System.out.println("Wrong username or password!");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void brandSearch(Scanner scanner) throws SQLException, IOException {
    System.out.println("What brand would you like to search for?");
    String brand = scanner.next();
    List<Shoe> list = ShoeRepository.getAllShoes();
    list.stream().filter(ae -> ae.getBrand().equals(brand)).forEach(System.out::println);
  }

  private static void maxPrice(Scanner scanner) throws SQLException, IOException {
    System.out.println("What is the max price you would like to pay?");
    int maxPrice = scanner.nextInt();
    List<Shoe> list = ShoeRepository.getAllShoes();
    list.stream().filter(ae -> ae.getPrice() <= maxPrice).forEach(System.out::println);
  }

  private static void colorSearch(Scanner scanner) throws SQLException, IOException {
    System.out.println("What color would you like to search for?");
    String color = scanner.next();
    List<Shoe> shoesColor = ShoeRepository.getAllShoes();
    shoesColor.stream()
        .filter(shoe -> shoe.getColor().equals(color))
        .forEach(System.out::println);
  }
}

