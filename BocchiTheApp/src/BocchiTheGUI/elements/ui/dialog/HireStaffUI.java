package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("Hire staff");

        addForms("First name: ", "Last name: ", "Contact number: ");
        addButtons("Confirm");
        setButtonActionCommands("button/next/select_staff_position");
    }
}