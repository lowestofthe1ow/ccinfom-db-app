package BocchiTheGUI.components.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BocchiTheGUI.components.abs.DialogUI;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.time.TimePicker;

public class TimeSlotMakerUI extends DialogUI {
	DatePicker datePicker;
	TimePicker timePicker;

	public TimeSlotMakerUI() {
		super("Pick a date and time");
		JPanel n = new JPanel();

		n.setLayout(new BorderLayout());
		datePicker = new DatePicker();
		timePicker = new TimePicker();

		timePicker.setSelectedTime(LocalTime.now());

		n.add(datePicker, BorderLayout.WEST);
		n.add(timePicker, BorderLayout.EAST);

		/* TODO: Wire the buttons to update the text fields/labels below */

		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));

		b.add(new JButton("Start Date Time Selected:"));
		b.add(new JButton("End Date Time Selected:"));

		/* TODO: Set to uneditable */

		JPanel c = new JPanel();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		c.add(new JTextField(""));
		c.add(new JTextField(""));

		JPanel d = new JPanel();

		d.add(b);
		d.add(c);

		n.add(d, BorderLayout.NORTH);

		setLayout(new BorderLayout());

		add(n, BorderLayout.CENTER);

		setPreferredSize(new Dimension(600, 400));

		addButtons("Confirm");
		setButtonActionCommands("add_timeslot");
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		return null;
	}
}
