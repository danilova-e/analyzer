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

    private static final SubjectDepot subjectDepot = new SubjectDepot();
    private static final SubjectGradeDepot subjectGradeDepot = new SubjectGradeDepot();

    public GradePlot(Subject subject, Date since, Date until) throws ParseException {

        //DataTable myPoints = new DataTable(Double.class, Double.class);
        DataTable time2grade = new DataTable(Long.class, Integer.class);

        /*Scanner cin = new Scanner(System.in);
        subjectId = cin.nextInt();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        since = format.parse(cin.next() + " " + cin.next());
        until = format.parse(cin.next() + " " + cin.next());
*/
        List<SubjectGrade> subjectGrades = subjectGradeDepot.getUntil(subject.getId(), since, until);
        //System.out.println(subjectGrades);

        for (SubjectGrade subjectGrade : subjectGrades) {
            time2grade.add(subjectGrade.getDate().getTime(), subjectGrade.getGrade().getValue());
        }

        //Panel
        XYPlot plot = new XYPlot(time2grade);
        getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);

        //Date
        AxisRenderer rendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy', 'HH:mm");
        rendererX.setTickLabelFormat(dateFormat);
        //Lines
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderer(time2grade, lines);
        plot.getLineRenderer(time2grade).setColor(Color.blue);

        //plot.getPointRenderer(myPoints).setColor(Color.WHITE);

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

        GradePlot frame = new GradePlot(subjectDepot.get(3), since, until);
        frame.setVisible(true);


        //System.out.println(subjectDepot.get(1));
    }
}
