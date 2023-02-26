package com.amazon.Menus;
// This class the View of admin Menu Which contains some Methods to perform the task of the Admin.

import com.amazon.Model.User;
import java.util.Date;
import java.util.Scanner;

public class AdminMenu extends Menu{
    public static AdminMenu menu=new AdminMenu();
    public static AdminMenu getInstance() {return  menu;}
    private AdminMenu(){}
    public void showMenu() throws NumberFormatException{
        Scanner scanner=new Scanner(System.in);
        System.out.println("Navigating to the Admin Menu...");
        //Login code of Admin
        User adminUser=new User();
        System.out.print("Enter your Email : ");
        adminUser.email = scanner.nextLine();

        System.out.print("Enter Your Password : ");
        adminUser.password = scanner.nextLine();

        boolean result=auth.loginUser(adminUser);
        if(result==true && adminUser.type==1) {
            ClassifiedSession.user=adminUser;// setting the user session as admin
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Hey Buddy Welcome to Admin App.");
            System.out.println("Happy To See You Again. " + adminUser.name);
            System.out.println("It's : + " + new Date());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            boolean exitCode=false;
            while(exitCode==false) {
                System.out.println("\n1 : Manage classified Requests.");
                System.out.println("2 : Manage User.");
                System.out.println("3 : Manage Classified.");
                System.out.println("4 : Manage Category.");
                System.out.println("5 : Manage Reports.");
                System.out.println("0 : Exit.");
                System.out.print("Buddy Please Enter your choice : ");
                int choice=0;
                try {
                     choice = Integer.parseInt(scanner.nextLine());
                }catch (NumberFormatException e){
                    choice=6;
                }
                switch(choice) {
                    case 1:
                        boolean requestRun=true;
                        while(requestRun) {
                            System.out.println("\n1 : View All Classified Requests.");
                            System.out.println("2 : Approve/Reject Classified Request.");
                            System.out.println("0 : Return back to the main menu.");
                            System.out.print("Buddy Please Enter Your Choice : ");

                            int passChoice=0;
                            try {
                                passChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                passChoice=3;
                            }
                            switch (passChoice) {
                                case 1:
                                    classifiedManager.viewClassifiedRequests();
                                    System.out.print("Enter User ID to View Requests of a Particular User or 0 to Cancel : ");
                                    int uid = Integer.parseInt(scanner.nextLine());
                                    classifiedManager.viewClassifiedRequestsByUser(uid);
                                    break;
                                case 2:
                                    classifiedManager.viewClassifiedRequests();
                                    System.out.print("Enter Classified ID to Respond the Request For : ");
                                    int classifiedId=0;
                                    try {
                                        classifiedId = Integer.parseInt(scanner.nextLine());
                                        classifiedManager.approveRejectClassified(classifiedId);
                                    }catch (NumberFormatException e){
                                        System.out.println("Please Enter a Valid Input Buddy");
                                    }
                                    break;
                                case 0:
                                    System.out.println("Exiting to Previous Menu Buddy.");
                                    requestRun=false;
                                    break;
                                default:
                                    System.out.println("Invalid Choice! Please Select a Valid Option Buddy...");
                            }
                        }
                        break;
                    case 2:
                        auth.viewUsers();
                        System.out.print("Enter the User ID to Activate Or Deactivate the User Account : ");
                        int uid=0;
                        try {
                            uid = Integer.parseInt(scanner.nextLine());
                            auth.activateOrDeactivateUser(uid);

                        }catch (NumberFormatException e){
                            System.out.println("Please Enter a Valid Input Buddy");
                        }
                        break;
                    case 3:
                        boolean classifiedRun=true;
                        while(classifiedRun) {
                            System.out.println("\n1 : View All Classified.");
                            System.out.println("2 : Add a Classified.");
                            System.out.println("3 : View Admin Classified.");
                            System.out.println("4 : Delete Admin Classified.");
                            System.out.println("5 : Update Admin Classified.");
                            System.out.println("0 : Return Back to the Main Menu.");
                            System.out.print("Please Enter Your Choice : ");
                            try {
                                choice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                choice=6;
                            }
                            if (choice == 1) {
                                classifiedManager.viewClassified(1);
                            } else if (choice == 2) {
                                // pre-setting the status as 1 (approved) as it as an admin posted classified
                                classifiedManager.postClassified(1);
                            } else if (choice==3) {
                                classifiedManager.viewClassifiedByUser(ClassifiedSession.user.id);
                            } else if (choice == 4) {
                                try {
                                    classifiedManager.deleteClassified(ClassifiedSession.user.id);
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }

                            } else if (choice==5) {
                                try{
                                    classifiedManager.updateClassified(ClassifiedSession.user.id);
                                }catch (NumberFormatException e){
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }

                            } else if (choice==0) {
                                classifiedRun=false;
                            } else
                                System.out.println(" Invalid Choice! Please Select a Valid Option Buddy...");
                        }
                        break;
                    case 4:
                        boolean categoryRun=true;
                        while(categoryRun) {
                            System.out.println("1 : Add Category.");
                            System.out.println("2 : Update Category.");
                            System.out.println("3 : Delete Category.");
                            System.out.println("4 : View All Category.");
                            System.out.println("0 : Return Back to the Main Menu.");
                            System.out.print("Please Enter Your Choice : ");
                            int categoryChoice =0;
                            try {
                                categoryChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                categoryChoice=5;
                            }
                            if (categoryChoice == 1) {
                                categoryManager.addCategory();
                            } else if (categoryChoice == 2) {
                                try{
                                    categoryManager.updateCategory();
                                }catch(NumberFormatException e) {
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }

                            } else if (categoryChoice == 3) {
                                try{
                                    categoryManager.deleteCategory();
                                }catch(NumberFormatException e) {
                                    System.out.println("Please Enter a Valid Input Buddy");
                                }
                            } else if (categoryChoice == 4) {
                                categoryManager.viewCategory();
                            } else if (categoryChoice==0) {
                                categoryRun=false;
                            } else {
                                System.out.println("Invalid Choice! Please Select a Valid Option Buddy...");
                            }
                        }
                        break;
                    case 5:
                        boolean reportsRun=true;
                        while (reportsRun) {
                            System.out.println("\nFetching the Reports...");
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            System.out.println("1 : To Check Total Active And De-Active User's On App.");
                            System.out.println("2 : To check Total Classifieds In Each Category On App.");
                            System.out.println("3 : To Check Total Number Of Classified By Each User On App.");
                            System.out.println("4 : To Check Total Status Of Classified (Sold / On Sale / Rejected).");
                            System.out.println("5 : To Check All Users Transaction. ");
                            System.out.println("0 : Return Back to the Main Menu");
                            System.out.print("Buddy Please Enter Your Choice : ");

                            int reportChoice = 0;
                            try {
                                reportChoice = Integer.parseInt(scanner.nextLine());
                            }catch (NumberFormatException e){
                                reportChoice=6;
                            }
                            switch (reportChoice) {
                                case 1:
                                    auth.activateDeactivateUserCount();
                                    break;
                                case 2:
                                    categoryManager.categoryCount();
                                    break;
                                case 3:
                                    classifiedManager.userClassifiedCount();
                                    break;
                                case 4:
                                    classifiedManager.classifiedSoldSellCount();
                                    break;
                                case 5:
                                    messageManager.allUserTransactions();
                                    break;
                                case 0:
                                    reportsRun=false;
                                    break;
                                default:
                                    System.out.println("Please Enter a Valid Choice Buddy.");
                            }
                        }
                        break;
                    case 0:
                        System.out.println("Thank You For Using Admin Application See You Soon.");
                        exitCode=true;
                        break;
                    default:
                        System.out.println("OOps...!Your Choice is Invalid Buddy.....");
                }
            }
        }
        else
            System.out.println("Invalid Credentials! Please Try Again Buddy...!!");
    }
}
