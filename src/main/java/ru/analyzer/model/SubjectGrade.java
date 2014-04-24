package ru.analyzer.model;

import java.util.Date;

public class SubjectGrade {

    private Subject subject;
    private Date date;
    private Grade grade;

    public SubjectGrade(Subject subject, Date date, Grade grade) {
        this.subject = subject;
        this.date = date;
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public Grade getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "SubjectGrade{" +
                "subject=" + subject +
                ", date=" + date +
                ", grade=" + grade +
                '}';
    }

}
