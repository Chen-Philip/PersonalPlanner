package ics_planner;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A panel for the days of the calendar
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class DayPanel extends JPanel{
    /* Class Variables */
    private static double dayPanelHeight;
    private static double tempPanelHeight;
    private static double dayPanelWidth;
    
    /* Instance  Variables */
    private JPanel topPanel;
    private JPanel centrePanel;
    private JScrollPane scrollPane;
    private JLabel dayNumber;
    private AddItemButton addNewItem;
    private ArrayList<JComponent> activities;
    private MouseAdapter mouseListener;
    
    /* Constructors */
    /**
     * Creates a panel for the days of the calendar given its dimension
     * 
     * @param dayNumber the day of the panel
     * @param dHeight the base height of the panel
     * @param tempHeight the height of the panel
     * @param dWidth the width of the panel
     */
    public DayPanel(int dayNumber, double dHeight, double tempHeight, double dWidth, MouseAdapter m) {
        // Initializes Variables
        dayPanelHeight = dHeight;
        tempPanelHeight = tempHeight;
        dayPanelWidth = dWidth;
        this.dayNumber = new JLabel("" + dayNumber);
        addNewItem = new AddItemButton("+", dayNumber);
        activities = new ArrayList<>();
        mouseListener = m;
        
        // Creates the day panel.
        makeMainPanel();
    } // end of constructor DayPanel(int dayNumber, double dHeight, double tempHeight, double dWidth)
    
    /**
     * Creates a blank panel in the beginning or end of the calendar
     */
    public DayPanel() {
        this.setBackground(new Color(86, 191, 191));
        dayNumber = new JLabel("" + 0);
        topPanel = null;
        activities = null;
        addNewItem = null;
    } // end of constructor DayPanel()
    
    /* Private methods for constructing the panels */
    /*
     * Makes the day (main) panel
     */
    private void makeMainPanel(){
        // Makes the top and centre panels
        centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
        centrePanel.setBackground(new Color(86, 191, 191));
        scrollPane = new JScrollPane(centrePanel);
        scrollPane.setPreferredSize(new Dimension((int) dayPanelWidth, (int) (dayPanelHeight-dayPanelHeight/7)));
        
        makeTopPanel();
        
        // Makes the main panel and adds the components to it
        this.setLayout(new BorderLayout());
        this.setBackground(Color.cyan);
        this.setPreferredSize(new Dimension((int) dayPanelWidth, (int) tempPanelHeight));
        this.add(topPanel, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
    } // end of private method void makeMainPanel(
    
    /*
     * Makes the top panel
     */
    private void makeTopPanel() {    
        // Makes the add button
        addNewItem.setBorder(BorderFactory.createEmptyBorder());
        addNewItem.setPreferredSize(new Dimension((int) dayPanelWidth/7, (int) dayPanelHeight/7));
        
        // Makes the top panel and adds the components
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(101, 191, 191));
        topPanel.add(dayNumber, BorderLayout.LINE_START);
        topPanel.add(addNewItem, BorderLayout.LINE_END);
    } // end of private method void makeTopPanel()
    
    /* Accessor Methods */
    /**
     * Returns the <code>addNewItem</code> button
     * @return the <code>addNewItem</code> button
     */
    public JButton getButton() {
        return addNewItem;
    } // end of method JButton getButton()
    
    /**
     * Returns the list of activities for that day
     * @return list of activities for that day
     */
    public ArrayList getArrayList() {
        return activities;
    } // end of method ArrayList getArrayList()
    
    /**
     * Returns the day of the panel
     * @return day of the panel
     */
    public int getDay() {
        return Integer.parseInt(dayNumber.getText());
    } // end of method int getDay()
    
    public int getArrayListSize() {
        return activities.size();
    }
    
    public JComponent getActivity(int index) {
        return activities.get(index);
    }
    /* Mutator Methods */
    /**
     * Adds a component to this panel
     * 
     * @param component the component that is being added to the specific dayPanel
     * @param usedByButton if this method is used by a button
     */
    public void addNewItem(JComponent component, boolean usedByButton) {
        // Adds the panel to the main panel
        component.addMouseListener(mouseListener);
        activities.add(component);
        centrePanel.add(activities.get(activities.size()-1));
        if (usedByButton) {
            // Remakes the panel
            this.revalidate();
            this.repaint();
        } // end of if (usedByButton)
    } // end of method void addNewItem(JComponent[] component, boolean usedByButton)
    
    public void editActivity(int index, String text) {
        ((ImpDateLabel) activities.get(index)).setText(text);
    }
    
    /**
     * Removes an activity from the day panel  
     * @param index the index of the activity that is being removed
     */
    public void removeActivity(int index) {
        centrePanel.remove(activities.get(index));
        activities.remove(index);
        activities.add(index, null);
        System.out.println();
        this.revalidate();
        this.repaint();
    } // end of method 
    /**
     * Sets the <code>ActionListener</code> for the <code>addNewItem</code> button
     * @param actionListener the <code>ActionListener</code> for the <code>addNewItem</code> button
     */
    public void setActionListener(ActionListener actionListener) {
        addNewItem.addActionListener(actionListener);
    } // end of method void setActionListener(ActionListener actionListener)
}
