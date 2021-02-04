package ics_planner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * A panel that asks for the login information or creates a new user
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Login extends JPanel {
    /* Constants */
    private static final int GAP = 5;
    
    /* Class Variables */
    private static double baseHeight;
    private static double baseWidth;
    
    /* Constants */
    private JFrame frame;
    private JPanel centrePanel;
    private Main mainProgram;
    
    private JPanel[] loginPanels;
    private JTextField username;
    private JLabel userLabel;
    private JLabel[] passLabel;
    private JPasswordField[] password;
    
    private JButton loginCreate;
    private JButton back;
    private JPanel buttonPanel;
    
    /* Constructors */
    /**
     * Creates a login panel
     * 
     * @param logType 0 if it is a returning user, 1 if it is a new user
     * @param a the <code>ActionListener</code> for the <code>back</code> button
     * @param frame the start frame
     */
    public Login(int logType, ActionListener a, JFrame frame) {
        baseHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/7.5;
        baseWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/9;
        back = new JButton("Back");
        back.addActionListener(a);
        this.frame = frame;
        if (logType == 0) {
            // Makes a login panel for a returning user
            loginPanels = new JPanel[2];
            password = new JPasswordField[1];
            passLabel = new JLabel[1];
            passLabel[0] = new JLabel("Password:");

            makeMainPanel();
            centrePanel.add(buttonPanel);
            loginCreate.addActionListener(new LoginListener());
        }
        else if (logType == 1) {
            // Makes a login screen for a new user
            loginPanels = new JPanel[3];
            password = new JPasswordField[2];
            passLabel = new JLabel[2];
            passLabel[0] = new JLabel("New Password:");
            passLabel[1] = new JLabel("Re-enter New Password:");
            
            makeMainPanel();
            loginCreate.setText("Create New User");
            password[1] = new JPasswordField(25);
            loginPanels[2].add(passLabel[1]);
            loginPanels[2].add(password[1]);
            centrePanel.add(loginPanels[2]);
            centrePanel.add(buttonPanel);
            loginCreate.addActionListener(new CreateUserListener());
        } // end of if
    } // end of constructor
    
    /* Private methods for constructing the panels */
    /*
     * Makes the main panel
     */
    private void makeMainPanel() {
        this.setLayout(new BorderLayout());

        makeCentrePanel();
        this.add(centrePanel, BorderLayout.CENTER);
    } // end of private method
    
    /*
     * Makes the centre panel
     */
    private void makeCentrePanel() {
        loginCreate = new JButton("Login");
        
        // Makes the button panel and add the components to it
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, GAP, GAP));
        buttonPanel.add(loginCreate);
        buttonPanel.add(back);
        
        // Makes the username and password panels
        userLabel = new JLabel("Username:");
        username = new JTextField(25);
        password[0] = new JPasswordField(25);

        for (int i = 0; i < loginPanels.length; i = i + 1) {
            loginPanels[i] = new JPanel();
            loginPanels[i].setLayout(new FlowLayout(FlowLayout.TRAILING, GAP, GAP));
            loginPanels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        loginPanels[0].add(userLabel);
        loginPanels[0].add(username);
        loginPanels[1].add(passLabel[0]);
        loginPanels[1].add(password[0]);
        
        // Makes the centrePanel panel and add the components to it
        centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.PAGE_AXIS));
        centrePanel.add(loginPanels[0]);
        centrePanel.add(loginPanels[1]);
    } // end of private method
    
    /* Accessor Methods */
    /**\
     * Returns the <code>back</code> button
     * @return the <code>back</code> button
     */
    public JButton getBackButton() {
        return back;
    } // end of method
    
    /* Private Methods */
    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            if (source == loginCreate) {
                try {
                    String fileName = "Users\\" + username.getText() + "\\" + "login" + ".txt";
                    String pword = new String(password[0].getPassword());
                    boolean isPassword = false;
                    // Opens and reads the file
                    File file = new File(fileName);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    // Checks if the password is correct
                    String note = "";
                    while ((line = br.readLine()) != null) {
                        if (line.equals(pword))
                            isPassword = true;
                    }// end of while ((line = br.readLine()) != null)
                    if (!isPassword)
                        new ErrorOptionPane("Incorrect username or password. \nPlease try again.","Login Error");
                    else {
                        // Opens the main program if the password is correct
                        mainProgram = new Main(username.getText(), baseHeight, baseWidth);
                        frame.dispose();
                    }
                }
                catch (Exception FileNotFoundException) {
                    new ErrorOptionPane("Incorrect username or password. \nPlease try again.","Login Error");
                }
            } 
        } // end of method actionPerformed(ActionEvent event)
    } // end of pirvate class
    private class CreateUserListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            if (source == loginCreate) {
                try {
                    // Opens the folder Agenda or makes a new folder if it doesn't exist
                    File file = new File("Users");
                    file.mkdir();
                                  
                    String user = username.getText();
                    String pword = new String(password[0].getPassword());
                    String pword2 = new String(password[1].getPassword());
                    
                    // Checks if the passwords entered are valid
                    boolean isSamePassword = pword.equals(pword2);
                    if (pword.length() == 0)
                        new ErrorOptionPane("Invalid password. \nPlease try again.","Password Error");
                    else if (!isSamePassword)
                        new ErrorOptionPane("Passwords do not match. \nPlease try again.","Password Error");
                    else {
                        file = new File("Users\\" + user);
                        file.mkdir();

                        // Saves the password into a file
                        String fileName = "Users\\" + user + "\\login.txt";
                        FileWriter write = new FileWriter(fileName, false);
                        PrintWriter printLine = new PrintWriter(write);
                        printLine.println(pword);
                        // Closes the file
                        printLine.close();
                        
                        // Opens the main program
                        mainProgram = new Main(user, baseHeight, baseWidth);
                        frame.dispose();
                    }    
                }
                catch (Exception FileNotFoundException) {
                    new ErrorOptionPane("An error has occured. \nPlease try again.","Error");
                }
            } 
        } // end of method actionPerformed(ActionEvent event)
    }// end of private cass
    private class ErrorOptionPane {
        /* Instance Variables */
        JFrame frame;

        ErrorOptionPane(String errorMessage, String typeOfError) {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, errorMessage, typeOfError, JOptionPane.ERROR_MESSAGE);
        } // end of constructor OptionPaneExample(String errorMessage)
    } // end of private class OptionPaneExample
}
