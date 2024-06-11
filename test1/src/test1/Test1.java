package test1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Test1 extends JFrame {

    JLabel lblTitle, lblName, lblStudentID, lblEmail, lblPLevel, lblPName, lblPSession, lblOutput;
    JTextField txtName, txtStudentID, txtEmail;
    JComboBox<String> cbPLevel, cbPName, cbPSession;
    JButton btnSubmit, btnView, btnReset;
    JTextArea txtDisplay;

    String url = "jdbc:mysql://localhost:3306/students";
    String user = "root";
    String password = "";

    Test1() {
        setTitle("Student Enrollment System Sunway University");

        lblTitle = new JLabel("ENROLLMENT DATA");
        lblName = new JLabel("Name :");
        lblStudentID = new JLabel("Student ID :");
        lblEmail = new JLabel("Email :");
        lblPLevel = new JLabel("Program Level : ");
        lblPName = new JLabel("Program Name : ");
        lblPSession = new JLabel("Program Session : ");
        lblOutput = new JLabel("OutPut :- ");

        txtName = new JTextField(20);
        txtStudentID = new JTextField(20);
        txtEmail = new JTextField(20);

        cbPLevel = new JComboBox<>(new String[]{"Please select", "Diploma", "Degree"});
        cbPName = new JComboBox<>(new String[]{});
        cbPSession = new JComboBox<>(new String[]{"Please select", "Session 1", "Session 2"});

        btnSubmit = new JButton("Submit");
        btnView = new JButton("View");
        btnReset = new JButton("Reset");

        txtDisplay = new JTextArea();

        add(lblTitle);
        add(lblName);
        add(lblStudentID);
        add(lblEmail);
        add(lblPLevel);
        add(lblPName);
        add(lblPSession);
        add(lblOutput);

        add(txtName);
        add(txtStudentID);
        add(txtEmail);

        add(cbPLevel);
        add(cbPName);
        add(cbPSession);

        add(btnSubmit);
        add(btnView);
        add(btnReset);

        add(txtDisplay);

        // Arrange components
        lblTitle.setBounds(180, 50, 200, 20);
        lblName.setBounds(50, 100, 60, 20);
        lblStudentID.setBounds(50, 140, 60, 20);
        lblEmail.setBounds(50, 180, 60, 20);

        lblPLevel.setBounds(50, 220, 120, 20);
        lblPName.setBounds(50, 260, 120, 20);
        lblPSession.setBounds(50, 300, 120, 20);

        txtName.setBounds(180, 100, 200, 20);
        txtEmail.setBounds(180, 140, 200, 20);
        txtStudentID.setBounds(180, 180, 200, 20);

        cbPLevel.setBounds(180, 220, 200, 20);
        cbPName.setBounds(180, 260, 200, 20);
        cbPSession.setBounds(180, 300, 200, 20);

        btnSubmit.setBounds(50, 350, 80, 20);
        btnView.setBounds(150, 350, 80, 20);
        btnReset.setBounds(250, 350, 80, 20);

        lblOutput.setBounds(50, 380, 80, 20);
        txtDisplay.setBounds(50, 400, 350, 150);

        setLayout(null);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cbPLevel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedLevel = (String) cbPLevel.getSelectedItem();
                    cbPName.removeAllItems();
                    cbPName.addItem("Please select your Program");
                    if ("Diploma".equals(selectedLevel.trim())) {
                        cbPName.addItem("Diploma in Information Technology");
                        cbPName.addItem("Diploma in Business Administration");
                        cbPName.addItem("Diploma in Mechanical Engineering");
                    } else if ("Degree".equals(selectedLevel.trim())) {
                        cbPName.addItem("Bachelor of Computer Science");
                        cbPName.addItem("Bachelor of Business Administration");
                        cbPName.addItem("Bachelor of Mechanical Engineering");
                    }
                }
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);
                    Statement stat = conn.createStatement();

                    String name = txtName.getText();
                    String studentId = txtStudentID.getText();
                    String email = txtEmail.getText();
                    String programLevel = (String) cbPLevel.getSelectedItem();
                    String programName = (String) cbPName.getSelectedItem();
                    String programSession = (String) cbPSession.getSelectedItem();

                    String sql = "INSERT INTO students_details (name, student_id, email, program_level, program_name, program_session) " +
                                 "VALUES ('" + name + "', '" + studentId + "', '" + email + "', '" + programLevel + "', '" + programName + "', '" + programSession + "')";
                    stat.executeUpdate(sql);

                    JOptionPane.showMessageDialog(null, "Successfully added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtName.setText("");
                txtStudentID.setText("");
                txtEmail.setText("");
                cbPLevel.setSelectedIndex(0);
                cbPName.removeAllItems();
                cbPSession.setSelectedIndex(0);
                txtDisplay.setText("");
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String studID = txtStudentID.getText();
                String email = txtEmail.getText();
                String PLevel = (String) cbPLevel.getSelectedItem();
                String PName = (String) cbPName.getSelectedItem();
                String PSession = (String) cbPSession.getSelectedItem();

                double price = calculatePrice(PName);
                txtDisplay.setText("Name: " + name + "\nStudent ID: " + studID + "\nEmail: " + email +
                        "\nProgram Level: " + PLevel + "\nProgram Name: " + PName + "\nProgram Session: " + PSession +
                        "\nThe price is: " + price);
            }
        });

        setVisible(true);
    }

    public double calculatePrice(String programName) {
        double price;

        switch (programName) {
            case "Diploma in Information Technology":
                price = 5000.0;
                break;
            case "Diploma in Business Administration":
                price = 4500.0;
                break;
            case "Diploma in Mechanical Engineering":
                price = 6000.0;
                break;
            case "Bachelor of Computer Science":
                price = 7000.0;
                break;
            case "Bachelor of Business Administration":
                price = 6500.0;
                break;
            case "Bachelor of Mechanical Engineering":
                price = 8000.0;
                break;
            default:
                price = 0.0;
        }

        return price;
    }

    public static void main(String[] args) {
        new Test1();
    }
}
