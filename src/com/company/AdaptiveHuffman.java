package com.company;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.stream.Collectors;

class AdaptiveHuffman {
    private String text = "";
    private Tree tree;
    private Hashtable<Character, Binary> shortCodeTable;

    AdaptiveHuffman(String text) {
        this.text = text;
        this.tree = new Tree();
        this.shortCodeTable = this.generateShortCodeTable();
    }

    String compress() {
        return Arrays.stream(this.text.split(""))
                .map(this::characterToCode)
                .collect(Collectors.joining(" "));
    }

    private Hashtable<Character, Binary> generateShortCodeTable() {
        Hashtable<Character, Binary> result = new Hashtable<>();
        final int[] counter = {0};
        Arrays.stream(this.text.split("")).forEach(i ->{
            if (result.get(i.charAt(0)) == null) {
                result.put(i.charAt(0), new Binary(counter[0]));
                counter[0]++;
            }
        });
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
                result = tree.getNyt().code.toString() + shortCodeTable.get(character);
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
