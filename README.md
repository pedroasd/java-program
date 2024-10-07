# Task 5 - Store Procedure

1. Take the existing (or write from zero) JDBC solution with a few CRUD operations and more complex SQL (for simple report generation) and migrate it to stored procedures.
  - [StoreProcedureDao](https://github.com/pedroasd/java-program/blob/m03-jdbc-3/src/main/java/com/pedro/jdbc/StoredProcedureDao.java) has some CRUD operations and complex SQL.
 
2. Write SQL script to create those stored procedures with Java style parameters and specific external names.
  - [createStoredProcedures](https://github.com/pedroasd/java-program/blob/m03-jdbc-3/src/main/java/com/pedro/jdbc/StoredProcedureDao.java#L108) includes 7 stored procedures for CRUD operations and 2 complex SQL.
    
3. Write a test which drops all stored procedures and creates a few of them via Java code.
  - [dropStoredProcedures](https://github.com/pedroasd/java-program/blob/m03-jdbc-3/src/test/java/com/pedro/jdbc/StoredProcedureDaoTest.java#L24) creates 7 SPs, then drop all, after that create only 5 and validates them.
   
4. Also, provide the script to print out all stored procedure in your database and dropping them for test purposes, for example.
   - [dropStoreProceduresTest](https://github.com/pedroasd/java-program/blob/m03-jdbc-3/src/test/java/com/pedro/jdbc/StoredProcedureDaoTest.java#L36) Drop all stored procedures based on the query of procedures on DB.

5. Check that the application works properly, all test are green and so on.
   - These tests have a MySQL container for testing. Three tests are included in [StoredProcedureDaoTest](https://github.com/pedroasd/java-program/blob/m03-jdbc-3/src/test/java/com/pedro/jdbc/StoredProcedureDaoTest.java)
