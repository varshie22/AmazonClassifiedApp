package com.amazon.Model;
//This Class is the Model of User Which Contains Attributes Of Class User with Constructor and toString
//And Two More Functions - Pretty Print(Which Prints On Console).

/*
 * SQL QUERY FOR TABLE USER
MYSQL:
 create table User(
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(256),
     phone VARCHAR(256),
     email VARCHAR(256),
     password VARCHAR(256),
     address VARCHAR(256),
     department VARCHAR(256),
     type INT,
     status INT,
     createdOn DATETIME DEFAULT CURRENT_TIMESTAMP
 );
*/

public class User {
    public int id;
    public String name;
    public String phone;
    public String email;
    public String password;
    public String address;
    public String department;
    public String createOn;
    public int type;
    public int status;

    public User(){
    }
    public User(int id, String name, String phone, String email, String password,
                String address, String department,String createOn, int type, int status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.department = department;
        this.createOn = createOn;
        this.type = type;
        this.status=status;
    }
    public void prettyPrint() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        System.out.println("ID \t\t\t\t: " + id);
        System.out.println("Name \t\t\t: " + name);
        System.out.println("Phone \t\t\t: " + phone);
        System.out.println("Email \t\t\t: " + email);
        System.out.println("Address  \t\t: " + address);
        if(department!=null) {
            System.out.println("Department \t\t: " + department);
        }
        System.out.println("Created On \t\t: " + createOn);
        System.out.print("Status \t\t\t: ");
        if(status==0){
            System.out.println("Deactivated");
         }else{
            System.out.println("Activated");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", createOn='" + createOn + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
