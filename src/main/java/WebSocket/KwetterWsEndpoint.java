/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Vito
 */
@ServerEndpoint(
        value = "/endpoint",
        encoders = JsonEncoder.class,
        decoders = JsonDecoder.class,
        configurator = HttpSessionProvider.class)
public class KwetterWsEndpoint {

    private static final Logger LOG = Logger.getLogger(KwetterWsEndpoint.class.toString());


    /**
     * stock glassfish 4.0 doesn't support dependency injection in Endpoints,
     * which is a bug. Hence the EJB lookup.
     */
    private static final EchoBean ECHO_BEAN;

    static {
//        final String name = "ws://localhost:8080/Kwetter/endpoint";
          final String name = "java:global/Kwetter/EchoBean";        
        try {
            ECHO_BEAN = (EchoBean) InitialContext.doLookup(name);
            System.out.println("****** socket found " + ECHO_BEAN.toString());
        } catch (NamingException ex) {
            throw new IllegalStateException(ex);
        }

    }

    private HttpSession httpSession;

    private Session session;

    @OnOpen
    public void onOpen(EndpointConfig endpointConfig, Session session) {
        this.httpSession = HttpSessionProvider.provide(endpointConfig);
        this.session = session;
        LOG.log(Level.INFO, "onOpen: endpointConfig: {0}, session: {1}", new Object[]{endpointConfig, session});
        
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        LOG.log(Level.INFO, "received message with text: {0}", message.getText());
        ECHO_BEAN.send(session, message, 5, 1000, 1.2);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOG.log(Level.INFO, "session {0} closed with reason {1}", new Object[]{session, closeReason});
        try {
            httpSession.invalidate();
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
