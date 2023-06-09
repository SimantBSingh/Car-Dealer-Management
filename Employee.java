import java.util.ArrayList;

public class Employee extends Person{
    private int carsSold; 
    private ArrayList<Customer> customerList;
    private double rate;
    private ArrayList<Customer> ratedBy;
    private int id;

    public Employee(String name, int age, String street, String city, int ZIPCode, String username, String password, String phoneNumber, String email, int id){
        super(name, age, street, city, ZIPCode, username, password, phoneNumber, email);
        this.carsSold = 0;
        this.rate = 0;
        this.ratedBy = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.id = id;
    }

    public void addCustomer(Customer customer){
        this.customerList.add(customer);
    }

    public void removeCustomer(Customer customer){
        this.customerList.remove(customer);
    }

    public int getCarsSold() {
        return this.carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }

    public ArrayList<Customer> getCustomerList() {
        return this.customerList;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }
    
    public ArrayList<Customer> getRatedBy() {
        return this.ratedBy;
    }

    @Override
    public String toString () {
        String result = super.toString();
        result += "ID: " + id + "\n";
        result += "Car sold: " + carsSold + "\n";
        result += "Rate: " + rate + "\n";
        
        if (customerList.isEmpty() == false){
            result += "Customer List:\n";
            for (Customer buyer : customerList){
                result += "* " + buyer.toString();
            }
        }

        return result;
    }

}
