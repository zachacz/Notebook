package com.example.notebook;

public class Note {

    Long number;

    String text;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Note() {
    }

    public Note(Long number, String text) {
        this.number = number;
        this.text = text;
    }

    @Override
    public String toString() {
        return number + ". " + text;
    }
}
