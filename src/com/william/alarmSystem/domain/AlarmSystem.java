package com.william.alarmSystem.domain;

import com.william.alarmSystem.presentation.Colors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlarmSystem {
    private final List<Sensor> sensorList = new ArrayList<>();
    private final List<String> eventList = new ArrayList<>();
    private boolean systemArmed = false;


    public void addSensor(String id, Location location, SensorType sensorType){
        Sensor newSensor = createSensor(id, location, sensorType);
        validateUniqueId(newSensor.getId());
        sensorList.add(newSensor);
        eventList.add(Colors.BLUE +"[addSensor] Sensor tillagd: " +  Colors.RESET + newSensor.getLabel() + " | Tillagd: " + getLocalDateTimeString());
    }

    private Sensor createSensor(String id, Location location, SensorType sensorType) {
        switch (sensorType) {
            case DOOR_SENSOR -> {
                return new DoorSensor(id, location);
            }
            case MOTION_SENSOR -> {
                return new MotionSensor(id, location);
            }
            case SMOKE_SENSOR -> {
                return new SmokeSensor(id, location);
            }
            default -> throw new IllegalArgumentException("Sensortypen finns ej");
        }
    }

    // Kontrollerar att ett likadant id inte redan finns
    private void validateUniqueId(String id){
        if (sensorList.stream()
                .anyMatch(sensor -> sensor.getId()
                        .equalsIgnoreCase(id.trim()))) {
            throw new IllegalArgumentException("En sensor med id: " + id + " finns redan");
        }
    }

    public void armSystem(){
        if  (sensorList.isEmpty()) {
            throw new IllegalStateException("Det finns inga sensorer i systemet");
        }
        systemArmed = true;
        eventList.add(Colors.BLUE+"[armSystem] Sensorsystem påslaget: " + Colors.RESET + getLocalDateTimeString());
    }

    public void disarmSystem(){
        if ( sensorList.isEmpty()) {
            throw new IllegalStateException("Det finns inga sensorer i systemet");
        }
        systemArmed = false;
        eventList.add(Colors.BLUE +"[disarmSystem] Sensorsystem Avslaget: " + Colors.RESET + getLocalDateTimeString());
    }

    public boolean isArmed() {
        return systemArmed;
    }

    public List<Sensor> getSensorList(){
        return List.copyOf(sensorList);
    }

    public void resetSystem(){
        sensorList.forEach(Sensor::reset);
        eventList.add(Colors.YELLOW+"[resetSystem] Sensorsystem återställt: " +Colors.RESET +  getLocalDateTimeString());
    }

    public List<String> getEventList() {
        return List.copyOf(eventList);
    }

    public void triggerById(String id){
        Sensor sensor = findSensorById(id);
        if (!systemArmed && sensor.getType() != SensorType.SMOKE_SENSOR) {
            throw new IllegalStateException("Sätt på systemet innan du aktiverar dörr- eller rörelsesensorer");
        }
        if (sensor.isTriggered()) {
            throw new IllegalStateException("Sensorn är redan aktiverad");
        }
        sensor.trigger();
        eventList.add(Colors.RED + "[triggerById] Sensor aktiverad: " + Colors.RESET +sensor.getLabel());
    }

    public void unTriggerById(String id){
        Sensor sensor = findSensorById(id);
        if (!sensor.isTriggered()) {
            throw new IllegalStateException("Finns ingen sensor med id: " + id + " aktiverad");
        }
        sensor.reset();
        eventList.add(Colors.BLUE+"[unTriggerById] Sensor avslagen: "+ Colors.RESET + sensor.getLabel() + " | Avslagen: " +  getLocalDateTimeString() );
    }

    public boolean isAlarmTriggered(){
        return sensorList.stream().anyMatch(Sensor::isTriggered);
    }


    private Sensor findSensorById(String id){
        Objects.requireNonNull(id, "Id kan ej vara null");
        if (id.isBlank()){
            throw new IllegalArgumentException("id kan ej vara tom");
        }
        return sensorList.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sensorn finns ej i systemet"));
    }


    private String getLocalDateTimeString(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public List<String> getSensorStatus(){
        return sensorList.stream().map(Sensor::getLabel).toList();
    }

    public long getTriggeredAlarms(){
        return sensorList.stream().filter(Sensor::isTriggered).count();
    }



}
