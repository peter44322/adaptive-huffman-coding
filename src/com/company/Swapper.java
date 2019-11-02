package com.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

class Swapper {
    private Tree tree;

    Swapper(Tree tree){
        this.tree = tree;
    }

    Node trySwap(Node targetNode) {
        Node swappable = swappableFor(targetNode);
        if (swappable !=null){
            swap(targetNode,swappable);
            return swappable;
        }
        throw new NullPointerException("Cant't Be Swapped");
    }

    private Node swappableFor(Node targetNode){
        ArrayList<Node> swappable = (ArrayList<Node>) tree.toArray()
                .stream()
                .filter(node -> canBeSwapped(targetNode,node))
                .collect(Collectors.toList());
        swappable.sort((o1, o2) -> o1.number > o2.number ? 1 : 0);
        if (swappable.isEmpty()){
            return null;
        }
        return swappable.get(0);
    }

    private boolean canBeSwapped(Node node,Node otherNode){
        return otherNode.number > node.number && node.count >= otherNode.count && !tree.isParent(node,otherNode);
    }

    private void swap(Node node,Node otherNode){
        Node temp = new Node(otherNode);
        otherNode.copy(node);
        node.copy(temp);
        temp.detach();
    }
}
