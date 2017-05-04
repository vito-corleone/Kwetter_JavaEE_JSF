/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools  Templates
 * and open the template in the editor.
 */
package JMS;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.DeliveryMode;
import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import javax.jms.JMSException;
import javax.jms.ExceptionListener;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Vito
 */
public class Topic_Publisher {

    private static final Logger LOG = Logger.getLogger(MessageBean.class.getName());
    private static final String JNDI_CONNECTION_FACTORY = "jms/kwetter_factory_topic";
    private static final String JNDI_TOPIC = "jms/Kwetter_Topic";

    public Topic_Publisher(String text) {
        try {
            // get the initial context
            InitialContext ctx = new InitialContext();
            
            // lookup the topic object
            Topic topic = (Topic) ctx.lookup(JNDI_TOPIC);
            
            // lookup the topic connection factory
            TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.
                    lookup(JNDI_CONNECTION_FACTORY);
            
            // create a topic connection
            TopicConnection topicConn = connFactory.createTopicConnection();
            
            // create a topic session
            TopicSession topicSession = topicConn.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
            
            // create a topic publisher
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);
            topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // create the "Hello World" message
            TextMessage message = topicSession.createTextMessage();
            message.setText(text);
            
            // publish the messages
            topicPublisher.publish(message);
            
            // print what we did
            System.out.println("published: " + message.getText());
            
            // close the topic connection
            topicConn.close();
        } catch (NamingException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
