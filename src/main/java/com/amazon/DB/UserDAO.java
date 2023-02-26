package com.amazon.DB;
// This Class contains crud Operation of User DAO

import com.amazon.Model.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {
    DB db=DB.getInstance();
    @Override
    public int insert(User object) {
        String sql= "INSERT INTO User (name, phone, email, password , address, department, type, status)"
                + "VALUES('"+object.name+"', '"+object.phone+"', '"+object.email+"', "
                + "'"+object.password+"', '"+object.address+"', '"+object.department+"', '"+object.type+"', "+object.status+")";
        return db.executeSQL(sql);
    }
    @Override
    public int update(User object) {
        String sql = "UPDATE User set name='"+object.name+"', phone='"+object.phone+"', password='"+object.password+"', " +
                "address='"+object.address+"', department='"+object.department+"', status='"+object.status+"' WHERE email ='"+object.email+"'" ;
        return db.executeSQL(sql);
    }
    @Override
    public int delete(User object) {
        String sql = "DELETE FROM User WHERE email = '"+object.email+"'";
        return db.executeSQL(sql);
    }
    //to retrieve all the information of user from the database
    @Override
    public List<User> retrieve() {

        String sql = "SELECT * from User";
        ResultSet set = db.executeQuery(sql);
        ArrayList<User> users = new ArrayList<User>();
        try {
            while(set.next()) {
                User user = new User();
                // Read the row from ResultSet and put the data into User object
                user.id = set.getInt("id");
                user.name = set.getString("name");
                user.phone = set.getString("phone");
                user.email = set.getString("email");
                user.password = set.getString("password");
                user.address = set.getString("address");
                user.department = set.getString("department");
                user.type = set.getInt("type");
                user.status = set.getInt("status");
                user.createOn = set.getString("createdOn");
                users.add(user);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong..."+e);
        }
        return users;
    }
    //to retrieve some particular information from the user database
    @Override
    public List<User> retrieve(String sql){
        ResultSet set = db.executeQuery(sql);
        ArrayList<User> users = new ArrayList<User>();
        try {
            while(set.next()) {
                User user = new User();
                user.id =set.getInt("id");
                user.name = set.getString("name");
                user.phone = set.getString("phone");
                user.email = set.getString("email");
                user.password = set.getString("password");
                user.address = set.getString("address");
                user.department = set.getString("department");
                user.type = set.getInt("type");
                user.status = set.getInt("status");
                user.createOn= set.getString("createdOn");
                users.add(user);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return users;
    }
}
