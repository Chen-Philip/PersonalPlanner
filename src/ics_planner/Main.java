package ics_planner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * The main frame of the program
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Main {
    /* Constants */
    private static final int GRID_GAP = 5;
    private static final String IS_LABEL= "*-%&LABEL&%-*";
    private static final String NEW_FUNCTION = "=========================================================================";
    
    /* Instance Variables */
    private static double baseHeight;
    private static double baseWidth;
    private static String path;
    
    // General Variables
    private Planner calendar;
    private Planner agenda;
    private Planner currentPlanner;
    private boolean isPrevButtonRemoved;
    
    private JPopupMenu popup;
    private JMenuItem edit;
    private JMenuItem remove;
    
    // Variables for the Main Frame
    private JFrame mainFrame;
    private JButton exit;
    private Start start;
    
    // Variables for Centre Panel
    private JPanel centrePanel;
    private JButton nextPlanner;
    private JButton prevPlanner;
    
    private JPanel plannerPanel;
    private JButton calenderButton;
    private JButton agendaButton;
    
    /* Constructors */
    /**
     * Creates the main frame of the program
     * @param username the username of the user
     * @param bHeight the base height of all panels
     * @param bWidth the base width of all panels
     */
    public Main(String username, double bHeight, double bWidth) {
        // Initializes instance variables
        path = "Users\\" + username + "\\";
        baseHeight = bHeight;
        baseWidth = bWidth;
        calendar = new Calendar(2, 2119, new AddThingButtonListener(), new RightClickListeners(), path);
        agenda = new Agenda(20, 2, 2119, new AddThingButtonListener(), new RightClickListeners(), path);
        currentPlanner = calendar;
        makePopUp();
                
        // Creates a frame.
        makeFrame();
        start = null;
    } // end of constructor public Main()
    
    /* Private methods for constructing the panels */
    /*
     * Makes the frame
     */
    private void makeFrame() {
        // Creates the frame
        mainFrame = new JFrame("Planner");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
       
        // Creates and adds the centre panel
        makeCentrePanel();
        mainFrame.add(centrePanel, BorderLayout.CENTER);

        // Finishes creating the frame.
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
    } // end of private method 
    
    /*
     * Makes the centre panel
     */
    private void makeCentrePanel() {
        // Local Variables
        PrevNextMonthButtonListener actionListener = new PrevNextMonthButtonListener();
        PlannerButtonListener buttonListener = new PlannerButtonListener();
        
        
        // Creates the centre panel
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        
        // Creates the planner panel
        plannerPanel = new JPanel();
        plannerPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, GRID_GAP, GRID_GAP));
        
        // Creates the buttons for the planner panel
        calenderButton = new JButton("Calender");
        calenderButton.addActionListener(buttonListener);
        agendaButton = new JButton("Agenda");
        agendaButton.addActionListener(buttonListener);
        
        // Adds the buttons to the planner panel
        plannerPanel.add(calenderButton);
        plannerPanel.add(agendaButton);
        
        // Creates the previous and next buttons
        nextPlanner = new JButton(">");
        nextPlanner.addActionListener(actionListener);
        prevPlanner = new JButton("<");
        prevPlanner.addActionListener(actionListener);
        
        // Creates the exit button
        exit = new JButton("Quit");
        exit.addActionListener(actionListener);
        
        // Adds the componenets to the centre panel
        centrePanel.add(plannerPanel, BorderLayout.PAGE_START);
        centrePanel.add(currentPlanner, BorderLayout.CENTER);
        centrePanel.add(exit, BorderLayout.PAGE_END);
        centrePanel.add(nextPlanner, BorderLayout.LINE_END);
        
        // Adds the previous button if the month isn't September 2019
        if (calendar.getMonth().getMonthNumber() != 9 || calendar.getMonth().getYear() != 2019)
        {
            centrePanel.add(prevPlanner, BorderLayout.LINE_START);
            isPrevButtonRemoved = false;
        } // end of if (calendar.getMonth().getMonthNumber() != 9 || calendar.getMonth().getYear() != 2019)
    } // end of private method void makeCentrePanel()
    
    /*
     * Makes the pop up menu
     */
    private void makePopUp() {
        popup = new JPopupMenu();
        edit = new JMenuItem("Edit");
        remove = new JMenuItem("Remove");
        
        popup.add(edit);
        popup.add(remove);
    } // end of private method
    
    /* Methods */
    /**
     * Returns the next day given the date
     * @param date the date
     * @return the next day given the date
     */
    private String getNextDate(String date) {
        // Splits the date into the day, the month, the year
        String[] dates = date.split(" ");
        int[] dateNum = {Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])};
        Month month = new Month(dateNum[1], dateNum[2]);
        
        // Finds the next date
        if (dateNum[0] + 1 > month.getDays()) {
            dateNum[0] = 1;
            month = month.nextMonth();
        } else {
            dateNum[0] = dateNum[0] + 1;
        }
        return dateNum[0] + " " + month.getMonthNumber() + " " + month.getYear();
    } // end of method

    /* private classes */
    private class PrevNextMonthButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            
            // Tells which button is pressed
            if (source == nextPlanner) {
                // Removes the current calandar and replaces it with the next month's calender                
                centrePanel.remove(currentPlanner);
                currentPlanner.Save();
                currentPlanner = currentPlanner.Next();
                centrePanel.add(currentPlanner, BorderLayout.CENTER);
                
                // Adds the previous button if it was removed
                if (isPrevButtonRemoved) {
                    centrePanel.add(prevPlanner, BorderLayout.LINE_START);
                } // end of if (isPrevButtonRemoved)
                
                // Remakes the panel
                centrePanel.revalidate();
                mainFrame.pack();
            } 
            else if (source == prevPlanner) {
                // Removes the current calandar and replaces it with the previous month's calender                
                centrePanel.remove(currentPlanner);
                currentPlanner.Save();
                currentPlanner = currentPlanner.Prev();
                centrePanel.add(currentPlanner, BorderLayout.CENTER);
                
                // Removes the previous button if the month is September 2019
                if (calendar.getMonth().getMonthNumber() == 9 && calendar.getMonth().getYear() == 2019) {
                    centrePanel.remove(prevPlanner);
                    isPrevButtonRemoved = true;
                } // end of if (calendar.getMonth().getMonthNumber() == 9 && calendar.getMonth().getYear() == 2019)
                
                // Remakes the panel
                centrePanel.revalidate();
                mainFrame.pack();
            } 
            else if (source == exit) {
                currentPlanner.Save();
                System.exit(0);
            } // end of if (source == nextMonth)
        } // end of method actionPerformed(ActionEvent event)
    } // end of class ButtonListener
    
    private class PlannerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            
            // Tells which button is pressed
            if (source == calenderButton) {
                // Removes the current planner and replaces it with a calendar
                savePlanner();
                currentPlanner = calendar;
                // Remakes the panel
                remakePanel();
                centrePanel.add(currentPlanner, BorderLayout.CENTER);
                mainFrame.pack();
                
            } // end of if (source == calenderButton)
            else if (source == agendaButton)
            {
                // Removes the current planner and replaces it with an agenda
                savePlanner();
                currentPlanner = agenda;
                // Remakes the panel
                remakePanel();
                centrePanel.add(currentPlanner, BorderLayout.CENTER);
                mainFrame.pack();
            }
        } // end of method actionPerformed(ActionEvent event)

        private void savePlanner() {
            currentPlanner.Save();
            if (currentPlanner.getClass().getName().equals("Calendar"))
                 calendar = currentPlanner;
            else if (currentPlanner.getClass().getName().equals("Agenda")) {
                agenda = currentPlanner;
            }
            // end of if (currentPlanner.getClass().getName().equals("Calendar"))
            centrePanel.remove(currentPlanner);
        } // end of private method void savePlanner()
        
        private void remakePanel() {
            currentPlanner = currentPlanner.Prev();
            currentPlanner = currentPlanner.Next();
            centrePanel.revalidate();
            centrePanel.repaint();
        } // end of private method void remakePanel()
    } // end of class ButtonListener
    
    private class RightClickListeners extends MouseAdapter{
        ToDoCheckBox comp1 = new ToDoCheckBox();
        ImpDateLabel comp2 = new ImpDateLabel();

        boolean isAdded = false;

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (!isAdded) {
                    // Adds the actionListeners to the edit and remove menuitems if they werent added
                    edit.addActionListener(new EditMenuListener());
                    remove.addActionListener(new EditMenuListener());
                    isAdded = true;
                }
                popup.show(e.getComponent(), e.getX(), e.getY());
                
                // Initializes comp 1 and comp2 to the proper classes

                if (e.getComponent().getClass() == comp1.getClass()) {
                    comp1 = (ToDoCheckBox) e.getComponent();
                } else if (e.getComponent().getClass() == comp2.getClass()) {
                    comp2 = (ImpDateLabel) e.getComponent();
                }
            } // end of if
        } // end of method void actionPerformed(ActionEvent event)

        private class EditMenuListener implements ActionListener {
            String name = "";
            ActionFrame frame;

            public void actionPerformed(ActionEvent event) {
                JMenuItem source = (JMenuItem) event.getSource();
                if (source == edit) {
                    if (comp1.getType() == -1) {
                        // If the component is in the Agenda's to-do list
                        mainFrame.setEnabled(false);
                        frame = new ActionFrame(true, comp1.getType());
                        frame.setActionListener(new EditButtonListener());
                    } else if (comp2.getType() == -2) {
                        // If the component is in the Agenda's important date list
                        mainFrame.setEnabled(false);
                        frame = new ActionFrame(true, comp2.getType());
                        frame.setActionListener(new EditButtonListener());
                    } else if (comp2.getType() > 0) {
                        // If the component is in the Calendar's day Panel
                        int frameNum = comp2.getType();
                        mainFrame.setEnabled(false);

                        // Opens the required action frame
                        ImpDateLabel temp = (ImpDateLabel) currentPlanner.getActivity(frameNum, comp2.getIndex());
                        String[] dates = temp.getDates();
                        if (dates.length == 0) {
                            frame = new ActionFrame(true, false, comp2.getType());
                        } else {
                            frame = new ActionFrame(true, true, comp2.getType());
                        }
                        frame.setActionListener(new EditButtonListener());
                    }
                } else if (source == remove) {
                    mainFrame.setEnabled(false);
                    if (comp1.getType() == -1) {
                        // If the component is in the Agenda's to-do list
                        frame = new ActionFrame(false, comp1.getType());
                        frame.setActionListener(new RemoveButtonListener());
                    } else if (comp2.getType() == -2) {
                        // If the component is in the Agenda's important date list
                        frame = new ActionFrame(false, comp2.getType());
                        frame.setActionListener(new RemoveButtonListener());
                    } else if (comp2.getType() > 0) {
                        // If the component is in the Calendar's day Panel
                        int frameNum = comp2.getType();
                        mainFrame.setEnabled(false);

                        // Opens the required action frame
                        ImpDateLabel temp = (ImpDateLabel) currentPlanner.getActivity(frameNum, comp2.getIndex());
                        String[] dates = temp.getDates();
                        if (dates.length == 0) {
                            frame = new ActionFrame(false, false, comp2.getType());
                        } else {
                            frame = new ActionFrame(false, true, comp2.getType());
                        }
                        frame.setActionListener(new RemoveButtonListener());
                    } // end of if 
                } // end of if (source == edit)
            } // end of method void actionPerformed(ActionEvent event)

            private class RemoveButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent event) {
                    JButton source = (JButton) event.getSource();
                    if (source == frame.getButton(0)) {
                        name = frame.getText();
                        if (frame.getFrameNumber() == -1) {
                            // If action frame is called by the Agenda's to-do list
                            currentPlanner.removeActivity(0, comp1.getIndex());
                            enableMainFrame();
                            comp1.reset();
                        } else if (frame.getFrameNumber() == -2) {
                            // If action frame is called by the Agenda's important date list
                            currentPlanner.removeActivity(1, comp2.getIndex());
                            enableMainFrame();
                            comp2.reset();
                        } else if (frame.getFrameNumber() > 0) {
                            // If action frame is called by a day Panel
                            int frameNum = frame.getFrameNumber();
                            ImpDateLabel temp = (ImpDateLabel) currentPlanner.getActivity(frameNum, comp2.getIndex());
                            String[] dates = temp.getDates();
                            String date = currentPlanner.getDate()[0] + "/" + frameNum + "/" + currentPlanner.getDate()[1];
                            if (dates.length == 0) {
                                currentPlanner.removeActivity(frameNum, comp2.getIndex());
                            } else if (dates.length == 1) {
                                editRemoveFromAgenda(false, date, temp.getText(), name, dates[0], dates[0]);
                                currentPlanner.removeActivity(frameNum, comp2.getIndex());
                            } else if (dates.length == 2) {
                                editRemoveFromAgenda(false, date, temp.getText(), name, dates[0], dates[1]);
                                currentPlanner.removeActivity(frameNum, comp2.getIndex());
                            }
                            enableMainFrame();
                            comp2.reset();
                        } // end of if 
                    } else if (source == frame.getButton(1)) {
                        enableMainFrame();
                    } // end of if 
                } // end of method void actionPerformed(ActionEvent event)
            } // end of private class 

            private class EditButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent event) {
                    JButton source = (JButton) event.getSource();
                    if (source == frame.getButton(0)) {
                        name = frame.getText();
                        if (frame.getFrameNumber() == -1) {
                            // If action frame is called by the Agenda's to-do list
                            currentPlanner.editActivity(0, comp1.getIndex(), name);
                            enableMainFrame();
                            comp1.reset();
                        } else if (frame.getFrameNumber() == -2) {
                            // If action frame is called by the Agenda's important list
                            if (frame.isValidDates() == 0) {
                                // Sees if the date is valid
                                String date = frame.getLabelDate(0);
                                currentPlanner.editActivity(1, comp2.getIndex(), name + " - " + date);
                                enableMainFrame();
                                comp2.reset();
                            } else if (frame.isValidDates() == 1) {
                                new ErrorOptionPane("The date entered was not valid."
                                        + "\nPlease enter a valid date", "Date Error");
                            }
                        } else if (frame.getFrameNumber() > 0) {
                            // If action frame is called by a day Panel
                            int frameNum = frame.getFrameNumber();
                            ImpDateLabel temp = (ImpDateLabel) currentPlanner.getActivity(frameNum, comp2.getIndex());
                            String[] dates = temp.getDates();
                            String date = currentPlanner.getDate()[0] + "/" + frameNum + "/" + currentPlanner.getDate()[1];
                            if (dates.length == 0) {
                                currentPlanner.editActivity(frameNum, comp2.getIndex(), name);
                            } else if (dates.length == 1) {
                                editRemoveFromAgenda(true, date, temp.getText(), name, dates[0], dates[0]);
                                currentPlanner.editActivity(frameNum, comp2.getIndex(), name);
                            } else if (dates.length == 2) {
                                editRemoveFromAgenda(true, date, temp.getText(), name, dates[0], dates[1]);
                                currentPlanner.editActivity(frameNum, comp2.getIndex(), name);
                            }
                            enableMainFrame();
                            comp2.reset();
                        } // end of if 
                    } else if (source == frame.getButton(1)) {
                        enableMainFrame();
                    } // end of if 
                } // end of method void actionPerformed(ActionEvent event)
            } // end of private class EditButtonListener

            private void enableMainFrame() {
                mainFrame.setEnabled(true);
                frame.dispose();
            }

            private void editRemoveFromAgenda(boolean isEdit, String date, String activity, String newAc, String fDate, String tDate) {
                try {
                    // Opens the folder Agenda or makes a new folder if it doesn't exist
                    File file = new File(path + "Agenda");
                    file.mkdir();

                    String fileName = path + "Agenda\\" + fDate + ".txt";
                    file = new File(fileName);

                    String line;
                    String newText = "";
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    
                    // Searched for the specific activity
                    while ((line = br.readLine()) != null) {
                        if (line.length() >= IS_LABEL.length() + 1) {
                            String labelText = line.substring(IS_LABEL.length() + 1);
                            // Either edits or removes the activity when found
                            if (!labelText.equals(activity + " - " + date))
                                newText = newText + line;
                            else {
                                if (isEdit)
                                    newText = newText + IS_LABEL + " " + newAc + " - " + date;
                            }
                        } 
                        else
                            newText = newText + line;
                        newText = newText + "\n";
                    }// end of while ((line = br.readLine()) != null && !(line.equals(NEW_FUNCTION)))

                    // Closes the file
                    br.close();
                    
                    // Rewrites the file with the new text that has the changes
                    FileWriter write = new FileWriter(file, false);
                    PrintWriter printLine = new PrintWriter(write);
                    printLine.print(newText);
                    printLine.close();
                } catch (Exception e) {
                    new ErrorOptionPane("Something went wrong with the file."
                            + "\nPlease try again.", "File Error");
                    return;
                }
                if (!fDate.equals(tDate)) {
                    editRemoveFromAgenda(isEdit, date, activity, newAc, getNextDate(fDate), tDate);
                }
            } // end of method void editRemoveFromAgenda
        } // end of private class EditMenuListener
    } // end of private class RightClickListener
    private class AddThingButtonListener implements ActionListener {
        String name = "";
        ActionFrame frame; 
        public void actionPerformed(ActionEvent event) {
            AddItemButton source = (AddItemButton) event.getSource();
            AddFrameButtonListener actionListener = new AddFrameButtonListener();
                // Tells which button is pressed
                if (source.getButtonNumber() == -1) {
                    mainFrame.setEnabled(false);
                    frame = new ActionFrame(source.getButtonNumber());
                    frame.setActionListener(actionListener);
                }
                else if (source.getButtonNumber() == -2) {
                    // Creates an option pane
                    mainFrame.setEnabled(false);
                    frame = new ActionFrame(source.getButtonNumber());
                    frame.setActionListener(actionListener);
                } // end of if (source == addNewItem[0])
                else if (source.getButtonNumber() > 0) {
                    
                    // Creates an option pane
                    mainFrame.setEnabled(false);
                    frame = new ActionFrame(source.getButtonNumber());
                    frame.setActionListener(actionListener);
                }
        } // end of method void actionPerformed(ActionEvent event)

        private class AddFrameButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                JButton source = (JButton) event.getSource();
                if (source == frame.getButton(0)) {
                    name = frame.getText();
                    if (frame.getFrameNumber() == -1) {
                        // If action frame is called by the Agenda's to-do list
                        int index = currentPlanner.getArrayListSize(0);
                        
                        // Adds the item to the to-do list
                        currentPlanner.addNewItem(0, new ToDoCheckBox(index,name,false));
                        enableMainFrame();
                    }
                    else if (frame.getFrameNumber() == -2) {
                        // If action frame is called by the Agenda's important date list
                        if (frame.isValidDates() == 0) {
                            // Sees if the date is valid
                            int index = currentPlanner.getArrayListSize(1);
                            String date = frame.getLabelDate(0);
                            
                            // Adds the item to the important date list
                            currentPlanner.addNewItem(1, new ImpDateLabel(-2,index, name + " - " + date));
                            enableMainFrame();
                        }
                        else if (frame.isValidDates() == 1) {
                            new ErrorOptionPane("The date entered was not valid."
                                    + "\nPlease enter a valid date", "Date Error");
                        }
                    }
                    else if (frame.getFrameNumber() > 0){
                        // If action frame is called by a day panel
                        int index = currentPlanner.getArrayListSize(frame.getFrameNumber());
                        int frameNum = frame.getFrameNumber();
                        String date = currentPlanner.getDate()[0] + "/" + frameNum + "/" + currentPlanner.getDate()[1];
                        if (frame.isSameDate()) {
                            // if the activity is added to the same date in the agenda
                            String toFromDate = frameNum + " " + currentPlanner.getDate()[0] + " " + currentPlanner.getDate()[1];
                            addToAgenda(name, date, toFromDate, toFromDate);
                            currentPlanner.addNewItem(frameNum, new ImpDateLabel(frameNum, index, name, toFromDate));
                            enableMainFrame();
                        }
                        else if (frame.isAddToAgendaPanelVisible()) {
                            // if the activity is added to multiple dates
                            if (frame.isValidDates() == 0) {
                                // Sees if the date is valid
                                String fromDate = frame.getFileDate(0);
                                String toDate = frame.getFileDate(1);
                                String[] dates = new String[2];
                                dates[0] = fromDate;
                                dates[1] = toDate;
                                addToAgenda(name, date, fromDate, toDate);
                                currentPlanner.addNewItem(frameNum, new ImpDateLabel(frameNum, index, name, dates));
                                enableMainFrame();
                            }
                            else if (frame.isValidDates() == 1) {
                                new ErrorOptionPane("The date entered was not valid."
                                        + "\nPlease enter a valid date", "Date Error");
                            }
                            else if (frame.isValidDates() == 2) {
                                new ErrorOptionPane("The second date was smaller than the first."
                                        + "\nPlease enter a valid date", "Date Error");
                            }
                        }
                        else {
                            // if the activity isn't added to the agenda
                            currentPlanner.addNewItem(frame.getFrameNumber(), new ImpDateLabel(frameNum, index, name));
                            enableMainFrame();
                        }
                    }
                }
                else if (source == frame.getButton(1))
                    enableMainFrame();
            } // end of method void actionPerformed(ActionEvent event)
            
            private void enableMainFrame() {
                mainFrame.setEnabled(true);
                frame.dispose();
            }
            private void addToAgenda(String activity, String date, String fromDate, String toDate) {
                try {
                    // Opens the folder Agenda or makes a new folder if it doesn't exist
                    File file = new File(path + "Agenda");
                    file.mkdir();
                    String fileName = path + "Agenda\\" + fromDate + ".txt";
                    // Opens the file
                    FileWriter write = new FileWriter(fileName, true);
                    PrintWriter printLine = new PrintWriter(write);
                    if (new File(fileName).length() == 0)
                        printLine.println(NEW_FUNCTION);
                    printLine.println(IS_LABEL + " " + activity + " - " + date);
                    
                    // Closes the file
                    printLine.close();
                } catch (Exception FileNotFoundException) {
                    new ErrorOptionPane("Something went wrong with the file."
                            + "\nPlease try again.", "File Error");
                    return;
                }
                if (!fromDate.equals(toDate)) {
                   addToAgenda(activity, date, getNextDate(fromDate), toDate);
                }
            } // end of method void addToAgenda
        } // end of private class AddFrameButtonListener
    } // end of private class AddThingButtonListener implements ActionListener
    
    private class ErrorOptionPane {
        /* Instance Variables */
        JFrame frame;

        ErrorOptionPane(String errorMessage, String typeOfError) {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, errorMessage, typeOfError, JOptionPane.ERROR_MESSAGE);
        } // end of constructor OptionPaneExample(String errorMessage)
    } // end of private class OptionPaneExample
}
