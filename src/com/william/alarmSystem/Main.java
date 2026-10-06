package com.william.alarmSystem;

import com.william.alarmSystem.presentation.Menu;
import com.william.alarmSystem.domain.*;


public class Main {

    public static void main(String[] args) {
        AlarmSystem alarmSystem = new AlarmSystem();

        try {
            Menu menu = new Menu(alarmSystem);
            alarmSystem.addSensor("smokeHall1", Location.HALLWAY, SensorType.SMOKE_SENSOR );
            alarmSystem.addSensor("motionGarage1", Location.GARAGE, SensorType.MOTION_SENSOR);
            alarmSystem.addSensor("doorOffice1", Location.OFFICE, SensorType.DOOR_SENSOR);
            alarmSystem.addSensor("motionHallway1", Location.HALLWAY, SensorType.MOTION_SENSOR);
            menu.showMenu();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }


}
