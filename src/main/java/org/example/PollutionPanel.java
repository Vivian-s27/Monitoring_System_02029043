package org.example;

import javax.swing.*;
import java.awt.*;

public class PollutionPanel extends StatusPanel{
    private final JLabel pollutionLabel;
    private final JPanel pollutionPanel;

    public PollutionPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Air Pollution"));

        pollutionPanel = new JPanel();
        pollutionLabel = new JLabel();

        pollutionPanel.add(pollutionLabel);
        add(pollutionPanel, BorderLayout.CENTER);
    }
    @Override
    public void updateFromSnapshot(EnvironmentSnapshot snapshot) {
        float pollution = snapshot.getPollution();

        String text = String.format(
                "<html>PM2.5 Level: %.1f<br>Air Purifier: %s</html>",
                pollution,
                snapshot.isAirPurifierOn() ? "ON" : "OFF"
        );
        pollutionLabel.setText(text);

        applyWarningBackground(pollutionPanel, snapshot.isPollutionTooHigh());
    }
}
