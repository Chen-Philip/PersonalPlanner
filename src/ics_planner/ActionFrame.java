package ics_planner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A frame for adding items to the <code>FunctionPanel</code> and 
 * <code>DayPanel</code> that pops up when <code>AddItemButton</code> is pressed
 * 
 * @author 000480-0008
 * @date 3/24/2020
 */
public class ActionFrame {
    /* Constants */
    private static final int GRID_GAP = 5;
    
    /* Instance Variables */
    private JFrame frame;
    private int frameNumber;
    private JLabel label;
    private JPanel questionAnswer;
    private double baseHeight;
    private double baseWidth;
    
    private JPanel centrePanel;
    private JTextField answer;
    private JCheckBox addToAgenda;

    private JPanel addToAgendaPanel;
    private JCheckBox today;
    private JPanel datePanel;
    private JLabel[] timeLabel;
    private JTextField[][] date;
    private boolean isAddToAgendaPanelVisible;
    private boolean isSameDate;

    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JButton okayRemove;
    private JButton cancel;
    
    private JCheckBox addChangesToAgenda;
    private boolean isAddChanges;
    
    /* Constructors */
    /**
     * Creates an ActionFrame that is responsible for adding items to the planners.
     * 
     * @param frameNum the number the frame is assigned. It is assigned a number > 0 if it is used by
     * a <code>dayPanel</code>, a -1 for the to-do list <code>functionPanel</code> and a -2 for a 
     * important date list <code>functionPanel</code>
     */
    public ActionFrame(int frameNum) {
        makeCentrePanel();
        if (frameNum > 0) {
            frameNumber = frameNum;
            addToAgenda = new JCheckBox("Add to Agenda");
            addToAgenda.addActionListener(new AddToAgendaCheckBoxListener());
            addToAgenda.setAlignmentX(0);
            centrePanel.add(addToAgenda);
            isAddToAgendaPanelVisible = false;
            isSameDate = false;
            addToAgenda();
        }
        else if (frameNum == -1) 
            frameNumber = -1;
        else if (frameNum == -2) {
            frameNumber = -2;
            makeDate();
        }
        else if (frameNum == -3) {
            label.setText("Enter the new activity:");
            makeDate();
        }
        makeFrame();
    } // end of constructor ActionFrame(int frameNum)
    
    /**
     * Creates an ActionFrame that is responsible for editing or removing items
     * on the Agenda.
     * 
     * @param isEdit if the ActionFrame is for editing items on the planners
     * @param frameNum the number the frame is assigned. It is assigned a number > 0 if it is used by
     * a <code>dayPanel</code>, a -1 for the to-do list <code>functionPanel</code> and a -2 for a 
     * important date list <code>functionPanel</code>
     */
    public ActionFrame(boolean isEdit, int frameNum) {
        // Initializes Variables
        makeCentrePanel();
        if (isEdit) {
            // Creates an edit action frame
            label.setText("Enter the new activity:");
            if (frameNum == -1) {
                frameNumber = -1;
            } 
            else if (frameNum == -2) {
                frameNumber = -2;
                makeDate();
            }
            makeFrame();
        }
        else if (!isEdit) {
            // Creates a remove action frame
            centrePanel.remove(answer);
            label.setText("Remove the activity?");
            frameNumber = frameNum;
            makeFrame();
            okayRemove.setText("Remove");
        }
    } // end of constructor 
    
    /**
     * Creates an ActionFrame that is responsible for editing or removing items
     * on the Calendar.
     * 
     * @param isEdit if the ActionFrame is for editing items on the planners
     * @param isAdd if the changes are added to the agenda
     * @param frameNum the number the frame is assigned. It is assigned a number > 0 if it is used by
     * a <code>dayPanel</code>, a -1 for the to-do list <code>functionPanel</code> and a -2 for a 
     * important date list <code>functionPanel</code> 
     */
    public ActionFrame(boolean isEdit, boolean isAdd, int frameNum) {
        makeCentrePanel();
        frameNumber = frameNum;
        if (isEdit) {
            // Creates an edit action frame
            label.setText("Enter the new activity:");
            if (isAdd && frameNum > 0) {
                addChangesToAgenda = new JCheckBox("Add Changes to Agenda");
                addChangesToAgenda.addActionListener(new AddChangesCheckBoxListener());
                addChangesToAgenda.setAlignmentX(0);
                centrePanel.add(addChangesToAgenda);
                isAddChanges = false;
                addToAgenda();
            }
            makeFrame();
        }
        else if (!isEdit) {
            // Creates a remove action frame
            centrePanel.remove(answer);
            label.setText("Remove the activity?");
            if (isAdd && frameNum > 0) {
                addChangesToAgenda = new JCheckBox("Add Changes to Agenda");
                addChangesToAgenda.addActionListener(new AddChangesCheckBoxListener());
                addChangesToAgenda.setAlignmentX(0);
                centrePanel.add(addChangesToAgenda);
                isAddChanges = false;
                addToAgenda();
            } 
            makeFrame();
            okayRemove.setText("Remove");
        }   
    } // end of constructor ActionFrame(boolean isEdit, boolean isAdd, int frameNum)
    
    /* Private methods for constructing the frame */
    /*
     * Makes the frame
     */
    private void makeFrame() {
        // Creates the frame
        frame = new JFrame("Planner");
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(centrePanel, BorderLayout.CENTER);

        // Creates and adds the bottom panel
        makeBottomPanel();
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        
        makeBorderPanels();
        // Finishes creating the frame.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    } // end of private method 
    
    /*
     * Makes the centre panel
     */
    private void makeCentrePanel() {
        baseHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/7.5;
        baseWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/9;
        label = new JLabel("Enter the activity:");
        label.setAlignmentX(0);
        answer = new JTextField(20);
        answer.setAlignmentX(0);
        datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.TRAILING, GRID_GAP, GRID_GAP));
        datePanel.setAlignmentX(0);
        centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
        centrePanel.add(label);
        centrePanel.add(answer);
    } // end of private method 
    
    /*
     * Makes the bottom panel
     */
    private void makeBottomPanel() {
        okayRemove = new JButton("OK");
        cancel = new JButton("Cancel");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, GRID_GAP, GRID_GAP));

        buttonPanel.add(okayRemove);
        buttonPanel.add(cancel);

        bottomPanel = new JPanel();

        bottomPanel.add(buttonPanel);
    } // end of private method 
    
    /*
     * Makes the date text-fields
     */
    private void makeDate() {
        date = new JTextField[1][3];
        timeLabel = new JLabel[1];
        timeLabel[0] = new JLabel("Date:");
        datePanel.add(timeLabel[0]);
        for (int i = 0; i < date[0].length; i = i + 1) {
            if (i == 0) {
                date[0][i] = new JTextField("MM", 2);
            } else if (i == 1) {
                date[0][i] = new JTextField("DD", 2);
            } else {
                date[0][i] = new JTextField("YYYY", 4);
            }
            // end of if (j == 0)
            datePanel.add(date[0][i]);
        }
        datePanel.setAlignmentX(0);
        centrePanel.add(datePanel);
    } // end of private method 
    
    /*
     * Makes the border panels of the frame
     */
    private void makeBorderPanels() {
        JPanel[] dummyPanel = new JPanel[3];
        dummyPanel[0] = new JPanel();
        dummyPanel[0].setPreferredSize(new Dimension((int) baseWidth/6, (int) baseHeight/6));
        dummyPanel[1] = new JPanel();
        dummyPanel[1].setPreferredSize(new Dimension((int) baseWidth/6, (int) baseHeight/6));
        dummyPanel[2] = new JPanel();
        dummyPanel[2].setPreferredSize(new Dimension((int) baseHeight/6, (int) baseWidth/6));

        frame.add(dummyPanel[0], BorderLayout.LINE_START);
        frame.add(dummyPanel[1], BorderLayout.LINE_END);
        frame.add(dummyPanel[2], BorderLayout.PAGE_START);
    }
    
    /*
     * Makes the panel responsible for the dates for adding to the agenda
     */
    private void addToAgenda() {
        date = new JTextField[2][3];
        today = new JCheckBox("Same Date");
        today.addActionListener(new SameDateCheckBoxListener());
        timeLabel = new JLabel[2];
        timeLabel[0] = new JLabel("From:");
        timeLabel[1] = new JLabel("To:");

        JPanel dummyPanel = new JPanel();
        dummyPanel.setPreferredSize(new Dimension((int) (GRID_GAP * 2), (int) 20));

        for (int i = 0; i < date.length; i = i + 1) {
            if (i == 1) {
                datePanel.add(dummyPanel);
            }
            datePanel.add(timeLabel[i]);
            for (int j = 0; j < date[i].length; j = j + 1) {
                if (j == 0) {
                    date[i][j] = new JTextField("MM", 2);
                } else if (j == 1) {
                    date[i][j] = new JTextField("DD", 2);
                } else {
                    date[i][j] = new JTextField("YYYY", 4);
                }
                // end of if (j == 0)
                datePanel.add(date[i][j]);
            }
        }
        today.setAlignmentX(0);

        addToAgendaPanel = new JPanel();
        addToAgendaPanel.setLayout(new BoxLayout(addToAgendaPanel, BoxLayout.PAGE_AXIS));

        addToAgendaPanel.add(today);
        addToAgendaPanel.add(datePanel);
    } // end of private method 

    /* Accessor Methods */
    /**
     * Returns the <code>okayRemove</code> button or the <code>cancel</code> button
     * @param buttonNum 0 to return the <code>okayRemove</code> button or 
     * 1 to return the <code>cancel</code> button
     * @return the <code>okayRemove</code> button or the <code>cancel</code> button
     */
    public JButton getButton(int buttonNum) {
        if (buttonNum == 0) {
            return okayRemove;
        } else if (buttonNum == 1) {
            return cancel;
        }
        return new JButton();
    } // end of method
    
    /**
     * Returns the text inside the <code>answer</code> text field
     * @return the text inside the <code>answer</code> text field
     */
    public String getText() {
        return answer.getText();
    } // end of method
    
    
    /**
     * Returns dates in the DD MM YYYY form
     * @param index the index of the date
     * @return dates in the DD MM YYYY form
     */
    public String getFileDate(int index) {
        return date[index][1].getText() + " " + date[index][0].getText() + " " + date[index][2].getText();
    } // end of method
    
    /**
     * Returns dates in the MM/DD/YYYY form
     * @param index the index of the date
     * @return dates in the MM/DD/YYYY form
     */
    public String getLabelDate(int index) {
        return date[index][0].getText() + "/" + date[index][1].getText() + "/" + date[index][2].getText();
    } // end of method
    
    /**
     * Returns the number of the frame
     * @return the number of the frame
     */
    public int getFrameNumber() {
        return frameNumber;
    } // end of method 
    
    /**
     * Returns if the date of activity is added to the agenda of the same date
     * @return if the date of activity is added to the agenda of the same date
     */
    public boolean isSameDate() {
        return isSameDate;
    } // end of method 
    
    /**
     * Returns if the changes to the Calendar activities are added to the agenda.
     * @return if the changes to the Calendar activities are added to the agenda.
     */
    public boolean isAddChanges() {
        return isAddChanges;
    } // end of method boolean isAddChanges()
    
    /**
     * Returns if the <code>addToAgendaPanel</code> is visible
     * @return if the <code>addToAgendaPanel</code> is visible
     */
    public boolean isAddToAgendaPanelVisible() {
        return isAddToAgendaPanelVisible;
    } // end of method boolean isAddToAgendaPanelVisible()
    
    /* Mutator Methods */
    /**
     * Sets the <code>ActionListener</code> for the <code>okayRemove</code> button and the
     * <code>cancel</code> button
     * @param actionListener the <code>ActionListener</code> for the <code>okayRemove</code> button and the
     * <code>cancel</code> button
     */
    public void setActionListener(ActionListener actionListener) {
        okayRemove.addActionListener(actionListener);
        cancel.addActionListener(actionListener);
    } // end of void setActionListener(ActionListener actionListener)
    
    /* Other Methods */
    /**
     * Disposes the <code>AddItemFrame</code>
     */
    public void dispose() {
        frame.dispose();
    } // end of method dispose()
    
    /**
     * Sees if the date(s) entered is valid. 
     * @return Returns 0 if the date(s) are valid. Returns 1 if the date(s) themselves 
     * are not valid. Returns 2 if the second date comes sooner than the first.
     */
    public int isValidDates() {
        try {
            int[] day = new int[date.length];
            Month[] month = new Month[date.length];
            for (int i = 0; i < date.length; i = i + 1) {
                month[i] = new Month(Integer.parseInt(date[i][0].getText()),Integer.parseInt(date[i][2].getText()));
                day[i] = Integer.parseInt(date[i][1].getText());
                // See if the date is valid
                if (day[i] > month[i].getDays())
                    return 1;
                // end of if (day[i] > month[i].getDays())
            } // end of for (int i = 0; i < date.length; i = i + 1)
            if (date.length == 2) {
                // Sees if the second date comes sooner than the first
                if (month[1].getYear() < month[0].getYear())
                    return 2;
                else if (month[1].getMonthNumber() < month[0].getMonthNumber())
                    return 2;
                else if (day[1] < day[0])
                    return 2;
                // end of if (month[1].getYear() < month[0].getYear())
            } // end of if (date.length == 2)
            return 0;
        }
        catch (NumberFormatException e) {
            return 1;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return 1;
        } // end of try-catch
    } // end of method isValidDates()
    
    /* Private Classes */
    private class AddChangesCheckBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox source = (JCheckBox) event.getSource();
            if (source.isSelected()) {
                isAddChanges = true;
            } else if (!source.isSelected()) {
                isAddChanges = false;
            }
        } // end of method void actionPerformed(ActionEvent event)
    } // end of private class
    private class AddToAgendaCheckBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox source = (JCheckBox) event.getSource();
            if (source.isSelected()) {
                isAddToAgendaPanelVisible = true;
                centrePanel.add(addToAgendaPanel);
                frame.pack();
            } else if (!source.isSelected()) {
                isAddToAgendaPanelVisible = false;
                centrePanel.remove(addToAgendaPanel);
                frame.pack();
            }
        } // end of method void actionPerformed(ActionEvent event)
    } // end of private class
    private class SameDateCheckBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox source = (JCheckBox) event.getSource();
            if (source.isSelected()) {
                isSameDate = true;
                addToAgendaPanel.remove(datePanel);
                frame.pack();
            } else if (!source.isSelected()) {
                isSameDate = false;
                addToAgendaPanel.add(datePanel);
                frame.pack();
            }
        } // end of method void actionPerformed(ActionEvent event)
    } // end of private class
}
