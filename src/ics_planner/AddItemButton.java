package ics_planner;

import javax.swing.JButton;

/**
 * A JButton for adding items to the <code>FunctionPanel</code> and 
 * <code>DayPanel</code>that is assigned a number
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class AddItemButton extends JButton{
    /* Instance Variables */
    private int buttonNumber;
    
    /* Constructors */
    /**
     * Creates an AddItemButton that is assigned a number
     * @param text the text the button has
     * @param num the button's number
     */
    public AddItemButton(String text, int num) {
        buttonNumber = num;
        this.setText(text);
    } // end of constructor AddItemButton(String text, int num)
    
    /* Accessor Methods */
    /**
     * Returns the button's number
     * @return the button's number
     */
    public int getButtonNumber() {
        return buttonNumber;
    } // end of method int getButtonNumber()
}
