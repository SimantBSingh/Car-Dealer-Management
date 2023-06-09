import java.io.Serializable;
import java.util.regex.Pattern;

public abstract class Person implements Serializable{
    private String name;
    private int age;
    private Address address; 
    private String username;
    private String password;
    private String phoneNumber;
    private String email;


    public Person (String name, int age, String street, String city, int ZIPCode, String username, String password, String phoneNumber, String email){
        this.name = name;
        this.age = age;
        this.address = new Address(street, city, ZIPCode);
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    

    public void setName (String name){
        this.name = name;
    }

    public void setAge (int age) throws IllegalArgumentException{
        if (age >= 18) this.age = age;
        else throw new IllegalArgumentException("Age should be over 18.");
    }

    public void setAddress (String street, String city, int ZIPCode) throws IllegalArgumentException{
        this.address = new Address(street, city, ZIPCode);
    }

    public void setUsername (String username) throws IllegalArgumentException {
        username = username.trim();
        if (username.contains(" ") == false) this.username = username;
        else throw new IllegalArgumentException("Username contains unvalid characters.");
    }

    public void setPassword (String password) throws IllegalArgumentException {
        password = password.trim();
        if (password.contains(" ") == false) this.password = password;
        else throw new IllegalArgumentException("Username contains unvalid characters.");
    }
    
    public void setPhoneNumber (String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber.length() == 10){
            this.phoneNumber = phoneNumber;
        }
        else {
            throw new IllegalArgumentException("Phone number not valid");
        }
    }
    
    public void setEmail (String email) throws IllegalArgumentException {
        email = email.toLowerCase().trim();
        if (Pattern.matches(".*@.*\\.com$", email))
            this.email = email;
        else throw new IllegalArgumentException("Email no valid.");
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public Address getAddress(){
        return address;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public String getEmail(){
        return email;
    }

    
    @Override
    public boolean equals(Object other){
        if (other instanceof Person){
            Person person = (Person) (other);
            if (person.getName().equals(this.name) &&
                person.getAddress().equals(this.address) &&
                person.getUsername().equals(this.username) &&
                person.getPassword().equals(this.password) &&
                person.getEmail().equals(this.email) &&
                person.getAge() == this.age &&
                person.getPhoneNumber() == this.phoneNumber)
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String result= "";
        result += "Name: " + this.name + "\n";
        result += "Age: " + age + "\n";
        result += "Address: " + this.address+ "\n";
        result += "Phone Number: " + phoneNumber + "\n";
        result += "Email: " + email + "\n";
        return result;
    }

}
