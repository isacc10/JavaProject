/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.model;

import com.este.chatroom.controller.ChatRoomController;
import com.este.chatroom.util.APPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import javafx.scene.control.Alert;


public class Client {
    Socket clientSocket = null;
    BufferedReader in;
    PrintWriter out;
    Scanner sc = new Scanner(System.in);//pour lire à partir du clavier
    
    private ChatRoomController controller;
    private int port;
    private String username;
    
    public Client(final int port, final String username, final ChatRoomController controller) {
      this.controller = controller;
      this.port = port;
      this.username = username;
  
      try {
         /*
         * les informations du serveur ( port et adresse IP ou nom d'hote
         * 127.0.0.1 est l'adresse local de la machine
         */
         clientSocket = new Socket("127.0.0.1",this.port);
   
         //flux pour envoyer
         out = new PrintWriter(clientSocket.getOutputStream());
         //flux pour recevoir
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        /*
         Thread envoyer = new Thread(new Runnable() {
             String msg;
              @Override
              public void run() {
                while(true){
                  msg = sc.nextLine();
                  out.println(msg);
                  out.flush();
                }
             }
         });
         envoyer.start();
        */
        Thread recevoir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
               try {
                 msg = in.readLine();
                 while(msg!=null){
                    String from;
                    //controller.recevoir(from, msg.substring(0, 10));
                    if (msg.substring(0, 10).equals("NEW_CLIENT")){
                        //controller.recevoir(from, "TEST OK");
                        controller.addClientToList(msg.substring(msg.indexOf("{")+1, msg.indexOf("}")));
                    }else{
                        try{
                            from = msg.substring(msg.indexOf("<")+1, msg.indexOf(">"));
                        }catch(Exception e){
                            from = "SERVEUR";
                        }
                        controller.recevoir(from, msg.substring(msg.indexOf(">")+1));
                    }
                    
                    //System.out.println("Serveur : "+msg);
                    msg = in.readLine();
                 }
                 System.out.println("Serveur déconecté");
                 out.close();
                 clientSocket.close();
               } catch (IOException ex) {
                   APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
               }
            }
        });
        recevoir.start();
   
      } catch (IOException e) {
           e.printStackTrace();
      }
  }


    
    

    /*
    public Client(){
    try {
    Socket s = new Socket("localhost", 234);
    InputStream is = s.getInputStream();
    OutputStream os = s.getOutputStream();
    Scanner scanner = new Scanner(System.in);
    System.out.print("Donner un nombre: ");
    int nb = scanner.nextInt();
    os.write(nb);
    int rep = is.read();
    System.out.println("Resultat: "+rep);
    s.close();
    } catch (IOException ex) {
    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    public static void main(String[] args) {
    }*/
    
    
    public String getUsername() {
        return username;
    }
    

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(final String msg) {
        this.out.println(msg);
        this.out.flush();
    }
    
}
