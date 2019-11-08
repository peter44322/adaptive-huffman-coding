package com.company;

public class Node {
    char symbol = '*';
    Binary code = null;
    int number = 100;
    int count = 0;
    private Node right = null;
    private Node left = null;
    boolean isNYT = false;


    Node() {
    }

    Node(Node that) {
        this.copy(that);
    }

    Node(boolean isNYT) {
        this.isNYT = isNYT;
    }

    Node(char symbol) {
        this.symbol = symbol;
        this.count = 0;
    }

    void setLeft(Node left) {
        this.left = left;
    }

    void setRight(Node right) {
        this.right = right;
    }

    Node getLeft() {
        return left;
    }

    Node getRight() {
        return right;
    }

    boolean hasRight() {
        return this.right != null;
    }

    boolean hasLeft() {
        return this.left != null;
    }

    @Override
    public String toString() {
        return (this.isNYT ? "NYT" : String.valueOf(this.symbol)) + "-" + this.code + "-" + this.number + "-" + this.count;
    }

    void clear() {
        this.isNYT = false;
    }

    void incrementCount() {
        this.count++;
    }

    void copy(Node that) {
        this.symbol = that.symbol;
        this.count = that.count;
        this.left = that.getLeft();
        this.right = that.getRight();
        this.isNYT = that.isNYT;
    }

    void detach() {
        this.right = null;
        this.left = null;
    }

    boolean hasInformation() {
        return this.symbol != '*' || this.isNYT;
    }
}
