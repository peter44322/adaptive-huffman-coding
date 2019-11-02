package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Tree {
    public Node root;

    public Tree() {
        this.root = new Node();
    }

    public boolean isEmpty() {
        return !this.root.hasLeft() && !this.root.hasRight();
    }

    public void append(char character) {
        Node previousNYT = this.getNyt(root);
        Node targetNode = new Node(character);
        if (previousNYT == null){
            root.setRight(targetNode);
            root.setLeft(new Node(true));
        }
        else {
            previousNYT.clear();
            previousNYT.setRight(targetNode);
            previousNYT.setLeft(new Node(true));
        }
        this.codeTheTree(root,"");
        this.setTheTreeNumbers(root,root.number);
        incrementNodeCount(targetNode);
    }

    public Node getNyt(Node node){
        Node result = null;
        if(node.isNYT){
            return node;
        }
        if (node.hasLeft()){
            result = getNyt(node.getLeft());
        }
        if (result == null && node.hasRight()){
            result = getNyt(node.getRight());
        }
        return result;
    }

    private Node lastNode(Node node) {
        Node lastNode = node;
        if (node.hasRight()){
            lastNode =  lastNode(node.getRight());
        }
        if (node.hasLeft()) {
            lastNode = lastNode(node.getLeft());
        }
        return lastNode;
    }

    public void traverseOrder(Node node) {
        if (node != null) {
            traverseOrder(node.getLeft());
            traverseOrder(node.getRight());
            System.out.print( "\t" + node);
        }
    }

    public Node get(Node node,char character){
        Node result = null;
        if(node.symbol == character){
            return node;
        }
        if (node.hasLeft()){
            result = get(node.getLeft(),character);
        }
        if (node.hasRight() && result == null){
            result = get(node.getRight(),character);
        }
        return result;
    }

    private void codeTheTree(Node node,String prefix){
        String code;
        if (node.hasLeft()){
            code  = prefix+  "0";
            node.getLeft().code = new Binary(code);
            codeTheTree(node.getLeft(),code);
        }
        if (node.hasRight()){
            code = prefix + "1";
            node.getRight().code = new Binary(code);
            codeTheTree(node.getRight(),code);
        }
    }

    private void setTheTreeNumbers(Node node, int number){
        int num = number;
        if (node.hasRight()){
            num--;
            node.getRight().number = num ;
            setTheTreeNumbers(node.getRight(),num);
        }
        if (node.hasLeft()){
            num--;
            node.getLeft().number = num;
            setTheTreeNumbers(node.getLeft(),num);
        }
    }

    void incrementNodeCount(Node targetNode) {
        do {
            Node parent = parent(root,targetNode);
            Node finalTargetNode = targetNode;
            ArrayList<Node> swapable = (ArrayList<Node>) toArray()
                    .stream()
                    .filter(node -> finalTargetNode.canSwapWith(node,this::isParent))
                    .collect(Collectors.toList());
            swapable.sort((o1, o2) -> o1.number > o2.number ? 1 : 0);
            if (!swapable.isEmpty()){
                targetNode.swapWith(swapable.get(0));
                targetNode = swapable.get(0);
                this.codeTheTree(root,"");
            }else {
                this.codeTheTree(root,"");
                targetNode.incrementCount();
                targetNode = parent;
            }

        }while (!isRoot(targetNode));
        root.incrementCount();
    }

    void trySwap(Node that){
        forEach(root,node -> that.trySwapWith(node,this::isParent));
    }

    void forEach(Node node,ForEach forEach){
        forEach.execute(node);
        if (node.hasLeft()){
            forEach(node.getLeft(),forEach);
        }
        if (node.hasRight()){
            forEach(node.getRight(),forEach);
        }
    }

    public void updateWith(char character){
        Node targetNode = get(root,character);
        if (targetNode == null) {
            append(character);
        }else {
            incrementNodeCount(targetNode);
        }
    }

    boolean isRoot(Node node){
        return node.number == root.number;
    }

    Node parent(Node node,Node target){
        Node parent = null;
        if (node.hasLeft()){
            if (node.getLeft().code == target.code){
                parent = node;
            }else {
                parent = parent(node.getLeft(),target);
            }
        }
        if (parent == null && node.hasRight() ){
            if (node.getRight().code == target.code){
                parent = node;
            }else {
                parent = parent(node.getRight(),target);
            }
        }
        return parent;
    }

    boolean isParent(Node child,Node node){
        Node parent = parent(root,child);
        while (parent != null){
            if (parent.number == node.number){
                return true;
            }
            parent = parent(root,parent);
        }
        return false;
    }

    ArrayList<Node> toArray(){
        ArrayList<Node> nodes = new ArrayList<>();
        forEach(root,node -> {
            nodes.add(node);
        });
        return nodes;
    }
}
