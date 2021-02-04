/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ics_planner;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A panel that contains for the different functions (To-Do List, List of Important Dates,
 * Notes) of the agenda
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class FunctionPanel extends JPanel {
    /* Instance Variables */
    private double dayPanelHeight;
    private double dayPanelWidth;
    
    private JLabel functionLabel;
    private JPanel functionPanel;
    private JScrollPane scrollPane;
    private AddItemButton addNewItem;
    private ArrayList<JComponent> components;
    private JTextArea notes;
    private MouseAdapter mouseListener;
    
    /* Constructors */
    /**
     * Creates a panel for the functions of the agenda
     * 
     * @param function the function of the panel
     * @param dHeight the base height of the panel
     * @param dWidth the width of the panel
     * @param buttonNum the number for the <code>AddItemButton</code>
     * @param mouseListener the eventlistener for the components
     */
    public FunctionPanel(String function, double dHeight, double dWidth, int buttonNum, MouseAdapter mouseListener) {
        // Initializes Variables
        dayPanelHeight = dHeight;
        dayPanelWidth = dWidth;
        functionLabel = new JLabel(function, SwingConstants.CENTER);
        addNewItem = new AddItemButton("+Add", buttonNum);
        this.mouseListener = mouseListener;
        initializeComponents();
        
        // Creates the <code>FunctionPanel</code>
        this.add(functionLabel, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(addNewItem, BorderLayout.PAGE_END);
    } // end of constructor FunctionPanel(String function, double dHeight, double dWidth, int buttonNum, MouseAdapter mouseListener)
    
    /**
     * Creates a panel for the functions of the agenda
     * 
     * @param dHeight the base height of the panel
     * @param dWidth the width of the panel
     */
    public FunctionPanel(double dHeight, double dWidth) {
        // Initializes Variables
        dayPanelHeight = dHeight;
        dayPanelWidth = dWidth;
        functionLabel = new JLabel("Notes", SwingConstants.CENTER);
        addNewItem = null;
        initializeComponents();
        
        // Creates the <code>FunctionPanel</code>
        scrollPane = new JScrollPane(notes);
        this.add(functionLabel, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
    } // end of constructor FunctionPanel(double dHeight, double dWidth)

    /* Private methods for constructing the panels */
    /*
     * Makes initializes the components
     */
    private void initializeComponents() {
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension((int) dayPanelWidth, (int) dayPanelHeight));
        components = new ArrayList<>();

        functionPanel = new JPanel();
        functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(functionPanel);
        scrollPane.setPreferredSize(new Dimension((int) dayPanelWidth, (int) dayPanelHeight));

        notes = new JTextArea();
        notes.setPreferredSize(new Dimension((int) dayPanelWidth, (int) dayPanelHeight));
    } // end of private method initializeComponents()
    
    /* Accessor Methods */
    /**
     * Returns the <code>addNewItem</code> button
     * @return the <code>addNewItem</code> button
     */
    public JButton getButton() {
        return addNewItem;
    } // end of method 
    
    /**
     * Returns the <code>arraylist</code> of the function panel
     * @return the <code>arraylist</code> of the function panel
     */
    public ArrayList getArrayList() {
        return components;
    } // end of method ArrayList getArrayList()
    
        
    /**
     * Returns the <code>notes</code>
     * @return the <code>notes</code>
     */
    public JTextArea getNotes() {
        return notes;
    } // end of method JTextArea getNotes()
    
    /**
     * Returns the size of <code>arraylists</code> of the specific function panel
     * @return the size of <code>arraylists</code> of the specific function panel
     */
    public int getArrayListSize() {
        return components.size();
    } // end of method 
    
    public JComponent getActivity(int index) {
        return components.get(index);
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
        components.add(component);
        functionPanel.add(components.get(components.size()-1));
        if (usedByButton) {
            // Remakes the panel
            this.revalidate();
            this.repaint();
        } // end of if (usedByButton)
    } // end of method void addItem(int mainContainer, JComponent[] component, boolean usedByButton)
    
    /**
     * Sets the <code>ActionListener</code> for the <code>addNewItem</code> button
     * @param actionListener the <code>ActionListener</code> for the <code>addNewItem</code> button
     */
    public void setActionListener(ActionListener actionListener) {
        addNewItem.addActionListener(actionListener);
    } // end of method void setActionListener(ActionListener actionListener)
    
    /**
     * Sets the text for the <code>notes</code>     
     * @param text the text for the <code>notes</code>
     */
    public void setText(String text) {
        notes.setText(text);
        this.revalidate();
        this.repaint();
    } // end of method 
    
    /**
     * Removes an activity from the function panel  
     * @param index the index of the activity that is being removed
     */
    public void removeActivity(int index) {
        functionPanel.remove(components.get(index));
        components.remove(index);
        components.add(index, null);
        System.out.println();
        this.revalidate();
        this.repaint();
    } // end of method 
    
    /**
     * Changes the text of the specified component in the specified function panel
     * 
     * @param type the type of component that is being removed. 0 if it is a <code>ToDoCheckBox</code> 
     * and 1 if it is a <code>ImpDateLabel</code> 
     * @param index the index of the activity in this function panel
     * @param text the new text for the component
     */
    public void editActivity(int type, int index, String text) {
        if (type == 0)
            ((ToDoCheckBox) components.get(index)).setText(text);
        else if (type == 1)
            ((ImpDateLabel) components.get(index)).setText(text);
        // end of if (type == 0)
    } // end of method void editActivity(int type, int index, String text)
}
