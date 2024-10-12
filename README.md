# Module 4 - Spring Data Access

1. Task 1 - Using the Hibernate ORM framework, update existing models
   - [Event](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/model/Event.java)
   - [Ticket](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/model/Ticket.java)
   - [User](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/model/User.java)
2. Task 2
   - [Add UserAccount](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/model/UserAccount.java)
   - [Add methods for refilling the account](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/facade/BookingFacade.java#L149)
   - [Add UserAccountDAO](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/dao/UserAccountDAO.java)
   - [Add UserAccountService](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/service/UserAccountService.java)
   - [Add ticketPrice field to Event entity](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/model/Event.java#L22)
3. Task 3
   - [EventDAO](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/dao/EventDAO.java)
   - [TicketDAO](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/dao/TicketDAO.java)
   - [UserDAO]([https://github.com/pedroasd/java-program/tree/m02-spring-core/src/main/java/com/pedro/dao](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/dao/UserDAO.java))
   - [Use transaction](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/facade/BookingFacadeImpl.java#L187)
   - [Configuration for work with DBMS that you choose](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/resources/application.properties)
4. Task 4 - Update ticket booking methods to check and withdraw money
   - [BookingFacadeImpl](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/facade/BookingFacadeImpl.java#L187)
   - [UserAccountService](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/main/java/com/pedro/service/UserAccountService.java#L40)
5. Task 5 - Cover code with unit tests
   - [All tests were updated](https://github.com/pedroasd/java-program/tree/m04-spring-data-access/src/test/java/com/pedro)
6. Task 6 - Add integration tests for newly implemented scenarios
   - [Integration tests](https://github.com/pedroasd/java-program/blob/m04-spring-data-access/src/test/java/com/pedro/integration/BookingTest.java)
