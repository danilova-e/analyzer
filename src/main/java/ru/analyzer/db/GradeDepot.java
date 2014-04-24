package ru.analyzer.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.analyzer.model.Grade;
import ru.analyzer.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Катя on 18.04.2014.
 */
public class GradeDepot {
    private final JdbcTemplate jdbcTemplate;

    public GradeDepot() {
        jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(
                "jdbc:mysql://localhost/analyzer",
                "root",
                "12345"
        ));
    }

    public void add(Grade grade) {
        jdbcTemplate.update(
                "INSERT INTO grade VALUES (?, ?)",
                grade.getId(),
                grade.getValue()
        );
    }

    public Grade get(int gradeId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM grade WHERE id = " + gradeId,
                    new GradeRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //реализует интерфейс RowMapper(конструирует из строки таблицы объект Grade)
    private class GradeRowMapper implements RowMapper<Grade> {
        //ResultSet содержит строку таблицы, i - номер строки
        @Override
        public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
            int gradeId = resultSet.getInt("id");
            int value = resultSet.getInt("value");

            return new Grade(gradeId, value);
        }
    }

    public static void main(String[] args) {
        GradeDepot gradeDepot = new GradeDepot();
        System.out.println(gradeDepot.get(1));
    }
}
