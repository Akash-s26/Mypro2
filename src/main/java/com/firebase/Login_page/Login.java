
package com.firebase.Login_page;

import com.firebase.firebase_connection.FirestoreService1;
import com.firebase.login_apk.Landing;
import com.firebase.login_apk.Homepage;
import com.firebase.notification.Notification;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login {

    private FirestoreService1 firebaseService;
    private Label welcomeLabel;

    public Scene createLoginScene(Stage pstage) {

        pstage.setWidth(1920);
        pstage.setHeight(1080);
        firebaseService = new FirestoreService1();

        // Stage settings
        pstage.setTitle("Abhyasika");
        pstage.setHeight(1080);
        pstage.setWidth(1920);
        pstage.setResizable(true);
        pstage.getIcons().add(new Image("logo-removebg-preview.png"));

        // Background image
        Image image = new Image("back.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setOpacity(0.7); // Adjust the value as needed (0.0 to 1.0)
        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);

        // Logo image
        Image image1 = new Image("logo-removebg-preview.png");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(200);

        // Username label and text field
        Label logoname = new Label(" Abhyasika.Com ");
        logoname.setFont(new Font("Arial", 50));
        logoname.setTextFill(Color.WHITE);
        logoname.setStyle("-fx-font-weight: bold");

        Label labelforusername = new Label(" Username:");
        labelforusername.setFont(new Font(20));
        labelforusername.setTextFill(Color.WHITE);
        TextField txt_username = new TextField();
        txt_username.setPromptText("Enter Username");
        txt_username.setFont(new Font(25));
        txt_username.setMaxWidth(400);
        txt_username.setStyle("-fx-text-fill: black ;-fx-font-size:22px;-fx-background-color: white");
        txt_username.setMaxWidth(400);
        txt_username.setPrefHeight(80);

        Rectangle clip = new Rectangle(400, 400);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        txt_username.setClip(clip);

        // Password label and password field
        Label labelforpassword = new Label(" Password:");
        labelforpassword.setFont(new Font(20));
        labelforpassword.setTextFill(Color.WHITE);
        PasswordField pswd_username = new PasswordField();
        pswd_username.setPromptText("Enter Password");
        pswd_username.setFont(new Font(25));
        pswd_username.setMaxWidth(400);
        pswd_username.setStyle("-fx-text-fill: black ;-fx-font-size:22px;-fx-background-color: white");
        pswd_username.setMaxWidth(400);
        pswd_username.setPrefHeight(80);

        Rectangle clip2 = new Rectangle(400, 400);
        clip2.setArcWidth(50);
        clip2.setArcHeight(50);
        pswd_username.setClip(clip2);


        // Initialize welcome label
        welcomeLabel = new Label();
        welcomeLabel.setFont(new Font("Arial", 30));
        welcomeLabel.setTextFill(Color.BLACK);
        welcomeLabel.setVisible(false); // Initially hidden

        Region spacer1 = new Region();
        spacer1.setPrefHeight(250);


        // Login button
        Button loginButton = createStyledButton("Login");
        loginButton.setOnAction(event -> {
            animateButton(loginButton);
            String username = txt_username.getText();
            String password = pswd_username.getText();
            System.out.println("Username: " + username + ", Password: " + password);

            // Handle login logic using FirebaseService
            boolean loginSuccessful = firebaseService.login(username, password);
            if (loginSuccessful) {
                boolean isAdmin = firebaseService.isAdmin(username);
                if (isAdmin) {
                    openAdminDashboard(pstage,username);
                } else {
                    openUserDashboard(pstage, username);
                    welcomeLabel.setText("Welcome, " + username);
                    welcomeLabel.setVisible(true);
                    Notification.showNotification(pstage, "Login Successful: You have logged in successfully!");
                }
            } else {
                Notification.showNotification(pstage, "Login Failed: Invalid username or password.");
            }

            txt_username.clear();
            pswd_username.clear();
        });

        // Sign Up button
        Button signUpButton = createStyledButton("Sign Up");
        signUpButton.setOnAction(event -> {
            animateButton(signUpButton);
            String username = txt_username.getText();
            String password = pswd_username.getText();
            String userId = firebaseService.signUp(username, password);
            System.out.println("User ID: " + userId);
            Notification.showNotification(pstage, "Sign Up Successful: Account created successfully!");
            txt_username.clear();
            pswd_username.clear();
        });

        // Close button
        Button closeButton = createStyledButton("Close");
        closeButton.setOnAction(event -> {
            animateButton(closeButton);
            Landing closescene = new Landing();
            try {
                closescene .start(pstage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            txt_username.clear();
            pswd_username.clear();
        });

        // HBox for buttons
        HBox buttonsBox = new HBox(20, loginButton, signUpButton, closeButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // VBox for login form
        VBox vbforlogin = new VBox(20, imageView1, logoname, labelforusername, txt_username, labelforpassword, pswd_username, spacer1,buttonsBox, welcomeLabel);
        vbforlogin.setAlignment(Pos.CENTER);
        vbforlogin.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 50px; -fx-border-radius: 10; -fx-background-radius: 10;");
        vbforlogin.setMaxWidth(600);
        vbforlogin.setMaxHeight(600);

        // Create a StackPane and set its background
        StackPane pane = new StackPane();
        pane.getChildren().addAll(imageView, vbforlogin);

        // Create a scene and add the pane to it
        Scene scene = new Scene(pane);

        return scene;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 23));
        button.setAlignment(Pos.CENTER);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        return button;
    }

    private void animateButton(Button button) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), button);
        transition.setByY(10);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();
        button.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10 20;");
    }

    private void openAdminDashboard(Stage pstage,String username) {
        try {
            AdminDashboard adminScene = new AdminDashboard();
            adminScene.createScene(pstage,username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openUserDashboard(Stage pstage, String username) {
        Homepage loginPage = new Homepage();
        try {
            loginPage.start(pstage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
