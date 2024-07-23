import java.io.Serializable;

public class Car implements Serializable{
    private Customer owner;
    private final String id;
    private String model;
    private double price;
    private int mileage;
    private int age;
    private boolean isSold;
    private Customer prevOwner;
    private Customer boughtBy;


    public Car (String model, String id, int mileage, double price, int age){
        this.owner = null;
        this.id = id;
        this.model = model.toUpperCase().trim();
        this.mileage = mileage;
        this.price = price;
        this.age = age;
        this.isSold = false;
        this.prevOwner = null;
        // this.boughtBy = null;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model.toUpperCase();
    }

    public String getId() {
        return this.id;
    }

    public int getMileage() {
        return this.mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getOwner() {
        return this.owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public boolean getIsSold() {
        return this.isSold;
    }

    public void setSold() {
        this.isSold = !this.isSold;
    }

    public int getAge() {
        return this.age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public Customer getPrevOwner() {
        return this.prevOwner;
    }

    public void setPrevOwner(Customer prevOwner) {
        this.prevOwner = prevOwner;
    }


    @Override
    public String toString() {
        String result= "";
        result += "\tModel: " + this.model + "\n";
        result += "\tMileage: " + this.mileage + "\n";
        result += "\tPrice: " + this.price + "\n";
        return result;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Car){
            Car car = (Car) object;
            if (this.model.equals(car.getModel()) &&
                this.mileage == car.getMileage() &&
                this.price == car.getPrice() &&
                this.age == car.getAge()) return true;
        }
        return false;
    }

}

