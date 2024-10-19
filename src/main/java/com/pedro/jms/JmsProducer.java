package com.pedro.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {

    private final JmsTemplate jmsTemplate;

    public JmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendBookingMessage(TicketMessage message) {
        try {
            jmsTemplate.convertAndSend("bookingQueue", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

