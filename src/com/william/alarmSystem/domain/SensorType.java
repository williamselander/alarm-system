package com.william.alarmSystem.domain;

public enum SensorType {
    SMOKE_SENSOR("Smokesensor"),
    MOTION_SENSOR("Motionsensor"),
    DOOR_SENSOR("Doorsensor");

    private final String cleanText;

    SensorType(String cleanText){
        this.cleanText = cleanText;
    }

    @Override
    public String toString(){
        return cleanText;
    }


}


