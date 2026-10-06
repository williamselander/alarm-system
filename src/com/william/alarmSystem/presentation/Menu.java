package com.william.alarmSystem.presentation;

import com.william.alarmSystem.domain.*;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final AlarmSystem alarmSystem;

    public Menu(AlarmSystem alarmSystem) {
        this.alarmSystem = alarmSystem;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            mainMenu();
            System.out.println("Välj: ");
            String userInput = scanner.nextLine();
            try {
                switch (userInput) {
                    case "1":
                        boolean menuOneRunning = true;
                        while (menuOneRunning) {
                            String id = getId();
                            SensorType sensorType = getSensorChoice();
                            Location sensorLocation = getLocation();
                            alarmSystem.addSensor(id, sensorLocation, sensorType);
                            confirmationPrintForSensor(id);
                            menuOneRunning = false;
                        }
                        break;
                    case "2":
                        alarmSystem.armSystem();
                        System.out.println(Colors.GREEN+"Systemet är på"+ Colors.RESET);
                        System.out.println();
                        break;
                    case "3":
                        alarmSystem.disarmSystem();
                        System.out.println(Colors.GREEN+"Systemet är av"+Colors.RESET);
                        System.out.println();
                        break;
                    case "4":
                        System.out.println("Skriv id för larmet du vill utlösa: ");
                        System.out.println(">");
                        String idToTrigger = scanner.nextLine();
                        alarmSystem.triggerById(idToTrigger);
                        alarmSystem.getSensorStatus().forEach(System.out::println);
                        System.out.println();
                        break;
                    case "5":
                        System.out.println("Skriv id för larmet du vill stoppa");
                        System.out.println(">");
                        String idToStop = scanner.nextLine();
                        alarmSystem.unTriggerById(idToStop);
                        alarmSystem.getSensorStatus().forEach(System.out::println);
                        System.out.println();
                        break;
                    case "6":
                        alarmSystem.resetSystem();
                        System.out.println(Colors.GREEN+"Återställer systemet..."+Colors.RESET);
                        alarmSystem.getSensorStatus().forEach(System.out::println);
                        System.out.println();
                        break;
                    case "7":
                        List<String> eventList = alarmSystem.getEventList();
                        if (eventList.isEmpty()) {
                            System.out.println(Colors.RED+"Logglistan är tom"+Colors.RESET);
                        }
                        alarmSystem.getEventList().forEach(System.out::println);
                        break;
                    case "8":
                        System.out.println(Colors.GREEN+"Avslutar, ha en trevlig dag!");
                        running = false;
                        break;
                    default:
                        System.out.println("Skriv en siffra mellan 1-8");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println(e.getMessage());
            }  catch (DateTimeParseException e) {
                System.err.println("Datumet för trigger blev fel, försök igen");
            }
        }
    }

    private void mainMenu(){
        System.out.println(Colors.GREEN+"""
                ---------------------
                     ALARMSYSTEM
                ---------------------
                """ + Colors.RESET);
        boolean isSystemArmed = alarmSystem.isArmed();
        boolean isSystemAlarming = alarmSystem.isAlarmTriggered();

        if (isSystemArmed) {
            System.out.println(Colors.PURPLE+"Status: PÅ" +Colors.RESET);
            if (isSystemAlarming) {
                System.out.println(Colors.RED + "Systemet larmar!" + Colors.RESET);
            } else System.out.println(Colors.PURPLE+"Inga larm från systemet"+Colors.RESET);
            System.out.println(Colors.PURPLE+"Sensorer: " + alarmSystem.getSensorList().size()+Colors.RESET);
            System.out.println(Colors.PURPLE+"Aktiverade larm: " + alarmSystem.getTriggeredAlarms() +Colors.RESET);
        } else {
            System.out.println(Colors.PURPLE+"Status: AV"+Colors.RESET);
            System.out.println(Colors.PURPLE+"Larmstatus: " + alarmSystem.getTriggeredAlarms() + " sensorer utlösta"+Colors.RESET);
            System.out.println(Colors.PURPLE+"Sensorer: " + alarmSystem.getSensorList().size()+Colors.RESET);
        }
        System.out.println();
        System.out.println("""
                    1. Lägg till sensor
                    2. Sätt på systemet
                    3. Stäng av systemet
                    4. Utlös specifikt larm
                    5. Stoppa specifikt larm
                    6. Återställ larm
                    7. Se loggar
                    8. Avsluta
                    """);
    }

    private String getId() {
        System.out.println("Ange id för sensor: ");
        System.out.println(">");
        return scanner.nextLine();
    }

    private Location getLocation() {
        while (true) {
            System.out.println("""
                    
                    Ange plats för sensor:
                    1. Hallen
                    2. Kontoret
                    3. Förrådet
                    4. Garaget
                    """);
            System.out.println(">");
            String userLocation = scanner.nextLine();
            switch (userLocation) {
                case "1" -> {
                    return Location.HALLWAY;
                }
                case "2" -> {
                    return Location.OFFICE;
                }
                case "3" -> {
                    return Location.STOREHOUSE;
                }
                case "4" -> {
                    return Location.GARAGE;
                }
                default -> System.out.println(Colors.RED+"Välj en plats för din sensor (1-4)"+Colors.RESET);
            }
        }
    }

    private void confirmationPrintForSensor(String sensorId){
        System.out.println(Colors.GREEN + "Sensor: " + sensorId + " har lagts till" +Colors.RESET);
        System.out.println();
    }

    private SensorType getSensorChoice(){
        while (true) {
            System.out.println("""  
                                                                            
        Vilken sensor vill du lägga till?
        1. Sensor för dörr
        2. Sensor för rörelse
        3. Sensor för rök                                                                     
        """);
            System.out.println(">");
            String userChoice = scanner.nextLine();

            switch (userChoice) {
                case "1" -> {
                    return SensorType.DOOR_SENSOR;
                }
                case "2" -> {
                    return SensorType.MOTION_SENSOR;
                }
                case "3" -> {
                    return SensorType.SMOKE_SENSOR;
                }
                default -> System.out.println(Colors.RED+"Välj vilken sensor du vill lägga till (1-3)"+Colors.RESET);
            }
        }
    }


}



