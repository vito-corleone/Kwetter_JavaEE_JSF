/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Decodes {@link Message}s from JSON
 * @author Vito
 */
public class JsonDecoder implements Decoder.Text<Message>{

    private final Gson gson = new Gson();
    
    @Override
    public void init(EndpointConfig config) {

    }
    
    @Override
    public Message decode(String arg0) throws DecodeException {
        return gson.fromJson(arg0, Message.class);
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }


    @Override
    public void destroy() {

    }
    
}