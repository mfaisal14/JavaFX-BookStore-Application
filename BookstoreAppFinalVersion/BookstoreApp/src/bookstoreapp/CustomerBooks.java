package bookstoreapp;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;

public class CustomerBooks {
    
    private String name;
    private double price;
    private CheckBox select;
    
    //private double points;
    
    public CustomerBooks() {
        this.name="";
        this.price=0.0;
    }
    
    public CustomerBooks(String books, double price){
        this.name=books;
        this.price=price;
        select = new CheckBox();
    }
    
    public String getBooks() {
        return name;
    }

    public void setBooks(String books) {
        this.name = books;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public CheckBox getSelect() {
        return select;
    }
    
    public void setSelect(CheckBox select) {
        this.select=select;
    }
    
}
