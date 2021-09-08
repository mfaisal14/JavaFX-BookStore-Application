/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapp;

/**
 *
 * @author Miguel
 */
public class ownerCustomers {
    public String username;
    public String password;
    public double points;
    public State state;    

    
   
    
    public ownerCustomers(){
        this.username = "";
        this.password = "";
        this.points = 0;
    }
    
    public ownerCustomers(String username, String password, double points){
        this.username = username;
        this.password = password;
        this.points = points;
        if (this.points < 1000) {
            state = new Silver();
        } else {
            state = new Gold();
        }
                
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public double getPoints() {
        return this.points;
    }
    
    public void makeGold() {
        state.makeGold(this);
    }
    
    public void makeSilver() {
        state.makeSilver(this);
    }
    
    public void setState(State s) {
        state = s;
    }
    
    public State getStatus() {
        if (points < 1000) {
            makeSilver();
        }
        else {
            makeGold();
        }
        return state;
    }
    
    public String toString() {
        return (this.username + "\t" + this.password + "\t" + this.points + "\t" + state.toString() + "\n");
    }
    
}

