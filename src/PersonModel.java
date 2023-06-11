import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PersonModel{
    private ArrayList<Person> personList;
    private ArrayList<Car> carsList;

    public PersonModel(){
        this.personList = new ArrayList<>();
        this.carsList = new ArrayList<>();
    }

    public void addPerson(Person person){
        personList.add(person);
        if (new File("./person_info.ser").exists()){
            ArrayList<Person> prevArrayInfo = Serializer.deserializePersonInfo();
            prevArrayInfo.addAll(this.getPersonList());
            Set<Person> set = new HashSet<Person>(prevArrayInfo);
            Serializer.serializePersonInfo(new ArrayList<>(set));
        }else{
            Serializer.serializePersonInfo(this.personList);
        }

    }

    public void removePerson(Person person){
        boolean isRemoved = false;
        for (Person person2 : personList){
            if (person2.getUsername() == person.getUsername()){
                personList.remove(person);
                Serializer.serializePersonInfo(personList);
                isRemoved = true;
            }
        }
        if (!isRemoved){
            System.out.println("Person provided to remove doesn't belong in the list");
        }

    }

    public ArrayList<Car> getCars(){
        return this.carsList;
    }

    public void setCars(ArrayList<Car> cars){
        this.carsList = cars;
    }

    public void addCar(Car car){
        this.carsList.add(car);
        if (new File("./car_info.ser").exists()){
            ArrayList<Car> prevCarInfo = Serializer.deserializeCarInfo();
            prevCarInfo.addAll(this.getCars());
            Set<Car> carSet = new HashSet<Car>(prevCarInfo);
            Serializer.serializeCarInfo(new ArrayList<>(carSet));
        }else{
            Serializer.serializeCarInfo(this.carsList);
        }
    }

    public void removeCar(Car car){
        this.carsList.remove(car);
    }
    
    public ArrayList<Car> availableCars(){
        ArrayList<Car> unsoldCars = new ArrayList<>();
        for (Car car: this.getCars()){
            if (!car.getIsSold()){unsoldCars.add(car);}
        }
        return unsoldCars;
    }


    public double cotization (Car car, Buyer buyer, int months, double downPayment){
        double price = car.getPrice();
        int credit = buyer.getCreditScore();
        double interest = 17.5;
        if (credit >= 800) interest = 6.5;
        else if (credit >= 740) interest = 7.0;
        else if (credit >= 670) interest = 10.0;
        else if (credit >= 580) interest = 13.0;
        else if (credit >= 300) interest = 15.5;
        if (months > 48) interest = interest + 4;

        price = price - downPayment;
        price = (price + ((interest*0.01) * price))/months;
        return price;
    }


    public ArrayList<Person> getPersonList(){
        return this.personList;
    }

    public void setPersonList(ArrayList<Person> peopleList){
        this.personList = peopleList;
    }
    
    public ArrayList<Customer> getCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        for (Person person: personList){
            if (person instanceof Customer){
                customers.add((Customer) person);
            }
        }
        return customers;
    }


    public ArrayList<Buyer> getBuyers(){
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (Person person: personList){
            if (person instanceof Buyer){
                buyers.add((Buyer) person);
            }
        }
        return buyers;
    }

    public ArrayList<Seller> getSellers(){
        ArrayList<Seller> sellers = new ArrayList<>();
        for (Person person: personList){
            if (person instanceof Seller){
                sellers.add((Seller) person);
            }
        }
        return sellers;
    }

    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        for (Person person: personList){
            if (person instanceof Employee){
                employees.add((Employee) person);
            }
        }
        return employees;
    }

    public ArrayList<Manager> getManagers(){
        ArrayList<Manager> managers = new ArrayList<>();
        for (Person person: personList){
            if (person instanceof Manager){
                managers.add((Manager) person);
            }
        }
        return managers;
    }

    public boolean checkUsernameDuplication(Person user){
        for (Person person : personList){
            if (person.getUsername().equals(user.getUsername())) return true;;
        }
        return false;
    } 


    public boolean checkEmailDuplication(Person user){
        for (Person person : this.personList){
            if (person.getEmail().equals(user.getEmail())) return true;
        }
        return false;
    } 


    public boolean checkPhoneDuplication(Person user){
        for (Person person : personList){
            if (person.getPhoneNumber().equals(user.getPhoneNumber())) return true;
        }
        return false;
    } 

    public boolean checkDuplication(Person user){
        if ((this.checkUsernameDuplication(user) || checkEmailDuplication(user) || checkPhoneDuplication(user)) == true){
            return true;
        }
        return false;
    } 


    public void signUp (Person person){
        if (this.getPersonList().size() == 0){
            this.addPerson(person);
        }
        else if (checkUsernameDuplication(person) == true){
            throw new IllegalArgumentException("Username already taken");
        }
        else if (checkEmailDuplication(person) == true){
            throw new IllegalArgumentException("Already register with this email");
        }
        else if (checkPhoneDuplication(person) == true){
            throw new IllegalArgumentException("Already register with this Phone Number");
        }
        else{
            this.addPerson(person);
        }
    }


    public Person logIn(String username, String password){
        // System.out.println(Serializer.deserializePersonInfo().get(0).getUsername());
        // System.out.println(Serializer.deserializePersonInfo().get(0).getPassword());

        this.setPersonList(Serializer.deserializePersonInfo());
        if (new File("./car_info.ser").exists()){this.setCars(Serializer.deserializeCarInfo());}
 
        for (Person person : this.getPersonList()){
            if (person.getUsername().equals(username) && person.getPassword().equals(password))
                return person;
        }
        throw new IllegalArgumentException("Wrong username or password.");
    }
}