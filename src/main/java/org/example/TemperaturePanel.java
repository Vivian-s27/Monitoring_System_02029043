package org.example;

import javax.swing.*;
import java.awt.*;

public class TemperaturePanel extends StatusPanel{ //Creating the Temp Panel
    private final JPanel northPanel;
    private final JPanel centralPanel;
    private final JPanel southPanel;

    private final JLabel northLabel;
    private final JLabel centralLabel;
    private final JLabel southLabel;

    private static final double Tolerance = 0.5;

    public TemperaturePanel(){
        setLayout(new GridLayout(1, 3));

        northPanel = new JPanel();
        centralPanel = new JPanel();
        southPanel = new JPanel();

        northLabel = new JLabel();
        centralLabel = new JLabel();
        southLabel = new JLabel();

        northPanel.add(northLabel);
        centralPanel.add(centralLabel);
        southPanel.add(southLabel);

        add(northPanel);
        add(centralPanel);
        add(southPanel);
    }
    @Override
    public void updateFromSnapshot(EnvironmentSnapshot snapshot) {
        updateWardPanel(snapshot.getNorthWard(), northPanel, northLabel);
        updateWardPanel(snapshot.getCentralWard(), centralPanel, centralLabel);
        updateWardPanel(snapshot.getSouthWard(), southPanel, southLabel);
    }
    private void updateWardPanel(WardStatus ward, JPanel panel, JLabel label){
        if (ward==null) return;

        String text = String.format(
                "<html><b>%s</b><br>Current: %.1°C<br>Ideal: %.1°C<br>Heating: %s</html>",
                ward.getName(),
                ward.getCurrentTemperature(),
                ward.getIdealTemperature(),
                ward.isHeatingOn() ? "ON" : "OFF"
        );
        label.setText(text);

        boolean outOfRange = ward.isOutOfRange(Tolerance);
        applyWarningBackground(panel, outOfRange);

    }






}
