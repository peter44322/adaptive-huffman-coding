package com.company;

public class Main {

    public static void main(String[] args) {
        int code = 0b00;
        String text = "ABCCCAAAA";

        AdaptiveHuffman adaptiveHuffman = new AdaptiveHuffman(text);

        String compressed = adaptiveHuffman.compress();

        System.out.println("");
        System.out.println(compressed);
    }
}
