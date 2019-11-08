package com.company;

public class Main {

    public static void main(String[] args) {
        String text = "ABCCCAAAA";

        AdaptiveHuffman adaptiveHuffman = new AdaptiveHuffman(text);

        String compressed = adaptiveHuffman.compress();

        System.out.println(adaptiveHuffman.getPossibleCharacters());
        System.out.println(compressed);
        System.out.println(adaptiveHuffman.decompress());

    }
}
