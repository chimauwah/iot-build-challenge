package com.captech.ioteam.machine;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Data
@NoArgsConstructor
public class PrintOSMachine {
    private Long id;
    private String pressName;
    private String pressState = "UP";
    private int jobsInQueue = 0;
    private int printedJobs = 0;
    private int sheets = 0;
    private int timeToCompletePendingJobs = 0;
    private String operatorName;
    private String colorTestScore;
    private String maintenanceStatus = "GREEN";
    private String version = "10070.0.0.47";
    private String model = "HP Indigo 10000 Digital Press";
    private String lastTimeStateChanged = "2019-01-16T21:44:55.000+0000";
    private int value = 0;
    private int totalImpsSinceInstallation;
    private int totalPrintedImpsSinceInstallation;
    private int meters;
    private List<Substrate> substrates;
    private ResourceLevel resourceLevel;

    public PrintOSMachine(Machine machine) {
        this.id = machine.getId();
        this.pressName = machine.getName();
        this.substrates = Arrays.asList(new Substrate(), new Substrate());
        double percent = ((double) machine.getQuantity() / (double) machine.getCapacity()) * 100.0;
        if (percent == 100.0) {
            this.resourceLevel = ResourceLevel.FULL;
        } else if (percent >= 80.0) {
            this.resourceLevel = ResourceLevel.NORMAL;
        } else if (percent >= 40.0) {
            this.resourceLevel = ResourceLevel.LOW_WARNING;
        } else if (percent >= 15) {
            this.resourceLevel = ResourceLevel.LOW_DANGER;
        } else {
            this.resourceLevel = ResourceLevel.EMPTY;
        }
    }

    @Data
    @NoArgsConstructor
    private static class Substrate {
        private String name = "Sappi Flo 100# Gloss Cover 20.75x29.5";
        private int Thickness = 236;
        private int Weight = 270;
        private double Length = 527.5;
        private int Width = 750;
        private String Gloss = "Gloss";
        private String DefinedWeightUnit = "GSM";
        private boolean MediaHasMfp = false;
        private boolean CentrallyManaged = false;
    }
}
