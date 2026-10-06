package com.william.alarmSystem.domain;

public enum Location {
    HALLWAY,
    OFFICE,
    STOREHOUSE,
    GARAGE;
    @Override
    public String toString(){
        return name().toLowerCase();
    }
}
