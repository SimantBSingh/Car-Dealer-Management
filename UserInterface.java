import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private PersonModel personModel;
    private Buyer prevBuyerLoggedIn;
    private Seller prevSellerLoggedIn;


    public UserInterface(PersonModel pm){
        this.personModel = pm;
        this.prevBuyerLoggedIn = null;
        this.prevSellerLoggedIn = null;
    }

    public void printOptions(){
        System.out.println("Type in the correct option number to sign up\n");
        
        System.out.println("1. LOG-IN");
        System.out.println("2. SIGN UP / CREATE AN ACCOUNT");
        
        Scanner input = new Scanner(System.in);
        int optionNumber = input.nextInt();

        if (optionNumber == 1){
            this.LoggingIn();
        }
        else if (optionNumber == 2){
            String[] info = this.initiliazeStringVars();
            String driverLicense = this.initializeDriverLicense();
            int creditScore = this.intializeCreditScore();
            
            System.out.print("\nType in B if you are a buyer and S for a seller: ");
            String personType = input.next();
            
            if (personType.equals("B")){
                Buyer buyer = new Buyer(info[0], Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]), info[5], info[6], info[7], info[8], driverLicense, creditScore);
                this.personModel.signUp(buyer);
            } else if (personType.equals("S")){
                Seller seller = new Seller(info[0], Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]), info[5], info[6], info[7], info[8], driverLicense, creditScore);
                this.personModel.signUp(seller);
            } else{ throw new IllegalArgumentException("Wrong option chosen."); }
            this.LoggingIn();

        }
        else{
            System.out.println("Wrong option number\n");
        }
    }

    public String initializeDriverLicense(){
        Scanner input = new Scanner(System.in);
        String driverLicense;
        try {
            System.out.print("Enter your Driver License ID: "); 
            driverLicense = input.nextLine();


        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("WRONG INPUTS PROVIDED.");
        }
        return driverLicense;
    }
    public int intializeCreditScore(){
        Scanner input = new Scanner(System.in);
        int creditScore;
        try {
            System.out.print("Enter your Credit Score: "); 
            creditScore = input.nextInt();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("WRONG INPUTS PROVIDED.");
        }
        return creditScore;
    }


    public void LoggingIn(){
        System.out.println("Enter your username and password");
        System.out.print("Username:");

        Scanner input = new Scanner(System.in);
        String username = input.nextLine();
        
        System.out.print("Password:");
        String password = input.nextLine();

        Person user = this.personModel.logIn(username, password);

        for (Person person: this.personModel.getPersonList()){
            if (person.equals(user)){
                if (person instanceof Buyer){
                    System.out.println("\nYou have been registered as a Buyer\n");
                    Buyer currentUser = (Buyer) person;
                    buyerOptions(currentUser);
                    break;
                } else if (person instanceof Seller){
                    System.out.println("\nYou have been registered as a Seller\n");
                    Seller currentUser = (Seller) person;
                    sellerOptions(currentUser);
                    break;
                } else if (person instanceof Manager){
                    System.out.println("\nYou have been registered as a Manager");
                    Manager currentUser = (Manager) person;
                    managerOptions(currentUser);
                    break;
                } else if (person instanceof Employee){
                    System.out.println("\nYou have been registered as a Employee\n");
                    Employee currentUser = (Employee) person;
                    employeeOptions(currentUser);
                    break;
                }
            }
        }
        Serializer.serializePersonInfo(this.personModel.getPersonList());
        Serializer.serializeCarInfo(this.personModel.getCars());

    }

    public void BasicOptions(){
        System.out.println("\n\nType the correct option number");
        System.out.println("1.Edit Name\n2.Edit Age\n3.Edit Username\n4.Edit Password\n5.Edit Email\n6.Edit PhoneNumber\n7.Edit Address\n8.See my information\n9.See Cars available");
    }

    public void customerOptions(){
        System.out.println("10.Check if you have a agent\n11.Get an Agent\n12.Get in contact with my agent\n13.Rate your agent");
    }

    public void buyerOptions(Buyer currentUser){
        boolean exit = false;
        while(!exit){
            this.BasicOptions();
            this.customerOptions();
            System.out.println("14. Buy a Car\n15. Print the Cars bought\n16. Exit\n");
            
            Scanner input = new Scanner(System.in);
            int optionNumber = input.nextInt();
    
            if (optionNumber >= 1 && optionNumber <= 9){
                this.basicOperations(optionNumber, (Person) currentUser);
            } else if (optionNumber >= 10 && optionNumber <=13){ 
                this.customerOperations(optionNumber, (Customer) currentUser);
            } else if (optionNumber == 14){
                if (currentUser.getHasAgent() == false){ throw new Error("FIND AN AGENT FIRST"); }
                else{ 
                    if (this.personModel.availableCars().isEmpty()){System.out.println("Sorry, but we don't have any cars available right now. Try again later.");}
                    else{
                        System.out.println("You are automatically Logged Out and your agent will log-in and carry out the process from there.");
                        this.prevBuyerLoggedIn = currentUser;
                        this.LoggingIn();
                    }
                }
            } else if (optionNumber == 15){
                for (Car car : this.personModel.getCars()){
                    if (car.getIsSold() == true && car.getOwner().getUsername().equals(currentUser.getUsername())){
                        System.out.println(car);
                    }
                }
            }
            else if (optionNumber == 16){exit=true;}
            else{ System.out.println("Wrong option Number choosen"); }
        }
    }

    public void buyCar(Employee currentUser){
        Scanner input = new Scanner(System.in);
        System.out.println("These are the Cars we have: ");
            if (this.personModel.availableCars().isEmpty()){System.out.println("No Cars to show");}
            else if (this.prevBuyerLoggedIn == null){System.out.println("No Request from the Previous Buyer to buy a car OR no previous Log-in from a Buyer");}
            else{
                this.printAvailableCars();
                System.out.print("\nType the option number of a car you like or else type any other number to exit: ");
                int optionNumber = input.nextInt();
                Car purchasedCar = this.personModel.availableCars().get(optionNumber);
                boolean bought = false;

                if (optionNumber >= 0 && optionNumber < this.personModel.getCars().size()){
                    System.out.printf("Thank you for choosing this car. I, %s am your agent and will carry out the necessary steps from here on and provide you all the required information\n", currentUser.getName());
                    System.out.println("I am going to ask you some questions regarding the purchase of the car.");
                    System.out.print("Press Y if you want to pay the price of the Car in full or else Press N to enter a Down Payment: ");
                    String inputPurchaseType = input.next();
                    if (inputPurchaseType.equals("Y")){
                        System.out.println("Thank you for purchasing the Car. We will carry out the rest of the process. Even though you bought a car, you will still have a Buyer Account. You can do all the activities as a buyer again without creating a new account. If you want to change your status from Buyer, simply log-in to your account and choose the option to change it from Buyer.");
                        bought = true;
                    } else if (inputPurchaseType.equals("N")){
                        try{
                            System.out.print("\nWhat is the down payment you would like to pay now. Simply type down the amount in dollars. Make sure there are no symbols present: ");
                            int downPayment = input.nextInt();
                            System.out.print("\nSpanning how many months would you like to pay for the Car in full: ");
                            int months = input.nextInt();

                            System.out.printf("\nYou would have to pay $%f for %d months for the Car\n", this.personModel.cotization(purchasedCar, this.prevBuyerLoggedIn, months, downPayment), months);
                            bought = true;

                        } catch (InputMismatchException exception){
                            System.out.println("\nWRONG INPUT/S WRITTEN!");
                        }                
                    } else { System.out.println("\nWRONG OPTION TYPED!"); }
                }
                if (bought){
                    purchasedCar.setSold();
                    Customer currentOwner = purchasedCar.getOwner();
                    purchasedCar.setPrevOwner(currentOwner);
                    purchasedCar.setOwner(prevBuyerLoggedIn);
                    currentUser.setCarsSold(currentUser.getCarsSold()+1);
                }
            }
    }

    public void sellerOptions(Seller currentUser){
        boolean exit = false;
        while(!exit){
            BasicOptions();
            customerOptions();
            System.out.println("14. Sell a Car\n15. Print Cars sold\n16. Print Cars on Sale\n17. Exit\n");
            
            Scanner input = new Scanner(System.in);
            int optionNumber = input.nextInt();
        
            if (optionNumber >= 1 && optionNumber <= 9){
                this.basicOperations(optionNumber, (Person) currentUser);
            } else if (optionNumber >= 10 && optionNumber <=13){ 
                this.customerOperations(optionNumber, (Customer) currentUser);
            } else if (optionNumber >= 14 && optionNumber <= 16){
                switch (optionNumber) {
                    case 14:
                        if (currentUser.getHasAgent() == false){ throw new Error("FIND AN AGENT FIRST"); }
                        else{ 
                            System.out.println("You are automatically Logged Out and your agent will log-in and carry out the process from there.");
                            this.prevSellerLoggedIn = currentUser;
                            this.LoggingIn();
                        }
                        break;
                    case 15: 
                        for (Car car : this.personModel.getCars()){
                            if (car.getIsSold()){System.out.println(car);}
                        } break;
                    case 16: 
                        for (Car car : this.personModel.getCars()){
                            if (!car.getIsSold()){System.out.println(car);}
                        } break;
                }
            } else if (optionNumber == 17){exit=true;break;}
            else{System.out.println("WRONG OPTION NUMBER CHOOSEN");}
        }
    }
    
    public void sellCar(Employee currentUser){
        if (this.prevSellerLoggedIn == null){
            System.out.println("No Request from the Previous Seller to sell the car OR no previous Log-in from a Seller");
        } 
        else{
            System.out.println("Provide all the necessary information about the Car");
            
            Car newCar = this.createCar();
            newCar.setOwner(this.prevSellerLoggedIn);
            this.personModel.addCar(newCar);
            System.out.println("Thank you for choosing us! You can log-in and check whether your car is sold.");
            this.prevSellerLoggedIn = null;
        } 
    }

    public void managerOptions(Manager currentUser){
        boolean exit = false;
        while (!exit){
            BasicOptions();
            System.out.println("10.Add Employee\n11.Add another Manager\n12.Remove yourself from the Manager Position\n13.Print all the Employees\n14.Print all the Managers\n15.Print all the Customers\n16.Print all the people\n17. Print all the Cars\n18.Remove an employee\n19.Exit\n");
            Scanner input = new Scanner(System.in);
            int optionNumber = input.nextInt();

            if (optionNumber >= 1 && optionNumber <= 9){
                basicOperations(optionNumber, (Person) currentUser);
            } else if (optionNumber >= 10 && optionNumber <=18){ 
                int id;String[] info;
                switch (optionNumber) {
                    case 10:
                        info = this.initiliazeStringVars();
                        id = this.personModel.getEmployees().size() + this.personModel.getManagers().size()+1;
                        Employee employee = new Employee(info[0], Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]), info[5], info[6], info[7], info[8], id);
                        this.personModel.signUp(employee);
                        break;
                    case 11:
                        info = this.initiliazeStringVars();
                        id = this.personModel.getEmployees().size() + this.personModel.getManagers().size()+1;
                        System.out.print("Provide all the necessary information of the to be employee.");
                        Manager manager = new Manager(info[0], Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]), info[5], info[6], info[7], info[8], id);
                        this.personModel.signUp(manager);
                        System.out.println("Successfully Added");
                        break;
                    case 12:
                        if (this.personModel.getManagers().size() <= 1){System.out.println("FIRST, YOU HAVE TO ADD A MANAGER BEFORE YOU REMOVE YOURSELF AS ONE.");}
                        else{this.personModel.removePerson(currentUser);}
                        break;
                    case 13: System.out.println(this.personModel.getEmployees()); break;
                    case 14: System.out.println(this.personModel.getManagers()); break;
                    case 15: System.out.println(this.personModel.getCustomers()); break;
                    case 16: System.out.println(this.personModel.getPersonList()); break;
                    case 17: printAllCars(); break;
                    case 18: 
                        if (this.personModel.getEmployees().isEmpty()){System.out.println("NO EMPLOYEE TO REMOVE");} 
                        else{
                            for (int i=0; i<this.personModel.getEmployees().size(); i++){
                                System.out.println(i+". "+this.personModel.getEmployees().get(i));
                            }
                            int employeeNum = input.nextInt();
                        
                            if (employeeNum >= 0 && employeeNum<= this.personModel.getEmployees().size()){
                                Employee employeeToBeRemoved = (Employee) this.personModel.getEmployees().get(employeeNum);
                                this.personModel.removePerson(employeeToBeRemoved);
                                for (int j=0; j<employeeToBeRemoved.getCustomerList().size(); j++){
                                    employeeToBeRemoved.getCustomerList().get(j).setAgent(null);
                                    employeeToBeRemoved.getCustomerList().get(j).setHasAgent(false);
                                }
                            } else{System.out.println("WRONG OPTION NUMBER CHOOSEN");}
                        }
                        break;
                    default: throw new IllegalArgumentException("WRONG OPTION NUMBER");
                }
            } 
            else if (optionNumber == 19){exit = true;} 
            else{ System.out.println("WRONG OPTION NUMBER CHOOSEN"); }
        }
    }

    public void employeeOptions(Employee currentUser){
        boolean exit = false;
        while (!exit){
            BasicOptions();
            System.out.println("10.Help Customer to buy a Car\n11. Help Customer Sell a Car\n12.Edit a Car\n13.Remove a Car from the Car List\n14.See Total Customer List\n15.See my Customer List\n16.Remove customer from the customer list\n17.Remove Customer from my individual Customer List\n18.Check my Rating\n19.Take a look at the number of cars sold\n20.Look at the customers who rated by\n21.Exit\n");
            Scanner input = new Scanner(System.in);
            int optionNumber = input.nextInt();
    
            if (optionNumber >= 1 && optionNumber <= 9){
                basicOperations(optionNumber, (Person) currentUser);
            } else if (optionNumber >= 10 && optionNumber <= 20){
                switch (optionNumber) {
                    case 10: this.buyCar(currentUser);break;
                    case 11: this.sellCar(currentUser);break;
                    case 12: this.editCar();break;
                    case 13: 
                        if (this.personModel.availableCars().isEmpty()){
                            System.out.println("No Cars available to show.");
                        } else {
                            printAllCars();
                            System.out.print("Type the option number of the car to be removed: ");
                            int carOption = input.nextInt();
                            this.personModel.removeCar(this.personModel.getCars().get(carOption)); break;
                        }
                    case 14: System.out.println(this.personModel.getCustomers());break;
                    case 15:System.out.println(currentUser.getCustomerList());break;
                    case 16: 
                        if (this.personModel.getCustomers().isEmpty()){System.out.println("NO CUSTOMERS TO REMOVE");} 
                        else{
                            for (int i=0; i<this.personModel.getCustomers().size(); i++){System.out.println(i+". "+this.personModel.getCustomers().get(i));}
                            int CustomerNum = input.nextInt();
                            if (CustomerNum >= 0 && CustomerNum<= this.personModel.getCustomers().size()){
                                Customer customerToBeRemoved = this.personModel.getCustomers().get(CustomerNum);
                                this.personModel.getPersonList().remove(customerToBeRemoved);
                                customerToBeRemoved.setAgent(null);
                                if (currentUser.getCustomerList().contains(customerToBeRemoved)){
                                    currentUser.getCustomerList().remove(customerToBeRemoved);
                                }
                            } else{System.out.println("WRONG OPTION NUMBER CHOOSEN");}
                        }
                        break;
                    case 17:
                        if (currentUser.getCustomerList().isEmpty()){System.out.println("NO CUSTOMERS TO REMOVE");} 
                        else{
                            for (int i=0; i<currentUser.getCustomerList().size(); i++){System.out.println(i+". "+currentUser.getCustomerList().get(i));}
                            int CustomerNum = input.nextInt();
                            if (CustomerNum >= 0 && CustomerNum<= currentUser.getCustomerList().size()){
                                Customer customerToBeRemoved = currentUser.getCustomerList().get(CustomerNum);
                                currentUser.getCustomerList().remove(customerToBeRemoved);
                                customerToBeRemoved.setAgent(null);customerToBeRemoved.setHasAgent(false);
                            } else{System.out.println("WRONG OPTION NUMBER CHOOSEN");}
                        }
                        break;
                    case 18:System.out.println(currentUser.getRate());break;
                    case 19:
                        for (Car car : this.personModel.getCars()){
                            if (car.getIsSold()){System.out.println(car);}
                        } 
                        break;
                    case 20:System.out.println(currentUser.getRatedBy());break;
                    default:throw new IllegalArgumentException("Wrong option number choosen");
                }
            } else if (optionNumber == 21){exit=true;break;}
            else{ System.out.println("Wrong option Number choosen"); }
        }
        this.prevBuyerLoggedIn=null;
        this.prevSellerLoggedIn=null;
    }
    
    public void basicOperations(int optionNumber, Person currentUser){
        Scanner input = new Scanner(System.in);
        switch (optionNumber) {
            case 1:
                String name;
                System.out.print("Type the name you want to be edited to: ");
                try{
                    name = input.nextLine();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setName(name);
                break;
            case 2:
                int age;
                System.out.print("Type the age you be want to be edited to: ");
                try{
                    age = input.nextInt();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setAge(age);
                break;
            case 3:
                String username;
                System.out.print("Type the username you want to be edited to: ");
                try{
                    username = input.nextLine();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setUsername(username);
                break;
            case 4:
                String password;
                System.out.print("Type the password you want to be edited to: ");
                try{
                    password = input.nextLine();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setPassword(password);
                break;
            case 5:
                String email;
                System.out.print("Type the email you want to be edited to: ");
                try{
                    email = input.nextLine();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setEmail(email);
                break;
            case 6:
                String phone;
                System.out.print("Type the phone number you want to be edited to: ");
                try{
                    phone = input.nextLine();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setPhoneNumber(phone);
                break;
            case 7:
                String street;String city; int zipCode;
                try{
                    System.out.print("Type the new Street: ");
                    street = input.nextLine();
                    System.out.print("Type the new City: ");
                    city = input.nextLine();
                    System.out.print("Type the new Zip Code: ");
                    zipCode = input.nextInt();
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid input");
                }
                currentUser.setAddress(street, city, zipCode);
                break;
            case 8:System.out.println(currentUser);break;
            case 9:
                System.out.println(this.personModel.availableCars());
                break;
            default: throw new IllegalArgumentException("Wrong option number choosen");
        }
    }

    public void customerOperations(int optionNumber, Customer customer){
        Scanner input = new Scanner(System.in);
        if (this.personModel.getEmployees().isEmpty()){ System.out.println("NO EMPLOYEES UNDER THE CURRENT MANAGER."); return;}
            switch (optionNumber) {
                case 10:
                    if (customer.getHasAgent()){System.out.println("You have an agent. Press pther options for further actions"); }
                    else {System.out.println("You don't have an agent. Choose the correct option of activity and you will be assigned to an agent");}
                    break;
                case 11:
                    if (customer.getHasAgent()){System.out.println("You already have an Agent.");}
                    else {
                        int lowest = this.personModel.getEmployees().get(0).getCustomerList().size();
                        Employee emp = this.personModel.getEmployees().get(0);
                        for (int i=1; i<this.personModel.getEmployees().size(); i++){
                            ArrayList<Employee> employees= this.personModel.getEmployees();
                            if (lowest >=  employees.get(i).getCustomerList().size()){
                                lowest = employees.get(i).getCustomerList().size();
                                emp = employees.get(i);
                            }
                        }
                        customer.setAgent(emp);
                        customer.setHasAgent(true);
                        emp.addCustomer(customer);
                        System.out.println("Your Agent is " + emp);
                        return;
                    }
                    break;
                case 12:
                    if (customer.getHasAgent()){
                        System.out.println(customer.getAgent());
                    } else{System.out.print("NO Agent Assigned");}
                    break;
                case 13:
                    if (customer.getHasAgent()){
                        System.out.print("Rate our Agent honestly out 10: ");
                        double rating = (double) input.nextDouble();
                        if (rating < 0 || rating > 10){System.out.println("Rating Out Of Order");}
                        else{
                            if (customer.getAgent().getRatedBy().contains(customer)){
                                customer.getAgent().setRate(customer.getAgent().getRate() - customer.getGiveRating());
                            } else{customer.getAgent().getRatedBy().add(customer);}
                            double avgRate = (customer.getAgent().getRate() + rating) / customer.getAgent().getCustomerList().size();
                            customer.getAgent().setRate(avgRate);
                            customer.setGiveRating(rating);
                        }
                    } else{System.out.println("No Agent to give rating");}
                    break;
                default: System.out.println("WRONG OPTION CHOSEN");
            }
    }

    public String[] initiliazeStringVars(){
        Scanner input = new Scanner(System.in);
        String name;String age;String street;String city;String zipCode;String username;String password;String phoneNumber;String email;
        try{
            System.out.print("\nEnter Full Name: "); 
            // input.nextLine();
            name = input.nextLine();

            System.out.print("Enter age: ");
            age = input.nextLine();

            System.out.print("Enter Street Name: ");
            // input.nextLine();
            street = input.nextLine();

            System.out.print("Enter City: "); 
            city = input.nextLine();

            System.out.print("Enter ZIP CODE: "); 
            zipCode = input.nextLine();

            System.out.print("Enter Username: "); 
            // input.nextLine();
            username = input.nextLine();

            System.out.print("Enter Password: "); 
            password = input.nextLine();

            System.out.print("Enter Email: "); 
            email = input.nextLine();

            System.out.print("Enter Phone Number: "); 
            phoneNumber = input.nextLine();

        } catch (InputMismatchException error){
            throw new InputMismatchException("Wrong Inputs");
        }

        String[] array = {name, age, street, city, zipCode, username, password, email, phoneNumber};
        return array;
    }

    public void editCar(){
        Scanner input = new Scanner(System.in);
        printAllCars();
        System.out.println("Type the option number of the Car to be edited to: ");
        int optionNumber = input.nextInt();

        if (optionNumber >= 0 && optionNumber <= this.personModel.getCars().size()){
            Car choosenCar = this.personModel.getCars().get(optionNumber);
            System.out.println("1.Owner\n2.Model\n3.Price\n4.mileage\n5.age");
            int option = input.nextInt();
            switch (option) {
                case 1: 
                    choosenCar.setOwner(prevBuyerLoggedIn);
                    System.out.println("Type all necessary details of the new Owner of the Car to be edied to: ");
                    String[] info = this.initiliazeStringVars();
                    String driverLicense = this.initializeDriverLicense();
                    int creditScore = this.intializeCreditScore(); 
                    Customer newOwner = new Customer(info[0], Integer.parseInt(info[1]), info[2], info[3], Integer.parseInt(info[4]), info[5], info[6], info[7], info[8], driverLicense, creditScore);
                    choosenCar.setOwner(newOwner);
                    break;
                case 2: 
                    System.out.print("Write the new model of the Car: ");
                    String model = input.nextLine();
                    choosenCar.setModel(model);
                    break;
                case 3:
                    System.out.print("Write the new Price of the Car: ");
                    int price = input.nextInt();
                    choosenCar.setPrice(price);
                case 4:
                    System.out.print("Write the new mileage of the Car: ");
                    int mileage = input.nextInt();
                    choosenCar.setMileage(mileage);
                case 5:
                    System.out.print("Type how old the Car is: ");
                    int age = input.nextInt();
                    choosenCar.setAge(age);
                default: throw new IllegalArgumentException("Wrong Option Number Choosen");
            }
            System.out.print("Choose the option number of the attribute of the Car you want to be edited: ");
        } else{
            throw new IllegalArgumentException("Wrong Option Number Choosen");
        }
    }

    public Car createCar(){
        Scanner input = new Scanner(System.in);

        System.out.print("Type the Car Identification Number: ");
        String id = input.nextLine();
        
        System.out.print("Type the Model of the Car: ");
        String model = input.nextLine();

        System.out.print("Provide the price of the car you want to sell at: ");
        int price = input.nextInt();

        System.out.print("Enter the estimated mileage of the Car: ");
        int mileage =  input.nextInt();

        System.out.print("Enter how old the car is: ");
        int age =  input.nextInt();

        return new Car(model, id, mileage, price, age);
    }

    public void printAvailableCars(){
        System.out.println("The available cars are below");
        int idx = 0;
        for (Car car : this.personModel.availableCars()){
            System.out.printf("%d. %s", idx, car);
        }
    }

    public void printAllCars(){
        if (this.personModel.getCars().isEmpty()){
            System.out.println("No Cars available to show.");
        } else{
            System.out.println("All the cars are below");
            
            int idx = 0;
            for (Car car : this.personModel.getCars()){
                System.out.printf("%d. %s", idx, car);
                idx++;
            }
        }
    }
}
