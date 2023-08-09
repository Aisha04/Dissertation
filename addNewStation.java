package com.example.train;

public class addNewStation {
    private String id;
    private String StationName;

    public addNewStation() {
    }

    public addNewStation(String id, String stationName) {
        this.id = id;
        StationName = stationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }
}
