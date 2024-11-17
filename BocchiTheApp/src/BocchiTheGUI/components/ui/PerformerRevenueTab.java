package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

import javax.swing.*;
import java.awt.*;

public class PerformerRevenueTab extends TableSelectionUI {

    private JLabel revenueLabel;
    private JLabel quotaLabel;

    public PerformerRevenueTab() {
        super("Performer Revenue", "ID", "Performer Name");
        this.setLoadDataCommand("tab/performer_revenue");
        this.addSearchBoxFilter("Filter by Name", 1);
        this.addButtons("Generate");
        this.setButtonActionCommands("performer_revenue");

    }

    private void addBottomPanel() {

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        revenueLabel = new JLabel("Revenue: 0.00", SwingConstants.CENTER);

        quotaLabel = new JLabel("Quota: 0.00%", SwingConstants.CENTER);

        bottomPanel.add(revenueLabel);
        bottomPanel.add(quotaLabel);

        // Add the bottom panel to the main layout (assumes BorderLayout)
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateRevenue(double revenue) {
        revenueLabel.setText(String.format("Revenue: $%.2f", revenue));
    }

    public void updateQuota(double quota) {
        quotaLabel.setText(String.format("Quota: %.2f%%", quota));
    }

}
