public class Buyer extends Customer{
    
    public Buyer (String name, int age, String street, String city, int ZIPCode, 
            String username, String password, String phoneNumber, String email,
            String driverLicense, int creditScore){
        super(name, age, street, city, ZIPCode, username, password, phoneNumber, email, driverLicense, creditScore);
        
    }

    @Override
    public String toString(){
        String result = super.toString();
        result += "Driver License: " + this.getDriverLicense() + "\n";
        result += "Credit Score: " + this.getCreditScore() + "\n";
        return result;
    }
}
