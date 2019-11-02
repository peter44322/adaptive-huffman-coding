package com.company;

import java.util.ArrayList;

class Tree {
    Node root;
    private Swapper swapper;

    Tree() {
        this.root = new Node();
        this.swapper = new Swapper(this);
    }

    boolean isEmpty() {
        return !this.root.hasLeft() && !this.root.hasRight();
    }

    private Node append(char character) {
        Node previousNYT = this.getNyt();
        Node targetNode = new Node(character);
        if (previousNYT == null) {
            root.setRight(targetNode);
            root.setLeft(new Node(true));
        } else {
            previousNYT.clear();
            previousNYT.setRight(targetNode);
            previousNYT.setLeft(new Node(true));
        }
        this.codeTheTree(root, "");
        this.setTheTreeNumbers();
        return targetNode;
    }

    Node getNyt() {
        final Node[] result = {null};
        forEach(root, n -> {if (n.isNYT) result[0] = n; });
        return result[0];
    }

    void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.getLeft());
            traversePostOrder(node.getRight());
            System.out.print("\t" + node);
        }
    }

    Node get(char character) {
        final Node[] result = {null};
        forEach(root, n -> { if (n.symbol == character) result[0] = n; });
        return result[0];
    }

    private void codeTheTree(Node node, String prefix) {
        String code = "";
        if (node.hasLeft()) {
            code = prefix + "0";
            node.getLeft().code = new Binary(code);
            codeTheTree(node.getLeft(), code);
        }
        if (node.hasRight()) {
            code = prefix + "1";
            node.getRight().code = new Binary(code);
            codeTheTree(node.getRight(), code);
        }
    }

    private void setTheTreeNumbers() {
        final int[] number = {100};
        forEach(root, n -> {
            if (n.hasRight()) {
                number[0]--;
                n.getRight().number = number[0];
            }
            if (n.hasLeft()) {
                number[0]--;
                n.getLeft().number = number[0];
            }
        });
    }

    private void incrementNodeCount(Node targetNode) {
        do {
            try{
                targetNode = swapper.trySwap(targetNode);
            }catch (NullPointerException e){
                targetNode.incrementCount();
                targetNode = parent(targetNode);
            }
        } while (!isRoot(targetNode));
        codeTheTree(root, "");
        root.incrementCount();
    }

    private void forEach(Node node, ForEach forEach) {
        forEach.execute(node);
        if (node.hasLeft()) forEach(node.getLeft(), forEach);
        if (node.hasRight()) forEach(node.getRight(), forEach);
    }

    void updateWith(char character) {
        Node targetNode = get(character) == null ? append(character) : get(character) ;
        incrementNodeCount(targetNode);
    }

    private boolean isRoot(Node node) {
        return node.code == root.code;
    }

    private Node parent(Node target) {
        final Node[] parent = {null};
        forEach(root, n -> {
            if ((n.hasLeft() && n.getLeft().number == target.number)
                    || (n.hasRight() && n.getRight().number == target.number)) parent[0] = n;
        });
        return parent[0];
    }

    boolean isParent(Node child, Node node) {
        Node parent = parent(child);
        while (parent != null) {
            if (parent.number == node.number) return true;
            parent = parent(parent);
        }
        return false;
    }

    ArrayList<Node> toArray() {
        ArrayList<Node> nodes = new ArrayList<>();
        forEach(root, nodes::add);
        return nodes;
    }
}
