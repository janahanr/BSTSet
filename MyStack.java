public class MyStack {
    myStackNode root;
    static class myStackNode {
        TNode value;
        myStackNode next; //iterating through the next, next, next, etc values until null value is found

        myStackNode(TNode value) {
            this.value = value;
        }
    }
    public boolean isEmpty() { //checking for an empty stack, simple
        if(root == null) //null root indicates empty
            return true;
        else
            return false; //anything in the stack, not empty so false
    }

    public void push(TNode value) { //pushing a value to the top of a list
        myStackNode pushedNode = new myStackNode(value);

        if (root == null) { //simple case, no values in the list, just make the desired value into the 1st one right away
            root = pushedNode;
        } else { //pushed value (most recent) becomes the root
            myStackNode holderTemp = root; //push all the values further into the list
            root = pushedNode;
            pushedNode.next = holderTemp; //reconnect that chain so values can iterate through each other
        }
    }
    public TNode pop() { //pop method to remove the top value from the list
        TNode poppedNode = new TNode(0, null, null); //need this and just use this as a caller so we can actually refer to that new popped val
        // and also be able to return it
        if (root == null) {
            System.out.println("Empty Stack");
        } else {
            poppedNode = root.value; //integer value to be returned
            root = root.next; //cutting chain off of the root, basically just need to pop the root as it is most recent
        }
        return poppedNode; //pop that
    }
    public TNode top() { //similar to lecture "top" function
        if (root == null) { //case where npothing is actually there to be returned
            System.out.println("Empty stack");
            return new TNode(0, null, null); //need a blank thing to refer to but won;t actually be returned
        } else {
            return root.value; //non recursive 
        }
    }
}