package com.example.train;

public class Questiondb {
    private String id;
    private String TrainNumber;
    private String StationName;
    private String platformNumber;
    private String question;
    private String Email;

    public Questiondb() {
    }

    public Questiondb(String id, String trainNumber, String stationName, String platformNumber, String question, String email) {
        this.id = id;
        TrainNumber = trainNumber;
        StationName = stationName;
        this.platformNumber = platformNumber;
        this.question = question;
        Email = email;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
