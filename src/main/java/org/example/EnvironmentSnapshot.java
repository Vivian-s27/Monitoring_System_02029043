package org.example;

public class EnvironmentSnapshot {
    private WardStatus northWard;
    private WardStatus centralWard;
    private WardStatus southWard;

    private int insideHumidity;
    private int outsideHumidity;
    private int humidityDiff;
    private boolean dehumidifierOn;
    private boolean humidityTooHigh;

    private float pollution;
    private boolean airPurifierOn;
    private boolean pollutionTooHigh;


    public void logStatus(EnvironmentSnapshot snapshot) {
        try {
            String ultrasoundStatus = env.getUltrasoundScannerStatus();
            String ctStatus = env.getCTScannerStatus();

            System.out.println("ENVIRONMENT STATUS LOG ");
            System.out.println(snapshot.toConsoleString());
            System.out.println("Ultrasound Scanner: " + ultrasoundStatus);
            System.out.println("CT Scanner: " + ctStatus);
            System.out.println("  ");
        } catch (Exception e) {
            System.err.println("Failed to read scanner statuses: " + e.getMessage());
            e.printStackTrace();
        }


        public WardStatus getNorthWard () {
            return northWard;
        }

        public void setNorthWard (WardStatus northWard){
            this.northWard = northWard;
        }

        public WardStatus getCentralWard () {
            return centralWard;
        }

        public void setCentralWard (WardStatus centralWard){
            this.centralWard = centralWard;
        }

        public WardStatus getSouthWard () {
            return southWard;
        }

        public void setSouthWard (WardStatus southWard){
            this.southWard = southWard;
        }

        public int getInsideHumidity () {
            return insideHumidity;
        }

        public void setInsideHumidity ( int insideHumidity){
            this.insideHumidity = insideHumidity;
        }

        public int getOutsideHumidity () {
            return outsideHumidity;
        }

        public void setOutsideHumidity ( int outsideHumidity){
            this.outsideHumidity = outsideHumidity;
        }

        public int getHumidityDiff (); {
            return humidityDiff;
        }

        public void setHumidityDiff ( int humidityDiff){
            this.humidityDiff = humidityDiff;
        }

        public boolean isDehumidifierOn () {
            return dehumidifierOn;
        }

        public void setDehumidifierOn ( boolean dehumidifierOn){
            this.dehumidifierOn = dehumidifierOn;
        }

        public boolean isHumidityTooHigh () {
            return humidityTooHigh;
        }

        public void setHumidityTooHigh ( boolean humidityTooHigh){
            this.humidityTooHigh = humidityTooHigh;
        }

        public float getPollution () {
            return pollution;
        }

        public void setPollution ( float pollution){
            this.pollution = pollution;
        }

        public boolean isAirPurifierOn () {
            return airPurifierOn;
        }

        public void setAirPurifierOn ( boolean airPurifierOn){
            this.airPurifierOn = airPurifierOn;
        }

        public boolean isPollutionTooHigh () {
            return pollutionTooHigh;
        }

        public void setPollutionTooHigh ( boolean pollutionTooHigh){
            this.pollutionTooHigh = pollutionTooHigh;
        }

    }
}
