package com.example.train;

public class QuestionDriverdb {
    private String id;
    private String TrainNumber;
    private String StationName;
    private String platformNumber;
   private String Email;
    private String Reason;
    private String late;

    public QuestionDriverdb() {
    }

    public QuestionDriverdb(String id, String trainNumber, String stationName, String platformNumber, String email, String reason, String late) {
        this.id = id;
        TrainNumber = trainNumber;
        StationName = stationName;
        this.platformNumber = platformNumber;
        Email = email;
        Reason = reason;
        this.late = late;
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

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }
}
