import java.util.List;

public class Customer {
  protected int id;
  protected String firstName;
  protected String lastName;
  protected String personNumber;
  protected String telefonNumber;
  protected String postCode;
  protected String city;
  protected String street;
  protected String passwords;

  public Customer(int id, String firstName, String lastName, String personNumber,
                  String telefonNumber, String postCode, String city, String street,String passwords) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.personNumber = personNumber;
    this.telefonNumber = telefonNumber;
    this.postCode = postCode;
    this.city = city;
    this.street = street;
    this.passwords = passwords;
  }

  public Customer(int id, String firstName, String lastName){
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  public Customer(){}

  public Customer(String firstName, String passwords){
    this.firstName = firstName;
    this.passwords = passwords;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPersonNumber() {
    return personNumber;
  }

  public void setPersonNumber(String personNumber) {
    this.personNumber = personNumber;
  }

  public String getTelefonNumber() {
    return telefonNumber;
  }

  public void setTelefonNumber(String telefonNumber) {
    this.telefonNumber = telefonNumber;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPasswords() {
    return passwords;
  }

  public void setPasswords(String passwords) {
    this.passwords = passwords;
  }
}
