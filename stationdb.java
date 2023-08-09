package com.example.train;

public class stationdb {
    private String id;
    private String StationName;
    private String PlatformNumber;

    public stationdb() {
    }

    public stationdb(String id, String stationName, String platformNumber) {
        this.id = id;
        StationName = stationName;
        PlatformNumber = platformNumber;
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

    public String getPlatformNumber() {
        return PlatformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        PlatformNumber = platformNumber;
    }
}
