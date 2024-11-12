package BocchiTheGUI.components.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GenerateReportsUI extends JPanel {
    private JButton performerSalesBtn, livehouseSalesBtn, rentalSalesBtn;
    private JButton livehouseSchedBtn, staffSalaryBtn;

    public GenerateReportsUI() {
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        this.performerSalesBtn = new JButton("Performer sales");
        this.performerSalesBtn.setPreferredSize(new Dimension(300, 30));
        this.performerSalesBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.livehouseSalesBtn = new JButton("Live house sales");
        this.livehouseSalesBtn.setPreferredSize(new Dimension(300, 30));
        this.livehouseSalesBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.rentalSalesBtn = new JButton("Equipment/rental sales report");
        this.rentalSalesBtn.setPreferredSize(new Dimension(300, 30));
        this.rentalSalesBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.livehouseSchedBtn = new JButton("Live house schedule");
        this.livehouseSchedBtn.setPreferredSize(new Dimension(300, 30));
        this.livehouseSchedBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.staffSalaryBtn = new JButton("Staff salary report");
        this.staffSalaryBtn.setPreferredSize(new Dimension(300, 30));
        this.staffSalaryBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.add(this.performerSalesBtn);
        this.add(this.livehouseSalesBtn);
        this.add(this.rentalSalesBtn);
        this.add(this.livehouseSchedBtn);
        this.add(this.staffSalaryBtn);
    }

    /* TODO: They can all use the same listener? */

    public void setBtnAActionListener(ActionListener actionListener) {
        this.performerSalesBtn.addActionListener(actionListener);
    }

    public void setBtnBActionListener(ActionListener actionListener) {
        this.livehouseSalesBtn.addActionListener(actionListener);
    }

    public void setBtnCActionListener(ActionListener actionListener) {
        this.rentalSalesBtn.addActionListener(actionListener);
    }

    public void setBtnDActionListener(ActionListener actionListener) {
        this.livehouseSchedBtn.addActionListener(actionListener);
    }

    public void setBtnEActionListener(ActionListener actionListener) {
        this.staffSalaryBtn.addActionListener(actionListener);
    }
}