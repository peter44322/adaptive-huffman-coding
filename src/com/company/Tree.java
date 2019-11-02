package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Tree {
    public Node root;
    private Swapper swapper;

    Tree() {
        this.root = new Node();
        this.swapper = new Swapper(this);
    }

    boolean isEmpty() {
        return !this.root.hasLeft() && !this.root.hasRight();
    }

    private void append(char character) {
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
        incrementNodeCount(targetNode);
    }

    Node getNyt() {
        final Node[] result = {null};
        forEach(root, n -> {
            if (n.isNYT) result[0] = n;
        });
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
        forEach(root, n -> {
            if (n.symbol == character) result[0] = n;
        });
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
        final int[] num = {100};
        forEach(root, n -> {
            if (n.hasRight()) {
                num[0]--;
                n.getRight().number = num[0];
            }
            if (n.hasLeft()) {
                num[0]--;
                n.getLeft().number = num[0];
            }
        });
    }

    private void incrementNodeCount(Node targetNode) {
        do {
            Node parent = parent(targetNode);
            Node swaped = swapper.trySwap(targetNode);
            this.codeTheTree(root, "");
            if (swaped != null) {
                targetNode = swaped;
            } else {
                targetNode.incrementCount();
                targetNode = parent;
            }
        } while (!isRoot(targetNode));
        root.incrementCount();
    }

    void forEach(Node node, ForEach forEach) {
        forEach.execute(node);
        if (node.hasLeft()) {
            forEach(node.getLeft(), forEach);
        }
        if (node.hasRight()) {
            forEach(node.getRight(), forEach);
        }
    }

    public void updateWith(char character) {
        Node targetNode = get(character);
        if (targetNode == null) {
            append(character);
        } else {
            incrementNodeCount(targetNode);
        }
    }

    boolean isRoot(Node node) {
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
            if (parent.number == node.number) {
                return true;
            }
            parent = parent(parent);
        }
        return false;
    }

    ArrayList<Node> toArray() {
        ArrayList<Node> nodes = new ArrayList<>();
        forEach(root, node -> {
            nodes.add(node);
        });
        return nodes;
    }
}
