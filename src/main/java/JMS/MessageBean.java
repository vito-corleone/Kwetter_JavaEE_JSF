/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Model.Anon_Posting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import service.JMSService;

/**
 *
 * @author Vito
 */
@MessageDriven(mappedName = "jms/kwetter_queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener {
    
    @Inject
    private JMSService jmsService;
            

    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message;
        try {
            System.out.println(msg.getText());
            if (!msg.getText().isEmpty()) {
                int marker = msg.getText().indexOf(":");
                String name = msg.getText().substring(0, marker);
                String content = msg.getText().substring(marker + 1, msg.getText().length()); 
                Anon_Posting newMessage = new Anon_Posting(name, content);
                jmsService.createPosting(newMessage);
                Topic_Publisher publisher = new Topic_Publisher(msg.getText());
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
