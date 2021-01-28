public class TNode{
    int element;
    TNode left;
    TNode right;
    TNode(int i, TNode l, TNode r)
    { element =i; left = l; right = r; }
    public void printNode(){
        System.out.println(element);
    }
}//end class
