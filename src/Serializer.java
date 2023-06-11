import java.io.*;
import java.util.ArrayList;


public class Serializer {
    private static ObjectOutputStream outputPerson;
    private static ObjectInputStream inputPerson;
    
    private static ObjectOutputStream outputCar;
    private static ObjectInputStream inputCar;

    
    /**
     * Serialize and deserialize PersonModel
     * @param personModel
     */
    public static void serializePersonInfo(ArrayList<Person> person){
        try {
            outputPerson = new ObjectOutputStream(new FileOutputStream("person_info.ser"));

            outputPerson.writeObject(person);
            outputPerson.close();
        } // end of try block
        catch (IOException e) {
            System.out.println(e);
        } // end of catch block
        
    }
    
    
    public static ArrayList<Person> deserializePersonInfo(){

        ArrayList<Person> personArray = null;
        inputPerson = null;
    
        try {
            inputPerson = new ObjectInputStream(new FileInputStream("person_info.ser"));
    
            while (true){
                personArray = (ArrayList<Person>) inputPerson.readObject();
            }
        } // end of try block
        catch(ClassNotFoundException e1){
            e1.printStackTrace();
        } // end of catch block
    
        catch(IOException e2){
            try{
                inputPerson.close();
                if(e2 instanceof EOFException){ // When end of file is reached, print this message
                    
                }else{
                    e2.printStackTrace();
                }
    
            }// end of try block
            catch(IOException e3){
                e3.printStackTrace();
            } // end of catch  block
    
        }// end of catch block
        return personArray;
    }
    
    
    public static void serializeCarInfo(ArrayList<Car> car){
        try {
            outputCar = new ObjectOutputStream(new FileOutputStream("car_info.ser"));

            outputCar.writeObject(car);
            outputCar.close();
        } // end of try block
        catch (IOException e) {
            System.out.println(e);
        } // end of catch block
        
    }
    
    
    public static ArrayList<Car> deserializeCarInfo(){

        ArrayList<Car> carArray = null;
        inputCar = null;
    
        try {
            inputCar = new ObjectInputStream(new FileInputStream("car_info.ser"));
    
            while (true){
                carArray = (ArrayList<Car>) inputCar.readObject();
            }
        } // end of try block
        catch(ClassNotFoundException e1){
            e1.printStackTrace();
        } // end of catch block
    
        catch(IOException e2){
            try{
                inputCar.close();
                if(e2 instanceof EOFException){ // When end of file is reached, print this message
                    
                }else{
                    e2.printStackTrace();
                }
    
            }// end of try block
            catch(IOException e3){
                e3.printStackTrace();
            } // end of catch  block
    
        }// end of catch block
        return carArray;
    }


}
