/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.controller;

import com.este.chatroom.util.APPException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jamal
 */
public class YMController implements Initializable {
    @FXML
    private Label message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void config(final String msg){
        try{
            this.message.setText(msg);
        }catch(Exception ex){
            APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
        }
    }
}
