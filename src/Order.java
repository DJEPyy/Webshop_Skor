import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

  private int id;
  private int customerId;
  private Date date;
  private List<Shoe> shoes = new ArrayList<>();


  public Order(int id, int customerId, Date date) {
    this.id = id;
    this.customerId = customerId;
    this.date = date;
  }

  public Order(){}


  public List<Shoe> getShoes() {
    return shoes;
  }

  public void setShoes(List<Shoe> shoes) {
    this.shoes = shoes;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void addShoe(Shoe shoe){
    shoes.add(shoe);
  }
}
