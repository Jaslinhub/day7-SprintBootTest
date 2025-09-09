package org.example.springboottest.Entity;

public class ParkingBoy {
    private static int parkingBoyId=0;
    private String id;
    private String name;

    public ParkingBoy(String name) {
        this.name = name;
        this.id=parkingBoyId++ +"";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
