package com.amazon.Model;
//This Class is the Model of Category Which Contains Attributes Of Class Category with Constructor and toString
//And Two More Functions - Pretty Print(Which Prints On Console) & GetDetails(For Taking Inputs from User).

/*
 * SQL QUERY FOR TABLE CATEGORY
MYSQL:
   create table Category(
		 id INT PRIMARY KEY AUTO_INCREMENT,
		 title VARCHAR(256),
		 description VARCHAR(256),
		 adminId INT,
		 lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
		 FOREIGN KEY (adminId) REFERENCES User(id)
	 );
*/

import java.util.Scanner;

public class Category {
    public int id;
    public String title;
    public String description;
    public int adminId;
    public String lastUpdatedOn;
    public Category(){
    }
    public Category(int id, String title,String description, int adminId,String lastUpdatedOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.adminId = adminId;
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public void getDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Capturing Category Details....");

        System.out.print("Enter Category Title : ");
        title=scanner.nextLine();

        System.out.print("Enter Category Description : ");
         description=scanner.nextLine();
    }
    public void prettyPrint() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Id \t\t\t\t\t : " + id);
        System.out.println("Title \t\t\t\t : " + title);
        System.out.println("Description \t\t : " + description);
        System.out.println("Admin Id \t\t\t : " + adminId);
        System.out.println("Last Updated On \t : " + lastUpdatedOn);
    }
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", adminId=" + adminId +
                ", lastUpdatedOn='" + lastUpdatedOn + '\'' +
                '}';
    }
}
