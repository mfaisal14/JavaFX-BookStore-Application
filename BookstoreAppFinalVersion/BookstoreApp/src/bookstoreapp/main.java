package bookstoreapp;


import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import javafx.scene.control.CheckBox;
/**
 *
 * @author Abdul Mahir Mena Miguel 
 */
public class main extends Application {
    Button Loginbtn;
    
    Button Logoutbtn;
    Button Booksbtn;
    Button Customersbtn;
    Button DeleteBookbtn ;
    Button BackBookbtn ;
    Button AddBookbtn ;
    Button DeleteCustomerbtn ;
    Button BackCustomerbtn ;
    Button AddCustomerbtn ;
    TextField bookName;
    TextField bookPrice;
    TextField customerUsername;
    TextField customerPassword;
    
    Scene LoginScene;
    GridPane Login;
    TextField tfUser;
    PasswordField pfPass;
    
    Button buybtn;
    Button redeemNBuybtn;
    
    ObservableList<CustomerBooks> customerBooksList = FXCollections.observableArrayList();
    TableView<CustomerBooks> customerBooksTable;
    // Customer details
    Label welcomeMsg;
    //purchase screen
    Label totalCostMsg;
    Label PointsMsg;
    
    private File k = new File("books.txt");
    
    Text errorMsg = new Text("\n\nError! Username or password is invalid");
    Text priceError = new Text("\n\nError! price is invalid");
    //boolean isValidbook;
    //boolean isValidcustomer;
    
    ObservableList<ownerBooks> ownerBookList = FXCollections.observableArrayList();
    TableView<ownerBooks> OwnerBookstable;
    
    ObservableList<ownerCustomers> ownerCustomerList = FXCollections.observableArrayList();
    TableView<ownerCustomers> OwnerCustomerstable;
    
    private ArrayList<ownerCustomers> customersList = new ArrayList<ownerCustomers>();
    private ArrayList<ownerCustomers> booksList = new ArrayList<ownerCustomers>();
    private ArrayList<CustomerBooks> customerbooksList = new ArrayList<CustomerBooks>();
    
    private File f = new File("customers.txt");
    private File g = new File("books.txt");
    
    ownerCustomers customer;
    
    public void isValidLogin(TextField user, PasswordField pass, Stage primaryStage, Scene ownerStartScene, GridPane grid, Scene customerStartScreen){        
        //boolean isValidLog = false;
        boolean isValidUser = false;
        
        //Customer and Admin checks
        if(!user.getText().equals("admin") && !pass.getText().equals("admin")){
            for(int i = 0; i < customersList.size(); i++){
                String user_text = user.getText();
                String pass_text = pass.getText();
                customer = customersList.get(i);
                System.out.println(user_text + " : " + pass_text);
                if(user_text.equals(customer.getUsername()) && pass_text.equals(customer.getPassword())){
                    isValidUser = true;
                    primaryStage.setScene(customerStartScreen);
                    welcomeMsg.setText(customer.toString());
                    System.out.println(isValidUser);
                }  else{
                    //isValidUser = false;
                    if(!grid.getChildren().contains(errorMsg)) grid.getChildren().add(errorMsg); //doesn't already contain it, prevents overlapping
                    System.out.println(isValidUser);           
                }
            }
        } else if(user.getText().equals("admin") && pass.getText().equals("admin")){
            primaryStage.setScene(ownerStartScene);
            isValidUser = true;
            System.out.println(isValidUser);
        } else{
            //isValidUser = false;
            if(!grid.getChildren().contains(errorMsg)) grid.getChildren().add(errorMsg); 
            System.out.println(isValidUser);
        }
    }
    
    public void createCustomerData(){
       try{
            customersList.clear();
            Scanner s = new Scanner(f);
            while(s.hasNextLine()){
                String[] arr = s.nextLine().split("\t");
                customersList.add(new ownerCustomers(arr[0], arr[1], Double.parseDouble(arr[2])));
            }
        } catch(IOException e){
            System.out.println("Error occurred");
        }   
    }
    
    public Button createLogoutButton(Stage primaryStage){
        Button buttonLO = new Button();
        buttonLO.setText("Logout");
        buttonLO.setOnAction(e-> { // multiline lambda, first checks and removes login error message if needed, then changes scene to Loginscene
                if(Login.getChildren().contains(errorMsg)) Login.getChildren().remove(errorMsg);
                tfUser.clear();
                pfPass.clear();
                createCustomerData();
                customerBooksTable.setItems(getCustomerBooks()); 
                primaryStage.setScene(LoginScene);
        });
        return buttonLO;
    }
    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Login Screen
        createCustomerData();
        //getOwnerCustomers();
        primaryStage.setTitle("Bookstore App");
        Loginbtn = new Button();
        Loginbtn.setText("Login");
        Login = new GridPane();
        Login.setPadding(new Insets(10, 10, 10, 10));
        Login.setVgap(8);
        Login.setHgap(10);
        Login.setAlignment(Pos.CENTER);
        Label userLabel = new Label("Username: ");
        Label passLabel = new Label("Password: ");
        tfUser = new TextField();
        pfPass = new PasswordField();
        Text sceneTitle = new Text("Welcome to the BookStore App");
        GridPane.setConstraints(sceneTitle, 0, 0);
        GridPane.setConstraints(userLabel, 0, 1);
        GridPane.setConstraints(tfUser, 1, 1);
        GridPane.setConstraints(passLabel, 0, 2);
        GridPane.setConstraints(pfPass, 1, 2);
        GridPane.setConstraints(Loginbtn, 1, 3);
        Login.getChildren().addAll(sceneTitle,userLabel,tfUser,passLabel,pfPass, Loginbtn);
        LoginScene = new Scene(Login,850,450);
        
        //Owner Start Screen
        GridPane ownerStart = new GridPane();
        ownerStart.setPadding(new Insets(10, 10, 10, 10));
        ownerStart.setVgap(30);
        Login.setHgap(10);
        ownerStart.setAlignment(Pos.CENTER);
        Booksbtn = new Button();
        Customersbtn = new Button();
        Logoutbtn = createLogoutButton(primaryStage); // Calling function to create Logout button
        Booksbtn.setText("Books");
        Customersbtn.setText("Customers");
        GridPane.setConstraints(Booksbtn, 0, 0);
        GridPane.setConstraints(Customersbtn, 0, 1);
        GridPane.setConstraints(Logoutbtn, 0, 2);
        ownerStart.getChildren().addAll(Booksbtn, Customersbtn, Logoutbtn);
        Scene ownerStartScene = new Scene(ownerStart,850,425);
        
        
        //owner books screen
        //delete, add, list books
        
        //name column
        TableColumn<ownerBooks, String> nameColumn = new TableColumn<>("Book Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<ownerBooks, String>("name"));
        
        //book price column
        TableColumn<ownerBooks, Double> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<ownerBooks, Double>("price"));
        
        OwnerBookstable = new TableView<>();
        OwnerBookstable.setItems(getOwnerBooks()); 
        OwnerBookstable.getColumns().addAll(nameColumn, priceColumn);
        
        GridPane ownerBookStart = new GridPane();
        ownerBookStart.setPadding(new Insets(10, 10, 10, 10));
        ownerBookStart.setVgap(20);
        ownerBookStart.setHgap(10);
        ownerBookStart.setAlignment(Pos.BOTTOM_LEFT);
        Label nameLabel = new Label("Name: ");
        Label priceLabel = new Label("Price: "); 
        bookName = new TextField();
        bookPrice = new TextField();
        DeleteBookbtn = new Button();
        BackBookbtn =  new Button();
        AddBookbtn = new Button();
        DeleteBookbtn.setText("Delete");
        BackBookbtn.setText("Back");
        AddBookbtn.setText("Add");
        GridPane.setConstraints(DeleteBookbtn   ,0,2);
        GridPane.setConstraints(BackBookbtn     ,1,2);
        GridPane.setConstraints(nameLabel       ,0,1);
        GridPane.setConstraints(bookName        ,1,1);
        GridPane.setConstraints(priceLabel      ,2,1);        
        GridPane.setConstraints(bookPrice       ,3,1);
        GridPane.setConstraints(AddBookbtn      ,4,1);
        GridPane.setConstraints(OwnerBookstable ,0,0);
        ownerBookStart.getChildren().addAll(DeleteBookbtn, BackBookbtn, AddBookbtn, 
                nameLabel, priceLabel, bookName, bookPrice, OwnerBookstable);
        Scene ownerBookStartScene = new Scene(ownerBookStart, 850, 425);
                
                
        //owner customer screen
        //delete, add, list customer
        
        //Username column
        TableColumn<ownerCustomers, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<ownerCustomers, String>("username"));
        
        //Password column
        TableColumn<ownerCustomers, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(200);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<ownerCustomers, String>("password"));
        
        //Points column
        TableColumn<ownerCustomers, Double> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(100);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<ownerCustomers, Double>("points"));
        
        OwnerCustomerstable = new TableView<>();
        OwnerCustomerstable.setItems(getOwnerCustomers()); 
        OwnerCustomerstable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);
        
        GridPane ownerCustomerStart = new GridPane();
        ownerCustomerStart.setPadding(new Insets(10, 10, 10, 10));
        ownerCustomerStart.setVgap(30);
        ownerCustomerStart.setHgap(10);
        ownerCustomerStart.setAlignment(Pos.BOTTOM_LEFT);
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: "); 
        customerUsername = new TextField();
        customerPassword = new TextField();
        DeleteCustomerbtn = new Button();
        BackCustomerbtn =  new Button();
        AddCustomerbtn = new Button();
        DeleteCustomerbtn.setText("Delete");
        BackCustomerbtn.setText("Back");
        AddCustomerbtn.setText("Add");
        GridPane.setConstraints(DeleteCustomerbtn   ,0,2);
        GridPane.setConstraints(BackCustomerbtn     ,1,2);
        GridPane.setConstraints(usernameLabel       ,0,1);
        GridPane.setConstraints(customerUsername    ,1,1);
        GridPane.setConstraints(passwordLabel       ,2,1);        
        GridPane.setConstraints(customerPassword    ,3,1);
        GridPane.setConstraints(AddCustomerbtn      ,4,1); 
        GridPane.setConstraints(OwnerCustomerstable ,0,0);
        ownerCustomerStart.getChildren().addAll(DeleteCustomerbtn, BackCustomerbtn, AddCustomerbtn, 
                usernameLabel, passwordLabel, customerUsername, customerPassword, OwnerCustomerstable);
        Scene ownerCustomerStartScene = new Scene(ownerCustomerStart, 850, 425);
        
        ////////////////////////////////////////////////////////////////////////////////////

        //CUSTOMER START SCREEN
        primaryStage.setTitle("Bookstore App");
        
        GridPane customerStart = new GridPane();
        customerStart.setPadding(new Insets(10, 10, 10, 10));
        customerStart.setVgap(20);
        customerStart.setHgap(10);
        welcomeMsg = new Label("  ");
        //Label welcomeMsg = new Label(ownerCustomer.getUsername() + "You have " + ownerCustomers.points + "Your status is " + ownerCustomers.state.toString());

        //HBox bar = new HBox();
        buybtn = new Button();
        redeemNBuybtn = new Button();
        Logoutbtn = createLogoutButton(primaryStage);
        buybtn.setText("Buy");
        redeemNBuybtn.setText("Redeem Points and Buy");
        
        //select column
        TableColumn<CustomerBooks, CheckBox> selectColoumn = new TableColumn<>("Select");
        selectColoumn.setMinWidth(200);
        selectColoumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        
        //book name column
        TableColumn<CustomerBooks, String> bookNameColumn = new TableColumn<>("Books");
        bookNameColumn.setMinWidth(200);
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("Books"));
        
        //book price column
        TableColumn<CustomerBooks, Double> bookPriceColumn = new TableColumn<>("Price");
        bookPriceColumn.setMinWidth(100);
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        customerBooksTable = new TableView<>();
        customerBooksTable.setItems(getCustomerBooks()); 
        customerBooksTable.getColumns().addAll(bookNameColumn, bookPriceColumn, selectColoumn);
        
        customerBooksTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                customerBooksTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // CTRL click to select multiple
            }
        });
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(buybtn, redeemNBuybtn, Logoutbtn);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(customerBooksTable, hBox);
        
        BorderPane customerBorderPane = new BorderPane();
        customerBorderPane.setTop(welcomeMsg);
        customerBorderPane.setBottom(hBox);
        customerBorderPane.setCenter(customerBooksTable);
        
        Scene customerStartScreen = new Scene (customerBorderPane, 850, 425);
        
        //CUSTOMER COST SCREEN
        BorderPane customerCostScreenBorderPane = new BorderPane();

        primaryStage.setTitle("Bookstore App");

        totalCostMsg = new Label();
        PointsMsg = new Label(); // points you have after purchase
        Logoutbtn = createLogoutButton(primaryStage);
        
        VBox customerCostElements = new VBox();
        customerCostElements.setPadding(new Insets(10,10,10,10));
        customerCostElements.setSpacing(10);
        customerCostElements.getChildren().addAll(totalCostMsg, PointsMsg, Logoutbtn);
        
        customerCostScreenBorderPane.setCenter(customerCostElements);
        
        Scene customerCostScreen = new Scene (customerCostScreenBorderPane, 300, 200);
        
        //////////////////////////////////////////////////////////////////////////////////
        
        //Action Events (all scenes must be created before action events)        
        Loginbtn.setOnAction(e->isValidLogin(tfUser,pfPass,primaryStage, ownerStartScene, Login, customerStartScreen));
        Booksbtn.setOnAction(e->primaryStage.setScene(ownerBookStartScene));
        Customersbtn.setOnAction(e->primaryStage.setScene(ownerCustomerStartScene));
        BackBookbtn.setOnAction(e->{
            if(ownerBookStart.getChildren().contains(priceError)){
                ownerBookStart.getChildren().remove(priceError);
            }
            primaryStage.setScene(ownerStartScene);
                });
        BackCustomerbtn.setOnAction(e->primaryStage.setScene(ownerStartScene));
        AddBookbtn.setOnAction(e-> addBookButtonClicked(ownerBookStart));
        DeleteBookbtn.setOnAction(e-> deleteBookButtonClicked());
        AddCustomerbtn.setOnAction(e-> addCustomerButtonClicked());
        DeleteCustomerbtn.setOnAction(e-> deleteCustomerButtonClicked());
        buybtn.setOnAction(e->{
            attemptBookPurchase(false);
            primaryStage.setScene(customerCostScreen);
        });
        redeemNBuybtn.setOnAction(e-> {
            attemptBookPurchase(true);
            primaryStage.setScene(customerCostScreen);
        });
        
        primaryStage.setScene(LoginScene);
        primaryStage.show();
        
    }
    
    public void attemptBookPurchase(boolean byPoints){
        double totalCost = 0;
        if (byPoints=false) {
            //calculate cost
            customerBooksList = customerBooksTable.getSelectionModel().getSelectedItems();
            for(int i=0; i < customerBooksList.size(); i++){
                //if(customerBooksList.get(i).getPrice().isSelected()) {
                    totalCost += customerBooksList.get(i).getPrice();
                //}
            }
            /*
            for(int i = 0; i < customerBooksList.size(); i++) {
                    if(customerBooksList.get(i).getPrice().isSelected()) {
                        customer.selectbook(customerBooksList.getBooks().get(i));
                    }
                }
            */

            // Check
            customer.setPoints(customer.getPoints() + totalCost * 10); 
            
        } else if (byPoints==true) {
            customerBooksList = customerBooksTable.getSelectionModel().getSelectedItems();
            for(int i=0; i < customerBooksList.size(); i++){
                //if(customerBooksList.get(i).getPrice().isSelected()) {
                    totalCost += customerBooksList.get(i).getPrice();
                //}
            }
            if (customer.getPoints()/100<=(int)totalCost) {
                totalCost-=customer.getPoints()/100;
                customer.setPoints(0); 

            } else {
                customer.setPoints(100*(customer.getPoints()/100-(int)totalCost));
                totalCost=0;
            }
        }
        
        //Display the info
        totalCostMsg.setText("Total Cost: $" + totalCost);
        PointsMsg.setText("Points: " + customer.getPoints()+ ", Status: " + customer.getStatus());
    }
    
    //Get all the Books
    public ObservableList<ownerBooks> getOwnerBooks() {
        try{
            ownerBookList.clear();
            Scanner s = new Scanner(g);
            while(s.hasNextLine()){
                String[] arr = s.nextLine().split("\t");
                ownerBookList.add(new ownerBooks(arr[0], Double.parseDouble(arr[1])));                
            }
        } catch(IOException e){
            System.out.println("Error occurred");
        }               
        return ownerBookList;
    }
    
    //Get all the Customers
    public ObservableList<ownerCustomers> getOwnerCustomers() {
        try{
            ownerCustomerList = FXCollections.observableArrayList();
            Scanner s = new Scanner(f);
            while(s.hasNextLine()){
                String[] arr = s.nextLine().split("\t");
                ownerCustomerList.add(new ownerCustomers(arr[0], arr[1], Double.parseDouble(arr[2])));                
            }
        } catch(IOException e){
            System.out.println("Error occurred");
        }               
        return ownerCustomerList;
    }
    
    //add books to the lest and text files
    public void addBookButtonClicked(GridPane grid){
        ownerBooks book = new ownerBooks();
        try{
            book.setName(bookName.getText());
            book.setPrice(Double.parseDouble(bookPrice.getText()));
            OwnerBookstable.getItems().add(book);
            bookName.clear();
            bookPrice.clear();
        } catch(NumberFormatException e){
            if(grid.getChildren().contains(priceError)){
                grid.getChildren().remove(priceError);
            }
            GridPane.setConstraints(priceError, 4, 2);
            grid.getChildren().add(priceError);
            bookName.clear();
            bookPrice.clear();
            return;
        }
        try { 
            FileWriter a = new FileWriter("books.txt",true);
            if(OwnerBookstable.getItems().size() != 1)  a.write("\n"); // does not append newline when textfile is empty AKA. list is of size 1 
            a.write(book.getName());
            a.write("\t");
            Double price = book.getPrice();
            a.write(price.toString());
            a.close();
            //System.out.println("wrote to file");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
    
     //add customers to the list and text files
    public void addCustomerButtonClicked(){
        ownerCustomers customer = new ownerCustomers();
        customer.setUsername(customerUsername.getText());
        customer.setPassword(customerPassword.getText());        
        customer.setPoints(0.0);
        OwnerCustomerstable.getItems().add(customer);
        customerUsername.clear();
        customerPassword.clear();
                
        try { 
            FileWriter b = new FileWriter("customers.txt",true);
            if(OwnerCustomerstable.getItems().size() != 1)  b.write("\n"); // does not append newline when textfile is empty AKA. list is of size 1 
            b.write(customer.getUsername());
            b.write("\t");
            b.write(customer.getPassword());
            b.write("\t");
            Double points = customer.getPoints();
            b.write(points.toString());
            b.close();
            //System.out.println("wrote to file");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
    
    //delete books from list and text file 
    public void deleteBookButtonClicked(){
        ObservableList<ownerBooks> bookSelected, allBooks;
        allBooks = OwnerBookstable.getItems();
        bookSelected = OwnerBookstable.getSelectionModel().getSelectedItems();
        //bookSelected.get(0).
        bookSelected.forEach(allBooks::remove);
        try { 
            FileWriter a = new FileWriter("books.txt");
            
            Double price;
            for(int i = 0; i < OwnerBookstable.getItems().size(); i++){
                
                a.write(OwnerBookstable.getItems().get(i).getName());
                a.write("\t");
                price = OwnerBookstable.getItems().get(i).getPrice();
                a.write(price.toString());
                if(i != OwnerBookstable.getItems().size() - 1) a.write("\n");
            }
            a.close();
            //System.out.println("wrote to file");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
      
    }
    
    //delete customers from list and text file 
    public void deleteCustomerButtonClicked(){
        ObservableList<ownerCustomers> customerSelected, allCustomers;
        allCustomers = OwnerCustomerstable.getItems();
        customerSelected = OwnerCustomerstable.getSelectionModel().getSelectedItems();
        customerSelected.forEach(allCustomers::remove);
        try { 
            FileWriter b = new FileWriter("customers.txt");
            System.out.println("FileWriter Opened\n");
            Double points; // might comment
            for(int i = 0; i < OwnerCustomerstable.getItems().size(); i++){
                b.write(OwnerCustomerstable.getItems().get(i).getUsername());
                b.write("\t");
                b.write(OwnerCustomerstable.getItems().get(i).getPassword());
                b.write("\t");
                points = OwnerCustomerstable.getItems().get(i).getPoints();
                b.write(points.toString());
                if(i != OwnerCustomerstable.getItems().size() - 1) b.write("\n"); //might not be good to put last customer entry
            }
            b.close();
            //System.out.println("wrote to file");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
      
    }
    
    public ObservableList<CustomerBooks> getCustomerBooks(){
        try{
            customerBooksList = FXCollections.observableArrayList();;
            Scanner s = new Scanner(k);
            while(s.hasNextLine()){
                String[] arr = s.nextLine().split("\t");
                customerBooksList.add(new CustomerBooks(arr[0], Double.parseDouble(arr[1])));                
            }
        } catch(IOException e){
            System.out.println("Error occurred");
        }               
        return customerBooksList;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
