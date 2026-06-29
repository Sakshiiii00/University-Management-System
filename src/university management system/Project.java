package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener {

    public Project() {
        setSize(1540, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Proper exit handling

        // Background Image
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/3.jpg"));
        Image img = icon.getImage().getScaledInstance(1500, 750, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(img));
        add(image);

        // Menu Bar
        JMenuBar mb = new JMenuBar();
        addMenu(mb, "New Information", Color.BLUE, "New Faculty Information", "New Student Information");
        addMenu(mb, "View Details", Color.RED, "View Faculty Details", "View Student Details");
        addMenu(mb, "Apply Leave", Color.BLUE, "Faculty Leave", "Student Leave");
        addMenu(mb, "Leave Details", Color.RED, "Faculty Leave Details", "Student Leave Details");
        addMenu(mb, "Examination", Color.BLUE, "Examination Results", "Enter Marks");
        addMenu(mb, "Update Details", Color.RED, "Update Faculty Details", "Update Student Details");
        addMenu(mb, "Fee Details", Color.BLUE, "Fee Structure", "Student Fee Form");
        addMenu(mb, "Attendance", Color.RED, "Mark Attendance", "View Attendance","Update Attendance");
        addMenu(mb, "Utility", Color.BLUE, "Notepad", "Calculator");
        addMenu(mb, "About", Color.RED, "About");
        addMenu(mb, "Exit", Color.BLUE, "Exit");

        setJMenuBar(mb);
        setVisible(true);
    }

    // 🔹 Helper method to create menu items
    private void addMenu(JMenuBar menuBar, String title, Color color, String... items) {
        JMenu menu = new JMenu(title);
        menu.setForeground(color);
        menuBar.add(menu);
        for (String item : items) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setBackground(Color.WHITE);
            menuItem.addActionListener(this);
            menu.add(menuItem);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        switch (command) {
            case "Exit" -> exitConfirmation();
            case "Calculator" -> openUtility("calc.exe");
            case "Notepad" -> openUtility("notepad.exe");
            case "New Faculty Information" -> new AddTeacher();
            case "New Student Information" -> new AddStudent();
            case "View Faculty Details" -> new TeacherDetails();
            case "View Student Details" -> new StudentDetails();
            case "Faculty Leave" -> new TeacherLeave();
            case "Student Leave" -> new StudentLeave();
            case "Faculty Leave Details" -> new TeacherLeaveDetails();
            case "Student Leave Details" -> new StudentLeaveDetails();
            case "Update Faculty Details" -> new UpdateTeacher();
            case "Update Student Details" -> new UpdateStudent();
            case "Enter Marks" -> new EnterMarks();
            case "Examination Results" -> new ExaminationDetails();
            case "Fee Structure" -> new FeeStructure();
            case "Mark Attendance" -> new MarkAttendance();  
            case "View Attendance" -> new ViewAttendance();
            case "Update Attendance" -> new UpdateAttendance();
            case "About" -> new About();
            case "Student Fee Form" -> new StudentFeeForm();
            default -> System.out.println("No action found for: " + command);
        }
    }

    // 🔹 Confirmation before exiting
    private void exitConfirmation() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            setVisible(false);
            dispose();
        }
    }

    // 🔹 Open utilities (Notepad/Calculator)
    private void openUtility(String program) {
        try {
            Runtime.getRuntime().exec(program);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening " + program);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Project();
    }
}
