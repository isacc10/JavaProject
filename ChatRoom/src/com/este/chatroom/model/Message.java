package com.este.chatroom.model;

import java.io.Serializable;


public class Message implements Serializable{
	/**
	 * 
	 */
    private static final long serialVersionUID = -5070250809390820023L;
    private String message;
    private String username;
    private boolean flag;

    public Message(String message, String username, boolean flag) {
        this.message = message;
        this.username = username;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Message{" + "message=" + message + ", username=" + username + ", flag=" + flag + '}';
    }

    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
	
	
	
	
}
