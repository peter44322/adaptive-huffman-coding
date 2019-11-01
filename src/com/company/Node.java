package com.company;

public class Node implements Cloneable{
    public char symbol = '#';
    public Binary code = null;
    public int number = 100;
    public int count = 0;
    private Node right = null;
    private Node left = null;
    public boolean isNYT = false;
    public boolean lefty ;


    public Node() {
        //root
    }

    public Node(Node that){
        this.copy(that);
    }

    public Node(boolean isNYT) {
        this.isNYT = isNYT;
    }

    public Node(char symbol) {
        this.symbol = symbol;
        this.count = 1;
    }

    public Node(char symbol, Binary code, int number, int count) {
        this.code = code;
        this.symbol = symbol;
        this.number = number;
        this.count = count;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        return (this.isNYT ? "NYT" : String.valueOf(this.symbol)) + "-" + this.code + "#" + this.number + "#" + this.count;
    }

    void clear() {
        this.symbol = '*';
        this.isNYT = false;
        this.count = 1;
    }

    void incrementCount() {
        this.count++;
    }



    boolean canSwapWith(Node that,IsParentFunction isParentFunction){
        return that.number > this.number && this.count >= that.count && !isParentFunction.isParent(this,that);
    }

    void swapWith(Node that){
        Node temp = new Node(that);
        that.copy(this);
        this.copy(temp);
        temp.detach();
    }

    void copy(Node that){
        this.symbol = that.symbol;
        this.count = that.count;
        this.left = that.getLeft();
        this.right = that.getRight();
        this.isNYT = that.isNYT;
    }
    private void detach() {
        this.right = null;
        this.left = null;
    }

    boolean trySwapWith(Node that,IsParentFunction isParentFunction){
        if (this.canSwapWith(that,isParentFunction)){
            this.swapWith(that);
            return true;
        }
        return false;
    }
}
