# Module 6 - Spring Test

1. Task 1 - Turn booking service controllers into REST endpoints, returning domain objects directly.
   - [AccountController](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/controller/AccountController.java)
   - [EventController](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/controller/EventController.java)
   - [TicketController](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/controller/TicketController.java)
   - [UserController](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/controller/UserController.java)
2. Task 2 - Implement asynchronous ticket booking
   - [JmsProducer](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/jms/JmsProducer.java)
   - [JmsConsumer](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/jms/JmsConsumer.java)
   - [/book-ticket-async endpoint implementation](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/java/com/pedro/controller/TicketController.java#L58)
     
3. Task 3 - Configure Spring JMS infrastructure
   - [ActiveMQ configuration](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/main/resources/application.properties)
   - [ActiveMQ configuration for testing](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/test/java/com/pedro/integration/AppTestConfig.java)     

4. Task 4 - Create integration tests that verify asynchronous booking
   - [Test using embedded ActiveMQ](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/test/java/com/pedro/integration/BookTicketAsycnTest.java#L43)
   - [Test using ActiveMQ mock](https://github.com/pedroasd/java-program/blob/m06-spring-testing/src/test/java/com/pedro/integration/BookTicketAsycnTest.java#L60)
