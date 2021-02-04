package ics_planner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * A panel that contains the agenda of a given day in the year
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Agenda extends Planner {

    /* Constants */
    private static final String IS_CHECKED = "*-%&ISCHECKED&%-*";
    private static final String IS_NOT_CHECKED = "*-%&ISNOTCHECKED&%-*";
    private static final String IS_LABEL= "*-%&LABEL&%-*";
   
    /* Class Variables */
    private static int day;
    private static Month month;
    private static ActionListener actionListener;
    private static MouseAdapter mouseListener;
    private static String path;

    /* Instance Variables */
    private final String fileName;
    
    // Variables for Top Panel
    private JPanel topPanel;
    private JLabel dateLabel;
    private JLabel monthName;

    // Variables for Centre Panel
    private JPanel centrePanel;
    private JPanel toDoAndImpDates;
    private FunctionPanel[] functions;
    
    /* Constructors */
    /**
     * Creates an agenda of a specific day
     */
    public Agenda() {
        // Initializes Variables
        fileName  = path + "Agenda\\" + day + " " + month.getMonthNumber() + " " + month.getYear() + ".txt";
        
        // Creates the calendar panel.
        makeMainPanel();
    } // end of constructor Agenda()
    
    /**
     * Creates an agenda given the day, the month and the year
     * 
     * @param dayNumber the day
     * @param monthNumb the month
     * @param year the year
     * @param a the <code>ActionListener</code> for the <code>functionPanel's add</code> button
     * @param m the <code>MouseAdapter</code> for the <code>functionPanel's components</code> 
     * @param path the path for the files
     */
    public Agenda(int dayNumber, int monthNumb, int year, ActionListener a, MouseAdapter m, String path) {
        // Initializes Variables
        month = new Month(monthNumb, year);
        day = dayNumber;
        fileName  = path + "Agenda\\" + dayNumber + " " + monthNumb + " " + year + ".txt";
        this.path = path;
        actionListener = a;
        mouseListener = m;

        // Create a frame.
        makeMainPanel();
    } // end of constructor Agenda(int dayNumber, int monthNumb, int year, double sHeight, double sWidth)

    /* Private methods for constructing the panels */
    /*
     * Makes the main panel
     */
    private void makeMainPanel() {
        this.setLayout(new BorderLayout());

        makeCentrePanel();
        this.add(centrePanel, BorderLayout.CENTER);

        makeTopPanel();
        this.add(topPanel, BorderLayout.PAGE_START);
    } // end of private method void makeMainPanel()
    
    /*
     * Makes the top panel
     */
    private void makeTopPanel() {
        // Creates the label for the month and date
        monthName = new JLabel(month.getMonthName(0) + " " + month.getYear());
        monthName.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateLabel = new JLabel(month.getMonthName(1) + " " + day + ", " + month.getYear());
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Makes the top panel and adds the components to the top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(monthName);
        topPanel.add(dateLabel);
    } // end of private method void makeTopPanel()
    
    /*
     * Makes the centre panel
     */
    private void makeCentrePanel() {
        // Makes the function panels
        functions = new FunctionPanel[3];
      
        functions[0] = new FunctionPanel("To-Do List", BASE_HEIGHT, BASE_WIDTH, -1, mouseListener);
        functions[1] = new FunctionPanel("Important Dates", BASE_HEIGHT, BASE_WIDTH, -2, mouseListener);
        functions[2] = new FunctionPanel(BASE_HEIGHT, BASE_WIDTH * 2);
        
        functions[0].setActionListener(actionListener);
        functions[1].setActionListener(actionListener);
        try {
            // Opens and reads the file
            File file = new File(fileName);
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
           
            // Reads the lines for the notes
            String note = "";
            while ((line = br.readLine()) != null && !(line.equals(NEW_FUNCTION))) {  
                note = note + line + "\n";
            }// end of while ((line = br.readLine()) != null && !(line.equals(NEW_FUNCTION)))
            functions[2].setText(note);
            
            // Reads the lines for the to do list and important dates
            while ((line = br.readLine()) != null) {
                // Checks if the check box was clicked or not and adds the to do things
                if (line.substring(0, IS_CHECKED.length()).equals(IS_CHECKED)) {
                    int index = functions[0].getArrayListSize();
                    functions[0].addNewItem(new ToDoCheckBox(index, line.substring(IS_CHECKED.length() + 1), true), false);
                }
                else if (line.substring(0, IS_NOT_CHECKED.length()).equals(IS_NOT_CHECKED)) {
                    int index = functions[0].getArrayListSize();
                    functions[0].addNewItem(new ToDoCheckBox(index, line.substring(IS_NOT_CHECKED.length() + 1), false), false);
                } 
                else if (line.substring(0, IS_LABEL.length()).equals(IS_LABEL)) {
                    int index = functions[1].getArrayListSize();
                    functions[1].addNewItem(new ImpDateLabel(-2, index, line.substring(IS_LABEL.length() + 1)), false);
                } // end of if (line.substring(0, IS_CHECKED.length()).equals(IS_CHECKED))                   
            } // end of while ((line = br.readLine()) != null)
        }
        catch (Exception FileNotFoundException) {

        }
       
        // Makes the toDoAndImpDates panel and adds the component to the centre panel
        toDoAndImpDates = new JPanel();
        toDoAndImpDates.setLayout(new BoxLayout(toDoAndImpDates, BoxLayout.Y_AXIS));
        toDoAndImpDates.add(functions[0]);
        toDoAndImpDates.add(functions[1]);
       
        // Makes the centre panel and adds the components to the centre panel
        centrePanel = new JPanel();
        centrePanel.setLayout(new BorderLayout());
        centrePanel.add(toDoAndImpDates, BorderLayout.LINE_START);
        centrePanel.add(functions[2], BorderLayout.CENTER);
    } // end of private method makeCentrePanel()
    
    /* Accessor Methods */
    /**
     * Returns the size of <code>arraylists</code> of the specific function panel
     * @param index the index of the function panels. 0 for the to do list. 1 for the important dates.
     * @return the size of <code>arraylists</code> of the specific function panel
     */
    @Override
    public int getArrayListSize(int index) {
        return functions[index].getArrayListSize();
    } // end of method int getArrayListSize(int index)
    
    /**
     * Returns the month number and year of this agenda
     * @return the month number and year of this agenda in a string array
     */
    @Override
    public String[] getDate() {
        String[] date = new String[2];
        date[0] = "" + month.getMonthNumber();
        date[1] = "" + month.getYear();
        return date;
    } // end of method String[] getDate()
    
    /**
     * Returns the month of this agenda
     * @return the month of this agenda
     */
    @Override
    public Month getMonth() {
        return month;
    } // end of method Month getMonth()
    
    /**
     * Returns next month's Agenda
     * @return next month's Agenda
     */
    @Override
    public Agenda Next() {
        day = day + 1;
        if (day > month.getDays()) {
            day = 1;
            month = month.nextMonth();
        } // end of if (day <= month.getDays())
        return new Agenda();
    } // end of method Agenda Next()
    
    /**
     * Returns the previous month's Agenda
     * @return the previous month's Agenda
     */
    @Override
    public Agenda Prev() {
        day = day - 1;
        if (day < 1) {
            month = month.prevMonth();
            day = month.getDays();
        } // end of if (day <= month.getDays())
        return new Agenda();
    } // end of method Agenda Prev()
    
    /* Mutator Methods */
    /**
     * Adds a component to the specific function panel
     * 
     * @param index the index of the function panels. 0 for the to do list. 1 for the important dates.
     * @param component the component that is being added to the specific function panel
     */
    @Override
    public void addNewItem(int index, JComponent component) {
        functions[index].addNewItem(component, true);
    } // end of method void addNewItem(int index, JComponent component)
    
    /**
     * Changes the text of the specified component in the specified function panel
     * 
     * @param function the index of the function panels. 0 for the to do list. 1 for the important dates.
     * @param index the index of the component in the function panel
     * @param text the new text for the component
     */
    @Override
    public void editActivity(int function, int index, String text) {
        functions[function].editActivity(function, index, text);
    } // end of method void editActivity(int function, int index, String text)
    
    /**
     * Removes the specified component in the specified function panel
     * 
     * @param function the index of the function panels. 0 for the to do list. 1 for the important dates.
     * @param index the index of the component in the function panel
     */
    @Override
    public void removeActivity(int function, int index) {
        functions[function].removeActivity(index);
    } // end of method void removeActivity(int function, int index)
    
    /**
     * Returns the specified component in the specified function panel
     * 
     * @param function the index of the function panels. 0 for the to do list. 1 for the important dates.
     * @param index the index of the component in the function panel
     * @return the specified component in the specified function panel
     */
    @Override
    public JComponent getActivity(int function, int index) {
        return functions[function].getActivity(index);
    } // end of method
    
    /* Other Methods */
    /**
     * Saves the agenda by creating/opening a file and writing down all the activities (the JComponents)
     * in each of the dayPanels
     */
    @Override
    public void Save() {
        try {
            // Opens the folder Agenda or makes a new folder if it doesn't exist
            File file = new File(path + "Agenda");
            file.mkdir();
           
            // Opens the file
            FileWriter write = new FileWriter(fileName, false);
            PrintWriter printLine = new PrintWriter(write);
                                  
            printLine.println(functions[2].getNotes().getText());
            
            // Adds a break/ sperator
            printLine.println(NEW_FUNCTION);
            
            // Prints the items in the to do list and whether or not the items were checked
            for (int i = 0; i < functions[0].getArrayList().size(); i = i + 1) {
                if (functions[0].getArrayList().get(i) != null) {
                    ToDoCheckBox temp = (ToDoCheckBox) functions[0].getArrayList().get(i);
                    if (temp.isSelected()) {
                        printLine.print(IS_CHECKED + " ");
                    }
                    else {
                        printLine.print(IS_NOT_CHECKED + " ");
                    } // end of if (toDoChecks.get(i).isSelected())
                    printLine.println(temp.getText());
                } // end of if (functions[0].getArrayList().get(i) != null)
            } // end of for (int i = 0; i < toDoLabels.size(); i = i + 1)
           
            // Prints the important dates onto the file
            for (int i = 0; i <  functions[1].getArrayList().size(); i = i + 1) {
                if (functions[1].getArrayList().get(i) != null) {
                    JLabel temp = (JLabel) functions[1].getArrayList().get(i);
                    printLine.println(IS_LABEL + " " + temp.getText());
                } // end of if (functions[1].getArrayList().get(i) != null)
            } // end of for (int i = 0; i <  functions[1].getArrayList().size(); i = i + 1)
           
            // Closes the file
            printLine.close();
        }
        catch (Exception IOException) {
            new ErrorOptionPane("Something went wrong with the file."
                            + "\nPlease try again.", "File Error");
        } // end of try-catch
    } // end of method Save()
    
    private class ErrorOptionPane {
        /* Instance Variables */
        JFrame frame;

        ErrorOptionPane(String errorMessage, String typeOfError) {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, errorMessage, typeOfError, JOptionPane.ERROR_MESSAGE);
        } // end of constructor OptionPaneExample(String errorMessage)
    } // end of private class OptionPaneExample
} // end of public class Agenda extends Planner