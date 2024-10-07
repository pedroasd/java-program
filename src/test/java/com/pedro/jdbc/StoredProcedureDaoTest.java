package com.pedro.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class StoredProcedureDaoTest {

	@Container
	private MySQLContainer database = new MySQLContainer("mysql:latest");

	@Test
	void dropStoreProceduresTest() throws SQLException {
		try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())){
			StoredProcedureDao dao = new StoredProcedureDao(connection);

			dao.createTables();
			assertEquals(0, dao.getStoredProcedures().size());

			dao.createCRUDStoredProcedures();
			dao.createQueryStoredProcedures();
			var createdProcedures = dao.getStoredProcedures();
			assertEquals(7, createdProcedures.size());

			dao.dropStoredProcedures(createdProcedures);
			assertEquals(0, dao.getStoredProcedures().size());
		}
    }

	@Test
	void addUserTest() throws SQLException {
		try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
			StoredProcedureDao dao = new StoredProcedureDao(connection);
			dao.createTables();
			dao.addUser("Pedro", "pedro@example.com", Date.valueOf(LocalDate.now()));
			StoredProcedureDao.User user = dao.getUser(1);
			Assertions.assertNotNull(user);
			assertEquals("Pedro", user.name());
		}
	}

	@Test
	void addUserStoreProcedureTest() throws SQLException {
		try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
			StoredProcedureDao dao = new StoredProcedureDao(connection);
			dao.createTables();
			dao.createCRUDStoredProcedures();
			dao.addUserProcedure("Pedro", "pedro@example.com", Date.valueOf(LocalDate.now()));
			StoredProcedureDao.User user = dao.getUserProcedure(1);
			Assertions.assertNotNull(user);
			assertEquals("Pedro", user.name());
		}
	}
}
