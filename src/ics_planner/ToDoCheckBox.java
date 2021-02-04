
package ics_planner;

import javax.swing.JCheckBox;

/**
 * A JCheckBox for the <code>FunctionPanel</code> and 
 * <code>DayPanel</code> that knows its index and knows the dates the label was added to
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class ToDoCheckBox extends JCheckBox{
    /* Instance Variables */
    private int type;
    private int index;
    
    /* Constructors */
    /**
     * Creates an ImpDateLabel
     */
     public ToDoCheckBox() {
        index = -1;
        type = 0;
    } // end of constructor
     
    /**
     * Creates a ToDoCheckBox that knows its index
     * 
     * @param index the label's index
     * @param text the text the label has
     * @param isChecked if the checkBox is checked
     */
    public ToDoCheckBox(int index, String text, boolean isChecked) {
        this.index = index;
        this.setText(text);
        this.setSelected(isChecked);
        type = -1;
    } // end of constructor
    
    /* Accessor Methods */
    /**
     * Returns the index of this checkbox
     * @return the index of this checkbox
     */
    public int getIndex() {
        return index;
    } // end of method
    
    /**
     * Returns the type of checkbox
     * @return the type of label. -1 if it belongs to a <code>FunctionPanel</code>.
     * 0 if it does not belong to any panel
     */
    public int getType() {
        return type;
    } // end of method
    
    /**
     * Sets the type back to 0
     */
    public void reset() {
        type = 0;
    } // end of method
}
