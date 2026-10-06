package com.william.alarmSystem.domain;

import com.william.alarmSystem.presentation.Colors;

public class MotionSensor extends Sensor {

    public MotionSensor(String id, Location location){
        super(id, location, SensorType.MOTION_SENSOR);
    }

    @Override
    public String getLabel(){
        return "id: " + super.getId() + super.getLabel() + (isTriggered() ? Colors.RED + " | Rörelse upptäckt" + Colors.RESET : "");
    }
}
