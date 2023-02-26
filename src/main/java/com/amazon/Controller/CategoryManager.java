package com.amazon.Controller;

import com.amazon.DB.CategoryDAO;
import com.amazon.DB.DB;
import com.amazon.Menus.ClassifiedSession;
import com.amazon.Model.Category;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
public class CategoryManager {
    public static CategoryManager categoryManager = new CategoryManager();
    public static CategoryManager getInstance(){
        return categoryManager;
    }
    CategoryDAO dao =new CategoryDAO();
    Scanner scanner=new Scanner(System.in);
    private CategoryManager(){
    }
    public void addCategory() {
        Category category = new Category();
        category.getDetails();
        category.adminId = ClassifiedSession.user.id;
        int result=dao.insert(category);
        String message = (result > 0) ? "Buddy Category Added Successfully" : "Adding Category Failed. Please Try Again Buddy...";
        System.out.println(message);
    }
    public void deleteCategory() throws NumberFormatException{
        Category category = new Category();
        System.out.print("Enter Category ID to be Deleted : ");
        category.id = Integer.parseInt(scanner.nextLine());
        int result = dao.delete(category);
        String message = (result > 0) ? "\nBuddy Category Deleted Successfully" : "\nDeleting Category Failed. Try Again Buddy...";
        System.out.println(message);
    }
    public void updateCategory() throws NumberFormatException{
        System.out.print("Enter Category ID to be Updated: ");
        int id=Integer.parseInt(scanner.nextLine());
        CategoryDAO dao=new CategoryDAO();
        String sql="Select * from Category Where id="+id;
        List<Category> categories=dao.retrieve(sql);
        if(categories.size()>0){
            Category category=categories.get(0);
            System.out.print("Enter The Title : ");
            String title= scanner.nextLine();
            if(!title.isEmpty()){
                category.title=title;
            }
            System.out.print("Enter Description : ");
            String description=scanner.nextLine();
            if(!description.isEmpty()){
                category.description=description;
            }
            if(dao.update(category)>0){
                System.out.println("\nHey Buddy! Category Updated Successfully");
            }
            else {
                System.out.println("\nHey Buddy! Category Not Updated Please Try Again...");
            }
        }
        else {
            System.out.println("Updating Category Failed. Try Again Buddy...");
        }
    }
    public void viewCategory() {
        List<Category> categories = dao.retrieve();
        for(Category object : categories) {
            object.prettyPrint();
        }
    }
    public void categoryCount(){
        CategoryDAO dao=new CategoryDAO();
        List<Category> categories=dao.retrieve();
        for(Category category:categories){
            String sql="select count(id) from classified where categoryId="+category.id;
            DB db=DB.getInstance();
            int catergoryCount=0;
            ResultSet resultSet= db.executeQuery(sql);
            try{
                while (resultSet.next()){
                    catergoryCount=resultSet.getInt(1);
                    if(catergoryCount>0){
                        System.out.println("Category ID "+ category.id+ " Of "+category.title + " Has Total "+catergoryCount + " Classifieds\n");
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
