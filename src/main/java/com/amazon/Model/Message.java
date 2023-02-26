package com.amazon.Model;
//This Class is the Model of Message Which Contains Attributes Of Class Message with Constructor and toString
//And Two More Functions - Pretty Print(Which Prints On Console) & GetDetails(For Taking Inputs from User).

/*
 * SQL QUERY FOR TABLE MESSAGE
MYSQL:
 create table Message(
     id INT PRIMARY KEY AUTO_INCREMENT,
     classifiedId INT,
     fromUserId INT,
     toUserId INT,
     proposedPrice INT,
     status INT,
     lastUpdateOn DATETIME DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (classifiedId) REFERENCES classified(id),
     FOREIGN KEY (fromUserId) REFERENCES User(id),
     FOREIGN KEY (toUserId) REFERENCES User(id)
 );
*/

import java.util.Scanner;

public class Message {
    public int id;
    public int classifiedId;
    public int fromUserId;
    public int toUserId;
    public int proposedPrice;
    public int status;//
    public String lastUpdateOn;
    public Message(){
    }
    public Message(int id, int classifiedId, int fromUserId, int toUserId, int proposedPrice, int status, String lastUpdateOn) {
        this.id = id;
        this.classifiedId = classifiedId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.proposedPrice = proposedPrice;
        this.status = status;
        this.lastUpdateOn = lastUpdateOn;
    }
    public void getDetails(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Capturing Message Details....");
        System.out.print("Enter Proposed Price : ");
        proposedPrice=Integer.parseInt(scanner.nextLine());
    }
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", classifiedId=" + classifiedId +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", ProposedPrice=" + proposedPrice +
                ", status=" + status +
                ", lastUpdateOn=" + lastUpdateOn +
                '}';
    }
}
