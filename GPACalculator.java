/* Homework 5 GUI
 * Jared Zhang
 * jz3ba
 * 
 * Sources:
 * https://stackoverflow.com/questions/10806893/how-can-i-get-the-unicode-infinity-symbol-converted-to-string
 * https://stackoverflow.com/questions/5710394/how-do-i-round-a-double-to-two-decimal-places-in-java
 * https://alvinalexander.com/java/jframe-title-set
 * https://stackoverflow.com/questions/1097366/java-swing-revalidate-vs-repaint
 * https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
 */


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GPACalculator {
	
	/* created a variable to set up the main window */
	private JFrame frame;
	
	/* created a few JPanels: one for editing classes, one for displaying GPA related info, and one for the constructor class */
	private JPanel editClasses;
	private JPanel gpaInfo;
	private JPanel constructor;
	
	/* created three JTextFields: one to display current GPA, one for user to input their desired GPA, and one for requiredGPA */
	private JTextField currentGPA;
	private JTextField targetGPA;
	private JTextField requiredGPA;
	
	/* created three JButtons: one for calculating GPA, one for adding a course, and another to remove all courses */
	private JButton calculateGPA;
	private JButton addCourse;
	private JButton removeAll;
	
	/* created four JLabels: one for warnings, one for current GPA, one for target GPA, and one for required GPA*/
    private JLabel recommendation;
    private JLabel currentGPALabel;
    private JLabel targetGPALabel;
    private JLabel requiredGPALabel;
    
    /* created an arraylist that holds all the information for courses */
    ArrayList<JPanel> constructorPanel;
    
    /* used for sizing */
    private final int width = 6;
    
    public static void main(String[] args) {
        new GPACalculator();
    }
    
    /**
     * updates the JFrame to reflect any new changes 
     */
    public void updatePanel() {
    	frame.repaint(); 
        frame.revalidate();
	}
    
    /**
     * constructor class that setups up the overall properties of the GUI
     */
    public GPACalculator() {
    	frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(900,600);
    	frame.setTitle("GPA Calculator");
    	
    	/* adds ActionListener to addCourse; it applies the AddCourseAction which adds a panel to input info about the course you're adding */
    	addCourse = new JButton("Add Course");
        addCourse.addActionListener(new AddCourseAction());
        
        /* adds ActionListener to removeAll; it removes all elements inside the constructor and constructorPanel */
        removeAll = new JButton("Remove All Courses");
        removeAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                constructor.removeAll();
                constructorPanel.removeAll(constructorPanel);
                updatePanel();
            }
        });
        
        /* adds ActionListener to calculateGPA; it updates everything on the screen and the current GPA */
    	calculateGPA = new JButton("Calculate GPA");
    	calculateGPA.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			updateGPA();
    			updatePanel();
    		}
    	});
        
    	/* creates new JPanel and adds addCourse, removeAll, and calculateGPA in a 1x3 layout */
        editClasses = new JPanel();
        editClasses.setLayout(new GridLayout(1, 3));
        editClasses.setBorder(new TitledBorder(new EtchedBorder(), "Add and Remove Courses"));
        editClasses.add(addCourse);
        editClasses.add(removeAll);
        editClasses.add(calculateGPA);
        
        /* creates a new JPanel for the classes that will be added */
        constructor = new JPanel();
        constructor.setLayout(new GridLayout(0, 1));
        constructor.setBorder(new TitledBorder(new EtchedBorder(), "Course Planner"));
        constructorPanel = new ArrayList<JPanel>();
     
        /* creates a new JPanel for GPA related info; adds labels and textfields to the panel */
        gpaInfo = new JPanel();
        targetGPA = new JTextField(width);
        currentGPA = new JTextField(width);
        requiredGPA = new JTextField(width);
        recommendation = new JLabel("Recommendation: none                   ");
        targetGPALabel = new JLabel("Target GPA: ");
        currentGPALabel = new JLabel("Current GPA: ");
        requiredGPALabel = new JLabel("Required GPA: ");
        gpaInfo.setLayout(new GridLayout(0, 1));
        gpaInfo.setBorder(new TitledBorder(new EtchedBorder(), "Summary"));
        gpaInfo.add(currentGPALabel);
        gpaInfo.add(currentGPA);
        gpaInfo.add(targetGPALabel);
        gpaInfo.add(targetGPA);
        gpaInfo.add(requiredGPALabel);
        gpaInfo.add(requiredGPA);
        gpaInfo.add(recommendation);
        
        /* puts info relating to GPA on the left side of the window, class info on the right of the window, and buttons on the bottom */
        frame.add(gpaInfo, BorderLayout.WEST);
        frame.add(constructor, BorderLayout.EAST);
        frame.add(editClasses, BorderLayout.SOUTH);
        frame.setVisible(true);
        
    }
    
    /**
     * creates the fields necessary for user to input info about the course they're adding */
    
    private class AddCourseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	/* created a panel that will be nested within the constructorPanel */
    	JPanel course;
    	
    	/* created a button that will allow user to remove any classes from the course planner */
    	JButton remove;
    	
    	/* created JTextFields to let user input info about the course they're adding */
        JTextField courseName;
        JTextField courseGrade;
        JTextField courseCredit;
    	
        /* created JLabels to indicate which JTextField is which */
        JLabel courseNameLabel;
        JLabel courseGradeLabel;
        JLabel courseCreditLabel;
        
        /* setup JPanel to contain all necessary fields */
        course = new JPanel();
        course.setLayout(new FlowLayout());
        remove = new JButton("Remove");
        /* cycles through all components of the constructor panel, looking for a match for the course the user is trying to remove 
         * and then removes it from the constructor panel and arrayList */
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Component[] c = constructor.getComponents();
                for (int i = 0; i < c.length; i++) {
                    if (c[i].toString().equals(course.toString())) {
                    	constructorPanel.remove(c[i]);
                        constructor.remove(c[i]);
                        updatePanel();
                    }
                }
            }
        });
                
        /* adds the JTextFields and JLabels to the course JPanel */
        courseName = new JTextField("", width);
        courseGrade = new JTextField("", width);
        courseCredit = new JTextField("", width);
        courseNameLabel = new JLabel(" Course Name: ");
        courseGradeLabel = new JLabel(" Grade: ");
        courseCreditLabel = new JLabel(" Number of Credits: ");
        course.add(courseNameLabel);
        course.add(courseName);
        course.add(courseGradeLabel);
        course.add(courseGrade);
        course.add(courseCreditLabel);
        course.add(courseCredit);
        course.add(remove);
                
        /* adds the JPanel to the constructor and constructorPanel and updates the window*/
        constructor.add(course);
        constructorPanel.add(course);
        updateGPA();
        updatePanel();
        
        }
    }
    
    /**
     * calculates the current GPA by cycling through all courses, adding credits and GPA and 
     * then running final calculations to get the actual GPA 
     */
    public void updateGPA() {
        double totalGPA = 0;
        double totalCredits = 0.0;
        double numGPA;
        for (JPanel j : constructorPanel) {
            Component[] components = j.getComponents();
            JTextField gradeInCourse = (JTextField) components[3];
            JTextField creditsInCourse = (JTextField) components[5];
            numGPA = 0.0;
            if (!"".equals(creditsInCourse.getText().trim()) && !"".equals(gradeInCourse.getText().trim())) {
            	String grade = gradeInCourse.getText().trim();
            	String credit = creditsInCourse.getText().trim();
            /* Assumption: User inputs a positive integer for credits */
            /* Assumption: User inputs a valid entry for grade */
            if (grade.equals("A+")) numGPA += 4.0;
            else if (grade.equals("A")) numGPA += 4.0;
            else if (grade.equals("A-")) numGPA += 3.7;
            else if (grade.equals("B+")) numGPA += 3.3;
            else if (grade.equals("B")) numGPA += 3.0;
            else if (grade.equals("B-")) numGPA += 2.7;
            else if (grade.equals("C+")) numGPA += 2.3;
            else if (grade.equals("C")) numGPA += 2.0;
            else if (grade.equals("C-")) numGPA += 1.7;
            else if (grade.equals("D+")) numGPA += 1.3;
            else if (grade.equals("D")) numGPA += 1.0;
            else if (grade.equals("4")) numGPA += 4.0;
            else if (grade.equals("4.0")) numGPA += 4.0;
            else if (grade.equals("3.7")) numGPA += 3.7;
            else if (grade.equals("3.3")) numGPA += 3.3;
            else if (grade.equals("3")) numGPA += 3.0;
            else if (grade.equals("3.0")) numGPA += 3.0;
            else if (grade.equals("2.7")) numGPA += 2.7;
            else if (grade.equals("2.3")) numGPA += 2.3;
            else if (grade.equals("2")) numGPA += 2.0;
            else if (grade.equals("2.0")) numGPA += 2.0;
            else if (grade.equals("1.7")) numGPA += 1.7;
            else if (grade.equals("1.3")) numGPA += 1.3;
            else if (grade.equals("1.0")) numGPA += 1.0;
            else if (grade.equals("1")) numGPA += 1.0;
            else numGPA += 0.0;
            numGPA = numGPA * Double.parseDouble(credit);
            totalGPA += numGPA;
            totalCredits += Double.parseDouble(credit);
            }
        }
        
        /* calculates the required GPA by cycling through all the courses, finding courses with blank grades
         *  and then calculating the required grade to get the target GPA */
        double targetGrade;
        double undecidedCredits = 0.0;
        JTextField credit;
        JTextField grade;
        if (!"".equals(targetGPA.getText())) {
            targetGrade = Double.parseDouble(targetGPA.getText());
            for (JPanel p : constructorPanel) {
                Component[] components = p.getComponents();
                grade = (JTextField) components[3];
                credit = (JTextField) components[5];
                if ("".equals(grade.getText().trim())) {
                	if (!"".equals(credit.getText().trim())) {
                        undecidedCredits += Double.parseDouble(credit.getText().trim());
                    }
                }
            }
            /* depending on the value of neededGPA, a recommendation is given */
            double neededGPA = (targetGrade * (totalCredits + undecidedCredits) - totalGPA) / undecidedCredits;
            if (neededGPA > 4.0) {
                recommendation.setText("<html>Recommendation:<br /> try adding more credit hours and recalculating</html>");
            }
            else if (neededGPA < 2.0) {
                recommendation.setText("<html>Recommendation:<br /> try taking fewer credit hours if you wish</html>");
            }
            else recommendation.setText("<html>Recommendation:<br />  none</html>                   ");
            
            if (neededGPA == Double.POSITIVE_INFINITY) {
                String infinitySymbol = Character.toString('\u221e');
                requiredGPA.setText(infinitySymbol);
                updatePanel();
            }
            else {
            	neededGPA = roundTwoDecimals(neededGPA);
            	requiredGPA.setText(String.valueOf(neededGPA));
            	updatePanel();
            }
        }
        
        /* rounds totalGPA and then updates the currentGPA text */
        totalGPA = totalGPA / totalCredits;
        totalGPA = roundTwoDecimals(totalGPA);
        currentGPA.setText(String.valueOf(totalGPA));
        updatePanel();
    }
    
    	/** 
    	 * method that takes in a double and round it to two decimal places 
    	 * @param d
    	 * @return
    	 */
    	public double roundTwoDecimals(double d) {
    		DecimalFormat twoDForm = new DecimalFormat("#.##");
    		return Double.valueOf(twoDForm.format(d));
    	}
        
}
