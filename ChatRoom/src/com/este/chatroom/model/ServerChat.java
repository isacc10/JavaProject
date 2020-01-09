package com.este.chatroom.model;

import com.este.chatroom.util.APPException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;

public class ServerChat extends Thread{
	int nbClients = 0;
	private ArrayList<Socket> list = new ArrayList<>();
        private ArrayList<String> clients = new ArrayList<>();
	private int port; 
        private String chatroomName;
        
        
        public ServerChat(final String crn) throws Exception{
            int lpf = getLocalPortFree(1024,65535);
            if( lpf >= 1024 && lpf<=65535){
                this.port = lpf;
                this.chatroomName = crn;
                System.out.println("Port::"+port);
            }else{
                throw new Exception();
            }
        }
        
        public void addClient(String usname){
            this.clients.add(usname);
        }
        
        private int getLocalPortFree(final int from, final int to) {
            int p=from;
            while(p<=to){
                try {
                    new ServerSocket(p).close();
                    return p;
                } catch (IOException e) {
                    //NRF
                    p++;
                }
            }
            return -1;
        }
        
        @Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(getPort());
			while(true) {
				Socket s = ss.accept();
				list.add(s);
				nbClients++;
				new Conversation(s,nbClients,this).start();
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
		}
		
	}

	public void broadCast(final String IP,final String msg) {
		try {
			for (Socket socket : list) {
				if(socket.getRemoteSocketAddress().toString().equals(IP)) continue;
				PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
				pw.println(msg);
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch bloc
                    APPException.showErrorMessage(Arrays.toString(ex.getStackTrace()), Alert.AlertType.ERROR);
		}
	}

    public int getPort() {
        return port;
    }

    public String getChatroomName() {
        return chatroomName;
    }

	
	
	public static void main(String[] args) {
            //ServerChat s=new ServerChat(234);
            //s.setServer(s);
            //s.start();
	}
}
