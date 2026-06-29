package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MarkAttendance extends JFrame implements ActionListener {
    private JComboBox<String> userType, courseBox, branchBox;
    private JTextField idField, dateField;
    private JRadioButton present, absent;
    private ButtonGroup attendanceGroup;
    private JButton submit, cancel;

    MarkAttendance() {
        setTitle("Mark Attendance");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBounds(20, 20, 450, 380);
        add(panel);

        JLabel heading = new JLabel("Mark Attendance", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 22));
        heading.setBounds(100, 10, 250, 30);
        panel.add(heading);

        // User Type
        JLabel userLabel = new JLabel("Select Type:");
        userLabel.setBounds(30, 50, 100, 25);
        panel.add(userLabel);

        userType = new JComboBox<>(new String[]{"Student", "Teacher"});
        userType.setBounds(150, 50, 200, 25);
        panel.add(userType);

        // ID Field
        JLabel idLabel = new JLabel("Enter ID:");
        idLabel.setBounds(30, 85, 100, 25);
        panel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 85, 200, 25);
        panel.add(idField);

        // Course Selection
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(30, 120, 100, 25);
        panel.add(courseLabel);

        courseBox = new JComboBox<>(new String[]{"B.Tech", "BBA", "BCA", "BCom", "BSc", "BA", "MSc", "MBA", "MCA", "MCom", "MA"});
        courseBox.setBounds(150, 120, 200, 25);
        panel.add(courseBox);

        // Branch (renamed from subject) Selection
        JLabel branchLabel = new JLabel("Branch:");
        branchLabel.setBounds(30, 155, 100, 25);
        panel.add(branchLabel);

        branchBox = new JComboBox<>();
        branchBox.setBounds(150, 155, 200, 25);
        panel.add(branchBox);
        updateBranches();
        courseBox.addActionListener(e -> updateBranches());

        // Attendance Radio Buttons
        JLabel attendanceLabel = new JLabel("Attendance:");
        attendanceLabel.setBounds(30, 190, 100, 25);
        panel.add(attendanceLabel);

        present = new JRadioButton("Present");
        absent = new JRadioButton("Absent");
        present.setBounds(150, 190, 80, 25);
        absent.setBounds(250, 190, 80, 25);
        attendanceGroup = new ButtonGroup();
        attendanceGroup.add(present);
        attendanceGroup.add(absent);
        panel.add(present);
        panel.add(absent);

        // Date Field
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(30, 225, 100, 25);
        panel.add(dateLabel);

        dateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateField.setBounds(150, 225, 200, 25);
        panel.add(dateField);

        // Buttons
        submit = new JButton("Submit");
        submit.setBounds(80, 280, 100, 30);
        submit.addActionListener(this);
        panel.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(220, 280, 100, 30);
        cancel.addActionListener(this);
        panel.add(cancel);

        setVisible(true);
    }

    // This method updates the branch (subject) options based on the selected course.
    private void updateBranches() {
        branchBox.removeAllItems();
        String selectedCourse = (String) courseBox.getSelectedItem();

        if (selectedCourse.equals("B.Tech")) {
            branchBox.addItem("Computer Science");
            branchBox.addItem("Electronics");
            branchBox.addItem("Mechanical");
            branchBox.addItem("Civil");
            branchBox.addItem("IT");
        } else if (selectedCourse.equals("BBA") || selectedCourse.equals("MBA")) {
            branchBox.addItem("Management");
            branchBox.addItem("Finance");
            branchBox.addItem("Marketing");
        } else if (selectedCourse.equals("BCA") || selectedCourse.equals("MCA")) {
            branchBox.addItem("Computer Applications");
        } else if (selectedCourse.equals("MSc")) {
            branchBox.addItem("Physics");
            branchBox.addItem("Mathematics");
            branchBox.addItem("Chemistry");
        } else if (selectedCourse.equals("MA") || selectedCourse.equals("MCom")) {
            branchBox.addItem("Humanities");
            branchBox.addItem("Commerce");
        } else if (selectedCourse.equals("BSc")) {
            branchBox.addItem("Physics");
            branchBox.addItem("Chemistry");
            branchBox.addItem("Mathematics");
            branchBox.addItem("Biology");
        } else if (selectedCourse.equals("BCom")) {
            branchBox.addItem("Accounting");
            branchBox.addItem("Finance");
            branchBox.addItem("Taxation");
        } else if (selectedCourse.equals("BA")) {
            branchBox.addItem("History");
            branchBox.addItem("Political Science");
            branchBox.addItem("Sociology");
            branchBox.addItem("Economics");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String type = (String) userType.getSelectedItem();
            String id = idField.getText().trim();
            String course = (String) courseBox.getSelectedItem();
            String branch = (String) branchBox.getSelectedItem();
            String date = dateField.getText().trim();
            String attStatus = present.isSelected() ? "Present" : absent.isSelected() ? "Absent" : "";

            if (id.isEmpty() || attStatus.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all details.");
                return;
            }

            try {
                Conn con = new Conn();
                // Check if the ID exists in the corresponding table AND matches course and branch.
                String checkQuery;
                if (type.equals("Student")) {
                    checkQuery = "SELECT * FROM student WHERE rollno = ? AND course = ? AND branch = ?";
                } else {
                    checkQuery = "SELECT * FROM teacher WHERE empid = ? AND course = ? AND branch = ?";
                }
                PreparedStatement checkStmt = con.c.prepareStatement(checkQuery);
                checkStmt.setString(1, id);
                checkStmt.setString(2, course);
                checkStmt.setString(3, branch);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "No such " + type + " in the selected branch or course.");
                    rs.close();
                    checkStmt.close();
                    con.c.close();
                    return;
                }
                rs.close();
                checkStmt.close();

                // Insert the attendance record into the attendance table
                String query = "INSERT INTO attendance (person_id, person_type, course, branch, status, date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.c.prepareStatement(query);
                pstmt.setString(1, id);
                pstmt.setString(2, type);
                pstmt.setString(3, course);
                pstmt.setString(4, branch);
                pstmt.setString(5, attStatus);
                pstmt.setString(6, date);
                pstmt.executeUpdate();
                pstmt.close();
                con.c.close();

                JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                // Clear inputs for next entry
                idField.setText("");
                attendanceGroup.clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving attendance: " + e.getMessage());
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new MarkAttendance();
    }
}
