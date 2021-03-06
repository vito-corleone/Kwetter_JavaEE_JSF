/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebSocket;

import Model.Posting;
import Model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
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
import service.UserService;

/**
 *
 * @author Vito
 */
@ServerEndpoint(
        value = "/endpoint/",
        encoders = JsonEncoder.class,
        decoders = JsonDecoder.class,
        configurator = HttpSessionProvider.class)
@ApplicationScoped
public class KwetterWsEndpoint {

    private static final Logger LOG = Logger.getLogger(KwetterWsEndpoint.class.toString());

    @Inject
    private PostingService postingService;

    @Inject
    private UserService userService;

    @Inject
    SessionHandler sessionHandler;
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

    private HttpSession httpSession;
    private Session session;

    @OnOpen
    public void onOpen(EndpointConfig endpointConfig, Session session) {
        // mogelijk de httpsessionprovider klasse helemaal verwijderen
        this.httpSession = HttpSessionProvider.provide(endpointConfig);
        this.session = session;
        LOG.log(Level.INFO, "onOpen: user: {2}, endpointConfig: {0}, session: {1}", new Object[]{endpointConfig, session});
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        LOG.log(Level.INFO, "received message with text: {0}", message.getText());
        if (message.getText().equals("SessionInit")) {
            sessionHandler.addSession(message.getAuthor(), session);
        } else {
            Posting newPosting = new Posting(message.getAuthor(), message.getText());
            postingService.create(newPosting);
            ECHO_BEAN.send(session, newPosting, 5, 1000, 1.2);
            updateFriends(message.getAuthor(), newPosting);
        }
    }

    private void updateFriends(String emailAddress, Posting newPosting) {        
        List<User> followers = userService.find(emailAddress).getThePeopleThatFollowMe();
        for (User user : followers) {
            Session session = sessionHandler.getSession(user.getEmailAddress());
            if (session != null) {
                ECHO_BEAN.send(session, newPosting, 5, 1000, 1.2);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOG.log(Level.INFO, "session {0} closed with reason {1}", new Object[]{session, closeReason});
        sessionHandler.removeSession(session);
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
