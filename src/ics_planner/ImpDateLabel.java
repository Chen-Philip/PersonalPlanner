package ics_planner;

import javax.swing.JLabel;

/**
 * A JLabel for the <code>FunctionPanel</code> and 
 * <code>DayPanel</code> that knows its index and knows the dates the label was added to
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class ImpDateLabel extends JLabel{
    /* Instance Variables */
    private int index;
    private int labelType;
    private String[] dates;
    
    /* Constructors */
    /**
     * Creates an ImpDateLabel
     */
    public ImpDateLabel() {
        index = -1;
        labelType = -1;
        dates = new String[0];
    } // end of constructor ImpDateLabel()
    
    /**
     * Creates an ImpDateLabel that knows its index
     * 
     * @param type the type of panel that the label belongs to. -2 if it belongs to a <code>FunctionPanel</code>.
     * > 0 if it belongs to a <code>DayPanel</code>.
     * @param index the label's index
     * @param text the text the label has
     */
    public ImpDateLabel(int type, int index, String text) {
        this.index = index;
        labelType = type;
        this.setText(text);
        this.dates = new String[0];
    } // end of constructor ImpDateLabel(int type, int index, String text)
    
    /**
     * Creates an AddItemButton that is assigned a number
     * 
     * @param type the type of panel that the label belongs to. -2 if it belongs to a <code>FunctionPanel</code>.
     * > 0 if it belongs to a <code>DayPanel</code>.
     * @param index the label's index
     * @param text the text the label has
     * @param date the date the label was added to
     */
    public ImpDateLabel(int type, int index, String text, String date) {
        this.index = index;
        labelType = type;
        this.setText(text);
        this.dates  = new String[1];
        dates[0] = date;
    } // end of constructor ImpDateLabel(int type, int index, String text, String date)
    
    /**
     * Creates an ImpDateLabel that is assigned a number
     * 
     * @param type the type of panel that the label belongs to. -2 if it belongs to a <code>FunctionPanel</code>.
     * > 0 if it belongs to a <code>DayPanel</code>.
     * @param index the label's index
     * @param text the text the label has
     * @param dates the dates the label was added to
     */
    public ImpDateLabel(int type, int index, String text, String[] dates) {
        this.index = index;
        labelType = type;
        this.setText(text);
        this.dates  = dates;
    } // end of constructor ImpDateLabel(int type, int index, String text, String[] dates)
    
    /* Accessor Methods */
    /**
     * Returns the index of this label
     * @return the index of this label
     */
    public int getIndex() {
        return index;
    } // end of method int getIndex()
    
    /**
     * Returns the type of label
     * @return the type of label. -2 if it belongs to a <code>FunctionPanel</code>.
     * > 0 if it belongs to a <code>DayPanel</code>. 0 if it does not belong to any panel
     */
    public int getType() {
        return labelType;
    } // end of method int getType()
    
    /**
     * Returns the date(s) the label was added to
     * @return the date(s) the label was added to
     */
    public String[] getDates() {
        return dates;
    } // end of method String[] getDates()
    
    /* Mutator Methods */
    /**
     * Sets the type back to 0
     */
    public void reset() {
        labelType = 0;
    } // end of method void reset()
}
