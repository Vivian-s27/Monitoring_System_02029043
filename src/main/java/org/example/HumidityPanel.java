package org.example;

import javax.swing.*;
import java.awt.*;

public class HumidityPanel extends StatusPanel{//Creating a humidity Panel
    private final JLabel humidityLabel;
    private final JPanel humidityPanel;

    public HumidityPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Humidity"));

        humidityPanel = new JPanel();
        humidityLabel = new JLabel();

        humidityPanel.add(humidityLabel);
        add(humidityPanel, BorderLayout.CENTER);
    }
    @Override
    public void updateFromSnapshot(EnvironmentSnapshot snapshot) {
        int inside = snapshot.getInsideHumidity();
        int outside = snapshot.getOutsideHumidity();
        int diff = snapshot.getHumidityDiff();

        String text = String.format(
                "<html>Inside: %d%%<br>Outside: %d%%<br>Difference: %d%%<br>Dehumidifier: %s</html>",
                inside, outside, diff,
                snapshot.isDehumidifierOn() ? "ON" : "OFF"
        );
        humidityLabel.setText(text);

        applyWarningBackground(humidityPanel, snapshot.isHumidityTooHigh());
    }

}
