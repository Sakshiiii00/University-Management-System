package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class StudentFeeForm extends JFrame implements ActionListener {

    private Choice crollno;
    private JComboBox<String> cbcourse, cbbranch, cbsemester;
    private JLabel labeltotal, labelname, labelfname;
    private JButton update, pay, back;

    StudentFeeForm() {
        setTitle("Student Fee Form");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Left Panel for Form
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(30, 30, 400, 380);
        panel.setBackground(new Color(245, 245, 245)); // Light gray background
        add(panel);

        // Labels & Fields
        JLabel lblrollnumber = createLabel("Roll No", 20, 30);
        panel.add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(180, 30, 150, 25);
        panel.add(crollno);
        loadRollNumbers();

        // Add listener to populate student details when roll number is selected
        crollno.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    fetchStudentDetails();
                }
            }
        });

        JLabel lblname = createLabel("Name", 20, 70);
        panel.add(lblname);

        labelname = createValueLabel(180, 70);
        panel.add(labelname);

        JLabel lblfname = createLabel("Father's Name", 20, 110);
        panel.add(lblfname);

        labelfname = createValueLabel(180, 110);
        panel.add(labelfname);

        JLabel lblcourse = createLabel("Course", 20, 150);
        panel.add(lblcourse);

        String[] courses = {"BTech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = createDropdown(courses, 180, 150);
        panel.add(cbcourse);

        JLabel lblbranch = createLabel("Branch", 20, 190);
        panel.add(lblbranch);

        String[] branches = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT", "Electrical", "Chemical", "Biotechnology"};
        cbbranch = createDropdown(branches, 180, 190);
        panel.add(cbbranch);

        JLabel lblsemester = createLabel("Semester", 20, 230);
        panel.add(lblsemester);

        String[] semesters = {"Semester1", "Semester2", "Semester3", "Semester4", "Semester5", "Semester6", "Semester7", "Semester8"};
        cbsemester = createDropdown(semesters, 180, 230);
        panel.add(cbsemester);

        JLabel lbltotal = createLabel("Total Payable", 20, 270);
        panel.add(lbltotal);

        labeltotal = createValueLabel(180, 270);
        panel.add(labeltotal);

        // Buttons
        update = createButton("Update", 20, 320);
        panel.add(update);

        pay = createButton("Fee Paid", 140, 320);
        panel.add(pay);

        back = createButton("Back", 260, 320);
        panel.add(back);

        // Image on the Right Side
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(460, 60, 400, 300);
        add(image);

        // Fetch details for the first roll number
        if (crollno.getItemCount() > 0) {
            crollno.select(0);
            fetchStudentDetails();
        }

        setVisible(true);
    }

    private void fetchStudentDetails() {
        try {
            Conn c = new Conn();
            String rollno = crollno.getSelectedItem();
            
            String query = "SELECT name, fname, course, branch FROM student WHERE rollno = '" + rollno + "'";
            ResultSet rs = c.s.executeQuery(query);
            
            if (rs.next()) {
                // Populate name and father's name
                String name = rs.getString("name");
                String fatherName = rs.getString("fname");
                
                labelname.setText(name != null ? name : "N/A");
                labelfname.setText(fatherName != null ? fatherName : "N/A");

                // Auto-select course and branch
                String studentCourse = rs.getString("course");
                String studentBranch = rs.getString("branch");

                if (studentCourse != null) {
                    cbcourse.setSelectedItem(studentCourse);
                }

                if (studentBranch != null) {
                    cbbranch.setSelectedItem(studentBranch);
                }
            } else {
                labelname.setText("N/A");
                labelfname.setText("N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching student details: " + e.getMessage());
        }
    }

    private void loadRollNumbers() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading roll numbers");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("SELECT * FROM fee WHERE course = '" + course + "'");
                if (rs.next()) {
                    labeltotal.setText(rs.getString(semester));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == pay) {
            String rollno = crollno.getSelectedItem();
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            String branch = (String) cbbranch.getSelectedItem();
            String total = labeltotal.getText();
            try {
                Conn c = new Conn();
                String query = "INSERT INTO collegefee VALUES('" + rollno + "', '" + course + "', '" + branch + "', '" + semester + "', '" + total + "')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "College fee submitted successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        return label;
    }

    private JLabel createValueLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 180, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        return label;
    }

    private JComboBox<String> createDropdown(String[] items, int x, int y) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(x, y, 180, 25);
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 30);
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
        button.setBackground(new Color(60, 120, 200)); // Light blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}