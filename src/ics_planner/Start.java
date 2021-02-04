
package ics_planner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The start frame for this program
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Start {
    /* Class Variables */
    private static double baseHeight;
    private static double baseWidth;
    
    /* Instance Variables */
    private JFrame frame;

    private JLabel[] welcome;
    
    private JPanel centrePanel;
    private Login login1;
    private Login login2;
    private JPanel loginButtonPanel;
    
    private JButton newUser;
    private JButton returningUser;
    private JPanel buttonPanel;

    private JButton quit;
    
    /**
     * Creates a start frame
     */
    public Start() {
        baseHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/7.5;
        baseWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/9;
        makeFrame();
        login1 = new Login(0, new ButtonListener(), frame);
        login2 = new Login(1, new ButtonListener(), frame);
    } // end of constructor
    
    /* Private methods for constructing the panels */
    /*
     * Makes the frame
     */
    private void makeFrame() {
        // Makes the frame
        frame = new JFrame("Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Makes the button panel
        makeButtonPanel();
        loginButtonPanel = buttonPanel;
        
        // Makes the welcome labels
        welcome = new JLabel[2];
        welcome[0] = new JLabel("Welcome to your");
        welcome[1] = new JLabel("Personal Planner!");
        for (int i = 0; i < welcome.length; i = i + 1) {
            welcome[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            welcome[i].setFont(new Font("Sans_Serif", Font.PLAIN, 24));
        }
        
        // Makes the centre panel and adds the components to it
        centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.PAGE_AXIS));
        centrePanel.add(welcome[0]);
        centrePanel.add(welcome[1]);
        centrePanel.add(loginButtonPanel);
        
        // Makes the border panels
        makeBorderPanels();
        frame.add(centrePanel, BorderLayout.CENTER);
        
        // Finish creating the frame.
        frame.pack();
        frame.setVisible(true);
    } // end of private method
    
    /*
     * Makes the button panel
     */
    private void makeButtonPanel() {
        // Makes a dummy panel
        JPanel[] dummyPanel = new JPanel[2];
        for (int i = 0; i < dummyPanel.length; i = i + 1) {
            dummyPanel[i] = new JPanel();
            dummyPanel[i].setPreferredSize(new Dimension((int) baseWidth, (int) baseHeight/6));
        }
        
        // Makes the buttons
        ButtonListener actionListener = new ButtonListener();
        newUser = new JButton("New User");
        newUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        newUser.addActionListener(actionListener);
        newUser.setFont(new Font("Sans_Serif", Font.PLAIN, 20));
        returningUser = new JButton("Returning User");
        returningUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        returningUser.addActionListener(actionListener);
        returningUser.setFont(new Font("Sans_Serif", Font.PLAIN, 20));
        
        // Makes the button panel and adds the components to it
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.add(dummyPanel[0]);
        buttonPanel.add(returningUser);
        buttonPanel.add(dummyPanel[1]);
        buttonPanel.add(newUser);
    } // end of private method
    
    /*
     * Makes the border panels
     */
    private void makeBorderPanels() {
        // Makes the dummy panels
        JPanel[] dummyPanel = new JPanel[4];
        dummyPanel[0] = new JPanel();
        dummyPanel[0].setPreferredSize(new Dimension((int) baseWidth/2, (int) baseHeight/6));
        dummyPanel[1] = new JPanel();
        dummyPanel[1].setPreferredSize(new Dimension((int) baseWidth/2, (int) baseHeight/6));
        dummyPanel[2] = new JPanel();
        dummyPanel[2].setPreferredSize(new Dimension((int) baseHeight/6, (int) baseWidth/2));
        dummyPanel[3] = new JPanel();
        dummyPanel[3].setPreferredSize(new Dimension((int) baseHeight/6, (int) baseWidth/2));
        
        // Adds the panels to the frame
        frame.add(dummyPanel[0], BorderLayout.LINE_START);
        frame.add(dummyPanel[1], BorderLayout.LINE_END);
        frame.add(dummyPanel[2], BorderLayout.PAGE_START);
        frame.add(dummyPanel[3], BorderLayout.PAGE_END);
    } // end of private method
    
    /* Private Classes */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            if (source == returningUser) {
                centrePanel.remove(loginButtonPanel);
                loginButtonPanel = login1;
                centrePanel.add(loginButtonPanel);
                centrePanel.revalidate();
                frame.pack();
            }
            else if (source == newUser) {
                centrePanel.remove(loginButtonPanel);
                loginButtonPanel = login2;
                centrePanel.add(loginButtonPanel);
                centrePanel.revalidate();
                frame.pack();
            }
            else if (source == login1.getBackButton() || source == login2.getBackButton() ){
                centrePanel.remove(loginButtonPanel);
                loginButtonPanel = buttonPanel;
                centrePanel.add(loginButtonPanel);
                centrePanel.revalidate();
                frame.pack();
            }
        } // end of method actionPerformed(ActionEvent event)
    } // end of class ButtonListener
    
    /**
     * Starts the program
     */
    public static void main(String[] args) {
        Start main = new Start();
    } // end of method
}
