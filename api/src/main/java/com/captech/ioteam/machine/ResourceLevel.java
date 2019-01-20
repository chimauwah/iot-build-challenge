package com.captech.ioteam.machine;

import lombok.Getter;

public enum ResourceLevel {

    FULL("Everything is good"), NORMAL("No problems"), LOW_WARNING("**WARNING**"), LOW_DANGER("**Danger**"), EMPTY("**CRITICAL**");

    @Getter
    String alertLevel;

    ResourceLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }

}
