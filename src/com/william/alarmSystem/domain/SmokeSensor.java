package com.william.alarmSystem.domain;

import com.william.alarmSystem.presentation.Colors;

public class SmokeSensor extends Sensor{

    public SmokeSensor(String id, Location location) {
        super(id, location, SensorType.SMOKE_SENSOR);
    }

    @Override
    public String getLabel(){
        return "id: " + super.getId() + super.getLabel() + (isTriggered() ? Colors.RED +" | Rök upptäckt!" + Colors.RESET : "");
    }

}
