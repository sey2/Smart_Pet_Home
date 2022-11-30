package com.smart_pet;

public class DiaryDTO {
    private int img;
    private String humidityText;
    private String tempText;
    private String dateText;
    private String timeText;
    private String bobTimeText;

    private String filename;

    public DiaryDTO(int img, String humidityText, String tempText, String dateText, String timeText, String bobTimeText,
                    String filename) {
        this.img = img;
        this.humidityText = humidityText;
        this.tempText = tempText;
        this.dateText = dateText;
        this.timeText = timeText;
        this.bobTimeText = bobTimeText;
        this.filename = filename;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setHumidityText(String humidityText) {
        this.humidityText = humidityText;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public void setBobTimeText(String bobTimeText) {
        this.bobTimeText = bobTimeText;
    }

    public int getImg() {
        return img;
    }

    public String getHumidityText() {
        return humidityText;
    }

    public String getTempText() {
        return tempText;
    }

    public String getDateText() {
        return dateText;
    }

    public String getTimeText() {
        return timeText;
    }

    public String getBobTimeText() {
        return bobTimeText;
    }

    public String getFilename() {return filename;}
}
