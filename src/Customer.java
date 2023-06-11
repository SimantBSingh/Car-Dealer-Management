public class Customer extends Person{
    private String driverLicense;
    private int creditScore;
    private Employee agent;
    private boolean hasAgent;
    private double giveRating;

    
    public Customer(String name, int age, String street, String city, int ZIPCode, 
    String username, String password, String phoneNumber, String email,
    String driverLicense, int creditScore){

        super(name, age, street, city, ZIPCode, username, password, phoneNumber, email);
        this.driverLicense = driverLicense;
        this.creditScore = creditScore;
        this.agent = null;
        this.hasAgent = false;
        this.giveRating = 0;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }
    
    public void setCreditScore(int creditScore) {
        if (creditScore <= 900) this.creditScore = creditScore;
        else throw new IllegalArgumentException ("Credit Score not valid.");
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public Employee getAgent(){
        return this.agent;
    }

    public void setAgent(Employee agent){
        this.agent = agent;
    }

    public boolean getHasAgent(){
        return this.hasAgent;
    }

    public void setHasAgent(Boolean bool){
        this.hasAgent = bool;
    }   

    public double getGiveRating(){
        return this.giveRating;
    }

    public void setGiveRating(double giveRating){
        this.giveRating = giveRating;
    }
    
}
