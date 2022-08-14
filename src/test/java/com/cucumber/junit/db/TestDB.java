package com.cucumber.junit.db;

import configuration.db.JdbcDriverSetUp;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.assertNotNull;

public class TestDB {

    @Test
    @DisplayName("Read values from one table in User db and check it contains specific value")
    public void simpleTestDB() throws SQLException {
        final JdbcDriverSetUp jdbcDriverSetUp = new JdbcDriverSetUp();
        jdbcDriverSetUp.dbDriverSetUp();

        Connection connection = jdbcDriverSetUp.getConnection();
        assertNotNull(connection);

        Statement stm = connection.createStatement();
        String query = "select Name from users;";
        ResultSet resultSet = stm.executeQuery(query);
        List<String> namesList = new ArrayList<>();

        while (resultSet.next()) {
            String username = resultSet.getString("Name");
            namesList.add(username);
        }

        System.out.println(namesList);
        Assertions.assertThat(namesList.contains("Alex")).overridingErrorMessage("There are no such entries in the db").isTrue();
        resultSet.close();
        connection.close();
        stm.close();
    }

    @Test
    @DisplayName("Read values from two tables in User db")
    public void testQueryWithJoin() throws SQLException {
        final JdbcDriverSetUp jdbcDriverSetUp = new JdbcDriverSetUp();
        jdbcDriverSetUp.dbDriverSetUp();

        Connection connection = jdbcDriverSetUp.getConnection();
        assertNotNull(connection);

        Statement stm = connection.createStatement();
        String query = "select us.Name, us.Surname, us.email, ord.order_id, ord.order_total from users  as us join orders as ord where  ord.username = us.Name;";
        ResultSet resultSet = stm.executeQuery(query);
        List<String> namesList = new ArrayList<>();

        while (resultSet.next()) {
            String username = resultSet.getString("Name");
            namesList.add(username);
        }

        Assertions.assertThat(namesList.isEmpty()).overridingErrorMessage("There are matches between orders and users").isFalse();

        resultSet.close();
        connection.close();
        stm.close();
    }

}
