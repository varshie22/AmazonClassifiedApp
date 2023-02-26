package com.amazon;

import com.amazon.Controller.AuthenticationManager;
import com.amazon.Controller.ClassifiedManager;
import com.amazon.DB.ClassifiedDAO;
import com.amazon.DB.DB;
import com.amazon.Model.Classified;
import com.amazon.Model.User;
import org.junit.Assert;
import org.junit.Test;

public class AppTest{
    AuthenticationManager authenticationManager=AuthenticationManager.getInstance();
    ClassifiedManager classifiedManager= ClassifiedManager.getInstance();
    ClassifiedDAO classifiedDAO=new ClassifiedDAO();
    public AppTest(){
        DB.getInstance().loadDriver();
    }
    @Test
    public  void testUserLogin(){
        User user=new User();
        user.email="sam@abc.com";
        user.password="pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=";
        boolean result=authenticationManager.loginUser(user);
        Assert.assertEquals(true,result);
        Assert.assertEquals(2,user.type);
    }
    @Test
    public  void testAdminLogin(){
        User user=new User();
        user.email="varsha@abc.com";
        user.password="123";
        boolean result=authenticationManager.loginUser(user);
        Assert.assertEquals(true,result);
        Assert.assertEquals(1,user.type);
    }
    @Test
    public  void testPostingClassified(){
        //if we post a classified with a userID which is not in the database or
        // with a category which also not exist in the databse then this tes will fail
        Classified classified=new Classified();
        classified.userId=2;
        classified.status=1;
        classified.price=100;
        classified.categoryId=4;
        int result=classifiedDAO.insert(classified);
        Assert.assertEquals(true,result>0);
    }


}

