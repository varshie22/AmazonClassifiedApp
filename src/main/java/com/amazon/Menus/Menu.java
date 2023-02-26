package com.amazon.Menus;
// This Class is the View of the Main Menu When you start the Application to Login As User Or Admin.

import com.amazon.Controller.AuthenticationManager;
import com.amazon.Controller.CategoryManager;
import com.amazon.Controller.ClassifiedManager;
import com.amazon.Controller.MessageManager;
import com.amazon.DB.DB;
import java.util.Scanner;

public class Menu {
    AuthenticationManager auth = AuthenticationManager.getInstance();
    ClassifiedManager classifiedManager=ClassifiedManager.getInstance();
    CategoryManager categoryManager=CategoryManager.getInstance();
    MessageManager messageManager=MessageManager.getInstance();
    Scanner scanner = new Scanner(System.in);
    void showMainMenu() throws NumberFormatException{
        // Initial Menu for the Application
        while(true) {
            System.out.println("1: Admin");
            System.out.println("2: User");
            System.out.println("3: Quit");
            System.out.print("Please Select an Option Buddy : ");
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice==3){
                System.out.println("Thank You for Using Amazon Classified Application Buddy GoodBye...!");
                DB db =DB.getInstance();
                db.closeConnection();
                scanner.close();
                break;
            }
            try{
                Menu menu=MenuFactory.getMenu(choice);
                menu.showMenu();
            }
            catch (Exception e) {
                System.out.println("Invalid Choice Buddy ! Please Select a Valid Choice");
            }
        }
    }
    public void showMenu() {
        System.out.println("Showing the Menu...");
    }
}
