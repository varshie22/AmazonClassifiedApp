package com.amazon.Menus;
// This Class is Main Class of the Application Which Call the Main Menu Method To Show the Login option
// of User or Admin

import com.amazon.DB.DB;

import java.util.Scanner;

public class App {
    public static void main( String[] args ){
        Scanner scanner=new Scanner(System.in);
        System.out.println("******************************************************************");
        System.out.println( "Welcome to Amazon Classified Application For Amazonion's..." );
        System.out.println("******************************************************************");
        Menu menu = new Menu();
        if(args.length > 0) {
            DB.FILEPATH = args[0];
        }
        DB.getInstance().loadDriver();
        try {
            menu.showMainMenu();

        }catch (NumberFormatException e){
            System.out.println("Please enter a valid choice");
        }
        DB.getInstance().closeConnection();
    }
}
