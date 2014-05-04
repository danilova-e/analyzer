package ru.analyzer.plot;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;
import ru.analyzer.db.SubjectDepot;
import ru.analyzer.db.SubjectGradeDepot;
import ru.analyzer.model.Subject;
import ru.analyzer.model.SubjectGrade;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GradePlot extends JFrame {

    private final List<SubjectGrade> subjectGrades;

    private static final SubjectDepot subjectDepot = new SubjectDepot();
    private static final SubjectGradeDepot subjectGradeDepot = new SubjectGradeDepot();

    private double getSlope() {
        double xAverage = 0;
        for (SubjectGrade subjectGrade : subjectGrades) {
            xAverage += subjectGrade.getDate().getTime();
        }
        xAverage /= subjectGrades.size();

        double numerator = 0;
        double denominator = 0;
        for (SubjectGrade subjectGrade : subjectGrades) {
            numerator += (subjectGrade.getDate().getTime() - xAverage) * subjectGrade.getGrade().getValue();
            denominator += Math.pow(subjectGrade.getDate().getTime() - xAverage, 2);
        }

        return numerator / denominator;
    }

    private double getIntercept() {
        double xAverage = 0;
        double yAverage = 0;
        for (SubjectGrade subjectGrade : subjectGrades) {
            xAverage += subjectGrade.getDate().getTime();
            yAverage += subjectGrade.getGrade().getValue();
        }
        xAverage /= subjectGrades.size();
        yAverage /= subjectGrades.size();

        return yAverage - getSlope() * xAverage;
    }

    public GradePlot(Subject subject, Date since, Date until) throws ParseException {
        subjectGrades = subjectGradeDepot.getUntil(subject.getId(), since, until);

        DataTable time2grade = new DataTable(Long.class, Double.class);
        for (SubjectGrade subjectGrade : subjectGrades) {
            time2grade.add(subjectGrade.getDate().getTime(), (double) subjectGrade.getGrade().getValue());
        }

        double a = getSlope();
        double b = getIntercept();

        long x0 = subjectGrades.get(0).getDate().getTime();
        double y0 = a * x0 + b;
        long x1 = subjectGrades.get(subjectGrades.size() - 1).getDate().getTime();
        double y1 = a * x1 + b;

        DataTable trend = new DataTable(Long.class, Double.class);
        trend.add(x0, y0);
        trend.add(x1, y1);

        //Panel
        XYPlot plot = new XYPlot(time2grade, trend);
        getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);

        //Date
        AxisRenderer rendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy', 'HH:mm");
        rendererX.setTickLabelFormat(dateFormat);

        //Lines
        LineRenderer lines = new DefaultLineRenderer2D();
        LineRenderer lines2 = new DefaultLineRenderer2D();

        plot.setLineRenderer(trend, lines);
        plot.setLineRenderer(time2grade, lines2);
        plot.getLineRenderer(trend).setColor(Color.BLUE);
        plot.getLineRenderer(time2grade).setColor(new Color(0.0f, 0.3f, 1.0f, 0.3f));
        plot.getPointRenderer(time2grade).setColor(Color.lightGray);
        plot.getPointRenderer(trend).setColor(Color.white);

        setMinimumSize(getContentPane().getMinimumSize());
        plot.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
    }

    public GradePlot(Subject subject, Date since) throws ParseException {
        this(subject, since, new Date());
    }

    public GradePlot(Subject subject) throws ParseException {
        this(subject, new Date(0));
    }

    public static void main(String[] args) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date since = format.parse("2014-01-04 09:00:00");
        Date until = format.parse("2015-12-04 09:34:00");

        GradePlot frame = new GradePlot(subjectDepot.get(11));
        frame.setVisible(true);


        //System.out.println(subjectDepot.get(1));
    }
}
