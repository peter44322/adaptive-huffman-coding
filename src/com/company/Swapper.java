package com.company;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Swapper {
    private Tree tree;
    Swapper(Tree tree){
        this.tree = tree;
    }

    public Node trySwap(Node targetNode) {
        Node swapable = swappableFor(targetNode);
        if (swapable !=null){
            swap(targetNode,swapable);
            return swapable;
        }else {
            return null;
        }
    }

    private Node swappableFor(Node targetNode){
        Node finalTargetNode = targetNode;
        ArrayList<Node> swapable = (ArrayList<Node>) tree.toArray()
                .stream()
                .filter(node -> canBeSwapped(finalTargetNode,node))
                .collect(Collectors.toList());
        swapable.sort((o1, o2) -> o1.number > o2.number ? 1 : 0);
        if (swapable.isEmpty()){
            return null;
        }
        return swapable.get(0);
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
