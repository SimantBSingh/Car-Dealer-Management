# INTRODUCTION

<br>

## Description

This is a Java based Car Dealer Management System where we have Managers, Employees, Buyers, and Sellers where a Seller sells a car through the employee/agent and the buyer is given an option to buy the car.

<br>

## How the program works

First of all, we need a manager to start things off. The code part to add a manager at first is the commented part in the main method in the Main class. The manager is the only person who can add Employees. After adding the employee we can exit the code and run it back again and the information will already be stored in the file and will automatically be accessed while logging in. After that we can create a Seller and list the inforamtion of the car in the available cars database which can be later accessed by the buyer to buy the car. To sell or buy a car, the customer should have an agent first which is automatically choosen for them after choosing the correction option from the options given by the program. 


The below code is should run first:

        Manager manager = new Manager("John Doe", 47, "Gentilly Boulevard", "New Orleans", 70122, "john", "doe", "5042807777", "managerjohn@gmail.com", 1);
        personModel.addPerson(manager);
        System.out.println(Serializer.deserializePersonInfo());




## Run the program

Simply run the program first. After creating an account as a buyer or a seller, log-in using the username and password. Then type the option number assocciated with the desired task to be formed.
    


