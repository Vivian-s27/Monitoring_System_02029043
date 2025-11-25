package org.example;
import Feeds.TemperatureFeed;
import Feeds.HumidityFeed;
import Feeds.PollutionFeed;
import EnvironmentalSystem;
public class EnvironmentController {
//Trying to create a controller for the environment

        // Ideal temperatures (°C)
        private static final float NORTH_IDEAL = 23.0f;
        private static final float CENTRAL_IDEAL = 22.0f;
        private static final float SOUTH_IDEAL = 20.0f;
        private static final float TEMP_TOLERANCE = 0.5f;

        private static final int MAX_HUMIDITY_DIFF = 10; // inside ≤ outside + 10%
        private static final float MAX_POLLUTION = 5.0f; // PM2.5

        private final EnvironmentalSystem env;
        private final TemperatureFeed tempFeed;
        private final HumidityFeed humidityFeed;
        private final PollutionFeed pollutionFeed;

        public EnvironmentController() {
            // Error handling
            try {
                env = new EnvironmentalSystem();
                tempFeed = env.getTempFeed();
                humidityFeed = env.getHumidityFeed();
                pollutionFeed = env.getPollutionFeed();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialise EnvironmentalSystem", e);
            }
        }

        public EnvironmentSnapshot readAndControlEnvironment() {
            // Read temperatures
            float northTemp = safeReadTemperature(0);
            float centralTemp = safeReadTemperature(1);
            float southTemp = safeReadTemperature(2);

            // Apply heating control for each ward
            boolean northHeatingOn = controlWardHeating(0, northTemp, NORTH_IDEAL);
            boolean centralHeatingOn = controlWardHeating(1, centralTemp, CENTRAL_IDEAL);
            boolean southHeatingOn = controlWardHeating(2, southTemp, SOUTH_IDEAL);

            // Read humidity
            int insideHumidity = safeReadInsideHumidity();
            int outsideHumidity = safeReadOutsideHumidity();
            int humidityDiff = insideHumidity - outsideHumidity;
            boolean humidityTooHigh = humidityDiff > MAX_HUMIDITY_DIFF;

            // Control dehumidifier
            boolean dehumidifierOn = controlDehumidifier(humidityTooHigh);

            // Read pollution
            float pollution = safeReadPollution();
            boolean pollutionTooHigh = pollution > MAX_POLLUTION;

            // Control air purifier
            boolean airPurifierOn = controlAirPurifier(pollutionTooHigh);

        }}