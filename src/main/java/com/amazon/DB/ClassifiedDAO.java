package com.amazon.DB;

import com.amazon.Model.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ClassifiedDAO implements DAO<Classified> {
    DB db = DB.getInstance();
    @Override
    public int insert(Classified object) {
        String sql = "INSERT INTO classified (headline, productName, brandName, productCondition, productDescription, " +
                "price, images,  categoryId, status, userId) "
                + "VALUES ( '"+object.headline+"', '"+object.productName+"', '"+object.brandName+"', " +
                "'"+object.productCondition+"', '"+object.productDescription+"', "+object.price+",'"+object.images+"', "+object.categoryId+", " +
                ""+object.status+", "+object.userId+")";
        return db.executeSQL(sql);
    }

    @Override
    public int update(Classified object) {
        String sql = "UPDATE classified set price = "+object.price+",  productDescription = '"+object.productDescription+"', images = '"+object.images+"',status = "+object.status +" WHERE id = "+object.id;
            return db.executeSQL(sql);
    }

    @Override
    public int delete(Classified object) {
        String sql = "DELETE FROM Classified WHERE id = '"+object.id+"'";
        return db.executeSQL(sql);
    }
    //to retrieve all information from the Classified database
    @Override
    public List<Classified> retrieve() {
        String sql = "SELECT * from classified";
        ResultSet set = db.executeQuery(sql);
        ArrayList<Classified> classifieds = new ArrayList<Classified>();
        try {
            while(set.next()) {
                Classified classified=new Classified();
                // Read the row from ResultSet and put the data into User object
                classified.id = set.getInt("id");
                classified.userId = set.getInt("userId");
                classified.headline = set.getString("headline");
                classified.productName = set.getString("productName");
                classified.brandName = set.getString("brandName");
                classified.productCondition = set.getString("productCondition");
                classified.productDescription = set.getString("productDescription");
                classified.price = set.getInt("price");
                classified.images = set.getString("images");
                classified.categoryId = set.getInt("categoryId");
                classified.status = set.getInt("status");
                classified.createdOn = set.getString("createdOn");
                classifieds.add(classified);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return classifieds;
    }
    //to retrieve some particular information from the classified database
    @Override
    public List<Classified> retrieve(String sql) {
        ResultSet set = db.executeQuery(sql);
        ArrayList<Classified> classifieds = new ArrayList<Classified>();

        try {

            while(set.next()) {
                Classified classified=new Classified();
                // Read the row from ResultSet and put the data into User object
                classified.id = set.getInt("id");
                classified.userId = set.getInt("userId");
                classified.headline = set.getString("headline");
                classified.productName = set.getString("productName");
                classified.brandName = set.getString("brandName");
                classified.productCondition = set.getString("productCondition");
                classified.productDescription = set.getString("productDescription");
                classified.price = set.getInt("price");
                classified.images = set.getString("images");
                classified.categoryId = set.getInt("categoryId");
                classified.status = set.getInt("status");
                classified.createdOn = set.getString("createdOn");
                classifieds.add(classified);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return classifieds;
    }
}
