package org.example;

public class WardStatus {
    private final String name;
    private final int wardId;
    private final float idealTemperature;
    private final float currentTemperature;
    private final boolean heatingOn;

    public WardStatus(String name, int wardId, float idealTemperature, float currentTemperature, boolean heatingOn) {
        this.name = name;
        this.wardId = wardId;
        this.idealTemperature = idealTemperature;
        this.currentTemperature = currentTemperature;
        this.heatingOn = heatingOn;

        WardStatus north = new WardStatus("North Ward", 0, NORTH_IDEAL, northTemp, northHeatingOn);
        WardStatus central = new WardStatus("Central Ward", 1, CENTRAL_IDEAL, centralTemp, centralHeatingOn);
        WardStatus south = new WardStatus("South Ward", 2, SOUTH_IDEAL, southTemp, southHeatingOn);

    }

    public String getName() {
        return name;
    }

    public int getWardId() {
        return wardId;
    }

    public float getIdealTemperature() {
        return idealTemperature;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public boolean isHeatingOn() {
        return heatingOn;
    }

    public boolean isOutOfRange(float tolerance) {
        if (Float.isNaN(currentTemperature)) {
            return true;
        }
        return currentTemperature < idealTemperature - tolerance
                || currentTemperature > idealTemperature + tolerance;
}
}
