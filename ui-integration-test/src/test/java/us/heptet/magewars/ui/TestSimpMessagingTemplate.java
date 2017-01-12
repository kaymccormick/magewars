package us.heptet.magewars.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

/**
 * Created by jade on 14/09/2016.
 */
public class TestSimpMessagingTemplate implements SimpMessageSendingOperations {
    private static final Logger logger = LoggerFactory.getLogger(TestSimpMessagingTemplate.class);

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload) throws MessagingException {
        logger.info("{}", payload.toString());
    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, Map<String, Object> headers) throws MessagingException {

    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, MessagePostProcessor postProcessor) throws MessagingException {

    }

    @Override
    public void convertAndSendToUser(String user, String destination, Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) throws MessagingException {

    }

    @Override
    public void send(Message<?> message) throws MessagingException {

    }

    @Override
    public void send(String destination, Message<?> message) throws MessagingException {

    }

    @Override
    public void convertAndSend(Object payload) throws MessagingException {

    }

    @Override
    public void convertAndSend(String destination, Object payload) throws MessagingException {

    }

    @Override
    public void convertAndSend(String destination, Object payload, Map<String, Object> headers) throws MessagingException {

    }

    @Override
    public void convertAndSend(Object payload, MessagePostProcessor postProcessor) throws MessagingException {

    }

    @Override
    public void convertAndSend(String destination, Object payload, MessagePostProcessor postProcessor) throws MessagingException {

    }

    @Override
    public void convertAndSend(String destination, Object payload, Map<String, Object> headers, MessagePostProcessor postProcessor) throws MessagingException {

    }
}
