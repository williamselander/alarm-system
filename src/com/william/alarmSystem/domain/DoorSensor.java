package com.william.alarmSystem.domain;

import com.william.alarmSystem.presentation.Colors;

public class DoorSensor extends Sensor {

    public DoorSensor(String id, Location location){
        super(id, location, SensorType.DOOR_SENSOR);
    }


    @Override
    public String getLabel(){
        return  "id: " + super.getId() + super.getLabel() + (isTriggered() ? Colors.RED+ " | Dörr öppnad!" + Colors.RESET : "");
    }

}
