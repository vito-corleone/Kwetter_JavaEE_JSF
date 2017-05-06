/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import javax.websocket.Session;

/**
 *
 * @author Vito
 */
@Singleton
public class SessionHandler implements Serializable {

    private Map<String, Session> socketSessions = new HashMap();

    public SessionHandler() {

    }

    public void addSession(String emailAddress, Session session) {
        socketSessions.put(emailAddress, session);
        System.out.println("Starting session for: " + emailAddress + " with session-id: " + session.getId() + " total active sessions: " + socketSessions.size());
    }

    public void removeSession(Session session) {
        String emailAddress = "";
        String sessionId = session.getId();
        for (Map.Entry entry : socketSessions.entrySet()) {
            if (sessionId.equals(entry.getValue())) {
                emailAddress = entry.getKey().toString();
                break; //breaking because its one to one map
            }
        }
        if (!emailAddress.isEmpty()) {
            socketSessions.remove(emailAddress);
            System.out.println("Removing session for: " + emailAddress + " with session-id: " + session.getId() + " total active sessions: " + socketSessions.size());
        }
    }

    public Session getSession(String emailAddress) {
        return socketSessions.get(emailAddress);
    }

}
