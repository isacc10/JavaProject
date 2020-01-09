/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.controller;

import com.este.chatroom.model.Client;
import com.este.chatroom.model.ServerChat;
import com.este.chatroom.model.User;
import com.este.chatroom.util.APPException;
import com.sun.javafx.application.LauncherImpl;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jamal
 */
public class ChatRoom extends Application {
    
    private Stage stage;
    private User admin ;
    private boolean flag = false;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
                
            stage = primaryStage;
            stage.setTitle("ChatRoom Application");
            //stage.getIcons().add(new Image("/com/ocp/dotation/view/theme/ocplogo.png"));
            //stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            //stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            //Att
            gotoStartUI();
            
            
            //gotoMaster();
            
            primaryStage.centerOnScreen();
            primaryStage.show();
            //primaryStage.resizableProperty().setValue(Boolean.FALSE);
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void stop(){
        //Traitement 
        //GC
        this.stage.close();
    }
    
    public Stage getPrimaryStage(){
        return this.stage;
    }
    
    public User getLoggedAdmin() {
        return admin;
    }
    
    public void setLoggedAdmin(User admin){
        this.admin = admin ;
    }
    

    public void gotoStartUI() {
        try {
            StartController start = (StartController) replaceSceneContent("/com/este/chatroom/view/StartUI.fxml");
            start.setApp(this);
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void AdminLogout(){
        admin = null;
        gotoStartUI();
    }
    
    public void gotoChatRoomUI(final ServerChat server,final String username) {
        try {
            ChatRoomController chatRoom = (ChatRoomController) replaceSceneContent("/com/este/chatroom/view/ChatRoomUI.fxml");
            chatRoom.setApp(this);
            server.start(); 
            chatRoom.setChatRoomController(chatRoom);
            chatRoom.setServerChat(server);
            chatRoom.setCient(new Client(server.getPort(), username, chatRoom));
            File file = new File("C:\\Users\\Ishak\\Desktop\\ChatRoomAppData\\server_data.txt");
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file);PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.print(server.getPort());
            }
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public synchronized void init(){
        float total = 1;
        increment = 100f / total;
        load(null,null);
    }
    
    private float  increment = 0;
    private float  progress  = 0;
    
    private void load( String path, String name ){
        try {
            try{
                //Dao dao = new Dao();
                preloaderNotify();
            }catch(Exception e){
                System.out.println("ERROR can't connect to database server.");
                flag = true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    private synchronized void preloaderNotify() {
        progress += increment;
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ChatRoom.class, (java.lang.String[])null);        
    }
    
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        InputStream in = ChatRoom.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(ChatRoom.class.getResource(fxml));
        StackPane page;
        try {
            page = (StackPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.centerOnScreen();
       // stage.resizableProperty().setValue(Boolean.FALSE);
       // stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
