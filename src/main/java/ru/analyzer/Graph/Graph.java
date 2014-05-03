package ru.analyzer.graph;

import ru.analyzer.db.GradeDepot;
import ru.analyzer.db.SubjectDepot;
import ru.analyzer.db.SubjectGradeDepot;
import ru.analyzer.model.Grade;
import ru.analyzer.model.Subject;
import ru.analyzer.model.SubjectGrade;
import ru.analyzer.plot.GradePlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Date;

public class Graph extends JFrame {

    JPanel pane2;
    JPanel btnPanel;
    JTextField fieldName;
    JTextField fieldValue;
    JTextField fieldSince;
    JTextField fieldUntil;
    private static final SubjectGradeDepot subjectGradeDepot = new SubjectGradeDepot();
    private static final SubjectDepot subjectDepot = new SubjectDepot();
    private static final GradeDepot gradeDepot = new GradeDepot();

    public Graph() {
        pane2 = new JPanel(new GridLayout(2, 1));
        btnPanel = new JPanel(new GridLayout(1, 4));
        JButton btnFirst = new JButton("Estimate the object");
        JButton btnPrevious = new JButton("Plot the values");
        JButton btnNext = new JButton("Create the object");
        //    JButton btnLast = new JButton("Delete the object");
        btnPanel.add(btnFirst);
        btnPanel.add(btnPrevious);
        btnPanel.add(btnNext);
        //btnPanel.add(btnLast);

        pane2.add(btnPanel);

        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pane2.getComponentCount() > 0) {
                    pane2.removeAll();
                }
                pane2.add(createComponentSave());
                pane2.revalidate();
            }
        });
        btnPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pane2.getComponentCount() > 0) {
                    pane2.removeAll();
                }
                try {
                    pane2.add(createComponentPlot());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                pane2.revalidate();
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pane2.getComponentCount() > 0) {
                    pane2.removeAll();
                }
                pane2.add(createComponentCreate());
                pane2.revalidate();
            }
        });

        getContentPane().add(btnPanel, BorderLayout.NORTH);
        getContentPane().add(pane2, BorderLayout.CENTER);
        setSize(590, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    //оценить
    private JComponent createComponentSave() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new GridLayout(3, 2));

        JLabel labelName = new JLabel("Enter the object's name:");
        fieldName = new JTextField();
        namePanel.add(labelName);
        namePanel.add(fieldName);

        JLabel labelValue = new JLabel("Enter the object's value:");
        fieldValue = new JTextField();
        namePanel.add(labelValue);
        namePanel.add(fieldValue);

        JButton buttonSave = new JButton("Save");

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = fieldName.getText();
                int gradeValue = Integer.parseInt(fieldValue.getText());

                Subject subject = subjectDepot.getByName(subjectName);
                Grade grade = gradeDepot.getByValue(gradeValue);

                if (subject == null || grade == null) {
                    JOptionPane.showMessageDialog(null, "There is no " + subjectName + " or " + gradeValue);
                    System.out.println("There is no " + subjectName + " or " + gradeValue);
                    pane2.removeAll();
                    pane2.revalidate();
                }

                SubjectGrade subjectGrade = new SubjectGrade(subject, new Date(), grade);

                subjectGradeDepot.add(subjectGrade);

                JOptionPane.showMessageDialog(null, subjectGrade + " added");
                pane2.removeAll();
                pane2.revalidate();

                System.out.println(subjectGrade + " added");

            }
        });

        JPanel sincePanel = new JPanel(new GridLayout(1, 2));

        pane.add(sincePanel);
        pane.add(namePanel);
        pane.add(buttonSave);

        return pane;
    }
    //создать
    private JComponent createComponentCreate() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        JLabel labelName = new JLabel("Enter the new object's name:");
        fieldName = new JTextField();
        namePanel.add(labelName);
        namePanel.add(fieldName);

        JButton buttonSave = new JButton("Create");

        JPanel sincePanel = new JPanel(new GridLayout(1, 2));
        JPanel sincePanel2 = new JPanel(new GridLayout(1, 2));
        JPanel sincePanel3 = new JPanel(new GridLayout(1, 2));
        JPanel sincePanel4 = new JPanel(new GridLayout(1, 2));

        pane.add(sincePanel);
        pane.add(sincePanel3);
        pane.add(namePanel);
        pane.add(sincePanel2);
        pane.add(sincePanel4);
        pane.add(buttonSave);

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = fieldName.getText();
                Subject subject = subjectDepot.add(new Subject(subjectName));
                JOptionPane.showMessageDialog(null, subject + " created");
            }
        });

        return pane;
    }
    //построить
    private JComponent createComponentPlot() throws ParseException {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new GridLayout(3, 2));
        JLabel labelName = new JLabel("Enter the object's name:");
        fieldName = new JTextField();
        namePanel.add(labelName);
        namePanel.add(fieldName);

        JLabel labelSince = new JLabel("Enter the date since:");
        fieldSince = new JTextField("yyyy-mm-dd hh:mm:ss");
        namePanel.add(labelSince);
        namePanel.add(fieldSince);

        JLabel labelUntil = new JLabel("Enter the date until:");
        fieldUntil = new JTextField("yyyy-mm-dd hh:mm:ss");
        namePanel.add(labelUntil);
        namePanel.add(fieldUntil);

        JButton buttonPlot = new JButton("Plot");

        JPanel sincePanel = new JPanel(new GridLayout(1, 2));
        JPanel sincePanel2 = new JPanel(new GridLayout(1, 2));

        pane.add(sincePanel);
        pane.add(namePanel);
        pane.add(sincePanel2);
        pane.add(buttonPlot);

        buttonPlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradePlot gradePlot = null;

                String subjectName = fieldName.getText();
                Subject subject = subjectDepot.getByName(subjectName);

                String s = fieldSince.getText();
                s += fieldUntil.getText();

                String[] tokens = s.trim().split(" ");

                /*if (tokens.length >= 2) {
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
                -----*/

                try {
                    gradePlot = new GradePlot(subject);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                gradePlot.setVisible(true);
            }
        });

        return pane;
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Graph().setVisible(true);
    }

}
