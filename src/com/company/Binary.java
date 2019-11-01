package com.company;

public class Binary {
    public String value;
    public int base10;
    private int digitsNumber;

    public Binary(String value){
        this.value = value;
        this.digitsNumber = value.length();
    }

    public Binary(int value) {
        this.base10 = value;
        this.value = Integer.toBinaryString(value);
        this.digitsNumber = this.value.length();
    }

    public void setDigitsNumber(int digitsNumber){
        this.digitsNumber = digitsNumber;
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
