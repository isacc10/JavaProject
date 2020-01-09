/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.model;

import com.este.chatroom.util.APPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import javafx.scene.control.Alert;


class Conversation extends Thread{
		private Socket socket;
		private int numClient;
                private ServerChat server;
		/**
		 * @param socket
		 * @param numClient
		 */
		public Conversation(Socket socket,int numClient, ServerChat server) {
			super();
			this.socket = socket;
			this.numClient = numClient;
                        this.server = server;
		}

		@Override
		public void run() {
			try {
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os,true);
				
				String IP = socket.getRemoteSocketAddress().toString();
				
				System.out.println("Client numero "+numClient+" IP="+IP);
				pw.println("Bienvenue, Vous etes le client numero "+numClient);
				//pw.println(IP);
                                //server.broadCast(IP, "NEW_CLIENT={"+this.client.getUsername()+"}");
                                //Envoyer un messages vers ts les clients,
                                
				while(true) {
					String requete ;
					while((requete=br.readLine())!=null) {
						server.broadCast(IP, requete);
						//System.out.println(IP+" a envoy� "+requete);
						//String rep = "Size="+requete.length();
						//pw.println(rep);
					}
					
				}
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
			}
		}
	}
