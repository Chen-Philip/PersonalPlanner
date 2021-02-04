package ics_planner;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.*;
import javax.swing.*;

/**
 * A panel that contains the calendar of a month in the year
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Calendar extends Planner{

    /* Constants */
    private static final int NUMBER_OF_ROWS = 5;
    private static final String DAY_INDICATOR_L = "*-%&";
    private static final String DAY_INDICATOR_R = "&%-*";

    /* Class Variables */
    private static Month month;
    private static ActionListener actionListener;
    private static MouseAdapter mouseListener;
    private static String path;
    
    /* Instance  Variables */
    private final String fileName;
    private int daysBlank;
    // Variables for Top Panel
    private JPanel topPanel;
    private JLabel monthName;
    private JPanel weekdayPanel;
    private JLabel[] weekday;

    // Variables for Centre Panel
    private JPanel centrePanel;
    private DayPanel[] dayPanel;
    
    /* Constructors */
    /**
     * Creates a calendar of a specific month
     */
    public Calendar() {
        // Initializes Variables
        fileName  = path + "Calendar\\" + month.getMonthNumber() + " " + month.getYear() + ".txt";

        
        // Creates the calendar panel.
        makeCalendarPanel();
    } // end of constructor Calendar(int monthNumb, int year)
    
    /**
     * Creates a calendar given the month and year
     * 
     * @param monthNumb the number of the month
     * @param year the year
     * @param a the <code>ActionListener</code> for the dayPanel's <code>addNewItem</code> button
     */
    public Calendar(int monthNumb, int year, ActionListener a, MouseAdapter m,String path) {
        // Initializes Variables
        month = new Month(monthNumb, year);
        fileName  = path + "Calendar\\" + monthNumb + " " + year + ".txt";
        actionListener = a;
        mouseListener = m;
        this.path = path;
        // Creates the calendar panel.
        makeCalendarPanel();
    } // end of constructor Calendar(int monthNumb, int year, int sHeight, int sWidth)
    
    /* Private methods for constructing the panels */
    /*
     * Makes the Calendar (main) panel
     */
    private void makeCalendarPanel() {
        this.setLayout(new BorderLayout());

        makeTopPanel();
        this.add(topPanel, BorderLayout.PAGE_START);

        makeCentrePanel();
        this.add(centrePanel, BorderLayout.CENTER);
    } // end of private method void makeCalendarPanel()
    
    /*
     * Makes the top panel
     */
    private void makeTopPanel() {
        monthName = new JLabel(month.getMonthName(0) + " " + month.getYear());
        
        // Makes a panel that have the days of a week side by side
        weekdayPanel = new JPanel();
        weekdayPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, GRID_GAP, GRID_GAP));
        weekday = new JLabel[WEEKDAYS.length];
        for (int i = 0; i < WEEKDAYS.length; i = i + 1) {
            weekday[i] = new JLabel(WEEKDAYS[i], SwingConstants.CENTER);
            weekday[i].setPreferredSize(new Dimension((int) BASE_WIDTH, (int) BASE_HEIGHT / 3));
            weekdayPanel.add(weekday[i]);
        } // end of (int i = 0; i < WEEKDAYS.length; i = i + 1)
        
        // Makes the top panel and adds the components to the top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(monthName);
        topPanel.add(weekdayPanel);
    } // end of private method void makeTopPanel() 
    
    /*
     * Makes the centre panel
     */
    private void makeCentrePanel() {
        // Local Variables
        int numberOfDays = super.getNumberOfDays(month);
        int numRows = NUMBER_OF_ROWS;
        double tempDayPanelHeight = BASE_HEIGHT;
        int dayCounter = 1;
        daysBlank = numberOfDays % 7;
        
        // Adds another row to the calender when needed
        if (daysBlank + month.getDays() > 35) {
            numRows = numRows + 1;
            tempDayPanelHeight = tempDayPanelHeight/1.2;
        } // end of if (daysBlank + month.getDays() > 35) 
        
        // Creates the centre panel
        centrePanel = new JPanel();
        centrePanel.setLayout(new GridLayout(numRows, WEEKDAYS.length, GRID_GAP, GRID_GAP));
        
        // Creates the panels for the days of the month
        dayPanel = new DayPanel[numRows*WEEKDAYS.length];
        for (int i = 0; i < dayPanel.length; i = i + 1) {
            // Creates the day panels
            if (i < daysBlank) 
                dayPanel[i] = new DayPanel();
            else if (dayCounter <= month.getDays()) {
                dayPanel[i] = new DayPanel(dayCounter,BASE_HEIGHT,tempDayPanelHeight,BASE_WIDTH, mouseListener);
                dayPanel[i].setActionListener(actionListener);
                dayCounter = dayCounter + 1;
            }
            else
                dayPanel[i] = new DayPanel();
            // end of if (i < daysBlank) 
            centrePanel.add(dayPanel[i]);
        } // end of for (int i = 0; i < dayPanel.length; i = i + 1)
        
        dayCounter = daysBlank;
        
        try {
            // Opens and reads the file 
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            // Reads the lines and adds the activies for each dayPanel
            while ((line = br.readLine()) != null) {
                if (line.equals(NEW_FUNCTION)) {
                    dayCounter = dayCounter + 1;
                }
                else {
                    int indLength = DAY_INDICATOR_L.length();
                    int index = dayPanel[dayCounter].getArrayListSize();
                    String[] date = new String[2];
                    date[0] = line.substring(line.indexOf(DAY_INDICATOR_L) + indLength, line.indexOf("_"));
                    date[1] = line.substring(line.indexOf("_") + 1, line.indexOf(DAY_INDICATOR_R));
                    String text = line.substring(line.indexOf(DAY_INDICATOR_R) + indLength + 1);
                    if (date[0].length() == 0)
                        dayPanel[dayCounter].addNewItem(new ImpDateLabel(dayPanel[dayCounter].getDay(), index, text), false);
                    else if (date[0].equals(date[1]))
                        dayPanel[dayCounter].addNewItem(new ImpDateLabel(dayPanel[dayCounter].getDay(), index, text, date[0]), false);
                    else
                        dayPanel[dayCounter].addNewItem(new ImpDateLabel(dayPanel[dayCounter].getDay(), index, text, date), false);
                } // end of if (line.equals(NEW_FUNCTION))
            } // end of while ((line = br.readLine()) != null)
            this.revalidate();
            this.repaint();
        }
        catch (Exception FileNotFoundException) {

        } // end of try-catch
    } // end of private method void makeCentrePanel()
    
    /* Accessor Methods */
    /**
     * Returns the month of this calendar
     * @return the month of this calendar
     */
    public Month getMonth() {
        return month;
    } // end of method Month getMonth()
    
    /**
     * Returns the size of <code>arraylists</code> of the specific day panel
     * @param dayNumber the day of the dayPanel
     * @return the size of <code>arraylists</code> of the specific day panel
     */
    @Override
    public int getArrayListSize(int dayNumber) {
        return dayPanel[dayNumber + daysBlank-1].getArrayListSize();
    } // end of method
    
    /**
     * Returns the month number and year of this calendar
     * @return the month number and year of this calendar in a string array
     */
    @Override
    public String[] getDate() {
        String[] date = new String[2];
        date[0] = "" + month.getMonthNumber();
        date[1] = "" + month.getYear();
        return date;
    } // end of method String[] getDate(
    
    /**
     * Returns next month's calendar
     * @return next month's calendar
     */
    @Override
    public Calendar Next() {
        month = month.nextMonth();
        return new Calendar();
    } // end of method Calendar nextMonth()
    
    /**
     * Returns the previous month's calendar
     * @return the previous month's calendar
     */
    @Override
    public Calendar Prev() {
        month = month.prevMonth();
        return new Calendar();
    } // end of method Calendar prevMonth()
    
    /* Mutator Methods */
    /**
     * Adds a component to the specific dayPanel
     * 
     * @param dayNumber the day of the dayPanel
     * @param component the component that is being added to the specific dayPanel
     */
    @Override
    public void addNewItem(int dayNumber, JComponent component) {
        dayPanel[dayNumber + daysBlank-1].addNewItem(component, true);
    } // end of method void addNewItem(int dayNumber, JComponent component)
    
    /**
     * Changes the text of the specified component in the specified function panel
     * 
     * @param dayNumber the day of the dayPanel
     * @param index the index of the component in the day panel
     * @param text the new text for the component
     */
    @Override
    public void editActivity(int dayNumber, int index, String text) {
        dayPanel[dayNumber + daysBlank-1].editActivity(index, text);
    } // end of method
    
    /**
     * Removes the specified component in the specified day panel
     * 
     * @param dayNumber the day of the dayPanel
     * @param index the index of the component in the day panel
     */
    @Override
    public void removeActivity(int dayNumber, int index) {
        dayPanel[dayNumber + daysBlank-1].removeActivity(index);
    } // end of method
    
    /**
     * Returns the specified component in the specified day panel
     * 
     * @param dayNumber the day of the dayPanel
     * @param index the index of the component in the day panel
     * @return the specified component in the specified day panel
     */
    @Override
    public JComponent getActivity(int dayNumber, int index) {
        return dayPanel[dayNumber + daysBlank-1].getActivity(index);
    } // end of method
    /* Other Methods */
    /**
     * Saves the calendar by creating/opening a file and writing down all the activities (the JComponents)
     * in each of the dayPanels
     */
    @Override
    public void Save() {
        try {
            // Opens the folder Calendar or makes a new folder if it doesn't exist
            File file = new File(path + "Calendar");
            file.mkdir();
            // Opens the file
            FileWriter write = new FileWriter(fileName, false);
            PrintWriter printLine = new PrintWriter(write);
           
            int dayCounter = 1;
            // Prints the items in the dayPanels
            for (int i = 0; i < dayPanel.length; i = i + 1) {
                if (dayPanel[i].getDay() != 0) {
                    dayCounter = dayCounter + 1;
                    for (int k = 0; k < dayPanel[i].getArrayList().size(); k = k + 1) {
                        if (dayPanel[i].getArrayList().get(k) != null) {
                            ImpDateLabel temp = (ImpDateLabel) dayPanel[i].getArrayList().get(k);
                            String dateIndicator = DAY_INDICATOR_L;
                            if (temp.getDates().length == 1) {
                                dateIndicator = dateIndicator + temp.getDates()[0] + "_"
                                        + temp.getDates()[0] + DAY_INDICATOR_R + " ";
                            } else if (temp.getDates().length == 2) {
                                dateIndicator = dateIndicator + temp.getDates()[0] + "_"
                                        + temp.getDates()[1] + DAY_INDICATOR_R + " ";
                            } else {
                                dateIndicator = dateIndicator + "_" + DAY_INDICATOR_R + " ";
                            }
                            printLine.println(dateIndicator + temp.getText());
                        } // end of if (dayPanel[i].getArrayList().get(k) != null)
                    } // end of for (int k = 0; k < dayPanel[i].getArrayList().size(); k = k + 1)
                    printLine.println(NEW_FUNCTION);
                } // end of if (dayPanel[i].getDay() != 0)
            } // end of for (int i = 0; i < toDoLabels.size(); i = i + 1)
            // Closes the file
            printLine.close();
        }
        catch (Exception IOException) {
            new ErrorOptionPane("Something went wrong with the file."
                            + "\nPlease try again.", "File Error");
        } // end of try-catch
    } // end of method Save()
    /* private Classes */
    private class ErrorOptionPane {
        /* Instance Variables */
        JFrame frame;

        ErrorOptionPane(String errorMessage, String typeOfError) {
            frame = new JFrame();
            JOptionPane.showMessageDialog(frame, errorMessage, typeOfError, JOptionPane.ERROR_MESSAGE);
        } // end of constructor OptionPaneExample(String errorMessage)
    } // end of private class OptionPaneExample
}
