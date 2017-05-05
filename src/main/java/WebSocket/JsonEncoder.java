/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Encodes {@link Message}s to JSON
 * @author Vito
 */
public class JsonEncoder implements Encoder.Text<Message> {
    
    private final Gson gson = new Gson();

    @Override
    public String encode(Message arg0) throws EncodeException {
        return gson.toJson(arg0);
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
