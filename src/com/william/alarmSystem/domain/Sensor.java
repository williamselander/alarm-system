package com.william.alarmSystem.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Sensor {
    private String id;
    private Location location;
    private boolean triggered;
    private SensorType type;
    private String dateTriggered;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected Sensor(String id, Location location, SensorType type) {
        setId(id);
        setLocation(location);
        setType(type);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        Objects.requireNonNull(id, "Id kan ej vara null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Id kan ej vara tom");
        }
        this.id = id.trim();
    }

    public SensorType getType(){
        return type;
    }

    private void setLocation(Location location){
        Objects.requireNonNull(location, "Location kan ej vara null");
        this.location = location;
    }

    private void setType(SensorType type) {
        Objects.requireNonNull(type, "Sensortypen kan ej vara null");
        this.type = type;
    }

    public boolean isTriggered() {
        return triggered;
    }

    void trigger() {
        this.triggered = true;
        this.dateTriggered = LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    void reset() {
        this.triggered = false;
        this.dateTriggered = null;
    }


    public String getLabel() {
        if (dateTriggered == null) {
            return   " | Typ: " + type.toString() + " | Plats: " + location + " | Utlöst?: " + isTriggered();
        } else return " | Typ: " + type.toString() + " | Plats: " + location + " | Utlöst: " + dateTriggered;
    }



}


