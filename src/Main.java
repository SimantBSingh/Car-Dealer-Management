import java.io.Serializable;

public class Main implements Serializable {
    public static void main(String[] args){
        PersonModel personModel = new PersonModel();
        UserInterface UI = new UserInterface(personModel); 
        UI.printOptions();

        // To initiliaze the start part of the program by adding a manager.

        // Manager manager = new Manager("John Doe", 47, "Gentilly Boulevard", "New Orleans", 70122, "john", "doe", "5042807777", "managerjohn@gmail.com", 1);
        // personModel.addPerson(manager);
        // System.out.println(Serializer.deserializePersonInfo());
        
    }
}
