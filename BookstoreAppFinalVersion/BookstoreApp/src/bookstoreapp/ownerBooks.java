package bookstoreapp;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/*
 * @author Abdul Mahir Mena Miguel 
 */
public class ownerBooks {
    
    private String name;
    private double price;
  
    public ownerBooks(){
        this.name = "";
        this.price = 0;       
       
    }
    
    public ownerBooks(String name, double price){
        this.name = name;
        this.price = price;
                
    }
        
    public String getName(){
        return this.name;
    
    }
    
      public void setName(String name) {
        this.name = name;
        
    }

    public double getPrice() {
        return this.price;
        
    }

    public void setPrice(double price) {
        this.price = price;
        
    }

}