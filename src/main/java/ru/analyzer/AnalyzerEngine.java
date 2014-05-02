package ru.analyzer;

import ru.analyzer.db.GradeDepot;
import ru.analyzer.db.SubjectDepot;
import ru.analyzer.db.SubjectGradeDepot;
import ru.analyzer.model.Grade;
import ru.analyzer.model.Subject;
import ru.analyzer.model.SubjectGrade;
import ru.analyzer.plot.GradePlot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AnalyzerEngine {

    private static final SubjectGradeDepot subjectGradeDepot = new SubjectGradeDepot();
    private static final SubjectDepot subjectDepot = new SubjectDepot();
    private static final GradeDepot gradeDepot = new GradeDepot();

    private static final String ADD = "add";
    private static final String PLOT = "plot";
    private static final String END = "end";
    private static final String CREATE = "create";

    public static void main(String[] args) throws ParseException {
        Scanner cin = new Scanner(System.in);

        while (true) {
            String command = cin.next();

            if (command.equals(CREATE)) {
                String subjectName = cin.next();
                Subject subject = subjectDepot.add(new Subject(subjectName));
                System.out.println(subject + " created");
            }

            if (command.equals(ADD)) {
                String subjectName = cin.next();
                int gradeValue = cin.nextInt();

                Subject subject = subjectDepot.getByName(subjectName);
                Grade grade = gradeDepot.getByValue(gradeValue);

                if (subject == null || grade == null) {
                    System.out.println("There is no " + subjectName + " or " + gradeValue);
                    continue;
                }

                SubjectGrade subjectGrade = new SubjectGrade(subject, new Date(), grade);

                subjectGradeDepot.add(subjectGrade);

                System.out.println(subjectGrade + " added");
            }

            if (command.equals(PLOT)) {
                GradePlot gradePlot;

                String subjectName = cin.next();
                Subject subject = subjectDepot.getByName(subjectName);

                String[] tokens = cin.nextLine().trim().split(" ");

                if (tokens.length >= 2) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date since = format.parse(tokens[0] + " " + tokens[1]);

                    if (tokens.length == 4) {
                        Date until = format.parse(tokens[2] + " " + tokens[3]);
                        gradePlot = new GradePlot(subject, since, until);
                    } else {
                        gradePlot = new GradePlot(subject, since);
                    }
                } else {
                    gradePlot = new GradePlot(subject);
                }

                gradePlot.setVisible(true);
            }

            if (command.equals(END)) {
                System.out.println("Bye!");
                System.exit(0);
            }
        }
    }

}
