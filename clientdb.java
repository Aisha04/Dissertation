package com.example.train;

public class clientdb {
    private String id;
    private String TrainNumber;
     private String ArrivalTime;
      private String platformNumber;

    public clientdb() {
    }

    public clientdb(String id, String trainNumber, String arrivalTime, String platformNumber) {
        this.id = id;
        TrainNumber = trainNumber;
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
