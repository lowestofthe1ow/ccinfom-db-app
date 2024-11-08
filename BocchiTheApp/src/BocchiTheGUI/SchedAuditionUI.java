package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

public class SchedAuditionUI extends JPanel {
    //Sched audition inputs
    private JTextField performerNameTf;
    private JComboBox timeslotsCB;

    //Confirm button
    private JButton schedAuditionConfirmBtn;

    public SchedAuditionUI() {
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up panels
        JPanel performerNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        performerNameTf.add(new JLabel("Performer name: "));
        this.performerNameTf = new JTextField();
        this.performerNameTf.setColumns(15);
        performerNamePanel.add(this.performerNameTf);

        this.timeslotsCB = new JComboBox<String>();

        this.schedAuditionConfirmBtn = new JButton("Confirm");

        //Add panels into main sched audition panel
        this.add(performerNamePanel);
        this.add(this.timeslotsCB);
        this.add(this.schedAuditionConfirmBtn);
    }

    public String getPerformerName() {
    	return performerNameTf.getName();
    }

    public String getTimeslotsCB() {
        return this.timeslotsCB.getSelectedItem().toString();
    }
}