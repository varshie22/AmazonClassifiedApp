package com.amazon.Menus;
//This Class is the View of User Menu Which Contains Some Methods to Register or Login In the User Menu

import com.amazon.Model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

public class UserMenu extends Menu{
    public static UserMenu menu=new UserMenu();
    public static UserMenu getInstance(){
        return  menu;}
    private UserMenu(){}
    public void showMenu() {
        System.out.println("Navigating to the User Menu...");
        Scanner scanner=new Scanner(System.in);
        System.out.println("1 : Register");
        System.out.println("2 : Login");
        System.out.println("3 : Quit");
        System.out.print("Hey Buddy Please Enter Your Choice : ");
        int initialChoice = 0;
        try{
             initialChoice = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            initialChoice=4;//explicitly providing a wrong choice
        }
        boolean result = false;
        User user = new User();
        if(initialChoice == 1) {
            System.out.print("Enter Your Name : ");
            user.name =scanner.nextLine();

            System.out.print("Enter Your phone : ");
            user.phone =scanner.nextLine();

            System.out.print("Enter Your Email : ");
            user.email =scanner.nextLine();

            System.out.print("Enter Your password : ");
            user.password =scanner.nextLine();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(user.password.getBytes(StandardCharsets.UTF_8));
                user.password = Base64.getEncoder().encodeToString(hash);
            }catch(Exception e) {
                System.out.println("OOPs...! Something Went Wrong Buddy..."+e);
            }
            System.out.print("Enter Your Address :");
            user.address =scanner.nextLine();

            System.out.print("Enter Your Department :");
            user.department =scanner.nextLine();

            user.type = 2; //user
            user.status=1; //activated
            result = auth.registerUser(user);
            if(result){
                System.out.println("Profile created successfully Buddy.");
            }else{
                System.out.println("Buddy Profile creation failed.");
            }
            showMenu();
        }
        else if(initialChoice ==2) {
            System.out.print("Enter Your Email : ");
            user.email =scanner.nextLine();

            System.out.print("Enter Your Password : ");
            user.password =scanner.nextLine();
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(user.password.getBytes(StandardCharsets.UTF_8));
                user.password = Base64.getEncoder().encodeToString(hash);
            }catch(Exception e) {
                System.err.println("OOPs...! Something Went Wrong Buddy..."+e);
            }
            result = auth.loginUser(user);
        }
        else if(initialChoice ==3) {
            System.out.println("Thank You for Using Amazon Classified Application GoodBye Buddy...!");
            return;
        }
        else {
            System.out.println("Invalid choice ! Please Enter a Valid Choice Buddy.");
            System.out.println("Thank You for Using Amazon Classified Application GoodBye Buddy...!");
        }

        if(result && user.type == 2) {
            ClassifiedSession.user = user;
            if(user.status==0){
                System.out.println("Your Account is Deactivated");
                System.out.println("Please contact Us.\n");
                return;
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Hey Buddy Welcome to User App.");
            System.out.println("Happy to See You " + user.name);
            System.out.println("Its : " + new Date());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean exitCode = false;
            while (true) {
                System.out.println("1 : My Profile ");
                System.out.println("2 : Post a Classified");
                System.out.println("3 : View All classified and buy");
                System.out.println("4 : My Messages");
                System.out.println("5 : Make Payments");
                System.out.println("6 : Manage My Classified");
                System.out.println("0 : Quite");
                System.out.print("Buddy Please Enter Your Choice : ");
                int choice = 0;
                try{
                    choice = Integer.parseInt(scanner.nextLine());
                }catch (NumberFormatException e){
                    choice=7;//explicitly providing a wrong choice
                }
                switch(choice) {
                    case 1:
                        System.out.println("My Profile.....\n");
                        user.prettyPrint();
                        System.out.println("Do You Wish to Update Your Profile Please Select (1 : Update 0 : Cancel): ");
                        System.out.println("~Press Enter to Skip the Column Buddy.~");
                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        }catch (NumberFormatException e){
                            choice=2;
                        }
                        if(choice==1) {
                            System.out.print("Enter Your Name : ");
                            String name =scanner.nextLine();
                            if(name.isEmpty()==false) {
                                user.name =name;
                            }
                            System.out.print("Enter Your phone : ");
                            String phone =scanner.nextLine();
                            if(!phone.isEmpty()) {
                                user.phone=phone;
                            }
                            System.out.print("Enter Your password : ");
                            String password =scanner.nextLine();
                            if(!password.isEmpty()) {
                                user.password=password;
                                try {
                                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                    byte[] hash = digest.digest(user.password.getBytes(StandardCharsets.UTF_8));
                                    user.password = Base64.getEncoder().encodeToString(hash);
                                }catch(Exception e) {
                                    System.err.println("OOps...! Something Went Wrong Buddy..."+e);
                                }
                            }
                            System.out.print("Enter Your Address : ");
                            String address =scanner.nextLine();
                            if(!address.isEmpty()) {
                                user.address=address;
                            }
                            System.out.print("Enter Your Department : ");
                            String department =scanner.nextLine();
                            if(!department.isEmpty()) {
                                user.department=department;
                            }
                            if(auth.updateUser(user)) {
                                System.out.println("Hey Buddy Your Profile Updated Successfully....");
                            }else {
                                System.err.println("OOPs...! Profile Update Failed Please Try again Buddy....");
                            }
                        } else if (choice==0) {
                            System.out.println("Update Profile Canceled Buddy.");

                        } else{
                            System.out.println("Invalid Choice Buddy.");
                        }
                        break;
                    case 2:
                        System.out.println("Capturing Details...");
                        // pre-setting the status as 0 (requested) as it as a user posted classified
                        try {
                            classifiedManager.postClassified(0);
                        }catch (NumberFormatException e){
                            System.out.println("Please Enter a Valid Input Buddy");
                        }
                        break;
                    case 3:
                        //userType 2 means User
                        try {
                            classifiedManager.viewClassified(2);
                        }catch (NumberFormatException e){
                            System.out.println("Please Enter a Valid Input Buddy");
                        }

                        break;
                    case 4:
                        boolean messageRun=true;
                        while (messageRun) {
                            System.out.println("1 : Message Sent ");
                            System.out.println("2 : Message Received ");
                            System.out.println("3 : Compose Message ");
                            System.out.println("4 : Delete Message ");
                            System.out.println("0 : Return back to the main menu");
                            System.out.print("Enter your choice : ");
                            int messageChoice = 0;
                            try{
                                messageChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                messageChoice=5;//explicitly providing a wrong choice
                            }

                            if (messageChoice == 1) {
                                messageManager.viewSentMessages();

                            } else if (messageChoice == 2) {
                                messageManager.viewRecievedMessages();

                            } else if (messageChoice == 3) {
                                try {
                                    System.out.println("Buddy You Can Reply to Following Messages.");
                                    if (messageManager.viewRecievedMessages()) {
                                        messageManager.composeMessage();
                                    }
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }

                            } else if (messageChoice == 4) {
                                messageManager.viewRecievedMessages();
                                try {
                                    messageManager.deleteMyMessage();
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }
                            } else if (messageChoice==0) {
                                messageRun=false;
                            } else
                                System.out.println("Invalid Choice! Please Enter a Valid Option Buddy...");
                        }
                        break;
                    case 5:
                        boolean transactionRun = true;
                        while (transactionRun) {
                            System.out.println("Manage Your Transaction Payments ");
                            System.out.println("1 : Make Payment And Buy The Product.");
                            System.out.println("2 : View My Transactions History.");
                            System.out.println("0 : Return Back to the Main Menu.");
                            System.out.print("Please Enter Your Choice : ");
                            int paymentChoice = 0;
                            try{
                                paymentChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                paymentChoice=3;//explicitly providing a wrong choice
                            }

                            if (paymentChoice == 1) {
                                messageManager.makePayment();
                            } else if (paymentChoice == 2) {
                                messageManager.transactions(ClassifiedSession.user.id);
                            } else if (paymentChoice == 0) {
                                transactionRun = false;
                            } else
                                System.out.println("Buddy Please Enter a Valid Choice...");
                        }
                        break;
                    case 6:
                        boolean updateChoice = true;
                        while (updateChoice) {
                            System.out.println("1 : View My Classified.");
                            System.out.println("2 : Update My Classified.");
                            System.out.println("3 : Delete My Classified.");
                            System.out.println("0 : Return back to the main menu.");
                            System.out.print("Buddy Please Enter your choice : ");
                            int manageClassifiedChoice = 0;
                            try{
                                manageClassifiedChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                manageClassifiedChoice=4;//explicitly providing a wrong choice
                            }

                            if(manageClassifiedChoice==1){
                                classifiedManager.viewClassifiedByUser(ClassifiedSession.user.id);
                            }
                            else if (manageClassifiedChoice == 2) {
                                classifiedManager.viewClassifiedByUser(ClassifiedSession.user.id);
                                try {
                                    classifiedManager.updateClassified(ClassifiedSession.user.id);
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }

                            } else if (manageClassifiedChoice == 3) {
                                classifiedManager.viewClassifiedByUser(ClassifiedSession.user.id);
                                try {
                                    classifiedManager.deleteClassified(ClassifiedSession.user.id);
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");

                                }

                            } else if (manageClassifiedChoice == 0) {
                                updateChoice = false;
                            } else
                                System.out.println("Invalid choice! Please Enter a Valid Choice Buddy...");
                        }
                        break;
                    case 0:
                        System.out.println("Thank You For Using The User App Buddy See You Soon!!");
                        exitCode = true;
                        break;
                    default:
                        System.out.println("Please Enter Valid Choice Buddy.");
                }
                if(exitCode) {
                    break;
                }
            }
        }
        else {
            System.out.println("OOPs...! Invalid Credentials, Please Try Again Buddy...!!");
        }
    }
}
