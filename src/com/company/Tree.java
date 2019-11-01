package com.company;

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
        if (previousNYT == null){
            root.setRight(new Node(character));
            root.setLeft(new Node(true));
        }
        else {
            previousNYT.clear();
            previousNYT.setRight(new Node(character));
            previousNYT.setLeft(new Node(true));
//            while (!previousNYT.isRoot()){
//                Node parent = previousNYT.parent;
//                forEach(root, parent::trySwapWith);
//                previousNYT = parent;
//            }

        }
        this.codeTheTree(root,"");
        this.setTheTreeNumbers(root,root.number);
//        this.setTheTreeCounters(root);
    }

    public Node getNyt(Node node){
        Node result = null;
        if(node.isNYT){
            return node;
        }
        if (node.hasLeft()){
            result = getNyt(node.getLeft());
        }
        if (node.hasRight() && result == null){
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

//    private int setTheTreeCounters(Node node){
//        int count = 0;
//        if (node.hasLeft()){
//            count += setTheTreeCounters(node.getLeft());
//        }
//        if (node.hasRight()){
//            count += setTheTreeCounters(node.getRight());
//        }
//        node.count = count == 0 ? node.count : count;
//        return node.count;
//    }

    private void setTheTreeNumbers(Node node, int number){
        if (node.hasRight()){
            node.getRight().number = number - 1 ;
            setTheTreeNumbers(node.getRight(),number-1);
        }
        if (node.hasLeft()){
            node.getLeft().number = number - 2 ;
            setTheTreeNumbers(node.getLeft(),number-2);
        }
    }

    void incrementNodeCount(Node targetNode) {
        while (!isRoot(targetNode)){
            Node finalTargetNode = targetNode;
            forEach(root, n -> finalTargetNode.trySwapWith(n,this::isParent));
            targetNode.incrementCount();
            finalTargetNode.copy(targetNode);
            targetNode = parent(root,finalTargetNode);
        }
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
            if (node.getLeft().number == target.number){
                parent = node;
            }else {
                parent = parent(node.getLeft(),target);
            }
        }
        if (node.hasRight() && parent == null){
            if (node.getRight().number == target.number){
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
}
