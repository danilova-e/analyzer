package ru.analyzer.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.analyzer.model.Grade;
import ru.analyzer.model.Subject;
import ru.analyzer.model.SubjectGrade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SubjectGradeDepot {

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource(
            "jdbc:mysql://localhost/analyzer",
            "root",
            "12345"
    ));

    private final SubjectDepot subjectDepot = new SubjectDepot();
    private final GradeDepot gradeDepot = new GradeDepot();


    public void add(SubjectGrade subjectGrade) {
        jdbcTemplate.update(
                "INSERT INTO subject_grade VALUES (?, ?, ?)",
                subjectGrade.getSubject().getId(),
                subjectGrade.getDate(),
                subjectGrade.getGrade().getId()
        );
    }

    //все SubjectGrade(Subject subject, Date time, Grade grade) по subject_id
    public List<SubjectGrade> getAll(int subjectId) {
        return jdbcTemplate.query("SELECT * FROM subject_grade WHERE subject_id = " + subjectId, new SubjectGradeRowMapper());
    }

    //все SubjectGrade(Subject subject, Date time, Grade grade) - то что получается из строчек
    public List<SubjectGrade> getAll() {
        //select возращает таблицу, SubjectGradeRowMapper реализует интерфейс обработчика этой таблицы
        return jdbcTemplate.query("SELECT * FROM subject_grade", new SubjectGradeRowMapper());
    }

    //все SubjectGrade(Subject subject, Date date, Grade grade) по subject_id
    public List<SubjectGrade> getByDate(int subjectId, Date date) {
        return jdbcTemplate.query(
                "SELECT * FROM subject_grade WHERE subject_id = ? AND date >= ?",
                new SubjectGradeRowMapper(),
                subjectId,
                date
        );
    }

    //реализует интерфейс RowMapper(конструирует из строки таблицы объект SubjectGrade)
    private class SubjectGradeRowMapper implements RowMapper<SubjectGrade> {
        //ResultSet содержит строку таблицы, i - номер строки
        //
        @Override
        public SubjectGrade mapRow(ResultSet resultSet, int i) throws SQLException {
            int subjectId = resultSet.getInt("subject_id");
            Date date = resultSet.getTimestamp("date");
            int gradeId = resultSet.getInt("grade_id");

            Subject subject = subjectDepot.get(subjectId);
            Grade grade = gradeDepot.get(gradeId);

            return new SubjectGrade(subject, date, grade);
        }
    }

    public static void main(String[] args) {
        SubjectGradeDepot subjectGradeDepot = new SubjectGradeDepot();
        //subjectGradeDepot.add(new SubjectGrade(new Subject(3, "health"), new Date(), new Grade(10, 10)));

        Date date = subjectGradeDepot.getAll(4).get(0).getDate();
        System.out.println(subjectGradeDepot.getByDate(3, date));
    }
}
