/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.controller;

import com.este.chatroom.model.Client;
import com.este.chatroom.model.ServerChat;
import com.este.chatroom.util.APPException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class StartController implements Initializable {
    @FXML
    private JFXTextField tf_Username;

    @FXML
    private JFXTextField tf_roomCode;

    @FXML
    private JFXButton btn_join;

    @FXML
    private JFXButton btn_create;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private Label errorMessage;
        
    private ChatRoom application;
    
    
    public void setApp(ChatRoom application){
        this.application = application;
    } 
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        animateMessage();
    }   
    
    
    @FXML
    void CreateChatRoom(ActionEvent event) {
        animateMessage();
        try {
            if(checkForm()){
                ServerChat server;
                try {
                    server = new ServerChat(tf_roomCode.getText());
                    this.application.gotoChatRoomUI(server,tf_Username.getText());
                } catch (Exception e) {
                    errorMessage.setText("SVP, completer les champs requis");
                }
            }else{
                errorMessage.setText("SVP, completer les champs requis");
            }
        } catch (Exception e) {
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.NONE);
        }
    }

    @FXML
    void JoinChatRoom(ActionEvent event) {
        animateMessage();
        try {
            if(checkForm()){
                try {
                    try{
                        String fxml = "/com/este/chatroom/view/ChatRoomUI.fxml";
                        FXMLLoader loader = new FXMLLoader();
                        InputStream in = StartController.class.getResourceAsStream(fxml);
                        loader.setBuilderFactory(new JavaFXBuilderFactory());
                        Parent Root;
                        Stage stage = new Stage();
                        try {
                            Root = (Parent) loader.load(in);
                        } finally {
                            in.close();
                        } 
                        //Root.getStylesheets().add(getClass().getResource("/view/app/search/style.css").toExternalForm());
                        Scene scene = new Scene( Root );
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.resizableProperty().setValue(Boolean.FALSE);
                        stage.sizeToScene();
                        stage.resizableProperty().setValue(Boolean.FALSE);
                        stage.sizeToScene();


                        ChatRoomController ctrl = (ChatRoomController) loader.getController();
                        ctrl.setApp(this.application);
                        //chatRoom.setChatRoomController(chatRoom);
                        //ctrl.setServerChat(this.);
                        try {
							//File myObj = new File("C:\\Users\\Ishak\\Desktop\\ChatRoomAppData\\server_data.txt");
							//Scanner myReader = new Scanner(myObj);
							//String data = myReader.nextLine();
							int server_port =1024; //= Integer.parseInt(data);
							ctrl.setCient(new Client(server_port, tf_Username.getText(), ctrl));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							APPException.showErrorMessage( "ERROR 123456", Alert.AlertType.ERROR);
						}
                        
                        
                        stage.initModality( Modality.WINDOW_MODAL );
                        stage.initOwner( this.application.getPrimaryStage() );
                        stage.show();
                    }catch(IOException | NumberFormatException ex){
                        APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
                    }
                } catch (Exception ex) {
                    APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
                }
            }else{
                errorMessage.setText("SVP, completer les champs requis");
            }
        } catch (Exception e) {
            APPException.showErrorMessage( Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public boolean checkForm(){
        if(tf_Username.getText().isEmpty() || tf_roomCode.getText().isEmpty()){
            return false;
        }
        return true;
    }
    
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), errorMessage);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
}
