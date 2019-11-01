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
        compressedText =
                Arrays.stream(this.text.split(""))
                .map(c -> {
                    char character = c.charAt(0);
                    if (tree.isEmpty()){
                        tree.append(character);
                        tree.traverseOrder(tree.root);
                        System.out.println("character : "+character );
                        return String.valueOf(shortCodeTable.get(character));
                    }

                    Node targetNode = tree.get(tree.root,character);
                    if (targetNode == null) {
                        Node NYT = tree.getNyt(tree.root);
                        String result = NYT.code.toString() + shortCodeTable.get(character);
                        tree.updateWith(character);
                        tree.traverseOrder(tree.root);
                        System.out.println("character : "+character);
                        return result;
                    }else {
                        String result = targetNode.code.toString();
                        tree.updateWith(character);
                        tree.traverseOrder(tree.root);
                        System.out.println("character : "+character);
                        return result;
                    }
                })
                .collect(Collectors.joining(" "));
//        tree.traverseOrder(tree.root);
//        System.out.println(tree.get(tree.root,'A'));
        return compressedText;
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
}
