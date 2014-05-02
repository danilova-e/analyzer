package ru.analyzer.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.analyzer.model.Subject;

import java.sql.*;

public class SubjectDepot {

    private final JdbcTemplate jdbcTemplate;

    public SubjectDepot() {
        jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(
                "jdbc:mysql://localhost/analyzer",
                "root",
                "12345"
        ));
    }

    public boolean contains(Subject subject) {
        if (!jdbcTemplate.queryForList(
                "SELECT * FROM subject WHERE name = ?",
                subject.getName()
        ).isEmpty()) {
            return true;
        }
        return  false;
    }

    public Subject add(final Subject subject) {
        if (contains(subject)) {
            return null;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subject (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                        preparedStatement.setString(1, subject.getName());
                        return preparedStatement;
                    }
                },
                keyHolder
        );

        return new Subject(keyHolder.getKey().intValue(), subject.getName());
    }

    public Subject get(int subjectId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM subject WHERE id = " + subjectId,
                    new SubjectRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Subject getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM subject WHERE name = ?",
                    new SubjectRowMapper(),
                    name
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



    //реализует интерфейс RowMapper(конструирует из строки таблицы объект Subject)
    private class SubjectRowMapper implements RowMapper<Subject> {
        //ResultSet содержит строку таблицы, i - номер строки
        @Override
        public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
            int subjectId = resultSet.getInt("id");
            String name = resultSet.getString("name");

            return new Subject(subjectId, name);
        }
    }

    public static void main(String[] args) {
        SubjectDepot subjectDepot = new SubjectDepot();
        //System.out.println(subjectDepot.add(new Subject("polimer")));
    }
}
