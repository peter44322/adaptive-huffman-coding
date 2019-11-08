package com.company;

import java.util.Arrays;
import java.util.stream.Collectors;

class AdaptiveHuffman {
    private String text = "";
    private Tree tree;
    private String compressed = "";
    private ShortCodeTable shortCodeTable;

    AdaptiveHuffman(String text) {
        this.text = text;
        this.tree = new Tree();
        this.shortCodeTable = new ShortCodeTable(text);
    }

    AdaptiveHuffman(String decompressed, String possibleCharacters) {
        this.compressed = decompressed;
        this.tree = new Tree();
        this.shortCodeTable = new ShortCodeTable(possibleCharacters);
    }

    String compress() {
        this.compressed = Arrays.stream(this.text.split(""))
                .map(this::characterToCode)
                .collect(Collectors.joining(""));
        return this.compressed;
    }

    String decompress() {
        this.text = "";
        tree = new Tree();
        Character character;
        boolean prevCodeIsNyt = false;
        Binary sequence;
        int seqLength, i = 0;
        while (i < this.compressed.length()) {
            if (i == 0 || prevCodeIsNyt) {
                seqLength = shortCodeTable.getDigitsNumber();
                sequence = new Binary(compressed.substring(i, i + seqLength));
                character = shortCodeTable.getByBinary(sequence);
                prevCodeIsNyt = false;
            } else {
                seqLength = 0;
                Node node = null;
                while (node == null) {
                    seqLength++;
                    sequence = new Binary(compressed.substring(i, i + seqLength));
                    node = tree.getByBinary(sequence);
                    if (node != null && !node.hasInformation()) {
                        node = null;
                    }
                }
                character = node.isNYT ? null : node.symbol;
                prevCodeIsNyt = node.isNYT;
            }
            tree.updateWith(character);
            this.text += character == null ? "" : character;
            i += seqLength;
        }

        return this.text;
    }


    private String characterToCode(String c) {
        char character = c.charAt(0);
        String result;
        if (tree.isEmpty()) {
            result = shortCodeTable.get(character);
        } else {
            Node targetNode = tree.get(character);
            if (targetNode == null) {
                result = tree.getNyt().code.toString() + shortCodeTable.get(character);
            } else {
                result = targetNode.code.toString();
            }
        }
        tree.updateWith(character);
        return result;
    }

    String getPossibleCharacters(){
        return shortCodeTable.getPossibleCharacters();
    }
}
