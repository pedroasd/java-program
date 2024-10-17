# Module 5 - Spring MVC

1. Task 1 - Transform it into a web application
   Use starter dependencies on [pom.xml](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/pom.xml#L40)
2. Task 2 Implement annotation-based controllers
   - [AccountController](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/AccountController.java)
   - [EventController](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/EventController.java)
   - [TicketController](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/TicketController.java)
   - [UserController](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/UserController.java)
3. Task 3 - Implement simple thymeleaf templates
   - [Thymeleaf templates](https://github.com/pedroasd/java-program/tree/m05-spring-mvc/src/main/resources/templates)
   - I created a menu to access to all pages
     ![menu](https://github.com/user-attachments/assets/8d76d49b-e26e-466a-8676-3c62b87d5ce9)

4. Task 4 - Implement alternative controller for getBookedTickets
   - [Controller implementation](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/TicketController.java#L100)
   - [View implementation](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/resources/templates/searchTickets.html#L29)
     ![ticketsUser](https://github.com/user-attachments/assets/da923f96-1784-47b7-a3d0-6438a53c9153)

5. Task 5 - Implement batch creation of ticket bookings from XML file
   - [Controller imlpementation](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/controller/TicketController.java#L113)
   - [View implementation](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/resources/templates/uploadTickets.html)
     ![upoadTicket](https://github.com/user-attachments/assets/a226eef0-9457-4e33-80d9-fe1dda2b2ba8)

6. Task 6 - Implement custom HandlerExceptionResolver
   - [CustomExceptionResolver](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/exception/CustomExceptionResolver.java)
   - [View implementation](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/resources/templates/error.html)
     ![TestExeption](https://github.com/user-attachments/assets/bda5d02c-58df-40ce-a9d2-1caac88cab1a)

7. Task 7 - Unit tests, logging, javadocs.
   - [Tests](https://github.com/pedroasd/java-program/tree/m05-spring-mvc/src/test/java/com/pedro)
   - Logging in [services](https://github.com/pedroasd/java-program/tree/m05-spring-mvc/src/main/java/com/pedro/service)
   - Javadoc on [BookingFacade](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/main/java/com/pedro/facade/BookingFacade.java)

8. Task 8 - Implement integration tests MockMVC framework
   - [ControllerIntegrationTest](https://github.com/pedroasd/java-program/blob/m05-spring-mvc/src/test/java/com/pedro/controller/ControllerIntegrationTest.java)
   


