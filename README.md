# Task 2 - Highload Writing Console Tool

1. It creates N random tables with random unique name (or names from dictionary) and K random columns with type taken from Z random types.
   - It create randon tables [here](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/src/main/java/com/pedro/App.java#L25) based on configuration file. Each table is created [here](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/src/main/java/com/pedro/TableGenerator.java#L41) based on params.

2. It creates m random rows for the i-th table, where m is an i-th element of M. M is an N-length array predefined by a user of this tool and each element in the array by itself is array with follow structure [K, Z, m].
   - [populateTable](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/src/main/java/com/pedro/TableGenerator.java#L60)

3. It supports the table creation/populating via L concurrent connections (from different threads or from a few instances of classes running simultaneously).
   - App creates a thread pool based on the configuration file [here](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/src/main/java/com/pedro/App.java#L22).

4. All settings are located in a configuration file; the path to this file is a parameter of main() function.
   - The file is loaded [here](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/src/main/java/com/pedro/App.java#L20). [This a sample of this file](https://github.com/pedroasd/java-program/blob/m03-jdbc-2/sample.properties).
