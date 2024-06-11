package test1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Test1 extends JFrame {

    JFrame frame;
    JLabel lbltitle, lblName, lblID, lblEmail, lblProgamlevel, lblProgramname, lblProgrameSession;
    JTextField txtName, TxtID, TxtEmail;
    JComboBox<String> cboxProgramlevel, cboxProgramname, cboxProgramsession;
    JButton btnSubmit, btnView, btnReset;
    JTextArea txtarea;

    public Test1() {

        // Spawn the Frame
        frame = new JFrame("Sunway University Enrolment");
        frame.setLayout(null); // Use null layout for absolute positioning
        frame.setSize(700, 700);
        frame.setVisible(true); // Visible to see
        frame.setLocation(500, 200); // Set Spawn location
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Shutdown process on close

        // Create the title label
        lbltitle = new JLabel("Enrollment Data", SwingConstants.CENTER);
        lbltitle.setBounds(250, 10, 200, 50); // Centered title
        frame.add(lbltitle);

        // Create and position labels
        lblName = new JLabel("Name :");
        lblName.setBounds(70, 70, 200, 30); // Adjusted width and height
        frame.add(lblName);

        lblID = new JLabel("Student ID :");
        lblID.setBounds(70, 120, 200, 30); // Adjusted width and height
        frame.add(lblID);

        lblEmail = new JLabel("Email :");
        lblEmail.setBounds(70, 170, 200, 30); // Adjusted width and height
        frame.add(lblEmail);

        lblProgamlevel = new JLabel("Program Level :");
        lblProgamlevel.setBounds(70, 220, 200, 30); // Adjusted width and height
        frame.add(lblProgamlevel);

        lblProgramname = new JLabel("Program Name :");
        lblProgramname.setBounds(70, 270, 200, 30); // Adjusted width and height
        frame.add(lblProgramname);

        lblProgrameSession = new JLabel("Program Session :");
        lblProgrameSession.setBounds(70, 320, 200, 30); // Adjusted width and height
        frame.add(lblProgrameSession);

        // Create and position text fields
        txtName = new JTextField();
        txtName.setBounds(250, 70, 300, 30); // Adjusted width and height
        frame.add(txtName);

        TxtID = new JTextField();
        TxtID.setBounds(250, 120, 300, 30); // Adjusted width and height
        frame.add(TxtID);

        TxtEmail = new JTextField();
        TxtEmail.setBounds(250, 170, 300, 30); // Adjusted width and height
        frame.add(TxtEmail);

        // Create and position combo box for Program Level
        String[] programLevels = {"-Select-", "Diploma", "Degree"};
        cboxProgramlevel = new JComboBox<>(programLevels);
        cboxProgramlevel.setBounds(250, 220, 300, 30); // Adjusted width and height
        frame.add(cboxProgramlevel);

        // Create combo boxes for Program Name
        String[] DiplomaProgram = {"-Select-", "Diploma in Business Administration", "Diploma in Information Technology", "Diploma in Electrical Engineering"};
        String[] DegreeProgram = {"-Select-", "Degree in Manufacturing Technology", "Degree in Business Information"};
        cboxProgramname = new JComboBox<>(new String[]{"-Select-"});
        cboxProgramname.setBounds(250, 270, 300, 30); // Adjusted width and height
        frame.add(cboxProgramname);

        // Create and position combo box for Program Session
        String[] programSessions = {"-Select-", "Session 1", "Session 2", "Session 3"};
        cboxProgramsession = new JComboBox<>(programSessions);
        cboxProgramsession.setBounds(250, 320, 300, 30); // Adjusted width and height
        frame.add(cboxProgramsession);

        // Create and position buttons
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(100, 380, 100, 35);// (x, y, width, height)
        frame.add(btnSubmit);

        btnView = new JButton("View");
        btnView.setBounds(250, 380, 100, 35);// (x, y, width, height)
        frame.add(btnView);

        btnReset = new JButton("Reset");
        btnReset.setBounds(400, 380, 100, 35);// (x, y, width, height)
        frame.add(btnReset);
        
        txtarea = new JTextArea();
        txtarea.setBounds(50, 430, 600, 200); // Adjusted width and height
        txtarea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtarea);
        scrollPane.setBounds(50, 430, 600, 200);
        frame.add(scrollPane);
        
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a message in the text area
                String name = txtName.getText();
                String studentID = TxtID.getText();
                String email = TxtEmail.getText();
                String programLevel = (String) cboxProgramlevel.getSelectedItem();
                String programName = (String) cboxProgramname.getSelectedItem();
                String programSession = (String) cboxProgramsession.getSelectedItem();
                txtarea.append("------------------------------------------------------------------------------\n"
                        + "Name : " + name + "\n"
                        + "Student ID : " + studentID + "\n" 
                        + "Email : " + email + "\n"
                        + "Program Level : " + programLevel + "\n"
                        + "Program Name : " + programName + "\n" 
                        + "Program Session : " + programSession + "\n"
                        + "------------------------------------------------------------------------------");
            }
        });
        
        
        // Add ActionListener to cboxProgramlevel
        cboxProgramlevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLevel = (String) cboxProgramlevel.getSelectedItem();
                if (selectedLevel.equals("Diploma")) {
                    cboxProgramname.setModel(new DefaultComboBoxModel<>(DiplomaProgram));
                } else if (selectedLevel.equals("Degree")) {
                    cboxProgramname.setModel(new DefaultComboBoxModel<>(DegreeProgram));
                } else {
                    cboxProgramname.setModel(new DefaultComboBoxModel<>(new String[]{"-Select-"}));
                }
            }
        });

        // Add ActionListener to btnReset
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtName.setText("");
                TxtID.setText("");
                TxtEmail.setText("");
                txtarea.setText("");
                cboxProgramlevel.setSelectedIndex(0);
                cboxProgramname.setModel(new DefaultComboBoxModel<>(new String[]{"-Select-"}));
                cboxProgramsession.setSelectedIndex(0);
            }
        });

btnSubmit.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = txtName.getText();
        String studentID = TxtID.getText();
        String email = TxtEmail.getText();
        String programLevel = (String) cboxProgramlevel.getSelectedItem();
        String programName = (String) cboxProgramname.getSelectedItem();
        String programSession = (String) cboxProgramsession.getSelectedItem();

        // Database connection
        String url = "jdbc:mysql://localhost:3306/dbiptest";
        String user = "root"; // Update with your database username
        String password = ""; // Update with your database password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO studentinfo (Name, student_id, email, program_level, program_name, session) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, studentID);
            statement.setString(3, email);
            statement.setString(4, programLevel);
            statement.setString(5, programName);
            statement.setString(6, programSession);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Data saved successfully!");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving data: Student ID already exists.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving data: " + ex.getMessage());
        }
    }
});

    }   

    
    public static void main(String[] args) {        
        // Create an instance of Test1 to show the frame
        new Test1();
    }
}