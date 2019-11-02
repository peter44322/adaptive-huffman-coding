package com.company;

public class Binary {
    private String value;

    Binary(String value){
        this.value = value;
    }

    Binary(int value) {
        this.value = Integer.toBinaryString(value);
    }

    void setDigitsNumber(int digitsNumber){
        if (this.value.length() != digitsNumber){
            this.value = "0" + this.value;
            this.setDigitsNumber(digitsNumber);
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
