package com.amazon.Model;
//This Class is the Model of Classified Which Contains Attributes Of Classified Category with Constructor and toString
//And Two More Functions - Pretty Print(Which Prints On Console) & GetDetails(For Taking Inputs from User).

/*
 * SQL QUERY FOR TABLE CLASSIFIED
 MYSQL:
 create table Classified(
     id INT PRIMARY KEY AUTO_INCREMENT,
     headline VARCHAR(100),
     productName VARCHAR(50),
     brandName VARCHAR(25),
     productCondition VARCHAR(256),
     productDescription VARCHAR(500),
     price INT,
     images VARCHAR (256),
     categoryId INT,
     status INT DEFAULT 0,
     userId INT,
     adminId INT,
     createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (adminId) REFERENCES User(id),
     FOREIGN KEY (userId) REFERENCES User(id),
     FOREIGN KEY (categoryId) REFERENCES Category(id)
    );
*/

import com.amazon.DB.CategoryDAO;
import java.util.List;
import java.util.Scanner;

public class Classified {
    public int id;
    public int userId;
    public String headline;
    public String productName;
    public String brandName;
    public String productCondition;
    public String productDescription;
    public int price;
    public String images;
    public int categoryId;

    public int status;
    public String createdOn;

    public Classified(){

    }
    public Classified(int id, int userId, String headline, String productName, String brandName,
                      String productCondition, String productDescription, int price, String images, int categoryId,
                      int status,String createdOn) {
        this.id = id;
        this.userId = userId;
        this.headline = headline;
        this.productName = productName;
        this.brandName = brandName;
        this.productCondition = productCondition;
        this.productDescription = productDescription;
        this.price = price;
        this.images=images;
        this.categoryId = categoryId;
        this.status = status;
        this.createdOn = createdOn;
    }
    public void prettyPrint() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("Id \t\t\t\t\t\t: " + id);
        System.out.println("Headline \t\t\t\t: " + headline);
        System.out.println("Product Name \t\t\t: " + productName);
        System.out.println("Brand Name \t\t\t\t: " + brandName);
        System.out.println("Product Condition \t\t: " + productCondition);
        System.out.println("Product Description \t: " + productDescription);
        System.out.println("Price \t\t\t\t\t: \u20b9 " + price);
        System.out.println("Images \t\t\t\t\t: " +images);
        System.out.print("Category \t\t\t\t: ");
        CategoryDAO dao=new CategoryDAO();
        String sql="select * from category where id="+categoryId;
        List<Category> categories=dao.retrieve(sql);
        Category category=categories.get(0);
        System.out.println(category.title);
        String statusText = "";
        if(status == 0) {
            statusText = "Requested";
        }else if (status == 1) {
            statusText = "Approved";
        }else if (status == 2) {
            statusText = "Rejected";
        }else {
            statusText = "Sold";
        }
        System.out.println("Status \t\t\t\t\t: "+statusText);
        System.out.println("User Id \t\t\t\t: " + userId);
        System.out.println("Created On \t\t\t\t: " + createdOn);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
    public void getDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Capturing classified  Details....");

        System.out.print("Enter Classified Headline : ");
        headline=scanner.nextLine();

        System.out.print("Enter Product Name : ");
        productName =scanner.nextLine();

        System.out.print("Enter Brand Name : ");
        brandName =scanner.nextLine();
        boolean run=true;
        while (run) {
            System.out.println("Product Condition options : ");
            System.out.println("1: Brand New (Sealed Pack)");
            System.out.println("2: Lightly Used");
            System.out.println("3: Moderately Used");
            System.out.println("4: Heavily Used");
            System.out.println("5: Damaged or Dented");
            System.out.println("6: Not Working");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("Please Enter Your choice: ");
            int condition = 0;
            try{
                condition = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                condition=7;//explicitly providing a wrong choice
            }
            switch (condition) {
                case 1:
                    productCondition = "Brand New";
                    run=false;
                    break;
                case 2:
                    productCondition = "Lightly Used";
                    run=false;
                    break;
                case 3:
                    productCondition = "Moderately Used";
                    run=false;
                    break;
                case 4:
                    productCondition = "Heavily Used";
                    run=false;
                    break;
                case 5:
                    productCondition = "Damaged / Dented";
                    run=false;
                    break;
                case 6:
                    productCondition = "Not Working";
                    run=false;
                    break;
                default:
                    System.out.println("Invalid Choice ! Please Select a Valid Option");
            }
        }
        System.out.print("Enter Product Description : ");
        productDescription =scanner.nextLine();

        System.out.print("Enter Product Price : ");
        price =Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Image Link : ");
        images=scanner.nextLine();

        System.out.println("~Product Categories~");
        CategoryDAO dao=new CategoryDAO();
        List<Category> categories=dao.retrieve();
        for(Category category:categories){
            System.out.println("ID "+ category.id+ " Of "+category.title);
        }
        run=true;
        while (run) {
            System.out.print("Enter Product category id from the options above : ");
            categoryId = Integer.parseInt(scanner.nextLine());
            List<Category> list = dao.retrieve("select * from category where id=" + categoryId);
            if (list.isEmpty()) {
                System.out.println("You have chosen a wrong category.");
            } else {
                run = false;
            }
        }
    }

    @Override
    public String toString() {
        return "Classified{" +
                "id=" + id +
                ", userId=" + userId +
                ", headline='" + headline + '\'' +
                ", productName='" + productName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", productCondition='" + productCondition + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                ", categoryId=" + categoryId +
                ", status=" + status +
                ", createdOn='" + createdOn + '\'' +
                '}';
    }
}
