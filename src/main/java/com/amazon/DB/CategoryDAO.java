package com.amazon.DB;

import com.amazon.Model.Category;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class CategoryDAO implements DAO<Category>{
    DB db = DB.getInstance();
    @Override
    public int insert(Category object) {
        String sql= "INSERT INTO Category (title, description, adminId) " +
                "VALUES('"+object.title+"', '"+object.description+"', "+object.adminId+") ";
        return db.executeSQL(sql);
    }
    @Override
    public int update(Category object) {
        String sql = "UPDATE Category set title ='"+object.title+"', description='"+object.description+"' WHERE id ='"+object.id+"'" ;
        return db.executeSQL(sql);
    }
    @Override
    public int delete(Category object) {
        String sql = "DELETE FROM Category WHERE id = '"+object.id+"'";
        return db.executeSQL(sql);
    }
    //to retrieve all information from the category database
    @Override
    public List<Category> retrieve() {
        String sql = "SELECT * from Category";
        ResultSet set = db.executeQuery(sql);
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            while(set.next()) {
                Category category = new Category();
                // Read the row from ResultSet and put the data into User object
                category.id = set.getInt("id");
                category.title = set.getString("title");
                category.description = set.getString("description");
                category.adminId = set.getInt("adminId");
                category.lastUpdatedOn = set.getString("lastUpdateOn");
                categories.add(category);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return categories;
    }
    //to retrieve some particular information from the category database
    @Override
    public List<Category> retrieve(String sql) {
        ResultSet set = db.executeQuery(sql);
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            while(set.next()) {
                Category category = new Category();
                // Read the row from ResultSet and put the data into User object
                category.id = set.getInt("id");
                category.title = set.getString("title");
                category.description = set.getString("description");
                category.adminId = set.getInt("adminId");
                category.lastUpdatedOn = set.getString("lastUpdateOn");
                categories.add(category);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return categories;
    }

}

