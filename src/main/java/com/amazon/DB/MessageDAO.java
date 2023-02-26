package com.amazon.DB;
//This Class contains crud operations for Message DAO

import com.amazon.Model.Message;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements DAO<Message>{
    DB db=DB.getInstance();
    @Override
    public int insert(Message object) {
        String sql= "INSERT INTO Message (classifiedId, fromUserId, toUserId, ProposedPrice, status)"
                + "VALUES("+object.classifiedId+", "+object.fromUserId+", "+object.toUserId+", "
                + ""+object.proposedPrice+", "+object.status+")";
        return db.executeSQL(sql);
    }

    @Override
    public int update(Message object) {
        String sql = "UPDATE Message set classifiedId='"+object.classifiedId+"', fromUserId='"+object.fromUserId+"', toUserId='"+object.toUserId+"', " +
                "ProposedPrice='"+object.proposedPrice+"', status='"+object.status+"', status='"+object.status+"' WHERE id ='"+object.id+"'" ;
        return db.executeSQL(sql);
    }

    @Override
    public int delete(Message object) {
        String sql = "DELETE FROM Message WHERE id = '"+object.id+"'";
        return db.executeSQL(sql);
    }
    //to retrieve all the information from the Message database
    @Override
    public List<Message> retrieve() {
        String sql = "SELECT * from Message";
        ResultSet set = db.executeQuery(sql);
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            while(set.next()) {
                Message message = new Message();
                // Read the row from ResultSet and put the data into User object
                message.id = set.getInt("id");
                message.classifiedId = set.getInt("classifiedId");
                message.fromUserId = set.getInt("fromUserId");
                message.toUserId = set.getInt("toUserId");
                message.proposedPrice = set.getInt("proposedPrice");
                message.status = set.getInt("status");
                message.lastUpdateOn = set.getString("lastUpdateOn");
                messages.add(message);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return messages;
    }
    //to retrieve some particular information from the Message database
    @Override
    public List<Message> retrieve(String sql) {
        ResultSet set = db.executeQuery(sql);
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            while(set.next()) {
                Message message = new Message();
                // Read the row from ResultSet and put the data into User object
                message.id = set.getInt("id");
                message.classifiedId = set.getInt("classifiedId");
                message.fromUserId = set.getInt("fromUserId");
                message.toUserId = set.getInt("toUserId");
                message.proposedPrice = set.getInt("proposedPrice");
                message.status = set.getInt("status");
                message.lastUpdateOn = set.getString("lastUpdateOn");
                messages.add(message);
            }
        }catch(Exception e) {
            System.out.println("Something Went Wrong...."+e);
        }
        return messages;
    }
}
