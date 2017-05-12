/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools  Templates
 * and open the template in the editor.
 */
package JMS;

import java.util.List;
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
    private static final String JNDI_TOPIC_SPORT = "jms/Kwetter_Topic_Sport";
    private static final String JNDI_TOPIC_POLITIEK = "jms/Kwetter_Topic_Politiek";
    private static final String JNDI_TOPIC_COMPUTER = "jms/Kwetter_Topic_Computer";
    private static final String JNDI_TOPIC_GAMES = "jms/Kwetter_Topic_Games";

    private TopicConnection topicConn;
    private TopicSession topicSession;
    private TopicPublisher topicPublisher;
    private static Topic_Publisher instance = null;

    protected Topic_Publisher() {
    }

    public static Topic_Publisher getInstance() {
        if (instance == null) {
            instance = new Topic_Publisher();
        }
        return instance;
    }

    // de casus vereist dat er vier topics gemaakt worden.
    // in payara zijn dit dan ook vier afzonderlijke topics. 
    // op basis van de message topic wordt het bericht naar de juiste topic gestuurd
    public void sendMessageToTopic(String text, List<String> topics) {
        if (!text.isEmpty()) {
            if (topics != null && topics.size() > 0) {
                for (String topic : topics) {
                    switch (topic) {
                        case "#sport":
                            if (connectToTopic("jms/Kwetter_Topic_Sport")) {
                                sendMessage(text);
                                System.out.println("JMS.Topic_Publisher.sendMessageToTopic() published " + text + " to topic " + topic);
                                closeConnection();
                            }
                            break;
                        case "#politiek":
                            if (connectToTopic("jms/Kwetter_Topic_Politiek")) {
                                sendMessage(text);
                                System.out.println("JMS.Topic_Publisher.sendMessageToTopic() published " + text + " to topic " + topic);
                                closeConnection();
                            }
                            break;
                        case "#computer":
                            if (connectToTopic("jms/Kwetter_Topic_Computer")) {
                                sendMessage(text);
                                System.out.println("JMS.Topic_Publisher.sendMessageToTopic() published " + text + " to topic " + topic);
                                closeConnection();
                            }
                            break;
                        case "games":
                            if (connectToTopic("jms/Kwetter_Topic_Games")) {
                                sendMessage(text);
                                System.out.println("JMS.Topic_Publisher.sendMessageToTopic() published " + text + " to topic " + topic);
                                closeConnection();
                            }
                            break;
                        default:
                            System.out.println("JMS.Topic_Publisher.sendMessageToTopic() topic not recognized: " + topic);
                    }
                }
            }
        }
    }

    private boolean connectToTopic(String topic) {
        try {
            // get the initial context
            InitialContext ctx = new InitialContext();

            // lookup the topic object
            Topic jndiTopic = (Topic) ctx.lookup(topic);

            // lookup the topic connection factory
            TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.
                    lookup(JNDI_CONNECTION_FACTORY);

            // create a topic connection
            topicConn = connFactory.createTopicConnection();

            // create a topic session
            topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            // create a topic publisher
            topicPublisher = topicSession.createPublisher(jndiTopic);
            topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            System.out.println("JMS.Topic_Publisher.connectToTopic() " + topic);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void sendMessage(String message) {
        try {
            TextMessage newMessage = topicSession.createTextMessage();
            newMessage.setText(message);
            topicPublisher.publish(newMessage);
            System.out.println("published: " + newMessage.getText());
        } catch (JMSException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeConnection() {
        try {
            this.topicConn.close();
            System.out.println("JMS.Topic_Publisher.closeConnection()");
        } catch (JMSException ex) {
            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public Topic_Publisher(String text) {
////        try {
////            // get the initial context
////            InitialContext ctx = new InitialContext();
////
////            // lookup the topic object
////            Topic topic = (Topic) ctx.lookup(JNDI_TOPIC);
////
////            // lookup the topic connection factory
////            TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.
////                    lookup(JNDI_CONNECTION_FACTORY);
////
////            // create a topic connection
////            TopicConnection topicConn = connFactory.createTopicConnection();
////
////            // create a topic session
////            TopicSession topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
////
////            // create a topic publisher
////            TopicPublisher topicPublisher = topicSession.createPublisher(topic);
////            topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
////
////            // create the message
////            TextMessage message = topicSession.createTextMessage();
////            message.setText(text);
////
////            // publish the messages
////            topicPublisher.publish(message);
////
////            // print what we did
////            System.out.println("published: " + message.getText());
////
////            // close the topic connection
////            topicConn.close();
////        } catch (NamingException ex) {
////            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
////        } catch (JMSException ex) {
////            Logger.getLogger(Topic_Publisher.class.getName()).log(Level.SEVERE, null, ex);
////        }
//
//    }
}
