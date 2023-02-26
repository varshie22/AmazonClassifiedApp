package com.amazon.Controller;

import com.amazon.DB.ClassifiedDAO;
import com.amazon.DB.MessageDAO;
import com.amazon.DB.UserDAO;
import com.amazon.Menus.ClassifiedSession;
import com.amazon.Model.Classified;
import com.amazon.Model.Message;
import com.amazon.Model.User;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class MessageManager {
    public static MessageManager messageManager =new MessageManager();
    public static MessageManager getInstance(){
        return messageManager;
    }
    MessageDAO messageDAO = new MessageDAO();
    UserDAO userDAO=new UserDAO();
    ClassifiedDAO classifiedDAO=new ClassifiedDAO();
    Scanner scanner=new Scanner(System.in);
    private MessageManager(){}
    public void sendMessage(int status,int classifiedId) {

        String sql="select * from classified where id="+classifiedId;
        List<Classified> classifieds=classifiedDAO.retrieve(sql);

        if(classifieds.get(0).userId==ClassifiedSession.user.id){
            System.out.println("Hey Buddy You Can't Buy Your Own Product...!");
            return;
        }
        if(classifieds.size()>0 && classifieds.get(0).status==1){
            Classified classified=classifieds.get(0);
            Message message=new Message();
            message.getDetails();
            message.fromUserId = ClassifiedSession.user.id;
            message.status=status;
            message.toUserId=classified.userId;
            message.classifiedId=classifiedId;
            int result=messageDAO.insert(message);
            String alert = (result > 0) ? "Buddy Message Sent Successfully" : "Message Sending Failed... Please Try Again Buddy...";
            System.out.println(alert);
        }
        else{
            System.out.println("OOPS...! Buddy It's Invalid Classified ID.");
        }
    }
    public void viewSentMessages() {
        String sql="select * from message where fromUserId="+ClassifiedSession.user.id;
        List<Message> messages=messageDAO.retrieve(sql);
        if(messages.isEmpty()){
            System.out.println("No Messages...");
            return;
        }
        for(Message message:messages){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Message ID \t\t\t\t\t\t : " + message.id);
            List<Classified> classifieds=classifiedDAO.retrieve("select * from classified where id="+message.classifiedId);
            System.out.println("Classified ID \t\t\t\t\t : " + message.classifiedId);
            System.out.println("Classified name \t\t\t\t : " + classifieds.get(0).headline);
            System.out.println("Classified Selling Price \t\t : \u20b9"+ classifieds.get(0).price);
            List<User> users=userDAO.retrieve("select * from user where id="+message.toUserId);
            System.out.println("Sent To User ID \t\t\t\t : " + message.toUserId);
            System.out.println("Sent To User name \t\t\t\t : " + users.get(0).name);
            System.out.println("Proposed Price \t\t\t\t\t : \u20b9" + message.proposedPrice);
            int status=message.status;
            String statusText = "";
            if(status == 0) {
                statusText = "Request";
            }else if (status == 1) {
                statusText = "Accepted";
            }else if (status == 2) {
                statusText = "Not Interested";
            }else {
                statusText = "Sold";
            }
            System.out.println("Status \t\t\t\t\t\t\t : "+statusText);
            System.out.println("Last Update On \t\t\t\t\t : " + message.lastUpdateOn+'\n');
        }
    }
    public boolean viewRecievedMessages() {
        String sql="select * from message where toUserId="+ClassifiedSession.user.id;
        List<Message> messages=messageDAO.retrieve(sql);
        if(messages.isEmpty()){
            System.out.println("No Messages Received Yet Buddy...");
            return false;
        }
        for(Message message:messages){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Message ID \t\t\t\t\t\t : " + message.id);
            List<Classified> classifieds=classifiedDAO.retrieve("select * from classified where id="+message.classifiedId);
            System.out.println("Classified ID \t\t\t\t\t : " + message.classifiedId);
            System.out.println("Classified name \t\t\t\t : " + classifieds.get(0).headline);
            System.out.println("Classified Selling Price \t\t : \u20b9"+ classifieds.get(0).price);
            List<User> users=userDAO.retrieve("select * from user where id="+message.fromUserId);
            System.out.println("Sender User ID \t\t\t\t\t : " + message.fromUserId);
            System.out.println("Sender User name \t\t\t\t : " + users.get(0).name);
            System.out.println("Proposed Price \t\t\t\t\t : \u20b9" + message.proposedPrice);
            int status=message.status;
            String statusText = "";
            if(status == 0) {
                statusText = "Request";
            }else if (status == 1) {
                statusText = "Accepted";
            }else if (status == 2) {
                statusText = "Not Interested";
            }else {
                statusText = "Sold";
            }
            System.out.println("Status \t\t\t\t\t\t\t : "+statusText);
            System.out.println("Last Update On \t\t\t\t\t : " + message.lastUpdateOn+'\n');
        }
        return true;
    }
    public void composeMessage()  {
        System.out.print("Enter The Message ID : ");
        int messageId=Integer.parseInt(scanner.nextLine());
        List<Message> messages=messageDAO.retrieve("select * from message where id="+messageId);
        if(messages.isEmpty() || messages.get(0).toUserId!=ClassifiedSession.user.id){
            System.out.print("Enter a Valid Message ID Buddy.");
            System.out.println("");
            return;
        }
        Message message= messages.get(0);
        System.out.println("Hey Buddy! Do You Want to Accept the Buyer Proposed Offer of Rs. " + message.proposedPrice + '?');
        System.out.println("1. Accept \n2. Reject");
        System.out.print("Enter Your Choice : ");
        int choice=Integer.parseInt(scanner.nextLine());
        message.toUserId=message.fromUserId;
        message.fromUserId=ClassifiedSession.user.id;
        if(choice==1){
            message.status=1;
        } else if (choice==2) {
            message.status=2;
        }else {
            System.out.println("Invalid Choice! Please Enter a Valid Choice Buddy....");
            return;
        }
        int result=messageDAO.insert(message);

        String text=(result>0)?"Message Sent Successfully Buddy.":"Message Sent Failed Buddy....!";
        System.out.println(text);
    }

    public void makePayment() {
//        String sql="select * from message where status= "+1+" and toUserId="+ ClassifiedSession.user.id;
        String sql="SELECT message.id, message.classifiedId, message.fromUserId, message.toUserId, message.proposedPrice, message.status,message.lastUpdateOn  FROM message " +
                "INNER JOIN classified ON message.classifiedId = classified.id " +
                "where message.status=1 and classified.status=1 and message.toUserId="+ClassifiedSession.user.id;
        List<Message> messages=messageDAO.retrieve(sql);

        if(messages.isEmpty()){
            System.out.println("Hey Buddy! You Haven't Any Classified to Buy Yet...");
            return;
        }

        for(Message message:messages){
            List<Classified> classifieds=classifiedDAO.retrieve("select * from classified where id="+message.classifiedId);
            if(classifieds.get(0).status==3)continue;//skip the sold ones
            System.out.println("ID : "+message.id);
            System.out.println("Product name : "+ classifieds.get(0).productName);
            System.out.println("Product Selling Price : \u20b9 "+ classifieds.get(0).price);
            System.out.println("Proposed Price : \u20b9 "+messages.get(0).proposedPrice);
            System.out.println("``````````````````````````````````````````````");
        }
        System.out.print("Select The Message ID : ");
        int messageId = 0;
        try{
            messageId = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){}

        Message message=null;
        for(Message obj:messages){
            if(obj.id==messageId){
                List<Classified> classifieds=classifiedDAO.retrieve("select * from classified where id="+messageId);
                if(classifieds.get(0).status==3)break;
                message=obj;
                break;
            }
        }
        if(message==null){
            System.out.println("Hey Buddy! You Have Entered Wrong Message ID...");
            return;
        }
        boolean run=true;
        while (run) {
            System.out.println("Payment of \u20b9 " + message.proposedPrice + " ......");
            System.out.println("Select Payment Options : ");
            System.out.println("1. UPI\n2. Debit Or Credit Card\n3. Cash on Delivery");
            int choice = 0;
            try{
                choice = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                choice=4;//explicitly providing a wrong choice
            }
            if (choice == 1) {
                System.out.println("You have Chosen UPI Payment.");
                run=false;
            } else if (choice == 2) {
                System.out.println("You have Chosen Debit Or Credit Card Payment.");
                run=false;
            } else if (choice == 3) {
                System.out.println("You have Chosen Cash on Delivery.");
                run=false;
            } else {
                System.out.println("Buddy Please Enter Valid Payment Mode!\n");
            }
        }
        message.status=3;
        message.toUserId = message.fromUserId;
        message.fromUserId = ClassifiedSession.user.id;
        if(messageDAO.insert(message)>0) {
            List<Classified> classifieds = classifiedDAO.retrieve("select * from classified where id=" + message.classifiedId);
            Classified classified = classifieds.get(0);
            classified.status = 3;
            classifiedDAO.update(classified);
            System.out.println("Buddy! Payment Was Successful.\n");
        }else {
            System.out.println("Payment Failed! Please Try Again Buddy...\n");
        }
    }
    public void deleteMyMessage() {
        System.out.print("Enter The Message ID : ");
        int messageId=Integer.parseInt(scanner.nextLine());
        String sql="select * from message where id="+messageId+" and toUserId="+ClassifiedSession.user.id;

        List<Message> messages=messageDAO.retrieve(sql);
        if(messages.isEmpty()){
            System.out.println("Invalid Message ID Buddy...");
            return;
        }
        int result=messageDAO.delete(messages.get(0));
        String text = (result > 0) ? "Buddy! Message Deleted Successfully" : "Deleting Message Failed. Please Try Again Buddy...";
        System.out.println(text);
    }
    public void allUserTransactions(){
        List<User> users=userDAO.retrieve();
        for(User user:users){
            System.out.println("\nALl The Transaction Of User : "+ user.name);
            transactions(user.id);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
    // debited -> message received with status 3 (payment done)
    public void transactions(int uid) {
        String sql="select * from message where toUserId="+uid+" and status="+3;
        List<Message> messages=messageDAO.retrieve(sql);
        int spent=0,recieved=0;
        System.out.println("\nPayment Received.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Message message:messages){
            System.out.println(message.proposedPrice + " \u20b9 on " + message.lastUpdateOn);
            recieved+=message.proposedPrice;
        }
        String sql2="select * from message where fromUserId="+uid+" and status= "+3;
        List<Message> messages2=messageDAO.retrieve(sql2);
        System.out.println("Payment Spent.");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Message message:messages2){
            System.out.println(message.proposedPrice+" \u20b9 On " + message.lastUpdateOn);
            spent+=message.proposedPrice;
        }
        System.out.println("\nTotal Money Spent : \u20b9 "+spent);
        System.out.println("Total Money Received : \u20b9 "+recieved+'\n');
    }
}
