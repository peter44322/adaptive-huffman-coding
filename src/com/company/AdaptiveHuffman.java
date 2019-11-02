package com.company;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.stream.Collectors;

public class AdaptiveHuffman {
    private String text = "";
    private Tree tree;
    public Hashtable<Character, Binary> shortCodeTable;
    public String compressedText;

    AdaptiveHuffman(String text) {
        this.text = text;
        this.tree = new Tree();
        this.shortCodeTable = this.generateShortCodeTable();
    }

    public String compress() {
        return Arrays.stream(this.text.split(""))
                .map(this::characterToCode)
                .collect(Collectors.joining(" "));
    }

    private Hashtable<Character, Binary> generateShortCodeTable() {
        Hashtable<Character, Binary> result = new Hashtable<>();
        int counter = 0;
        for (char i : this.text.toCharArray()) {
            if (result.get(i) == null) {
                result.put(i, new Binary(counter));
                counter++;
            }
        }
        int digitsNumber = (int) Math.ceil(
                Math.log(result.size()) / Math.log(2)
        );
        result.forEach((k, v) -> v.setDigitsNumber(digitsNumber));
        return result;
    }

    private String characterToCode(String c){
        char character = c.charAt(0);
        String result;
        if (tree.isEmpty()){
            result =  shortCodeTable.get(character).toString();
        }else {
            Node targetNode = tree.get(character);
            if (targetNode == null) {
                Node NYT = tree.getNyt();
                result = NYT.code.toString() + shortCodeTable.get(character);
            } else {
                result = targetNode.code.toString();
            }
        }
        tree.updateWith(character);
        tree.traversePostOrder(tree.root);
        System.out.println(" character : " + character);
        return result;
    }
}
