
package ics_planner;
/**
 * A month object
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class Month {
    /* Constants */
    private static final String[][] MONTH_NAMES = {
        {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"},
        {"Jan.", "Feb.", "Mar.", "Apr.", "May", "June", "July", "Aug.", "Sept.", "Oct.", "Nov.", "Dec."}
    };

    private static final int[] NUMBER_DAYS = {31,28,31,30,31,30,31,31,30,31,30,31};

    /* Instance Variables */
    private String []monthName;
    private int monthNumb;
    private int year;
    private int numberOfDays;
    
    /* Constructors */
    /**
     * Creates a month given the month number and year
     * 
     * @param month the month
     * @param year the year
     */
    public Month(int month, int year)
    {
        // Initializes the instance variables
        monthName = new String[2];
        monthName[0] = MONTH_NAMES[0][month-1];
        monthName[1] = MONTH_NAMES[1][month-1];
        monthNumb = month;
        this.year = year;
        
        // Checks if the month is a February on a leap year
        if (month == 2 && year % 400 == 0)
            numberOfDays = 29;
        else if (month == 2 && year % 4 == 0 && year % 100 != 0)
            numberOfDays = 29;
        else
            numberOfDays = NUMBER_DAYS[month-1];
        // end of if (month == 2 && year % 5 == 0)
    } // end of constructor Month(int month, int year)
    
    /* Accessor Methods */
    /**
     * Returns either the full month name or the shortened form
     * 
     * @param rowNum 0 for the full name, 1 for the shortened name
     * @return either the full month name or the shortened form
     */
    public String getMonthName(int rowNum)
    {
        return monthName[rowNum];
    } // end of method getMonthName()
    
    /**
     * Returns the month's number
     * @return the month's number
     */
    public int getMonthNumber()
    {
        return monthNumb;
    } // end of method getMonthName()
    
    /**
     * Returns the number of days in the month
     * @return the number of days in the month
     */
    public int getDays()
    {
        return numberOfDays;
    } // end of method getDays()
    
    /**
     * Returns the year
     * @return the year
     */
    public int getYear()
    {
        return year;
    } // end of method getYear()
    
    /**
     * Returns the next month
     * @return the next month
     */
    public Month nextMonth()
    {
        if (monthNumb == 12)
            return new Month(1, year + 1);
        // end of if (monthNumb == 12)
        return new Month(monthNumb + 1, year);
    }
    
    /**
     * Returns the previous month
     * @return the previous month
     */
    public Month prevMonth()
    {
        if (monthNumb == 1)
            return new Month(12, year - 1);
        // end of if (monthNumb == 12)
        return new Month(monthNumb - 1, year);
    } // end of method
}
