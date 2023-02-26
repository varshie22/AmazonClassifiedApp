package com.amazon.Controller;

import com.amazon.DB.ClassifiedDAO;
import com.amazon.DB.DB;
import com.amazon.DB.UserDAO;
import com.amazon.Menus.ClassifiedSession;
import com.amazon.Model.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClassifiedManager {
    public static ClassifiedManager manager = new ClassifiedManager();

    public static ClassifiedManager getInstance(){
        return manager;
    }
    ClassifiedDAO classifiedDAO=new ClassifiedDAO();
    MessageManager messageManager=MessageManager.getInstance();
    Scanner scanner=new Scanner(System.in);
    private ClassifiedManager(){}
    public void postClassified(int status) {
        Classified classified = new Classified();
        classified.getDetails();
        classified.userId = ClassifiedSession.user.id;
        classified.status=status;
        int result=classifiedDAO.insert(classified);
        String message = (result > 0) ? "Hey Buddy! Your Classified Added Successfully\n" : "Adding Classified Failed! Try Again Buddy.\n";
        System.out.println(message);
    }


    public void updateClassified(int uid) {

        System.out.print("Enter Classified Id to be Updated : ");
        int classifiedId = Integer.parseInt(scanner.nextLine());
        String sql="select * from classified where id="+classifiedId+" and userId="+uid;
        List<Classified> classifieds=classifiedDAO.retrieve(sql);

        if(classifieds.isEmpty()){
            System.out.println("Hey Buddy! You Have Entered Wrong Classified ID Please Try Again.");
            return;
        }
        Classified classified=classifieds.get(0);
        System.out.println("~Press enter to skip the column update~");
        System.out.print("Enter Product Description : ");
        String productDescription = scanner.nextLine();
        if(!productDescription.isEmpty()){
            classified.productDescription=productDescription;
        }
        System.out.print("Enter Price : ");
        String price = scanner.nextLine();
        if(!price.isEmpty()){
            classified.price=Integer.parseInt(price);
        }
        System.out.print("Enter Images : ");
        String image = scanner.nextLine();
        if(!image.isEmpty()){
            classified.images=image;
        }
        int result = classifiedDAO.update(classified);
        String message = (result > 0) ? "Hey Buddy! Classified Updated Successfully" : "Updating Classified Failed Please Try Again Buddy...";
        System.out.println(message);
    }

    public void deleteClassified(int uid) throws NumberFormatException{

        System.out.print("Enter Classified Id to be Deleted : ");
        int categoryId = Integer.parseInt(scanner.nextLine());

        String sql="select * from classified where id="+categoryId+" and userId="+uid;
        List<Classified> classifieds=classifiedDAO.retrieve(sql);
        if(classifieds.isEmpty()){
            System.out.println("Hey Buddy ! You Have Entered Wrong Classified ID Please Try Again...");
            return;
        }
        Classified classified=classifieds.get(0);
        int result = classifiedDAO.delete(classified);
        String message = (result > 0) ? "Hey Buddy! Classified Deleted Successfully" : "Deleting Classified Failed Try Again Buddy...";
        System.out.println(message);
    }
    public void approveRejectClassified(int classifiedId) throws NumberFormatException {

        List<Classified> classifieds=classifiedDAO.retrieve("select * from classified where id="+classifiedId);
        if(classifieds.isEmpty()){
            System.out.print("Enter a Valid Classified ID : ");
            return;
        }
        Classified classified= new Classified();
        classified.id = classifiedId;
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.print("Enter Approval Choice Buddy : ");
        int choice=Integer.parseInt(scanner.nextLine());
        if(choice !=1 && choice!=2){
            System.out.println("Enter a valid choice Buddy...");
            return;
        }
        classified.status = choice;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        classified.createdOn = dateFormat.format(date1);
        int result = classifiedDAO.update(classified);
        String message = (result > 0) ? "Hey Buddy! Classified Request Updated Successfully" : "Updating Classified Request Failed Please Try Again Buddy..";
        System.out.println(message);
    }
    //1 admin
    public void viewClassified(int userType) {
        //status 1 is for approved
        CategoryManager.getInstance().categoryCount();
        boolean run=true;
        int categoryId=0;
        List<Classified> classifieds=new ArrayList<>();
        while (run) {
            System.out.print("Select The Category (ID) : ");
            categoryId = Integer.parseInt(scanner.nextLine());
            String sql = "select * from classified where categoryId=" + categoryId;
            classifieds = classifiedDAO.retrieve(sql);
            if (classifieds.isEmpty()) {
                System.out.println("Please Enter a Valid Category ID Buddy.");
            }else{
                run=false;
            }
        }
        for(Classified classified : classifieds) {
            if(userType==2 && classified.status!=1) {
                continue;//skipping the classified with status as rejected and requested
            }
            classified.prettyPrint();
        }
        run=true;
        int classifiedId=0;
        System.out.println("Hey Buddy Are You Interested in Buying...?");
        while (run) {
            System.out.print("Please Select a Classified by Entering Its ID or 0 If Not Interested: ");
            classifiedId = Integer.parseInt(scanner.nextLine());
            if(classifiedId==0)return;
            String sql="select * from classified where id="+classifiedId;
            System.out.println(sql);
            List<Classified> classifieds1=classifiedDAO.retrieve(sql);
            if (classifieds1.get(0).categoryId != categoryId) {
                System.out.println("The Entered Classified ID Does Not Belong to the Chosen Category Buddy.");
                System.out.println("\n~Please Try Again Buddy~");
            }else
                run=false;
        }
        if (classifiedId!=0){
            messageManager.sendMessage(0,classifiedId);//status 0 means request or offer
        }
    }
    public void viewClassifiedRequests() {
        String sql = "SELECT * from Classified where status="+0;
        List<Classified> objects = classifiedDAO.retrieve(sql);
        if (objects.isEmpty()){
            System.out.println("No Requests...");
        }
        for(Classified object : objects) {
            object.prettyPrint();
        }
    }
    public void viewClassifiedByUser(int uid) {
        String sql = "SELECT * from Classified where userId = "+uid;
        List<Classified> objects = classifiedDAO.retrieve(sql);
        for(Classified object : objects) {
            object.prettyPrint();
        }
    }
    public void viewClassifiedRequestsByUser(int uid) {
        String sql = "SELECT * from Classified where userId = "+uid+" and status= "+0;
        List<Classified> objects = classifiedDAO.retrieve(sql);
        for(Classified object : objects) {
            object.prettyPrint();
        }
    }
    public void userClassifiedCount(){
        UserDAO dao=new UserDAO();
        List<User> users=dao.retrieve();
        for(User user:users){
            String sql="select count(id) from classified where userId="+user.id;
            DB db=DB.getInstance();
            int classifiedCount=0;
            ResultSet resultSet= db.executeQuery(sql);
            try{
                while (resultSet.next()){
                    classifiedCount=resultSet.getInt(1);
                    System.out.println("User "+ user.name +" Has Posted "+ classifiedCount + " Classified.");
                }
            }
            catch (Exception e){
                System.out.println("OOPS...! Something Went Wrong Buddy "+e);
            }
        }
    }
    public void classifiedSoldSellCount(){
        List<Classified> classifieds=classifiedDAO.retrieve();
        int classifiedSoldCount=0;
        int classifiedOnSellCount=0;
        int classifiedRequestCount=0;
        for(Classified classified:classifieds){
            if(classified.status==3){
                classifiedSoldCount++;
            }else if(classified.status==1){
                classifiedOnSellCount++;
            } else if (classified.status==0) {
                classifiedRequestCount++;
            }
        }
        int classifiedRejectedCount=classifieds.size()-classifiedSoldCount-classifiedOnSellCount-classifiedRequestCount;
        System.out.println("\nTotal Classified : "+classifieds.size());
        System.out.println("Total Classified Sold : " +classifiedSoldCount);
        System.out.println("Total Classified On Sell : "+ classifiedOnSellCount);
        System.out.println("Total Classified Rejected : "+ classifiedRejectedCount);
        System.out.println("Total Classified on Request : "+ classifiedRequestCount);
    }
}
