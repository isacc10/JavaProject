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
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class ChatRoomController implements Initializable {
    @FXML
    private JFXTextField tf_find;
    @FXML
    private JFXButton btn_find;
    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXListView<HBox> vbox_clients;
    @FXML
    private JFXListView<HBox> listView;
    @FXML
    private Label cur_user;
    @FXML
    private VBox vbox_conversation;
    @FXML
    private JFXTextField tf_msg;
    @FXML
    private JFXButton send;

    
    private ChatRoomController controller;
    private ChatRoom application;
    private ServerChat server;
    private Client client;
    
    public void setCient(final Client client){
        this.client = client;
        cur_user.setText(this.client.getUsername());
        addClientToList(this.client.getUsername());
        update();
    }
    
    public void update(){
        try {
            this.client.setOut("NEW_CLIENT={"+this.client.getUsername()+"}");
        } catch (Exception ex) {
                APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void setApp(ChatRoom application){
        this.application = application;
    } 
    
    public void setServerChat(final ServerChat server){
        this.server = server;
    }
    
    public void setChatRoomController(ChatRoomController ctrl){
        this.controller = ctrl;
    }
    
    public void addClientToList(final String usern){
        try{
            //listView.getItems().add(getConversationCell_You("ENCORE OK"));
            HBox h = getClientCell(usern);
            //if(h!=null) vbox_clients.getChildren().add(h);
            //else vbox_clients.getChildren().add(vbox_clients.getChildren().get(0));
            vbox_clients.getItems().add(h);
            //listView.getItems().add(getConversationCell_You("FINISH"));
        }catch(Exception ex){
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        vbox_clients.setMouseTransparent( true );
        vbox_clients.setFocusTraversable( false );
    }    

    
    public void recevoir(final String from, final String msg){
        try {
                listView.getItems().add(getConversationCell(from, msg));
        } catch (IOException ex) {
                APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
    
    public void envoye(final String msg){
        
    }
    
    public HBox getConversationCell( final String from, final String msg ) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/este/chatroom/view/MsgFromOthers.fxml"));
            Parent root = loader.load();
            MFOController CTRL = loader.getController();
            CTRL.config( from,msg );
            return (HBox) root;
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
        return null;
    }
    
    public HBox getConversationCell_You( final String msg ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/este/chatroom/view/YourMsg.fxml"));
            Parent root = loader.load();
            YMController CTRL = loader.getController();
            CTRL.config( msg );
            return (HBox) root;
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
        return null;
    }
    
    public HBox getClientCell( final String username ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/este/chatroom/view/ClientUI.fxml"));
            Parent root = loader.load();
            ClientUIController CTRL = loader.getController();
            CTRL.config( username );
            return (HBox) root;
        } catch (Exception ex) {
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
        return null;
    }
    
    @FXML
    private void findPeople(ActionEvent event) {
        try {
                listView.getItems().add(getConversationCell("TEST", "MSG_TEST"));
        } catch (IOException ex) {
                APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void Send(ActionEvent event) {
        //Makhedamach !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        try {
            this.client.setOut("<"+this.client.getUsername()+">"+tf_msg.getText());
            listView.getItems().add(getConversationCell_You(tf_msg.getText()));
            tf_msg.setText("");
        } catch (Exception ex) {
                APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
        
    }
    
}
