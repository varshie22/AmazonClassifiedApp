Drop database classifiedDB;
create database ClassifiedDB;

use classifiedDB;

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

  create table Category(
 	 id INT PRIMARY KEY AUTO_INCREMENT,
 	 title VARCHAR(256),
 	 description VARCHAR(256),
	 adminId INT,
 	 lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
 	 FOREIGN KEY (adminId) REFERENCES User(id)
  );

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


INSERT INTO user (name,phone,email,password,address,department,type,status)
               VALUES("Varsha Singh","963852741","varsha@abc.com","123","Green Park","Admin",1,1),
               ("Sam","987654321","sam@abc.com","123","Red Park","Dev",2,1),
               ("Hector","951847623","hector@abc.com","123","Yellow Park","HR",2,1),
               ("Naksh","963741258","naksh@abc.com","123","White Park","Transportation",2,1),
               ("Suraj","968574123","suraj@abc.com","123","Black Park","driver",2,1),
               ("Shobhit","968558723","shobhit@abc.com","123","Lavender Park","Dev",2,1),
               ("Jaya","965874123","jaya@abc.com","123","Blue Park","CS",2,1),
               ("Aastha","986532147","aastha@abc.com","123","Pink Park","CS",2,1);



INSERT INTO Category (title,description,adminId)
            VALUES("Mobile & Laptop","All kind of Mobile & Laptop",1),
            ("Furniture","all household Furniture",1),
            ("Bikes","All kind of Bikes",1),
            ("Cars","All kind of Cars",1),
            ("Electronics & Appliances","All kind of Electronics",1),
            ("Books","All kind of Books",1),
            ("Sports","All kind of Sports supplies",1),
            ("Fashion & Accessories","Men & Women Accessories",1),
            ("Properties","Buy/Sell/Rent",1),
            ("Pets & Supply","PetFood & Accessories",1),
            ("Gardening","All kind of Gardening supply",1);







