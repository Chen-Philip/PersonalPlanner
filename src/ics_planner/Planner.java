package ics_planner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * A planner
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public abstract class Planner extends JPanel{
    
    /* Constants */
    static final String[] WEEKDAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    static final int GRID_GAP = 5;
    static final int BASE_MONTH = 9;
    static final int BASE_YEAR = 2019;
    static final int BASE_NUMB_OF_LEAP_DAYS = 489;
    static final int DAYS_IN_YEAR = 365;
    static final int DAYS_IN_YEAR_FROM_SEPTEMBER = 122;
    static final String NEW_FUNCTION = "=========================================================================";
    static final double BASE_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/7.5;
    static final double BASE_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/9;
    
    /* Abstract Methods */
    public abstract Planner Next();
    public abstract Planner Prev();
    public abstract Month getMonth();
    public abstract String[] getDate();
    public abstract int getArrayListSize(int index);
    public abstract JComponent getActivity(int panel, int index);
    
    public abstract void Save();
    public abstract void addNewItem(int index, JComponent component);
    public abstract void editActivity(int function, int index, String text) ;
    public abstract void removeActivity(int function, int index);
    
    /**
     * Returns the number of number days from September 1, 2019 until the first day of 
     * the given month
     * @param month the month
     * @return the number of number days from September 1, 2019 until the first day of 
     * the given month
     */
    public int getNumberOfDays(Month month) {
        int numberOfDays = 0;
        int numberOfLeapYears = (month.getYear() - 1) / 4 - (month.getYear() - 1) / 100 + (month.getYear() - 1) / 400;
        
        // Checks if the year is 2019 or not
        if (month.getYear() == BASE_YEAR) {
            // Calculates the days from Sept. 2019 to that month
            for (int m = month.getMonthNumber() - 1; m >= 9; m = m - 1) {
                if ((m - BASE_MONTH) % 2 == 0) {
                    numberOfDays = numberOfDays + 30;
                } 
                else {
                    numberOfDays = numberOfDays + 31;
                } // end of if((m-BASE_MONTH)%2 == 0)
            } // end of for (int m = month.getMonthNumber(); m <= 12; m = m + 1)
        } 
        else {
            // Checks if the year is a leap year and the month is after February
            if (month.getMonthNumber() > 2 && month.getYear() % 400 == 0)
                numberOfDays = numberOfDays + 1;
            else if (month.getMonthNumber() > 2 && month.getYear() % 4 == 0 && month.getYear() % 100 != 0)
                numberOfDays = numberOfDays + 1;
            // end of if (month.getMonthNumber() > 2 && month.getYear() % 400 == 0)
            
            // Caculates the number of days up to the month in that year
            for (int m = month.getMonthNumber() - 1; m >= 1; m = m - 1) {
                switch (m) {
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        numberOfDays = numberOfDays + 30;
                        break;
                    case 2:
                        numberOfDays = numberOfDays + 28;
                        break;
                    default:
                        numberOfDays = numberOfDays + 31;
                } // end of switch(m)                      
            } // end of for (int m = month.getMonthNumber(); m <= 12; m = m + 1)
            
            // Calculates the number of days that lead up to that year
            numberOfDays = numberOfDays + DAYS_IN_YEAR_FROM_SEPTEMBER + (month.getYear() - BASE_YEAR - 1) * DAYS_IN_YEAR + numberOfLeapYears - BASE_NUMB_OF_LEAP_DAYS;
        } // end of if (month.getYear() == BASE_YEAR)
        return numberOfDays;
    } // end of method
}
