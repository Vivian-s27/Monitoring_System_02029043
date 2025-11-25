package org.example;
import Feeds.TemperatureFeed;
import Feeds.HumidityFeed;
import Feeds.PollutionFeed;
import EnvironmentalSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WardSensorsApp extends JFrame {
    private final EnvironmentController controller;
    private final TemperaturePanel temperaturePanel;
    private final HumidityPanel humidityPanel;
    private final PollutionPanel pollutionPanel;
    private final JLabel errorLabel;

    // Counter for 30 seconds
    private int secondsCounter = 0;

    public WardSensorsApp() {

        // Controller from EnvironmentalSystem
        controller = new EnvironmentController();
        env = new EnvironmentalSystem();

        // Creating Panels
        temperaturePanel = new TemperaturePanel();
        humidityPanel = new HumidityPanel();
        pollutionPanel = new PollutionPanel();
        errorLabel = new JLabel("Status: OK");
        errorLabel.setForeground(Color.BLACK);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Grid: one row for temperature with 3 wards, one row for humidity, one for pollution
        JPanel gridPanel = new JPanel(new GridLayout(3, 1));

        gridPanel.add(temperaturePanel);
        gridPanel.add(humidityPanel);
        gridPanel.add(pollutionPanel);

        add(gridPanel, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);

        // Timer: every second, reads sensors, updates UI and every 30 seconds log to console
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUIFromSensors();
            }
        });
        timer.start();
    }

    private void updateUIFromSensors() {
        try {
            EnvironmentSnapshot snapshot = controller.readAndControlEnvironment();

            // Update panels (polymorphic call via AbstractStatusPanel)
            temperaturePanel.updateFromSnapshot(snapshot);
            humidityPanel.updateFromSnapshot(snapshot);
            pollutionPanel.updateFromSnapshot(snapshot);

            errorLabel.setText("Status: OK");
            errorLabel.setForeground(Color.BLACK);

            // Log every 30 seconds
            secondsCounter++;
            if (secondsCounter % 30 == 0) {
                controller.logStatus(snapshot);
            }
        } catch (Exception ex) {
            // Error handling
            errorLabel.setText("ERROR: " + ex.getMessage());
            errorLabel.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WardSensorsApp app = new WardSensorsApp();
            app.setVisible(true);
        });
    }
    private float safeReadTemperature(int wardId) {
        try {
            return tempFeed.getTemperature(wardId);
        } catch (Exception e) {
            System.err.println("Failed to read temperature for ward " + wardId + ": " + e.getMessage());
            e.printStackTrace();
            return Float.NaN;
        }
    }

    private int safeReadInsideHumidity() {
        try {
            return humidityFeed.getInsideHumidity();
        } catch (Exception e) {
            System.err.println("Failed to read inside humidity: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    private int safeReadOutsideHumidity() {
        try {
            return humidityFeed.getOutsideHumidity();
        } catch (Exception e) {
            System.err.println("Failed to read outside humidity: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    private float safeReadPollution() {
        try {
            return pollutionFeed.getPollution();
        } catch (Exception e) {
            System.err.println("Failed to read pollution: " + e.getMessage());
            e.printStackTrace();
            return Float.NaN;
        }
    }

    private boolean controlWardHeating(int wardId, float currentTemp, float ideal) {
        try {
            if (Float.isNaN(currentTemp)) {
                // If sensor is broken, don't change heating
                return env.isHeatingOn(wardId);
            }

            boolean currentlyOn = env.isHeatingOn(wardId);

            if (currentTemp < ideal - TEMP_TOLERANCE) {
                if (!currentlyOn) {
                    env.turnHeatingOn(wardId, true);
                    currentlyOn = true;
                }
            } else if (currentTemp > ideal + TEMP_TOLERANCE) {
                if (currentlyOn) {
                    env.turnHeatingOn(wardId, false);
                    currentlyOn = false;
                }
            }

            return currentlyOn;
        } catch (Exception e) {
            System.err.println("Error controlling heating for ward " + wardId + ": " + e.getMessage());
            e.printStackTrace();
            try {
                return env.isHeatingOn(wardId);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    private boolean controlDehumidifier(boolean humidityTooHigh) {
        try {
            boolean currentlyOn = env.isDehumudifierOn();

            if (humidityTooHigh && !currentlyOn) {
                env.turnDehumudifierOn(true);
                currentlyOn = true;
            } else if (!humidityTooHigh && currentlyOn) {
                env.turnDehumudifierOn(false);
                currentlyOn = false;
            }

            return currentlyOn;
        } catch (Exception e) {
            System.err.println("Error controlling dehumidifier: " + e.getMessage());
            e.printStackTrace();
            try {
                return env.isDehumudifierOn();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    private boolean controlAirPurifier(boolean pollutionTooHigh) {
        try {
            boolean currentlyOn = env.isAirPurifierOn();

            if (pollutionTooHigh && !currentlyOn) {
                env.turnAirPurifierOn(true);
                currentlyOn = true;
            } else if (!pollutionTooHigh && currentlyOn) {
                env.turnAirPurifierOn(false);
                currentlyOn = false;
            }

            return currentlyOn;
        } catch (Exception e) {
            System.err.println("Error controlling air purifier: " + e.getMessage());
            e.printStackTrace();
            try {
                return env.isAirPurifierOn();
            } catch (Exception ex) {
                return false;
            }
        }
    }
}


}
