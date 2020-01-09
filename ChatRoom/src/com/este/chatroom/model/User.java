/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.este.chatroom.model;

public class User {
    private String username;
    private int numClient;
    private String  addresseIP;

    public User(String username, int numClient, String addresseIP) {
        this.username = username;
        this.numClient = numClient;
        this.addresseIP = addresseIP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public String getAddresseIP() {
        return addresseIP;
    }

    public void setAddresseIP(String addresseIP) {
        this.addresseIP = addresseIP;
    }

    @Override
    public String toString() {
        return "Client{" + "username=" + username + ", numClient=" + numClient + ", addresseIP=" + addresseIP + '}';
    }
    
    
}
