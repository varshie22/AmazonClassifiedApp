package com.amazon.DB;

import java.sql.*;
import java.io.*;

public class DB {
    Connection con;
    Statement statement;
    public static String URL ="";
    public static String USER = "";
    public static String password = "";
    public static String FILEPATH="src/main/java/com/amazon/dbconfig.txt";

    private static DB db=new DB();
    public static DB getInstance() {
        return db;
    }
    private DB() {

    }
    public  void loadDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[db] Driver loaded successfully");
            createConnection();
        }catch(Exception e) {
            System.out.println("Something went wrong"+e);
        }
    }

    private void createConnection() {
        try {
            File file = new File(FILEPATH);
            if(file.exists()) {
                FileReader reader = new FileReader(file);
                BufferedReader buffer = new BufferedReader(reader);

                URL = buffer.readLine();
                USER = buffer.readLine();
                password = buffer.readLine();

                buffer.close();
                reader.close();
                System.out.println("DB configured using the file.");
            }else {
                System.err.println("Cannot Read the db Config File.......");
            }
            con = DriverManager.getConnection(URL, USER, password);
            System.out.println("[DB Connection created successfully...");
        }catch(Exception e) {
            System.out.println("Something went wrong"+e);
        }
    }
    public int executeSQL(String sql) {
        int result=0;
        try {
            statement=con.createStatement();
            result=statement.executeUpdate(sql);
        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("This record is invalid");
        }
        catch(Exception e) {
            System.out.println("Something went wrong");
        }
        return result;
    }
    public ResultSet executeQuery(String sql) {
        ResultSet set = null;
        try {
            statement = con.createStatement();
            set = statement.executeQuery(sql);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return set;
    }

    public void closeConnection() {
        try {
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}