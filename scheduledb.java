package com.example.train;

public class scheduledb {
    private String id;
    private String TrainNumber;
    private String Date;
    private String ArrivalTime;
    private String platformNumber;

    public scheduledb() {
    }

    public scheduledb(String id, String trainNumber, String date, String arrivalTime, String platformNumber) {
        this.id = id;
        TrainNumber = trainNumber;
        Date = date;
        ArrivalTime = arrivalTime;
        this.platformNumber = platformNumber;
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

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }
}
