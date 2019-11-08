package com.company;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class ShortCodeTable {
    private Hashtable<Character, Binary> table;

    ShortCodeTable(String possibleCharacters){
        generate(possibleCharacters);
    }

    private Hashtable<Character, Binary> generate(String possibleCharacters) {
        this.table = new Hashtable<>();
        final int[] counter = {0};
        Arrays.stream(possibleCharacters.split("")).forEach(i ->{
            if ( this.table.get(i.charAt(0)) == null) {
                this.table.put(i.charAt(0), new Binary(counter[0]));
                counter[0]++;
            }
        });
        int digitsNumber = this.getDigitsNumber();
        this.table.forEach((k, v) -> v.setDigitsNumber(digitsNumber));
        return  this.table;
    }

    String get(char character){
        return this.table.get(character).toString();
    }

    public int getDigitsNumber(){
        return (int) Math.ceil(
                Math.log(this.table.size()) / Math.log(2)
        );
    }

    public Character getByBinary(Binary binary){
        for(Map.Entry entry: table.entrySet()){
            if(binary.equals((Binary) entry.getValue())){
                return (char) entry.getKey();
            }
        }
        throw new NullPointerException("Binary Not found");
    }

    String getPossibleCharacters(){
        return table.keySet().stream().map(String::valueOf).collect(Collectors.joining(""));
    }

}
