package com.example.grocerylistapplogin;

public class DataClass {

    private String dataName;
    private String dataAmount;
    private String key;




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getDataName() {

        return dataName;
    }
    public String getDataAmount() {

        return dataAmount;
    }

    public void setDataName(String dataName) {

        this.dataName = dataName;
    }
    public void setDataAmount(String dataAmount) {

        this.dataAmount = dataAmount;
    }

    public DataClass(String dataName, String dataAmount) {

        this.dataName = dataName;
        this.dataAmount = dataAmount;
    }

    public DataClass(){


    }
}
