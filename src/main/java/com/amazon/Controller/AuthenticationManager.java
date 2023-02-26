package com.amazon.Controller;

import com.amazon.DB.UserDAO;
import com.amazon.Model.User;
import java.util.List;
import java.util.Scanner;

public class AuthenticationManager {
   public static AuthenticationManager auth=new AuthenticationManager();
    UserDAO dao = new UserDAO();
    Scanner scanner=new Scanner(System.in);
    private AuthenticationManager(){
    }
    public boolean loginUser(User user) {
        String sql= "SELECT * FROM User WHERE email = '"+user.email+"' AND password = '"+user.password+"'";
        List<User> users = dao.retrieve(sql);
        if(users.size()>0) {
            User u = users.get(0);// to access first element of the list
            user.id = u.id;
            user.name=u.name;
            user.phone=u.phone;
            user.email=u.email;
            user.address=u.address;
            user.type=u.type;
            user.status=u.status;
            user.createOn=u.createOn;
            return true;
        }
        return false;
    }
    public boolean registerUser(User user) {
        int result = dao.insert(user);
        return result > 0;
    }
    public boolean updateUser(User user) {
        int result = dao.update(user);
        return result > 0;
    }
    public void viewUsers(){
        String sql="select * from user where type=2";
        List<User> users = dao.retrieve(sql);
        for(User object : users) {
            object.prettyPrint();
        }
    }
    public void activateOrDeactivateUser(int uid) throws NumberFormatException{
        String sql="select * from user where id="+uid;
        List<User> users = dao.retrieve(sql);
        if(users.isEmpty()){
            System.out.println("Enter valid User ID : ");
            return;
        }
        User user=users.get(0);
        System.out.print("The Current Status Of The User "+ user.name+" is: ");
        if(user.status==1){
            System.out.println("Activated.");
        }else {
            System.out.println("Deactivated.");
        }
        System.out.print("Press 1 to Change The Status Or Press Any Number to Cancel : ");
        int choice=Integer.parseInt(scanner.nextLine());
        if(choice==1) {
            if (user.status == 0) {
                user.status = 1;
            } else {
                user.status = 0;
            }
            boolean result = updateUser(user);
            if(result){
                System.out.println("Status "+ user.status + " Successfully.");
            }else{
                System.out.println("Status Not Updated.");
            }
        }

    }
    public void activateDeactivateUserCount(){
        UserDAO userDAO=new UserDAO();
        List<User> users= userDAO.retrieve();
        int activeUserCount=0;
        int deactiveUserCount=0;
        for(User user:users){
            if(user.status==1 && user.type==2){
                activeUserCount++;
            }else if(user.status==0 && user.type==2) {
                deactiveUserCount++;
            }
        }
        System.out.println("Total Active Users Are : " +activeUserCount);
        System.out.println("Total De-Active Users Are: "+ deactiveUserCount);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static AuthenticationManager getInstance() {return auth;}

}
