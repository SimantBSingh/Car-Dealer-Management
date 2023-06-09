import java.io.Serializable;

public class Address implements Serializable{
    private final String Country = "UNITED STATES";
    private String city;
    private String street;
    private int ZIPCode;

    public Address (String street, String city, int ZIPCode){
        this.city = city;
        this.street = street;
        this.ZIPCode = ZIPCode;

    }

    public void setCity (String city){
        this.city = city.toUpperCase().trim();
    }

    public void setStreet (String street){
        this.street = street.toUpperCase().trim();
    }

    public void setZIP (int ZIPCode) throws IllegalArgumentException{
        if (ZIPCode >= 501 && ZIPCode <=99950) this.ZIPCode = ZIPCode;
        else throw new IllegalArgumentException("ZIP Code not valid.");
    }

    public String getCity (){
        return this.city;
    }

    public String getStreet(){
        return this.street;
    }

    public int getZIPCode(){
        return this.ZIPCode;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Address){
            Address other = (Address) (object);
            boolean result = false;
            if (other.getCity().equals(this.city)){
                if (other.getStreet().equals(this.street)){
                    if (other.getZIPCode() == this.ZIPCode){
                        result = true;
                    }
                }
            }
            return result;
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %d", street, city, ZIPCode);
    }
}
