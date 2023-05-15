
import java.io.*;
import java.time.LocalDate;
import java.util.*;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{

    //Panes
    static BorderPane mainPane = new BorderPane();
    static VBox mediaPane = new VBox();
    static VBox customerPane = new VBox();
    static VBox rentPane = new VBox();

    //Scenes
    static Scene mainScene = new Scene(mainPane, 500, 500);
   
    //Fonts used throught
    static Font mainFont = Font.font(40);
    static Font secondaryFont = Font.font(20);

    //Global Padding
    static Insets paneInsets = new Insets(50,10,10,10);

    //Single Stage
    static Stage mainStage = new Stage();

    //Static Variables used throughout
    static Customer temp = new Customer();
    static String plan;
    static String ID;
    static String code;
    static String phone;
    static int copies;
    static String rating;
    static String songs;
    static String artist;
    static double weight;
    static String title;

    //Files used as database
    static File customerDB = new File("customers.txt");
    static File mediaDB = new File("media.txt");
    static File limitedValueFile = new File("limitedValue.txt");

    //Initiating an instance of MediaRentlaManager
    static MediaRentalManager myManager = new MediaRentalManager();

    //Global Scanner
    static Scanner input = new Scanner(System.in); //regular scanner


   
    @Override
    public void start(Stage mainStage) throws Exception {

        mainStage.setMaximized(true);
        //Title
        Text title = new Text("Welcome to the Media Rental Manager!");
        title.setFont(Font.font("Courier", FontWeight.BOLD, 70));
        title.setFill(Color.BLACK);
        mainPane.setTop(title);

        
        //Main panel
        Button mediaButton = new Button ("Media", new ImageView("file:media.png"));
        mediaButton.setFont(mainFont);

        mediaButton.setOnAction(e -> {
            mediaButtonClicked();
        });

        Button customerButton = new Button ("Customer", new ImageView("file:customer.png"));
        customerButton.setFont(mainFont);

        customerButton.setOnAction(e -> {
            customerButtonClicked();
        });

        Button rentButton = new Button ("Rent Manager", new ImageView("file:rent.png"));
        rentButton.setFont(mainFont);

        rentButton.setOnAction(e -> {
            rentButtonClicked();
        });

        Button exitButton = new Button ("Exit", new ImageView("file:exit.png"));
        exitButton.setFont(mainFont);

        exitButton.setOnAction(e -> {
            writeCustomers(customerDB);
            writeMedia(mediaDB);
            writeLimitedValue(limitedValueFile);
            mainStage.close();

            return;
        });

        VBox leftMainPanel = new VBox();
        leftMainPanel.getChildren().addAll(mediaButton, customerButton, rentButton, exitButton);
        mainPane.setLeft(leftMainPanel);
        
        // Image
        Image mainImg = new Image("file:luana-de-marco-PF1l1F1hzoU-unsplash.jpg");
        ImageView mainImgView = new ImageView(mainImg);
        mainImgView.setFitHeight(666 * 1.1);
        mainImgView.setFitWidth(1000 * 1.1);

        Text mainImgCaption = new Text("Photo by Luana De Marco on Unsplash");
        VBox centerMainPanel = new VBox();
        centerMainPanel.getChildren().addAll(mainImgView, mainImgCaption);
        centerMainPanel.setPadding(new Insets(45,10,10,40));
        mainPane.setCenter(centerMainPanel);

        Text iconCredit = new Text();
        iconCredit.setText("All icons used throughout were from icons8.com");
        mainPane.setBottom(iconCredit);
        
        BorderPane.setAlignment(title, Pos.CENTER);
        leftMainPanel.setPadding(paneInsets);
        leftMainPanel.setSpacing(50);
        // BorderPane.setAlignment(leftMainPanel, Pos.CENTER);

        mainStage.setTitle("Media Rental Manager");
        mainStage.setScene(mainScene);
        
        mainStage.show();

    }
    public static void main(String[] args) {
        
     
        //**reading files
        readMedia(mediaDB); //adds media from file to arraylist
        //Have to read media first because customer cart and owned media are dependent on media whether they exist or not
        readCustomers(customerDB); //adds customers from file to arraylist
        readLimitedValue(limitedValueFile); //adds limited value customers from file to arraylist
        
        launch(args);
        

       

    }

    
    public static void mediaButtonClicked(){
        

        Button addMediaButton = new Button("Add Media", new ImageView("file:add_media.png"));
        addMediaButton.setFont(mainFont);
        addMediaButton.setPrefWidth(500);

        Button removeMediaButton = new Button("Remove Media", new ImageView("file:remove_media.png"));
        removeMediaButton.setFont(mainFont);
        removeMediaButton.setPrefWidth(500);

        Button updateMediaButton = new Button("Update Media", new ImageView("file:update_media.png"));
        updateMediaButton.setFont(mainFont);
        updateMediaButton.setPrefWidth(500);

        Button searchCodeButton = new Button("Search by Code", new ImageView("file:find_media.png"));
        searchCodeButton.setFont(mainFont);
        searchCodeButton.setPrefWidth(500);

        Button searchMediaButton = new Button("Search Media", new ImageView("file:find_media.png"));
        searchMediaButton.setFont(mainFont);
        searchMediaButton.setPrefWidth(500);

        Button viewMediaButton = new Button("Print All Media Info", new ImageView("file:view_media.png"));
        viewMediaButton.setFont(mainFont);
        viewMediaButton.setPrefWidth(500);

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(mainFont);


        mediaPane = new VBox(); //Media Pane

        GridPane mediaButtonPane = new GridPane();
        mediaButtonPane.setHgap(50);
        mediaButtonPane.setVgap(50);
        mediaButtonPane.add(addMediaButton, 0, 0);
        mediaButtonPane.add(removeMediaButton, 0, 1);
        mediaButtonPane.add(updateMediaButton, 0, 2);
        mediaButtonPane.add(searchCodeButton, 1, 0);
        mediaButtonPane.add(searchMediaButton, 1, 1);
        mediaButtonPane.add(viewMediaButton, 1, 2);
        mediaButtonPane.setAlignment(Pos.CENTER);


        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().addAll(backButton);

        mediaPane.getChildren().addAll(mediaButtonPane,backButtonPane);
        mediaPane.setPadding(paneInsets);
        mediaPane.setSpacing(100);
        mediaPane.setAlignment(Pos.CENTER);


        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);

        mainScene.setRoot(mediaPane);


        addMediaButton.setOnAction(e -> {
            addMedia(); 
        });
        removeMediaButton.setOnAction(e -> {
            removeMedia(); 
        });
        updateMediaButton.setOnAction(e -> {
            removeMedia(); //same one used here and foor search by code
        });
        searchCodeButton.setOnAction(e -> { //searches for media by code
            removeMedia();
        });
        searchMediaButton.setOnAction(e -> {
            searchMedia();
        });
        viewMediaButton.setOnAction(e -> { //prints all media info
            viewMedia();
        });
        backButton.setOnAction(e -> {
            mainScene.setRoot(mainPane);
        });



        return;

    }
    public static void customerButtonClicked(){//customer pane
        
        Button addCustomerButton = new Button("Add Customer", new ImageView("file:add_customer.png"));
        addCustomerButton.setFont(mainFont);
        addCustomerButton.setPrefWidth(800);

        Button removeCustomerButton = new Button("Remove Customer", new ImageView("file:remove_customer.png"));
        removeCustomerButton.setFont(mainFont);
        removeCustomerButton.setPrefWidth(800);

        Button updateCustomerButton = new Button("Update Customer", new ImageView("file:update_customer.png"));
        updateCustomerButton.setFont(mainFont);
        updateCustomerButton.setPrefWidth(800);

        Button searchCustomerButton = new Button("Search Customer by ID", new ImageView("file:search_customer.png"));
        searchCustomerButton.setFont(mainFont);
        searchCustomerButton.setPrefWidth(800);
        

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(mainFont);

        // Button viewCustomerButton = new Button("Print All Customer Info");
        

        VBox customerButtonPane = new VBox();

        customerButtonPane.getChildren().addAll(addCustomerButton,removeCustomerButton,updateCustomerButton,searchCustomerButton);
        customerButtonPane.setSpacing(50);
        customerButtonPane.setAlignment(Pos.CENTER);

        
        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().addAll(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);
        
        customerPane = new VBox();//resting customer pane
        
        customerPane.getChildren().addAll(customerButtonPane, backButtonPane);
        customerPane.setSpacing(50);
        customerPane.setPadding(paneInsets);
        
        mainScene.setRoot(customerPane);

        addCustomerButton.setOnAction(e -> {
            addCustomer();
        });
        removeCustomerButton.setOnAction(e -> {
            removeCustomer();
        });
        updateCustomerButton.setOnAction(e -> {
            removeCustomer();
        });
        searchCustomerButton.setOnAction(e -> {
            removeCustomer();
        });
        // viewCustomerButton.setOnAction(e -> {
        //     viewCustomer();
        // });
        backButton.setOnAction(e -> {
            mainScene.setRoot(mainPane);
        });

        return;

    }
    public static void rentButtonClicked(){//rent pane
        
        Label customerID = new Label("Customer ID: ");
        customerID.setFont(secondaryFont);
        TextField customerIDTextField = new TextField();
        customerIDTextField.setFont(secondaryFont);
        customerIDTextField.setMaxWidth(500);
        // HBox customerIDTextFieldHbox = new HBox();
        // customerIDTextFieldHbox.getChildren().addAll(customerID,customerIDTextField);
        // customerIDTextFieldHbox.setSpacing(10);
        
        Label customerInfoLabel = new Label("Customer Info: ");
        customerInfoLabel.setFont(secondaryFont);
        TextArea customerInfoArea = new TextArea();
        customerInfoArea.setEditable(false);
        customerInfoArea.setWrapText(true);
        customerInfoArea.setPrefHeight(1000);
        
        VBox customerVBox = new VBox();
        customerVBox.getChildren().addAll(customerID, customerIDTextField, customerInfoLabel, customerInfoArea);
        customerVBox.setSpacing(15);
        
        Label mediaCode = new Label("Media Code: ");
        mediaCode.setFont(secondaryFont);
        TextField mediaCodeTextField = new TextField();
        mediaCodeTextField.setFont(secondaryFont);
        mediaCodeTextField.setMaxWidth(500);
        // HBox mediaCodeHbox = new HBox();
        // mediaCodeHbox.getChildren().addAll(mediaCode, mediaCodeTextField);
        
        Label mediaInfoLabel = new Label("Media Info: ");
        mediaInfoLabel.setFont(secondaryFont);
        TextArea mediaInfoArea = new TextArea();
        mediaInfoArea.setEditable(false);
        mediaInfoArea.setWrapText(true);
        mediaInfoArea.setPrefHeight(1000);
        
        VBox mediaVBox = new VBox();
        mediaVBox.getChildren().addAll(mediaCode, mediaCodeTextField, mediaInfoLabel, mediaInfoArea);
        mediaVBox.setSpacing(15);
        
        Font smallFont = new Font(30);
        
        Label rentDate = new Label("Rent Date: ");
        rentDate.setFont(smallFont);
        TextField rentDateTextField = new TextField();
        rentDateTextField.setFont(smallFont);
        HBox rentDateHbox = new HBox();
        rentDateHbox.getChildren().addAll(rentDate, rentDateTextField);
        rentDateHbox.setAlignment(Pos.CENTER);
        
        Text report = new Text ();
        report.setFont(smallFont);
        StackPane reporPane = new StackPane();
        reporPane.getChildren().addAll(report);
        reporPane.setAlignment(Pos.CENTER);

        
        
        HBox rentTextFields = new HBox();
        rentTextFields.getChildren().addAll(customerVBox, mediaVBox);
        rentTextFields.setSpacing(30);
        rentTextFields.setAlignment(Pos.CENTER);
        

        Button addToCartButton = new Button("Add to Cart", new ImageView("file:add_cart.png"));
        addToCartButton.setFont(smallFont);
        addToCartButton.setDisable(true);

        Button removeFromCartButton = new Button("Remove from Cart", new ImageView("file:remove_cart.png"));
        removeFromCartButton.setFont(smallFont);
        removeFromCartButton.setDisable(true);

        Button processRequestsButton = new Button("Process Requests", new ImageView("file:process.png"));
        processRequestsButton.setFont(smallFont);
        processRequestsButton.setDisable(true);

        Button returnMediaButton = new Button("Return Media", new ImageView("file:return.png"));
        returnMediaButton.setFont(smallFont);
        returnMediaButton.setDisable(true);

        Button clearButton = new Button ("clear", new ImageView("file:clear.png"));
        clearButton.setFont(smallFont);
        clearButton.setDisable(true);
        // StackPane clearButtonPane = new StackPane();
        // clearButtonPane.getChildren().addAll(clearButton);
        // clearButtonPane.setAlignment(Pos.BOTTOM_RIGHT);

        
        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(smallFont);
        // StackPane backButtonPane = new StackPane();
        // backButtonPane.getChildren().addAll(backButton);
        // backButtonPane.setAlignment(Pos.BOTTOM_LEFT);

        HBox clearBackPane = new HBox();
        clearBackPane.getChildren().addAll(backButton, clearButton);
        clearBackPane.setSpacing(1100);
        clearBackPane.setAlignment(Pos.BOTTOM_CENTER);

        HBox rentButtons = new HBox();
        rentButtons.setAlignment(Pos.CENTER);
        rentButtons.setSpacing(20);
        rentButtons.getChildren().addAll(addToCartButton, removeFromCartButton, processRequestsButton, returnMediaButton);
        
        VBox rentPane = new VBox();
        rentPane.getChildren().addAll(rentTextFields, rentDateHbox, reporPane, rentButtons, clearBackPane);// backButtonPane, clearButtonPane);
        rentPane.setSpacing(30);
        rentPane.setPadding(paneInsets);

        mainScene.setRoot(rentPane);

        customerIDTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isBlank() && isNumeric(newValue) && newValue.trim().length() == 7) {
                    customerInfoArea.setText(myManager.searchCustomer(newValue).getDetails());
                    if (!mediaInfoArea.getText().isBlank() && !mediaInfoArea.getText().equals("[Media not found]")) {
                        addToCartButton.setDisable(false);
                        removeFromCartButton.setDisable(false);
                        processRequestsButton.setDisable(false);
                        returnMediaButton.setDisable(false);
                        clearButton.setDisable(false);
                    }

                }
                else {
                    customerInfoArea.setText("[Customer not found]");
                    addToCartButton.setDisable(true);
                    removeFromCartButton.setDisable(true);
                    processRequestsButton.setDisable(true);
                    returnMediaButton.setDisable(true);
                }
                clearButton.setDisable(false);
            }
        });

        mediaCodeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isBlank() && newValue.trim().length() == 5) {
                    mediaInfoArea.setText(myManager.checkIfMediaExists(newValue).getDetails().replaceAll(";", "\n"));
                    if (!customerInfoArea.getText().isBlank() && !customerInfoArea.getText().equals("[Customer not found]")) {
                        addToCartButton.setDisable(false);
                        removeFromCartButton.setDisable(false);
                        processRequestsButton.setDisable(false);
                        returnMediaButton.setDisable(false);
                        clearButton.setDisable(false);
                    }
                }
                else {
                    mediaInfoArea.setText("[Media not found]");
                    addToCartButton.setDisable(true);
                    removeFromCartButton.setDisable(true);
                    processRequestsButton.setDisable(true);
                    returnMediaButton.setDisable(true);
                    
                }
                clearButton.setDisable(false);

            }
        });


        addToCartButton.setOnAction(e -> {
            boolean found = false;
            if (customerIDTextField.getText().isEmpty() || customerIDTextField.getText().trim().length() != 7 || customerIDTextField.getText().contains("[\\D]")){
                report.setText("You have to enter a valid customer ID; one that is 7 digits long!");
            }
            else if (mediaCodeTextField.getText().isEmpty() || mediaCodeTextField.getText().trim().length() != 5){
                report.setText("You have to enter a valid media code (format:A####)!");
            }
            else {
                    
                ID = customerIDTextField.getText();
               
                for (Customer exists : myManager.customers) {
                    if (exists.ID.trim().equals(ID.trim())) {
                        // customerInfo.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Customer found!");
                }
                else {
                    report.setText("Customer not found!");
                }
                code = mediaCodeTextField.getText();
                for (Media exists : myManager.mediaList) {
                    if (exists.code.equals(code)) {
                        // mediaInfo.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Media found!");
                }
                else {
                    report.setText("Media not found!");
                }
                if (myManager.addToCart(ID, code)){
                    report.setText("Media added to cart!");
                    writeCustomers(customerDB);
                    writeMedia(mediaDB);
                    report.setFill(Color.GREEN);
                }
                else {
                    report.setText("Media not added to cart!\n It's either already in the cart or rented!");
                    report.setFill(Color.RED);
                }
            }
            mediaInfoArea.setText(myManager.checkIfMediaExists(mediaCodeTextField.getText().trim()).getDetails().replaceAll(";", "\n"));
            customerInfoArea.setText(myManager.searchCustomer(customerIDTextField.getText().trim()).getDetails());

            
        });

        removeFromCartButton.setOnAction(e -> {

            boolean found = false;

            if (customerIDTextField.getText().isEmpty() || customerIDTextField.getText().trim().length() != 7 || customerIDTextField.getText().contains("[\\D]")){
                report.setText("You have to enter a valid customer ID; one that is 7 digits long!");
                report.setFill(Color.RED);
            }
            else if (mediaCodeTextField.getText().isEmpty() || mediaCodeTextField.getText().trim().length() != 5){
                report.setText("You have to enter a valid media code (format:A####)!");
                report.setFill(Color.RED);
            }
            else {
                    
                ID = customerIDTextField.getText();
               
                for (Customer exists : myManager.customers) {
                    if (exists.ID == ID) {
                        customerInfoArea.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Customer found!");
                    report.setFill(Color.RED);
                }
                else {
                    report.setText("Customer not found!");
                    report.setFill(Color.RED);
                }
                found = false;
                code = mediaCodeTextField.getText();
                for (Media exists : myManager.mediaList) {
                    if (exists.code.equals(code)) {
                        mediaInfoArea.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Media found!");
                    report.setFill(Color.RED);
                }
                else {
                    report.setText("Media not found!");
                    report.setFill(Color.RED);
                }
                if (myManager.removeFromCart(ID, code)){
                    
                    report.setText("Media removed from cart!");
                    report.setFill(Color.GREEN);
                    writeCustomers(customerDB);
                    writeMedia(mediaDB);
                    customerInfoArea.setText(myManager.searchCustomer(customerIDTextField.getText().trim()).getDetails());
                }
                else {
                    // System.out.println(ID + ' ' + code.toUpperCase());
                    report.setFill(Color.RED);
                    report.setText("Cart is already empty!");
                }
            }



        });

        processRequestsButton.setOnAction(e -> {
            report.setText(myManager.processRequests());
            report.setFill(Color.GREEN);
            mediaInfoArea.setText(myManager.checkIfMediaExists(mediaCodeTextField.getText().trim()).getDetails().replaceAll(";", "\n"));
            customerInfoArea.setText(myManager.searchCustomer(customerIDTextField.getText().trim()).getDetails());
            rentDateTextField.setText(LocalDate.now().toString());
            writeCustomers(customerDB);
            writeMedia(mediaDB);
        
        });

        returnMediaButton.setOnAction(e -> {
            boolean found = false;

            if (customerIDTextField.getText().isEmpty() || customerIDTextField.getText().trim().length() != 7 || customerIDTextField.getText().contains("[\\D]")){
                report.setText("You have to enter a valid customer ID; one that is 7 digits long!");
                report.setFill(Color.RED);
            }
            else if (mediaCodeTextField.getText().isEmpty() || mediaCodeTextField.getText().trim().length() != 5){
                report.setText("You have to enter a valid media code (format:A####)!");
                report.setFill(Color.RED);
            }
            else {
                    
                ID = customerIDTextField.getText();
               
                for (Customer exists : myManager.customers) {
                    if (exists.ID == ID) {
                        customerInfoArea.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Customer found!");
                    report.setFill(Color.RED);
                }
                else {
                    report.setText("Customer not found!");
                    report.setFill(Color.RED);
                }
                found = false;
                code = mediaCodeTextField.getText();
                for (Media exists : myManager.mediaList) {
                    if (exists.code.equals(code)) {
                        mediaInfoArea.setText(exists.getInfo().replaceAll("[;]", "\n"));
                        found = true;
                        break;
                    }
                }
                if (found == true) {
                    report.setText("Media found!");
                    report.setFill(Color.GREEN);
                }
                else {
                    report.setText("Media not found!");
                    report.setFill(Color.RED);
                }
                if (myManager.returnMedia(ID, code)){
                    report.setText("Media returned!");
                    report.setFill(Color.GREEN);
                    writeCustomers(customerDB);
                    writeMedia(mediaDB);
                }
                else {
                    report.setText("This customer ain't got nothing!");
                    report.setFill(Color.RED);
                }
            }
            mediaInfoArea.setText(myManager.checkIfMediaExists(mediaCodeTextField.getText().trim()).getDetails().replaceAll(";", "\n"));
            customerInfoArea.setText(myManager.searchCustomer(customerIDTextField.getText().trim()).getDetails());

        });

        backButton.setOnAction(e -> {
            mainScene.setRoot(mainPane);
        });

        clearButton.setOnAction(e -> {
            customerIDTextField.clear();
            mediaCodeTextField.clear();
            report.setText("");
            customerInfoArea.clear();
            mediaInfoArea.clear();
            clearButton.setDisable(true);
            rentDateTextField.clear();
        });
        
        return;

    }


    private static boolean isNumeric (String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    private static void addCustomer() {
     
        
        Label customerName = new Label("Customer's name: ");
        customerName.setFont(mainFont);
        TextField customerNameField = new TextField();
        customerNameField.setFont(mainFont);
        HBox customerNamePane = new HBox();
        customerNamePane.getChildren().addAll(customerName, customerNameField);

        Label customerAddress = new Label("Customer's address: ");
        customerAddress.setFont(mainFont);
        TextField customerAddressField = new TextField();
        customerAddressField.setFont(mainFont);
        customerAddressField.setDisable(true);
        HBox customerAddressPane = new HBox();
        customerAddressPane.getChildren().addAll(customerAddress, customerAddressField);

        Label customerPhone = new Label("Customer's phone number: ");
        customerPhone.setFont(mainFont);
        TextField customerPhoneField = new TextField();
        customerPhoneField.setFont(mainFont);
        customerPhoneField.setDisable(true);
        HBox customerPhonePane = new HBox();
        customerPhonePane.getChildren().addAll(customerPhone, customerPhoneField);
        
        Label customerPlan = new Label("Customer's plan: ");
        customerPlan.setFont(mainFont);
        RadioButton limitedPlanButton = new RadioButton("Limited");
        limitedPlanButton.setFont(mainFont);
        RadioButton unlimitedPlanButton = new RadioButton("Unlimited");
        unlimitedPlanButton.setFont(mainFont);
        ToggleGroup planGroup = new ToggleGroup();
        limitedPlanButton.setToggleGroup(planGroup);
        unlimitedPlanButton.setToggleGroup(planGroup);
        HBox customerPlanPane = new HBox();
        customerPlanPane.getChildren().addAll(customerPlan, limitedPlanButton, unlimitedPlanButton);
        
        if (!customerNameField.getText().isBlank()){
            customerPhoneField.setDisable(false);
            if(customerPhoneField.getText().isBlank()){
                customerAddressField.setDisable(false);
                if (customerAddressField.getText().isBlank()){
                    limitedPlanButton.setDisable(false);
                    unlimitedPlanButton.setDisable(false);
                }
                else {
                    limitedPlanButton.setDisable(true);
                    unlimitedPlanButton.setDisable(true);
                }
            }
            else{
                customerAddressField.setDisable(true);
                limitedPlanButton.setDisable(true);
                    unlimitedPlanButton.setDisable(true);
            }
        }
        else {
            customerPhoneField.setDisable(true);
            customerAddressField.setDisable(true);
            limitedPlanButton.setDisable(true);
            unlimitedPlanButton.setDisable(true);
        }

        customerNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isBlank()){// || customerNameField.getText().isBlank()) {
                    customerPhoneField.setDisable(true);
                    customerAddressField.setDisable(true);
                    limitedPlanButton.setDisable(true);
                    unlimitedPlanButton.setDisable(true);
                }
                else {
                    customerPhoneField.setDisable(false);
                    if (!customerPhoneField.getText().isBlank()){
                        customerAddressField.setDisable(false);
                        if (!customerAddressField.getText().isBlank()){
                            limitedPlanButton.setDisable(false);
                            unlimitedPlanButton.setDisable(false);
                        }
                        else {
                            limitedPlanButton.setDisable(true);
                            unlimitedPlanButton.setDisable(true);
                        }
                    }
                    else {
                        customerAddressField.setDisable(true);
                        limitedPlanButton.setDisable(true);
                        unlimitedPlanButton.setDisable(true);
                    }
                }
            }
        });

        customerPhoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isBlank() || !isNumeric(newValue)|| newValue.length() != 10 ){// || customerPhoneField.getText().isBlank()  || customerPhoneField.getText().length() != 10 || !isNumeric(customerPhoneField.getText())) {
                    customerAddressField.setDisable(true);
                    limitedPlanButton.setDisable(true);
                    unlimitedPlanButton.setDisable(true);
                }
                else {
                    customerAddressField.setDisable(false);
                    if (!customerAddressField.getText().isBlank()){
                        limitedPlanButton.setDisable(false);
                        unlimitedPlanButton.setDisable(false);
                    }
                    else {
                        limitedPlanButton.setDisable(true);
                        unlimitedPlanButton.setDisable(true);
                    }
                }
            }
        });

        customerAddressField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isBlank()){
                    limitedPlanButton.setDisable(true);
                    unlimitedPlanButton.setDisable(true);
                }
                else {
                    limitedPlanButton.setDisable(false);
                    unlimitedPlanButton.setDisable(false);
                }
            }
        });

        Text report = new Text();
        report.setFont(secondaryFont);
        StackPane reportPane = new StackPane();
        reportPane.getChildren().addAll(report);
        reportPane.setAlignment(Pos.CENTER);


        Button addCustomerButton = new Button("Add Customer", new ImageView("file:add.png"));
        addCustomerButton.setFont(mainFont);
        Button clearButton = new Button("clear", new ImageView("file:clear.png"));
        clearButton.setFont(mainFont);
        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(mainFont);

        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(addCustomerButton, clearButton);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(30);

        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().add(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);
        
        VBox customerFieldsPane = new VBox();
        customerFieldsPane.getChildren().addAll(customerNamePane, customerPhonePane, customerAddressPane, customerPlanPane);
        customerFieldsPane.setAlignment(Pos.CENTER);
        customerFieldsPane.setSpacing(30);
        
        VBox addCustomerPane = new VBox();
        addCustomerPane.setAlignment(Pos.CENTER);
        addCustomerPane.setPadding(paneInsets);
        addCustomerPane.setSpacing(30);
        addCustomerPane.getChildren().addAll(customerFieldsPane, buttonPane, reportPane, backButtonPane);


        // Scene addCustomerScene = new Scene(addCustomerPane, 400, 400);
        mainScene.setRoot(addCustomerPane);

        addCustomerButton.setOnAction(e -> {
            if (customerNameField.getText().isEmpty() || customerAddressField.getText().isEmpty() || customerPhoneField.getText().isEmpty() || !planGroup.getSelectedToggle().equals(limitedPlanButton) && !planGroup.getSelectedToggle().equals(unlimitedPlanButton)) {
                report.setText("Please fill in all fields");
                report.setFill(Color.RED);
            }
            else {
                if (planGroup.getSelectedToggle().equals(limitedPlanButton)) {
                    plan = "LIMITED";
                }
                else {
                    plan = "ULIMITED";
                }
               
                phone = customerPhoneField.getText().trim();
                if (phone.length() != 10 || !isNumeric(phone)) {
                    report.setText("Phone number has to be 10 digits");
                    report.setFill(Color.RED);
                }
                else {
                    myManager.addCustomer(customerNameField.getText(), phone, customerAddressField.getText().trim(), plan);

                    writeCustomers(customerDB);

                    report.setText("Customer added!");
                    report.setFill(Color.GREEN);
                    
                    customerNameField.clear();
                    customerAddressField.clear();
                    customerPhoneField.clear();
                    planGroup.selectToggle(null);
                }
            }
        });

        clearButton.setOnAction(e -> {
            customerNameField.clear();
            customerAddressField.clear();
            customerPhoneField.clear();
            planGroup.selectToggle(null);
        });

        backButton.setOnAction(e -> {
            mainScene.setRoot(customerPane);
        });

        
        return;

    }
    private static void removeCustomer(){//also update and search by ID
        
        Text report = new Text("");
        report.setFont(mainFont);
        StackPane reportPane = new StackPane();
        reportPane.getChildren().addAll(report);
        reportPane.setAlignment(Pos.CENTER);

        Label customerID = new Label("Customer's ID: ");
        customerID.setFont(mainFont);
        TextField customerIDField = new TextField();
        customerIDField.setFont(mainFont);
        HBox customerIDPane = new HBox();
        customerIDPane.getChildren().addAll(customerID, customerIDField);

        Label customerName = new Label("Customer's name: ");
        customerName.setFont(mainFont);
        TextField customerNameField = new TextField();
        customerNameField.setFont(mainFont);
        customerNameField.setDisable(true);
        HBox customerNamePane = new HBox();
        customerNamePane.getChildren().addAll(customerName, customerNameField);

        Label customerAddress = new Label("Customer's address: ");
        customerAddress.setFont(mainFont);
        TextField customerAddressField = new TextField();
        customerAddressField.setFont(mainFont);
        customerAddressField.setDisable(true);
        HBox customerAddressPane = new HBox();
        customerAddressPane.getChildren().addAll(customerAddress, customerAddressField);

        Label customerPhone = new Label("Customer's phone number: ");
        customerPhone.setFont(mainFont);
        TextField customerPhoneField = new TextField();
        customerPhoneField.setFont(mainFont);
        customerAddressField.setFont(mainFont);
        customerPhoneField.setDisable(true);
        HBox customerPhonePane = new HBox();
        customerPhonePane.getChildren().addAll(customerPhone, customerPhoneField);
        
        Label customerPlan = new Label("Customer's plan: ");
        customerPlan.setFont(mainFont);
        TextField customerPlanField = new TextField();
        customerPlanField.setFont(mainFont);
        customerPlanField.setDisable(true);
        HBox customerPlanPane = new HBox();
        customerPlanPane.getChildren().addAll(customerPlan, customerPlanField);
       
        
        // Button addCustomerButton = new Button("Add Customer", new ImageView("file:add.png"));
        // addCustomerButton.setDisable(true);
        // addCustomerButton.setFont(mainFont);

        Button findCustomerButton = new Button ("Find", new ImageView("file:find.png"));
        findCustomerButton.setDisable(true);
        findCustomerButton.setFont(mainFont);
        findCustomerButton.setMaxSize(500, 200);
        StackPane findButtonPane = new StackPane();
        findButtonPane.setAlignment(Pos.TOP_RIGHT);
        findButtonPane.getChildren().add(findCustomerButton);
        findButtonPane.setPadding(new Insets(200, 10, 10, 10));

        Button editCustomerButton = new Button ("Edit", new ImageView("file:edit.png"));
        editCustomerButton.setDisable(true);
        editCustomerButton.setFont(mainFont);

        Button updateCustomerButton = new Button ("Update", new ImageView("file:update.png"));
        updateCustomerButton.setDisable(true);
        updateCustomerButton.setFont(mainFont);

        Button removeCustomerButton = new Button ("Remove", new ImageView("file:remove.png"));
        removeCustomerButton.setDisable(true);
        removeCustomerButton.setFont(mainFont);

        Button clearButton = new Button("clear", new ImageView("file:clear.png"));
        clearButton.setFont(mainFont);

        Button backButton = new Button("back", new ImageView("file:back.png"));
        backButton.setFont(mainFont);

        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(updateCustomerButton, editCustomerButton, removeCustomerButton, clearButton);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(30);

        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().add(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);

        VBox customerFieldsPane = new VBox();
        customerFieldsPane.getChildren().addAll(customerIDPane, customerNamePane, customerAddressPane, customerPhonePane, customerPlanPane);
        customerFieldsPane.setSpacing(10);
        // customerFieldsPane.setPadding(new Insets(10, 10, 10, 10));
        customerFieldsPane.setAlignment(Pos.CENTER);

        VBox removeCustomerMainPane = new VBox(); //Pane that includes everything but the find button
        // removeCustomerMainPane.setPadding(paneInsets);
        removeCustomerMainPane.setSpacing(30);
        removeCustomerMainPane.setAlignment(Pos.CENTER);
        removeCustomerMainPane.getChildren().addAll(customerFieldsPane, buttonPane, reportPane, backButtonPane);
        // removeCustomerMainPane.setPadding(paneInsets);

        HBox removeCustomerPane = new HBox(); //Pane of all panes!
        removeCustomerPane.setPadding(paneInsets);
        removeCustomerPane.setSpacing(50);
        removeCustomerPane.setAlignment(Pos.CENTER);
        removeCustomerPane.getChildren().addAll(removeCustomerMainPane, findButtonPane);


        
        
        mainScene.setRoot(removeCustomerPane);

        customerIDField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isBlank() || !isNumeric(newValue.trim()) || newValue.trim().length() != 7) {
                    findCustomerButton.setDisable(true);
                }
                else {
                    findCustomerButton.setDisable(false);

                }
            }
            
        });
        
        findCustomerButton.setOnAction(e -> {

            customerNameField.clear();
            customerAddressField.clear();
            customerPhoneField.clear();
            customerPlanField.clear();
            customerNameField.setDisable(true);
            customerAddressField.setDisable(true);
            customerPhoneField.setDisable(true);
            customerPlanField.setDisable(true);
            // customerIDField.setDisable(true);

            if (customerIDField.getText().isBlank()){
                report.setText("Please enter customer's ID!");
                report.setFill(Color.RED);
            }
            // else if (customerIDField.getText().trim().length() != 7 || isNumeric(customerID.getText().trim()) == false){
            //     report.setText("Customer's ID has to be 7 digits!");
            // }
            else {
                customerIDField.setDisable(true);
                    
                temp = myManager.searchCustomer(customerIDField.getText().trim());
                
                if (temp == null) {
                    report.setText("No customer with this ID!");
                    report.setFill(Color.RED);

                }
                else {
                    customerNameField.setText(temp.name);
                    customerAddressField.setText(temp.address);
                    customerPhoneField.setText(temp.phone);
                    customerPlanField.setText(temp.plan);
                    
                    findCustomerButton.setDisable(true);
                    editCustomerButton.setDisable(false);
                    updateCustomerButton.setDisable(true);
                    removeCustomerButton.setDisable(false);
                }
            }
            
        });

        editCustomerButton.setOnAction(value -> {
            customerIDField.setDisable(false);
            customerNameField.setDisable(false);
            customerPhoneField.setDisable(false);
            customerAddressField.setDisable(false);
            customerPlanField.setDisable(false);
            
            updateCustomerButton.setDisable(false);
            findCustomerButton.setDisable(true);
            editCustomerButton.setDisable(true);
            removeCustomerButton.setDisable(true);
        });
       
        // addCustomerButton.setOnAction(e -> {
        //         myManager.addCustomer(customerNameField.getText().trim(), phone, customerAddressField.getText().trim(), plan);
        //         report.setText("Customer added successfully!");

        //         customerNameField.clear();
        //         customerAddressField.clear();
        //         customerPhoneField.clear();
        //         customerPlanField.clear();
        // });

        updateCustomerButton.setOnAction(e -> {
            if (customerNameField.getText().isBlank() || customerAddressField.getText().isBlank() || customerPhoneField.getText().isBlank() || customerPlanField.getText().isBlank()){ 
                report.setText("Please fill in all fields");
                report.setFill(Color.RED);
            }
            else {
                    phone = customerPhoneField.getText().trim();
                    if (String.valueOf(phone).length() != 10) {
                    report.setText("Phone number has to be 10 digits");
                    report.setFill(Color.RED);
                    }

                plan = customerPlanField.getText().trim();
                if (plan.equalsIgnoreCase("LIMITED") || plan.equalsIgnoreCase("ULIMITED")) {
                
                    if (myManager.updateCustomer(customerIDField.getText().trim(), customerNameField.getText().trim(), phone, customerAddressField.getText().trim(), plan)){
                        report.setText("Customer updated successfully!");
                        report.setFill(Color.GREEN);
                        writeCustomers(customerDB);

                    }
                    else {
                        report.setText("Customer not found!");
                        report.setFill(Color.RED);
                    }
                    customerIDField.clear();
                    customerNameField.clear();
                    customerAddressField.clear();
                    customerPhoneField.clear();
                    customerPlanField.clear();

                    customerIDField.setDisable(false);
                    customerNameField.setDisable(true);
                    customerPhoneField.setDisable(true);
                    customerAddressField.setDisable(true);
                    customerPlanField.setDisable(true);
                    

                    findCustomerButton.setDisable(false);
                    editCustomerButton.setDisable(true);
                    updateCustomerButton.setDisable(true);
                    removeCustomerButton.setDisable(true);

                }
                else {
                    report.setText("Plan has to be LIMITED or ULIMITED");
                    report.setFill(Color.RED);
                }
            }
            
        });
        

        removeCustomerButton.setOnAction(e -> {            
            if(myManager.removeCustomer(temp.ID)){
                report.setText("Customer removed successfully!");
                report.setFill(Color.GREEN);
                writeCustomers(customerDB);
            }
            else {
            report.setText("Customer not found!");
            report.setFill(Color.GREEN);
            }
            
        });

        clearButton.setOnAction(e -> {
            customerIDField.clear();
            customerNameField.clear();
            customerAddressField.clear();
            customerPhoneField.clear();
            customerPlanField.clear();

            findCustomerButton.setDisable(false);
            editCustomerButton.setDisable(true);
            updateCustomerButton.setDisable(true);
            removeCustomerButton.setDisable(true);

            customerIDField.setDisable(false);
            customerNameField.setDisable(true);
            customerPhoneField.setDisable(true);
            customerAddressField.setDisable(true);
            customerPlanField.setDisable(true);


        });

        backButton.setOnAction(e -> {
            mainScene.setRoot(customerPane);
        });

       

        return;

    }
    private static void addMedia() {
        
        Label mediaType = new Label("Type: ");
        mediaType.setFont(mainFont);
        ComboBox mediaTypeComboBox = new ComboBox<Media>();
        mediaTypeComboBox.getItems().addAll("Movie", "Album", "Game");
        HBox mediaTypePane = new HBox();
        mediaTypePane.getChildren().addAll(mediaType, mediaTypeComboBox);

        Label mediaTitle = new Label("Title: ");
        mediaTitle.setFont(mainFont);
        TextField mediaTitleField = new TextField();
        mediaTitleField.setFont(mainFont);
        mediaTitleField.setDisable(true);
        HBox mediaTitlePane = new HBox();
        mediaTitlePane.getChildren().addAll(mediaTitle, mediaTitleField);

        Label mediaCopies = new Label("Copies: ");
        mediaCopies.setFont(mainFont);
        TextField mediaCopiesTextField = new TextField();
        mediaCopiesTextField.setFont(mainFont);
        mediaCopiesTextField.setDisable(true);
        HBox mediaCopiesPane = new HBox();
        mediaCopiesPane.getChildren().addAll(mediaCopies, mediaCopiesTextField);

        Label albumArtist = new Label("Artist: ");
        albumArtist.setFont(mainFont);
        TextField albumArtistField = new TextField();
        albumArtistField.setFont(mainFont);
        albumArtistField.setDisable(true);
        HBox albumArtistPane = new HBox();
        albumArtistPane.getChildren().addAll(albumArtist, albumArtistField);
        
        Label songList = new Label("Songs (type each song in a new line): ");
        songList.setFont(mainFont);
        TextArea songListField = new TextArea();
        songListField.setFont(mainFont);
        songListField.setMaxWidth(700);
        songListField.setDisable(true);
        VBox songListPane = new VBox();
        songListPane.getChildren().addAll(songList, songListField);

        Label movieRating = new Label("Rating: ");
        movieRating.setFont(mainFont);
        ToggleGroup movieRatingGroup = new ToggleGroup();
        RadioButton ratingHR = new RadioButton("HR");
        ratingHR.setFont(mainFont);
        RadioButton ratingDR = new RadioButton("DR");
        ratingDR.setFont(mainFont);
        RadioButton ratingAC = new RadioButton("AC");
        ratingAC.setFont(mainFont);

        ratingHR.setToggleGroup(movieRatingGroup);
        ratingDR.setToggleGroup(movieRatingGroup);
        ratingAC.setToggleGroup(movieRatingGroup);
        
        ratingAC.setDisable(true);
        ratingDR.setDisable(true);
        ratingHR.setDisable(true);

        HBox movieRatingRadioButtonsPane = new HBox();
        movieRatingRadioButtonsPane.getChildren().addAll(ratingHR, ratingDR, ratingAC);

        HBox movieRatingPane = new HBox();
        movieRatingPane.getChildren().addAll(movieRating, movieRatingRadioButtonsPane);

        Label gameWeight = new Label("Weight: ");
        gameWeight.setFont(mainFont);
        TextField gameWeightField = new TextField();
        gameWeightField.setFont(mainFont);
        gameWeightField.setDisable(true);
        HBox gameWeightPane = new HBox();
        gameWeightPane.getChildren().addAll(gameWeight, gameWeightField);
        
        Text report = new Text();
        report.setFont(secondaryFont);
        StackPane reportPane = new StackPane();
        reportPane.getChildren().addAll(report);
        reportPane.setAlignment(Pos.CENTER);


        VBox mainFieldsPane = new VBox();
        mainFieldsPane.getChildren().addAll(mediaTypePane, mediaTitlePane, mediaCopiesPane, albumArtistPane, movieRatingPane, gameWeightPane);
        mainFieldsPane.setSpacing(30);

        HBox fieldsPane = new HBox();
        fieldsPane.getChildren().addAll(mainFieldsPane, songListPane);
        fieldsPane.setSpacing(70);
        fieldsPane.setAlignment(Pos.CENTER);
        


        mediaTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mediaTitleField.setDisable(false);
                if (newValue.equals("Movie")) {
                    if (!mediaTitleField.getText().isBlank()){
                        mediaCopiesTextField.setDisable(false);
                        if(!mediaCopiesTextField.getText().isBlank() && isNumeric(mediaCopiesTextField.getText())){
                            ratingAC.setDisable(false);
                            ratingDR.setDisable(false);
                            ratingHR.setDisable(false);
                        }
                        else {
                            ratingAC.setDisable(true);
                            ratingDR.setDisable(true);
                            ratingHR.setDisable(true);
                        }
                    }
                    else {
                        mediaCopiesTextField.setDisable(true);
                        ratingAC.setDisable(true);
                        ratingDR.setDisable(true);
                        ratingHR.setDisable(true);
                    }             
                    albumArtistField.setDisable(true);
                    songListField.setDisable(true);
                    gameWeightField.setDisable(true);
                }
                else if (newValue.equals("Album")) {
                    if (!mediaTitleField.getText().isBlank()){
                        mediaCopiesTextField.setDisable(false);
                        if (!mediaCopiesTextField.getText().isBlank() && isNumeric(mediaCopiesTextField.getText())){
                            albumArtistField.setDisable(false);
                            if(!albumArtistField.getText().isBlank()){
                                songListField.setDisable(false);
                            }
                            else{
                                songListField.setDisable(true);
                            }

                        }
                        else {
                            albumArtistField.setDisable(true);
                            songListField.setDisable(true);

                        }
                    }
                    else{
                        mediaCopiesTextField.setDisable(true);
                        albumArtistField.setDisable(true);
                        songListField.setDisable(true);
                    }
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);
                    gameWeightField.setDisable(true);
                }
                else if (newValue.equals("Game")) {
                    if (!mediaTitleField.getText().isBlank()){
                        mediaCopiesTextField.setDisable(false);
                        if (!mediaCopiesTextField.getText().isBlank() && isNumeric(mediaCopiesTextField.getText())){
                            gameWeightField.setDisable(false);
                        }
                        else {
                            gameWeightField.setDisable(true);
                        }
                    }
                    else {
                        mediaCopiesTextField.setDisable(true);
                        gameWeightField.setDisable(true);

                    }
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);
                    albumArtistField.setDisable(true);
                    songListField.setDisable(true);
                    
                }
          
            }
        });        
        

        mediaTitleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    mediaCopiesTextField.setDisable(true);
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);
                    albumArtistField.setDisable(true);
                    songListField.setDisable(true);
                    gameWeightField.setDisable(true);

                    
                }
                else {
                    mediaCopiesTextField.setDisable(false);
                    if(mediaTypeComboBox.getValue().equals("Movie") && !mediaCopiesTextField.getText().isBlank()){
                        ratingAC.setDisable(false);
                        ratingDR.setDisable(false);
                        ratingHR.setDisable(false);
                    }
                    else if (mediaTypeComboBox.getValue().equals("Album") && !mediaCopiesTextField.getText().isBlank()){
                        albumArtistField.setDisable(false);
                        if(!albumArtistField.getText().isBlank()){
                            songListField.setDisable(false);
                        }
                        else{
                        songListField.setDisable(true);
                        }
                    }
                    else if (mediaTypeComboBox.getValue().equals("Game") && !mediaCopiesTextField.getText().isBlank()){
                        gameWeightField.setDisable(false);
                    }
                    
            
                    
                }
                
            }
        });

        mediaCopiesTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty() || !isNumeric(mediaCopiesTextField.getText())) {
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);                    
                    albumArtistField.setDisable(true);
                    songListField.setDisable(true);
                    gameWeightField.setDisable(true);
                    if(!isNumeric(mediaCopiesTextField.getText())){
                        report.setFill(Color.RED);
                        report.setText("Copies must be a number");
                    }
                }
                else {
                    if (mediaTypeComboBox.getValue().equals("Movie")) {
                        ratingAC.setDisable(false);
                        ratingDR.setDisable(false);
                        ratingHR.setDisable(false);
                        albumArtistField.setDisable(true);
                        songListField.setDisable(true);
                        gameWeightField.setDisable(true);
                    }
                    else if (mediaTypeComboBox.getValue().equals("Album")) {
                        ratingAC.setDisable(true);
                        ratingDR.setDisable(true);
                        ratingHR.setDisable(true);
                        albumArtistField.setDisable(false);
                        if(!albumArtistField.getText().isBlank()){
                            songListField.setDisable(false);
                        }
                        else{
                        songListField.setDisable(true);
                    }
                    gameWeightField.setDisable(true);
                        
                        
                    }
                    else if (mediaTypeComboBox.getValue().equals("Game")) {
                        ratingAC.setDisable(true);
                        ratingDR.setDisable(true);
                        ratingHR.setDisable(true);
                        albumArtistField.setDisable(true);
                        songListField.setDisable(true);
                        gameWeightField.setDisable(false);
                    }
                }
            }
        });

        albumArtistField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);
                    songListField.setDisable(true);
                    gameWeightField.setDisable(true);

                }
                else {
                    ratingAC.setDisable(true);
                    ratingDR.setDisable(true);
                    ratingHR.setDisable(true);
                    songListField.setDisable(false);
                    gameWeightField.setDisable(true);

                }
            }
        });
        HBox buttonPane = new HBox();
        buttonPane.setSpacing(10);

        Button addMediaButton = new Button("Add Media", new ImageView("file:add.png"));
        addMediaButton.setFont(secondaryFont);

        Button clearButton = new Button("clear", new ImageView("file:clear.png"));
        clearButton.setFont(secondaryFont);

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(secondaryFont);
        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().add(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);

        buttonPane.getChildren().addAll(addMediaButton, clearButton);
        buttonPane.setSpacing(20);
        buttonPane.setAlignment(Pos.CENTER);
        
        VBox addMediaPane = new VBox();
        addMediaPane.getChildren().addAll(fieldsPane, reportPane, buttonPane, backButtonPane);
        addMediaPane.setAlignment(Pos.CENTER);
        addMediaPane.setSpacing(20);
        addMediaPane.setPadding(paneInsets);
        
        mainScene.setRoot(addMediaPane);

        addMediaButton.setOnAction(e -> {
            if (mediaTitleField.getText().isEmpty() || ((albumArtistField.getText().isEmpty() || songListField.getText().isEmpty()) && mediaTypeComboBox.getValue().equals("Album") ) || (mediaTypeComboBox.getValue().equals("Movie") && !ratingAC.isSelected() && !ratingDR.isSelected() && !ratingHR.isSelected()) || (gameWeightField.getText().isEmpty() && mediaTypeComboBox.getValue().equals("Game"))) {
                report.setFill(Color.RED);
                report.setText("Please fill all the fields");
            }
            else {
                title = mediaTitleField.getText().trim();
                try {
                    copies = Integer.parseInt(mediaCopiesTextField.getText());
                }
                catch (Exception ee) {
                    report.setFill(Color.RED);
                    report.setText("Error occured while trying to get copies");
                }
                if (mediaTypeComboBox.getValue().equals("Movie")){
                    if (ratingAC.isSelected()){
                        rating = "AC";
                    }
                    else if (ratingDR.isSelected()){
                        rating = "DR";
                    }
                    else 
                        rating = "HR";
                    
                    myManager.addMovie(title, copies, rating);
                    writeMedia(mediaDB);

                }
                else if (mediaTypeComboBox.getValue().equals("Album")){
                    songs = songListField.getText();
                    myManager.addAlbum(mediaTitleField.getText(), copies, albumArtistField.getText(), songs );
                    writeMedia(mediaDB);

                }
                else {
                    myManager.addGame(mediaTitleField.getText(), copies, Double.parseDouble(gameWeightField.getText()));
                    writeMedia(mediaDB);
                }
                report.setText("Media added successfully!");
                report.setFill(Color.GREEN);

                mediaTitleField.clear();
                mediaCopiesTextField.clear();
                mediaTypeComboBox.setValue(null);
                movieRatingGroup.selectToggle(null);
                albumArtistField.clear();
                songListField.clear();
                gameWeightField.clear();
                // addMedia();

            }
        });

        clearButton.setOnAction(e -> {
            mediaTitleField.clear();
            mediaCopiesTextField.clear();
            mediaTypeComboBox.setValue(null);
            movieRatingGroup.selectToggle(null);
            albumArtistField.clear();
            songListField.clear();
            gameWeightField.clear();
        });

        backButton.setOnAction(e -> {
            mainScene.setRoot(mediaPane);
        });

        
        return;

    }
    private static void searchMedia(){
        // Button addMediaButton = new Button("Add Media");
        // addMediaButton.setDisable(true);

        Text report = new Text("");
        report.setFont(secondaryFont);
        StackPane reportPane = new StackPane();
        reportPane.setAlignment(Pos.CENTER);
        reportPane.getChildren().add(report);

        Label mediaCode = new Label("Code: ");
        mediaCode.setFont(secondaryFont);
        TextField mediaCodeField = new TextField();
        mediaCodeField.setFont(secondaryFont);
        HBox mediaCodePane = new HBox();
        mediaCodePane.getChildren().addAll(mediaCode, mediaCodeField);

        Label mediaType = new Label("Type: ");
        mediaType.setFont(secondaryFont);
        ComboBox mediaTypeComboBox = new ComboBox<>();
        mediaTypeComboBox.getItems().addAll("Album", "Movie", "Game");
        // mediaTypeComboBox.setDisable(true);
        HBox mediaTypePane = new HBox();
        mediaTypePane.getChildren().addAll(mediaType, mediaTypeComboBox);

        Label mediaTitle = new Label("Title: ");
        mediaTitle.setFont(secondaryFont);
        TextField mediaTitleField = new TextField();
        mediaTitleField.setFont(secondaryFont);
        // mediaTitleField.setDisable(true);
        HBox mediaTitlePane = new HBox();
        mediaTitlePane.getChildren().addAll(mediaTitle, mediaTitleField);


        
        Label albumArtist = new Label("Artist: ");
        albumArtist.setFont(secondaryFont);
        TextField albumArtistField = new TextField();
        albumArtistField.setFont(secondaryFont);
        // albumArtistField.setDisable(true);
        HBox albumArtistPane = new HBox();
        albumArtistPane.getChildren().addAll(albumArtist, albumArtistField);
        
        Label songList = new Label("Songs (type each song in a new line): ");
        songList.setFont(secondaryFont);
        TextArea songListField = new TextArea();
        songListField.setFont(secondaryFont);
        songListField.setPrefWidth(300);
        // songListField.setDisable(true);
        VBox songListPane = new VBox();
        songListPane.getChildren().addAll(songList, songListField);

        Label movieRating = new Label("Rating: ");
        movieRating.setFont(secondaryFont);
        ToggleGroup movieRatingGroup = new ToggleGroup();
        RadioButton ratingHR = new RadioButton("HR");
        ratingHR.setFont(secondaryFont);
        RadioButton ratingDR = new RadioButton("DR");
        ratingDR.setFont(secondaryFont);
        RadioButton ratingAC = new RadioButton("AC");
        ratingAC.setFont(secondaryFont);

        ratingHR.setToggleGroup(movieRatingGroup);
        ratingDR.setToggleGroup(movieRatingGroup);
        ratingAC.setToggleGroup(movieRatingGroup);
        

        HBox movieRatingPane = new HBox();
        movieRatingPane.getChildren().addAll(movieRating, ratingHR, ratingDR, ratingAC);

        Label gameWeight = new Label("Weight: ");
        gameWeight.setFont(secondaryFont);
        TextField gameWeightField = new TextField();
        gameWeightField.setFont(secondaryFont);
        // gameWeightField.setDisable(true);
        HBox gameWeightPane = new HBox();
        gameWeightPane.getChildren().addAll(gameWeight, gameWeightField);


        VBox mainFieldsPane = new VBox();
        mainFieldsPane.getChildren().addAll(mediaCodePane, mediaTypePane, mediaTitlePane, albumArtistPane, songListPane, movieRatingPane, gameWeightPane);
        mainFieldsPane.setSpacing(30);


        Label info = new Label("Info: ");
        info.setFont(secondaryFont);
        TextArea infoArea = new TextArea();
        infoArea.setFont(secondaryFont);
        infoArea.setMinWidth(700);
        infoArea.setPrefHeight(1000);
        infoArea.setEditable(false);
        VBox infoPane = new VBox();
        infoPane.getChildren().addAll(info, infoArea);


        HBox fieldsPane = new HBox();
        fieldsPane.setSpacing(30);
        fieldsPane.setAlignment(Pos.CENTER);
        fieldsPane.getChildren().addAll(mainFieldsPane, infoPane);

        Button findMediaButton = new Button ("Find Media", new ImageView("file:find.png"));
        findMediaButton.setFont(secondaryFont);
        // Button updateMediaButton = new Button ("Update Media");
        // updateMediaButton.setDisable(true);
        // Button removeMediaButton = new Button ("Remove Media");
        // removeMediaButton.setDisable(true);
        Button clearButton = new Button("clear", new ImageView("file:clear.png"));
        clearButton.setFont(secondaryFont);

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(secondaryFont);
        StackPane backButtonPane = new StackPane();
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);

        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(findMediaButton, clearButton);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(20);

        HBox buttonzzPane = new HBox();
        buttonzzPane.getChildren().addAll(backButton, buttonPane);
        buttonzzPane.setAlignment(Pos.CENTER);
        buttonzzPane.setSpacing(500);

        VBox searchMediaPane = new VBox();
        searchMediaPane.setAlignment(Pos.CENTER);
        searchMediaPane.setPadding(paneInsets);
        searchMediaPane.getChildren().addAll(fieldsPane, reportPane, buttonzzPane);//, buttonPane, backButtonPane);
        searchMediaPane.setSpacing(30);
        
        // Scene searchMediaScene = new Scene(searchMediaPane, 600, 400);
        mainScene.setRoot(searchMediaPane);
        

        findMediaButton.setOnAction(e -> {
            infoArea.clear();
          
            if (!mediaCodeField.getText().isEmpty() && mediaCodeField.getText().trim().length() != 5 ) {
                report.setFill(Color.RED);
                report.setText("Media code has to be 5 characeters!");
            }
            else if (mediaCodeField.getText().isBlank()){
                code = null;
            }
            else {

                code = mediaCodeField.getText();
            }

            if(mediaTitleField.getText().isBlank()){
                title = null;
            }
            else {
                title = mediaTitleField.getText();
            }
                        

            if (ratingAC.isSelected()){
                rating = "AC";
            }
            else if (ratingDR.isSelected()){
                rating = "DR";
            }
            else if (ratingHR.isSelected()){
                rating = "HR";
            }
            else {
                rating = null;
            }

            if (albumArtistField.getText().isBlank()){
                artist = null;
            }
            else {
                artist = albumArtistField.getText();
            }


            if (songListField.getText().isBlank()){
                songs = null;
            }
            else {
                songs = songListField.getText();
            }

            
            
                
            if (myManager.searchMedia(code, title, rating, artist, songs) == null ){
                report.setFill(Color.RED);
                report.setText("Media not found!");
            }
            else {
                
                    String results = "";
                    for (String line : myManager.searchMedia(code, title, rating, artist, songs)){
                        results += line + "\n";
                    }
                    infoArea.setText(results);
            }

        });

        
        clearButton.setOnAction(e -> {
            mediaCodeField.clear();
            mediaTitleField.clear();
            mediaTitleField.clear();
            ratingAC.setSelected(false);
            ratingDR.setSelected(false);
            ratingHR.setSelected(false);
            albumArtistField.clear();
            songListField.clear();
            infoArea.clear();


        });

        backButton.setOnAction(e -> {
            mainScene.setRoot(mediaPane);
        });

        

        return;

    }

    private static void removeMedia(){//also update and find by code
        Text report = new Text();
        report.setFont(secondaryFont);
        StackPane reportPane = new StackPane();
        reportPane.setAlignment(Pos.CENTER);
        reportPane.getChildren().add(report);

        Label mediaCode = new Label("Media Code: ");
        mediaCode.setFont(secondaryFont);
        TextField mediaCodeField = new TextField();
        mediaCodeField.setFont(secondaryFont);
        HBox mediaCodePane = new HBox();
        mediaCodePane.getChildren().addAll(mediaCode, mediaCodeField);

        Label mediaTitle = new Label("Title: ");
        mediaTitle.setFont(secondaryFont);
        TextField mediaTitleField = new TextField();
        mediaTitleField.setFont(secondaryFont);
        mediaTitleField.setDisable(true);
        HBox mediaTitlePane = new HBox();
        mediaTitlePane.getChildren().addAll(mediaTitle, mediaTitleField);

        Label mediaType = new Label("Type: ");
        mediaType.setFont(secondaryFont);
        TextField mediaTypeField = new TextField();
        mediaTypeField.setFont(secondaryFont);
        mediaTypeField.setDisable(true);
        HBox mediaTypePane = new HBox();
        mediaTypePane.getChildren().addAll(mediaType, mediaTypeField);


        Label mediaCopies = new Label("Copies: ");
        mediaCopies.setFont(secondaryFont);
        TextField mediaCopiesTextField = new TextField();
        mediaCopiesTextField.setFont(secondaryFont);
        mediaCopiesTextField.setDisable(true);
        HBox mediaCopiesPane = new HBox();
        mediaCopiesPane.getChildren().addAll(mediaCopies, mediaCopiesTextField);

        Label albumArtist = new Label("Artist: ");
        albumArtist.setFont(secondaryFont);
        TextField albumArtistField = new TextField();
        albumArtistField.setFont(secondaryFont);
        albumArtistField.setDisable(true);
        HBox albumArtistPane = new HBox();
        albumArtistPane.getChildren().addAll(albumArtist, albumArtistField);

        Label songList = new Label("Song List: (Each song is put in a new line)");
        songList.setFont(secondaryFont);
        TextArea songListField = new TextArea();
        songListField.setFont(secondaryFont);
        songListField.setDisable(true);
        songListField.setMaxWidth(700);
        VBox songListPane = new VBox();
        songListPane.getChildren().addAll(songList, songListField);

        Text movieRating = new Text("Rating: ");
        movieRating.setFont(secondaryFont);
        TextField movieRatingField = new TextField();
        movieRatingField.setFont(secondaryFont);
        movieRatingField.setDisable(true);
        HBox movieRatingPane = new HBox();
        movieRatingPane.getChildren().addAll(movieRating, movieRatingField);

        Label gameWeight = new Label("Weight: ");
        gameWeight.setFont(secondaryFont);
        TextField gameWeightField = new TextField();
        gameWeightField.setFont(secondaryFont);
        gameWeightField.setDisable(true);
        HBox gameWeightPane = new HBox();
        gameWeightPane.getChildren().addAll(gameWeight, gameWeightField);


        Button removeMediaButton = new Button ("Remove Media", new ImageView("file:remove_media.png"));
        removeMediaButton.setDisable(true);
        removeMediaButton.setFont(secondaryFont);
        removeMediaButton.setMaxHeight(50);

        Button findMediaButton = new Button ("Find Media", new ImageView("file:find_media.png"));
        findMediaButton.setDisable(true);
        findMediaButton.setFont(secondaryFont);

        Button editMediaButton = new Button("Edit", new ImageView("file:edit_media.png"));
        editMediaButton.setDisable(true);
        editMediaButton.setFont(secondaryFont);

        Button updateMediaButton = new Button ("Update Media", new ImageView("file:update_media.png"));
        updateMediaButton.setDisable(true);
        updateMediaButton.setFont(secondaryFont);

        Button clearButton = new Button("clear", new ImageView("file:clear.png"));
        clearButton.setFont(secondaryFont);

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(secondaryFont);

        VBox mainFieldsPane = new VBox();
        mainFieldsPane.getChildren().addAll(mediaCodePane, mediaTitlePane, mediaTypePane, mediaCopiesPane, albumArtistPane, movieRatingPane, gameWeightPane);
        mainFieldsPane.setSpacing(20);

        HBox fieldsPane = new HBox();
        fieldsPane.getChildren().addAll(mainFieldsPane, songListPane);
        fieldsPane.setSpacing(70);
        // fieldsPane.setPadding(paneInsets);
        fieldsPane.setAlignment(Pos.CENTER);

        HBox removeMediaButtonPane = new HBox();
        removeMediaButtonPane.getChildren().addAll(findMediaButton,removeMediaButton, updateMediaButton, editMediaButton, clearButton);
        removeMediaButtonPane.setSpacing(20);
        removeMediaButtonPane.setAlignment(Pos.CENTER);

        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().addAll(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_LEFT);
        // backButtonPane.setPadding(paneInsets);

        VBox removeMediaPane = new VBox();
        removeMediaPane.getChildren().addAll(fieldsPane, reportPane, removeMediaButtonPane, backButtonPane);
        removeMediaPane.setAlignment(Pos.CENTER);
        removeMediaPane.setPadding(paneInsets);
        removeMediaPane.setSpacing(30);

        mainScene.setRoot(removeMediaPane);

        clearButton.setOnAction(e -> {
            mediaCodeField.clear();
            mediaCodeField.setDisable(false);
            mediaTitleField.clear();
            mediaTitleField.setDisable(true);
            mediaTypeField.clear();
            mediaTypeField.setDisable(true);
            mediaCopiesTextField.clear();
            mediaCopiesTextField.setDisable(true);
            albumArtistField.clear();
            albumArtistField.setDisable(true);
            songListField.clear();
            songListField.setDisable(true);
            movieRatingField.clear();
            movieRatingField.setDisable(true);
            gameWeightField.clear();
            gameWeightField.setDisable(true);
            updateMediaButton.setDisable(true);
            editMediaButton.setDisable(true);
            removeMediaButton.setDisable(true);
            findMediaButton.setDisable(false);

        });

        backButton.setOnAction(e -> {
            // goBack(mediaScene, mainStage);
            mainScene.setRoot(mediaPane);
        });

        

        mediaCodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty() && newValue.trim().length() == 5){
                    findMediaButton.setDisable(false);
                }
                else {
                    findMediaButton.setDisable(true);
                }
            }
        });

        findMediaButton.setOnAction(e -> {
            mediaTitleField.clear();
            mediaTitleField.setDisable(true);
            mediaTypeField.clear();
            mediaTypeField.setDisable(true);
            mediaCopiesTextField.clear();
            mediaCopiesTextField.setDisable(true);
            albumArtistField.clear();
            albumArtistField.setDisable(true);
            songListField.clear();
            songListField.setDisable(true);
            movieRatingField.clear();
            movieRatingField.setDisable(true);
            gameWeightField.clear();
            gameWeightField.setDisable(true);
            
            for(Media m : myManager.mediaList){
                if (m.code.equalsIgnoreCase(mediaCodeField.getText().trim())){
                    mediaTitleField.setText(m.title);
                    mediaCopiesTextField.setText(Integer.toString(m.copies));
                    if (m instanceof Movie){
                        mediaTypeField.setText("Movie");
                        movieRatingField.setText(((Movie) m).rating);
                    }
                    else if (m instanceof Album){
                        mediaTypeField.setText("Album");
                        albumArtistField.setText(((Album) m).artist);
                        songListField.setText(((Album) m).songs.replaceAll(",", "\n"));
                    }
                    else if (m instanceof Game){
                        mediaTypeField.setText("Game");
                        gameWeightField.setText(Double.toString(((Game) m).grams));
                    }
                    
                    report.setText("Media Found!");
                    report.setFill(Color.GREEN);
                    editMediaButton.setDisable(false);
                    removeMediaButton.setDisable(false);

                    break;
                }
                if (m == myManager.mediaList.get(myManager.mediaList.size()-1) && !m.code.equalsIgnoreCase(mediaCodeField.getText().trim())){
                    report.setText("Media not found!");
                    report.setFill(Color.RED);

                    editMediaButton.setDisable(true);
                    removeMediaButton.setDisable(true);
                    updateMediaButton.setDisable(true);
                }
            }
            
        });

        editMediaButton.onActionProperty().set(e -> {
            mediaCodeField.setDisable(true);
            mediaTitleField.setDisable(false);
            mediaTypeField.setDisable(false);
            mediaCopiesTextField.setDisable(false);

            if (mediaTypeField.getText().equalsIgnoreCase("Movie")){
                movieRatingField.setDisable(false);
                albumArtistField.setDisable(true);
                songListField.setDisable(true);
                gameWeightField.setDisable(true);

            }
            else if (mediaTypeField.getText().equalsIgnoreCase("Album")){
                albumArtistField.setDisable(false);
                songListField.setDisable(false);
                movieRatingField.setDisable(true);
                gameWeightField.setDisable(true);


            }
            else if (mediaTypeField.getText().equalsIgnoreCase("Game")){
                gameWeightField.setDisable(false);
                albumArtistField.setDisable(true);
                songListField.setDisable(true);
                movieRatingField.setDisable(true);
            }

            mediaTypeField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue.equalsIgnoreCase("Movie")) {
                        // mediaTitleField.setDisable(false);
                        // mediaTypeField.setDisable(false);
                        // mediaCopiesTextField.setDisable(false);

                        movieRatingField.setDisable(false);

                        albumArtistField.setDisable(true);
                        songListField.setDisable(true);
                        gameWeightField.setDisable(true);
                        
                        // updateMediaButton.setDisable(false);
                        // editMediaButton.setDisable(true);
                        // removeMediaButton.setDisable(true);
                    }
                    else if (newValue.equalsIgnoreCase("Album")){
                        // mediaTitleField.setDisable(false);
                        // mediaTypeField.setDisable(false);
                        // mediaCopiesTextField.setDisable(false);

                        albumArtistField.setDisable(false);
                        songListField.setDisable(false);
                        gameWeightField.setDisable(false);

                        movieRatingField.setDisable(true);
                        gameWeightField.setDisable(true);

                        // updateMediaButton.setDisable(false);
                        // editMediaButton.setDisable(true);
                        // removeMediaButton.setDisable(true);
                    }
                    else if (newValue.equalsIgnoreCase("Game")){
                        // mediaTitleField.setDisable(false);
                        // mediaTypeField.setDisable(false);
                        // mediaCopiesTextField.setDisable(false);

                        movieRatingField.setDisable(true);
                        albumArtistField.setDisable(true);
                        songListField.setDisable(true);

                        gameWeightField.setDisable(false);

                        // updateMediaButton.setDisable(false);
                        // editMediaButton.setDisable(true);
                        // removeMediaButton.setDisable(true);
                    }

                }
            });
            updateMediaButton.setDisable(false);
            editMediaButton.setDisable(true);
            removeMediaButton.setDisable(true);
            findMediaButton.setDisable(true);
        });

        removeMediaButton.setOnAction(e -> {
            if (myManager.removeMedia(mediaCodeField.getText().trim())){
                report.setFill(Color.GREEN);
                report.setText("Media Removed Successfully!");
                writeMedia(mediaDB);
                writeCustomers(customerDB);
            }
            else {
                report.setText("Media not found!");
                report.setFill(Color.RED);
            }

            mediaCodeField.clear();

            mediaTitleField.clear();
            mediaTitleField.setDisable(false);

            mediaTypeField.clear();
            mediaTypeField.setDisable(false);

            mediaCopiesTextField.clear();
            mediaCopiesTextField.setDisable(false);

            movieRatingField.clear();
            movieRatingField.setDisable(false);
            
            albumArtistField.clear();
            albumArtistField.setDisable(true);

            songListField.clear();
            songListField.setDisable(false);

            gameWeightField.clear();
            gameWeightField.setDisable(false);

            removeMediaButton.setDisable(true);
            editMediaButton.setDisable(true);
            findMediaButton.setDisable(false);

        });;

        
        updateMediaButton.setOnAction(e -> {

           
            
            if (mediaTitleField.getText().isBlank() || mediaCopiesTextField.getText().isBlank() || !isNumeric(mediaCopiesTextField.getText().trim()) || ((albumArtistField.getText().isBlank() || songListField.getText().isBlank()) && mediaTypeField.getText().equals("Album") ) || (mediaTypeField.getText().equalsIgnoreCase("Movie") && !movieRatingField.getText().equalsIgnoreCase("AC") && !movieRatingField.getText().equalsIgnoreCase("DR") && !movieRatingField.getText().equalsIgnoreCase("HR")) || (gameWeightField.getText().isBlank() && mediaTypeField.getText().equals("Game"))) {
                report.setText("Please fill all the related fields");
                report.setFill(Color.RED);
            }
            else {
                code = mediaCodeField.getText().trim();
                title = mediaTitleField.getText().trim();
                try {
                    copies = Integer.parseInt(mediaCopiesTextField.getText());
                }
                catch (Exception ee) {
                    report.setText("Error occured while trying to get copies!");
                    report.setFill(Color.RED);
                }
                if (mediaTypeField.getText().equalsIgnoreCase("Movie")){
                    if (movieRatingField.getText().equalsIgnoreCase("AC")){
                        rating = "AC";
                        myManager.updateMovie(code, title, copies, rating);
                        report.setText("Media successfuly updated!");
                        report.setFill(Color.GREEN);
                        writeMedia(mediaDB);
                        writeCustomers(customerDB);
                    }
                    else if (movieRatingField.getText().equalsIgnoreCase("DR")){
                        rating = "DR";
                        myManager.updateMovie(code, title, copies, rating);
                        report.setText("Media successfuly updated!");
                        report.setFill(Color.GREEN);
                        writeMedia(mediaDB);
                        writeCustomers(customerDB);
                    }
                    else if (movieRatingField.getText().equalsIgnoreCase("HR")){
                        rating = "HR";
                        myManager.updateMovie(code, title, copies, rating);
                        report.setText("Media successfuly updated!");
                        report.setFill(Color.GREEN);
                        writeMedia(mediaDB);
                        writeCustomers(customerDB);
                    }
                    else {
                        report.setText("Please enter a valid rating!");
                        report.setFill(Color.RED);
                    }
                }
                else if (mediaTypeField.getText().equalsIgnoreCase("Album")){
                    artist = albumArtistField.getText().trim();
                    songs = songListField.getText().trim().replaceAll("\n", ",");
                    myManager.updateAlbum(code, title, copies, artist, songs);
                    report.setText("Media successfuly updated!");
                    report.setFill(Color.GREEN);
                    writeMedia(mediaDB);
                    writeCustomers(customerDB);

                }
                else if (mediaTypeField.getText().equalsIgnoreCase("Game")){
                    try {
                        weight = Double.parseDouble(gameWeightField.getText());
                    }
                    catch (Exception exception){
                        report.setText("Error occured while trying to get weight!");
                        report.setFill(Color.RED);
                    }
                    myManager.updateGame(code, title, copies, weight);
                    report.setText("Media successfuly updated!");
                    report.setFill(Color.GREEN);
                    writeMedia(mediaDB);
                    writeCustomers(customerDB);
                }
                else {
                    report.setText("Please enter a valid media type!");
                    report.setFill(Color.RED);
                }


                mediaCodeField.clear();
                mediaCodeField.setDisable(false);
                mediaTitleField.clear();
                mediaTitleField.setDisable(true);
                mediaCopiesTextField.clear();
                mediaCopiesTextField.setDisable(true);
                mediaTypeField.clear();
                mediaTypeField.setDisable(true);
                mediaTypeField.clear();
                mediaTypeField.setDisable(true);
                albumArtistField.clear();
                albumArtistField.setDisable(true);
                songListField.clear();
                songListField.setDisable(false);
                gameWeightField.clear();
                gameWeightField.setDisable(true);

                updateMediaButton.setDisable(true);
                editMediaButton.setDisable(true);
                removeMediaButton.setDisable(true);
                findMediaButton.setDisable(false);

            

            
            }

        });
        return;

    }

    private static void viewMedia() {
        TextArea info = new TextArea();
        info.setFont(secondaryFont);
        info.setEditable(false);
        info.setText(myManager.getAllMediaInfo());
        info.setMaxWidth(1000);
        info.setPrefHeight(1300);
        StackPane infoPane = new StackPane();
        infoPane.getChildren().add(info);
        infoPane.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back", new ImageView("file:back.png"));
        backButton.setFont(mainFont);
        StackPane backButtonPane = new StackPane();
        backButtonPane.getChildren().add(backButton);
        backButtonPane.setAlignment(Pos.BOTTOM_CENTER);

        VBox viewMediaPane = new VBox();
        viewMediaPane.getChildren().addAll(infoPane, backButtonPane);
        viewMediaPane.setAlignment(Pos.CENTER);
        viewMediaPane.setSpacing(40);
        viewMediaPane.setPadding(paneInsets);

        mainScene.setRoot(viewMediaPane);

        backButton.setOnAction(e -> {
            mainScene.setRoot(mediaPane);
        });

        

        return;
    }



   
    
    private static void readCustomers(File customerDB) {
        try {
            if (customerDB.exists()){
                Scanner scanCus = new Scanner (customerDB);
                while (scanCus.hasNext()) {
                    String buffer = scanCus.nextLine();
                    String[] bufferArr = buffer.split(";");
                    String ID = bufferArr[0].trim();
                    String name = bufferArr[1].trim();
                    String phone = bufferArr[2].trim();
                    String address = bufferArr[3].trim();
                    String plan = bufferArr[4].trim();
                    Customer tmp = new Customer(ID, name, phone, address, plan);
                    try {
                        String cart = bufferArr[5];
                        String [] cartArr = cart.split(",");
                        for (String i: cartArr) {
                            for (Media j : myManager.mediaList){
                                if (i.trim().equalsIgnoreCase(j.code.trim())) {
                                    tmp.cart.add(j);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        // System.out.println("No items in this user's cart.");
                    }
                    try {
                        String owned = bufferArr[6];
                        String [] ownedArr = owned.split(",");
                        for (String i : ownedArr) {
                            for (Media j : myManager.mediaList) {
                                if (i.trim().equalsIgnoreCase(j.code.trim())) {
                                    tmp.owned.add(j);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        // System.out.println("No items in this user's owned list.");
                    }
                    myManager.customers.add(tmp);
                }
                scanCus.close();   
            }
            else {
                // System.out.println("Customer file not found!\nCreating new file...");
                try{
                    customerDB.createNewFile();
                }
                catch (Exception e) {
                    // System.out.println("Error creating file!");
                }
            }     
        } 
        catch (FileNotFoundException e) {
            // System.out.println("Customer file error!");
        }
    }


    private static void writeCustomers (File customerDB) {
        String owned = "";
        String cart = "";
        try {
            if (customerDB.exists()) {
                PrintWriter writeCus = new PrintWriter(customerDB); //file writer
                for (Customer customer : myManager.customers) {
                    for (Media m : customer.owned) {
                        owned+=m.code.trim() + ",";
                    }
                    for (Media c : customer.cart) {
                        cart+=c.code.trim() + ",";
                    }
                    writeCus.print(customer.getInfo() + "; " + cart + "; " + owned + "\n");
                    owned = "";
                    cart = "";
                }
                writeCus.close();
            }
            else {
                // System.out.println("Customer file not found!");
            }
        }
        catch (FileNotFoundException e){
            // System.out.println("Customer file error!");
        }
       
    }
    
    private static void readMedia(File mediaDB) {
        try {
            if (mediaDB.exists()){
                Scanner scanMed = new Scanner (mediaDB); //file scanner
                while (scanMed.hasNext()) {
                    String buffer = scanMed.nextLine();
                    // System.out.println(buffer);
                    String[] bufferArr = buffer.split(";");
                    String code = bufferArr[1].trim();
                    String title = bufferArr[2].trim();
                    int copies = Integer.parseInt(bufferArr[3].trim());
                    if (bufferArr[0].trim().equalsIgnoreCase("Game")) {
                        double grams = Double.parseDouble(bufferArr[4]);
                        Game tmp = new Game(code, title, copies, grams);
                        myManager.mediaList.add(tmp);
                    }
                    else if (bufferArr[0].trim().equalsIgnoreCase("Movie")) {
                        String rating = bufferArr[4].trim();
                        Movie tmp = new Movie(code, title, copies, rating);
                        myManager.mediaList.add(tmp);
                    }
                    else if (bufferArr[0].trim().equalsIgnoreCase("Album")) {
                        String artist = bufferArr[4].trim();
                        String songs = bufferArr[5].trim();
                        Album tmp = new Album(code, title, copies, artist, songs);
                        myManager.mediaList.add(tmp);
                    }
                }
                scanMed.close();  
            }
            else {
                // System.out.println("Media file not found!\nCreating a new file...");
                try{
                    mediaDB.createNewFile();
                }
                catch (IOException e) {
                    // System.out.println("Can't create file!");
                    // return "Can't create file!";
                }
            }     
        } 
        catch (FileNotFoundException e) {
            // System.out.println("Media file error!");
            // return "Media file error!";
        }
    }
        
    private static void writeMedia (File mediaDB) {
        try {
            if (mediaDB.exists()){
                PrintWriter writeMed = new PrintWriter(mediaDB); //file writer
                for (Media media : myManager.mediaList) {
                    writeMed.print(media.type() + "; " + media.getInfo() + "\n");
                }
                writeMed.close();
            }
            // else {
            //     // System.out.println("Media file not found!");
            // }
        }
            catch (FileNotFoundException e) {
                // System.out.println("Media file error!");
            }
        
    }
    private static void writeLimitedValue (File limitedValueFile) {
        try{
            if (limitedValueFile.exists()){
            PrintWriter writeLim = new PrintWriter(limitedValueFile);
            writeLim.print(LimitedCustomer.LimitedValue);
            writeLim.close();
            }
            // else System.out.println("Limited value file not found!");
        }
        catch (Exception e) {
            // System.out.println("Limited value file error.");
        }
        
    }
    private static void readLimitedValue (File limitedValueFile){
        try {
            if (limitedValueFile.exists()){
                Scanner scanLim = new Scanner (limitedValueFile); //file scanner
                LimitedCustomer.LimitedValue = scanLim.nextInt();
                scanLim.close();
            }
            else {
                // System.out.println("Limited value file not found!\nCreating a new file...");
                try{
                    limitedValueFile.createNewFile();
                }
                catch (IOException e) {
                    // System.out.println("Can't create file!");
                }
            }
        }
        catch (FileNotFoundException e) {
            // System.out.println("Limited value file error.");
        }
    }
    
}








