# File Sharing - Practical Task 7

1. DB schema diagram is provided

    | Files            |
    |------------------|
    | id (PK)          |
    | name             |
    | file_data        | 
    | upload_date      |
    | expiration_date  |    

    
2. Stored procedures for saving and retrieving files from DB are created
   - [Store procedure to save a file](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/DatabaseSetup.java#L42)
   - [Stored procedure to retrieve a file](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/FileDaoImpl.java#L28)
   
4. DAO methods that are not used in proposed use cases can throw UnsupportedOperationException
   - [Delete operation is not supported](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/FileDaoImpl.java#L41)
     
5. CallableStatement is used to call DB stored procedures
   - [For saving file](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/FileDaoImpl.java#L18)
   - [For retriving file](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/FileDao.java#L28)
     
6. Large binary files are retrievable from DB
   - In the App the binary file is check that it is not null. [Check file is not null](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/App.java#L36).
  
To run the application in the root path of the project:

1. Run `docker compose up`. It start up a MySQL database that support files up to 200MB according to [the database configuration](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/my.cnf).
2. Set the full path of the file that you want to store [here](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/App.java#L25).
3. Then run the application [App](https://github.com/pedroasd/java-program/blob/m03-jdbc-4/src/main/java/com/pedro/App.java).

