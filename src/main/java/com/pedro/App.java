package com.pedro;

import com.pedro.facade.BookingFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    static Log log = LogFactory.getLog(App.class);

    public static void main( String[] args ){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("context-config.xml");
        log.info("ApplicationContext loaded from XML file");
        BookingFacade booking = applicationContext.getBean(BookingFacade.class);
    }
}
