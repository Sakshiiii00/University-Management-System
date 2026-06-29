package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class AddStudent extends JFrame implements ActionListener{
    
    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfx, tfxii, tfaadhar;
    JLabel labelrollno;
    JDateChooser dcdob;
    JComboBox<String> cbcourse, cbbranch;
    JButton submit, cancel;
    
    Random ran = new Random();
    long first4 = Math.abs((ran.nextLong() % 9000L) + 1000L);
    
    AddStudent() {
        setTitle("Add Student");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel heading = new JLabel("New Student Details");
        heading.setBounds(310, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 30));
        add(heading);
        
        addLabel("Name", 50, 150);
        tfname = addTextField(200, 150);
        
        addLabel("Father's Name", 400, 150);
        tffname = addTextField(600, 150);
        
        addLabel("Roll Number", 50, 200);
        labelrollno = new JLabel("1533" + first4);
        labelrollno.setBounds(200, 200, 200, 30);
        labelrollno.setFont(new Font("serif", Font.BOLD, 20));
        add(labelrollno);
        
        addLabel("Date of Birth", 400, 200);
        dcdob = new JDateChooser();
        dcdob.setBounds(600, 200, 150, 30);
        add(dcdob);
        
        addLabel("Address", 50, 250);
        tfaddress = addTextField(200, 250);
        
        addLabel("Phone", 400, 250);
        tfphone = addTextField(600, 250);
        
        addLabel("Email Id", 50, 300);
        tfemail = addTextField(200, 300);
        
        addLabel("Class X (%)", 400, 300);
        tfx = addTextField(600, 300);
        
        addLabel("Class XII (%)", 50, 350);
        tfxii = addTextField(200, 350);
        
        addLabel("Aadhar Number", 400, 350);
        tfaadhar = addTextField(600, 350);
        
        addLabel("Course", 50, 400);
        cbcourse = new JComboBox<>(new String[]{"B.Tech", "BBA", "BCA", "BCom", "BSc", "BA", "MSc", "MBA", "MCA", "MCom", "MA"});
        cbcourse.setBounds(200, 400, 150, 30);
        add(cbcourse);
        
        addLabel("Branch", 400, 400);
        cbbranch = new JComboBox<>();
        cbbranch.setBounds(600, 400, 150, 30);
        add(cbbranch);
        
        updateBranches();
        cbcourse.addActionListener(e -> updateBranches());
        
        submit = addButton("Submit", 250, 550);
        cancel = addButton("Cancel", 450, 550);
        
        setVisible(true);
    }
    
    private void updateBranches() {
        cbbranch.removeAllItems();
        String selectedCourse = (String) cbcourse.getSelectedItem();
        
        if (selectedCourse.equals("B.Tech")) {
            cbbranch.addItem("Computer Science");
            cbbranch.addItem("Electronics");
            cbbranch.addItem("Mechanical");
            cbbranch.addItem("Civil");
            cbbranch.addItem("IT");
        } else if (selectedCourse.equals("BBA") || selectedCourse.equals("MBA")) {
            cbbranch.addItem("Management");
            cbbranch.addItem("Finance");
            cbbranch.addItem("Marketing");
        } else if (selectedCourse.equals("BCA") || selectedCourse.equals("MCA")) {
            cbbranch.addItem("Computer Applications");
        } else if (selectedCourse.equals("MSc")) {
            cbbranch.addItem("Physics");
            cbbranch.addItem("Mathematics");
            cbbranch.addItem("Chemistry");
        } else if (selectedCourse.equals("MA") || selectedCourse.equals("MCom")) {
            cbbranch.addItem("Humanities");
            cbbranch.addItem("Commerce");
        } else if (selectedCourse.equals("BSc")) {
            cbbranch.addItem("Physics");
            cbbranch.addItem("Chemistry");
            cbbranch.addItem("Mathematics");
            cbbranch.addItem("Biology");
        } else if (selectedCourse.equals("BCom")) {
            cbbranch.addItem("Accounting");
            cbbranch.addItem("Finance");
            cbbranch.addItem("Taxation");
        } else if (selectedCourse.equals("BA")) {
            cbbranch.addItem("History");
            cbbranch.addItem("Political Science");
            cbbranch.addItem("Sociology");
            cbbranch.addItem("Economics");
        }
    }
    
    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 30);
        label.setFont(new Font("serif", Font.BOLD, 20));
        add(label);
    }
    
    private JTextField addTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 150, 30);
        add(textField);
        return textField;
    }
    
    private JButton addButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 30);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.addActionListener(this);
        add(button);
        return button;
    }
    
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {
        String name = tfname.getText();
        String fname = tffname.getText();
        String rollno = labelrollno.getText();
        String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
        String address = tfaddress.getText();
        String phone = tfphone.getText();
        String email = tfemail.getText();
        String x = tfx.getText();
        String xii = tfxii.getText();
        String aadhar = tfaadhar.getText();
        String course = (String) cbcourse.getSelectedItem();
        String branch = (String) cbbranch.getSelectedItem();

        try {
            Conn con = new Conn();
            String query = "INSERT INTO student VALUES('"+name+"', '"+fname+"', '"+rollno+"', '"+dob+"', '"+address+"', '"+phone+"', '"+email+"', '"+x+"', '"+xii+"', '"+aadhar+"', '"+course+"', '"+branch+"')";
            con.s.executeUpdate(query);
            
            JOptionPane.showMessageDialog(null, "Student Details Inserted Successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else {
        setVisible(false);
    }
}

    
    public static void main(String[] args) {
        new AddStudent();
    }
}
