/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import java.io.Serializable;

/**
 * Represents a simple message for websockets
 * @author Vito
 */
public class Message implements Serializable{
    
    private String text;
    private String author;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }
    
    public Message(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
