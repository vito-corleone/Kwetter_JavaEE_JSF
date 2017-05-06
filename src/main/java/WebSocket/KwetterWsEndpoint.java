/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import Model.Posting;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import service.PostingService;

/**
 *
 * @author Vito
 */
@ServerEndpoint(
        value = "/endpoint/{emailAddress}",
        encoders = JsonEncoder.class,
        decoders = JsonDecoder.class,
        configurator = HttpSessionProvider.class)
public class KwetterWsEndpoint {

    private static final Logger LOG = Logger.getLogger(KwetterWsEndpoint.class.toString());

    @Inject
    private PostingService postingService;
    /**
     * stock glassfish 4.0 doesn't support dependency injection in Endpoints,
     * which is a bug. Hence the EJB lookup.
     */
    private static final EchoBean ECHO_BEAN;

    static {
        final String name = "java:global/Kwetter/EchoBean";
        try {
            ECHO_BEAN = (EchoBean) InitialContext.doLookup(name);
            System.out.println("****** socket found " + ECHO_BEAN.toString());
        } catch (NamingException ex) {
            throw new IllegalStateException(ex);
        }

    }

    Map<String, String> socketSessions = new HashMap();

    private HttpSession httpSession;
    private Session session;
    private String emailAddress;

    @OnOpen
    public void onOpen(EndpointConfig endpointConfig, Session session, @PathParam("emailAddress") String emailAddress) {
        this.httpSession = HttpSessionProvider.provide(endpointConfig);
        this.session = session;
        this.emailAddress = emailAddress;
        if (!socketSessions.containsKey(emailAddress)) {
            socketSessions.put(emailAddress, session.getId());
            System.out.println("onOpen: starting session for: " + emailAddress + " with session-id: " + session.getId() + " total session count: " + socketSessions.size());
        }
        LOG.log(Level.INFO, "onOpen: user: {2}, endpointConfig: {0}, session: {1}", new Object[]{endpointConfig, session, emailAddress});
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        //        System.out.println("Principal name : " + session.toString());
        LOG.log(Level.INFO, "received message with text: {0}", message.getText());
        //System.out.println("onMessage: posting-id: " + newPosting.getId());
        Posting newPosting = new Posting(message.getAuthor(), message.getText());
        postingService.create(newPosting);
        ECHO_BEAN.send(session, newPosting, 5, 1000, 1.2);
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOG.log(Level.INFO, "session {0} closed with reason {1}", new Object[]{session, closeReason});
        String sessionId = session.getId();
        if (socketSessions.containsValue(sessionId)) {
            String key = null;
            for (Map.Entry entry : socketSessions.entrySet()) {
                if (entry.getValue().equals(sessionId)) {
                    key = entry.getKey().toString();
                    break; //breaking because its one to one map
                }
            }
            socketSessions.remove(key);
            System.out.println("onClose: removed session for user: " + key.toString() + " with session-id: " + sessionId + " total session count: " + socketSessions.size());
        }
        try {
            //httpSession.invalidate();
        } catch (IllegalStateException ise) {
            //swallow: httpSession allready expired
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOG.log(
                Level.WARNING,
                new StringBuilder("an error occured for session ").append(session).toString(),
                throwable
        );
    }

    public Session getSession() {
        return session;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

}
