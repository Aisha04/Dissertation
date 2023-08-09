package com.example.train;

public class Infodb {
    private String id;
    private String TrainNumber;
    private String Date;
    private String ArrivalTime;
    private String DestinationStation;
    private String platformNumber;
    private String late;
    private String StationName;

    public Infodb() {
    }

    public Infodb(String id, String trainNumber, String date, String arrivalTime, String destinationStation, String platformNumber, String late, String stationName) {
        this.id = id;
        TrainNumber = trainNumber;
        Date = date;
        ArrivalTime = arrivalTime;
        DestinationStation = destinationStation;
        this.platformNumber = platformNumber;
        this.late = late;
        StationName = stationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainNumber() {
        return TrainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        TrainNumber = trainNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getDestinationStation() {
        return DestinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        DestinationStation = destinationStation;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }
}
