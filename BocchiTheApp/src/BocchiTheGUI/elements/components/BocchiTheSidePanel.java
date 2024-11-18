package BocchiTheGUI.elements.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import raven.datetime.component.date.DatePicker;

public class BocchiTheSidePanel extends JPanel {
    private DatePicker datePicker;
    private JEditorPane editorPane;

    public BocchiTheSidePanel() {
        setLayout(new BorderLayout());

        datePicker = new DatePicker();

        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body><h2>BANDS PLAYING FOR SELECTED DAY:</h2><ul>");

        editorPane.setEditable(false);
        editorPane.setBackground(Color.WHITE);

        add(datePicker, BorderLayout.NORTH);
        add(new JScrollPane(editorPane), BorderLayout.CENTER);

        setPreferredSize(new Dimension(300, 400));
    }

    public void editText(String... bands) {
        StringBuilder htmlContent = new StringBuilder("<html><body><h2>BANDS PLAYING FOR SELECTED DAY:</h2><ul>");

        for (String band : bands) {
            htmlContent.append("<li>").append(band).append("</li>");
        }

        htmlContent.append("</ul></body></html>");
        editorPane.setText(htmlContent.toString());
    }

    public void addDateSelectionListener(String... bands) {
        datePicker.addDateSelectionListener((e) -> {
            editText(bands);
        });

    }
}
