public class BSTSet{
    private TNode root;
    
    public BSTSet(){
        root = null;
    }//Runtime:1
    
    public BSTSet(int[] input){
        if(input.length == 0){//checks for empty array
            root = null;
        }else {
            int[] send= new int[input.length];
            int j=0;
            int zeroes=0;
            for (int i = 0; i < input.length; i++){
                int count=0;
                for(int k=0; k<send.length; k++){
                    if(input[i]==send[k]){//checks if there are any repeated values
                        count=1;
                        break;
                    }
                    if(input[i]==0){// checks if there are any zeores
                        zeroes=1;
                    }
                }
                if(count!=1){//avoids repeated values
                    send[j]=input[i];
                    j++;
                }
            }
            int[] reduced= new int[0];
            if(zeroes==0){
                reduced= new int[j];
                for(int l=0; l<j;l++){
                    reduced[l]=send[l];
                }
            }
            else{// takes into account zeroes
                j++;
                reduced= new int[j];
                reduced[0]=0;
                for(int l=1; l<j;l++){
                    reduced[l]=send[l-1];
                }
            }
            root= shortBuild(reduced, 0, reduced.length-1);
        }
    }//Runtime:n^2

    public TNode shortBuild(int[] input, int first, int last){
        int[] arrayInOrder = sortingAlgorithm(input);
        if(first>last){ 
            return null;
        }
        int mid = (first + last)/2;
        TNode start = new TNode(arrayInOrder[mid], null, null);
        start.left = shortBuild(arrayInOrder, first, mid-1); 
        start.right = shortBuild(arrayInOrder, mid+1, last);
        return start;
    }

    public TNode getRoot(){
        return root;
    }

    private int[] sortingAlgorithm(int num[]){ //insertion sort
        int n = num.length;
        for (int i = 1; i < n; i++) {
            int hold = num[i];
            int j = i - 1;
            while (j >= 0 && num[j] > hold) {
            	num[j + 1] = num[j];
                j = j - 1;
            }
            num[j + 1] = hold;
        }
        return num;
    }

    public boolean isIn(int v){
        return isInRecursive(v,root);
    }//Runtime:log(n)

    public boolean isInRecursive(int v, TNode start){
        if(start==null){
            return false;
        }
        else if(start.element==v){
            return true;
        }
        else if(start.element>v){
            return isInRecursive(v, start.left);
        }
        else if(start.element<v){
            return isInRecursive(v, start.right);
        }
        else{
            return false;
        }
    }

    public void add(int v){
        addRecursive(v, root);
    }//Runtime:log(n)

    public TNode addRecursive(int v, TNode start){
        if(start==null){
            start= new TNode(v,null,null);
        }
        else if(start.element>v){
            start.left= addRecursive(v, start.left);
        }
        else if(start.element<v){
            start.right=addRecursive(v, start.right);
        }
        return start;
    }
    public boolean remove(int v){
        if(!isIn(v))
            return false;
        else{
            removeRecursive(root, v);
            return true;
        }
    }//Runtime:log(n)
    private TNode removeRecursive(TNode start, int v){
        if(v < start.element){
            start.left = removeRecursive(start.left, v);
        }
        else if(v > start.element){
            start.right = removeRecursive(start.right, v);
        }
        else if(start.left != null && start.right != null){
            start.element = smallest(start.right).element; 
            start.right = removesmallest(start.right);
        }
        else if(start.left == null && start.right == null){
            start=null;
        }
        else if(start.left != null){
            start = start.left;
        }
        else{
            start = start.right;
        }
        return start;
    }
    private TNode smallest(TNode start){ 
        TNode minimumNode = start;
        while(start.left != null){
            minimumNode = start.left;
            start = start.left;
        }
        return minimumNode;
    }
    private TNode largest(TNode start){
        TNode maximumNode = start;
        while(start.right != null){
            maximumNode = start.right;
            start = start.right;
        }
        return maximumNode;
    }
    private TNode removesmallest(TNode start){
        if(start.left != null)
            start.left = removesmallest(start.left);
        else
            start = start.right; 
        return start;
    }

    public BSTSet union(BSTSet s){
        int[] set= new int[this.size()+s.size()];
        int[] sArr= new int[s.size()];
        intoArray(root, set);
        intoArray(s.root,sArr);
        for(int i=0; i<s.size(); i++){
            set[i+this.size()]=sArr[i];
        }
        BSTSet fin= new BSTSet(set);
        return fin;
    }//Runtime:n

    public BSTSet intersection(BSTSet s){
        int[] set= new int[this.size()];
        int[] thisArr= new int[this.size()];
        intoArray(root, thisArr);
        int j=0;
        for(int i=0; i<size(); i++){
            if(s.isIn(thisArr[i])){
                set[j]=thisArr[i];
                j++;
            }
        }
        int[] reducedSet= new int[j];
        for(int k=0; k<j; k++){
            reducedSet[k]=set[k];
        }
        BSTSet fin= new BSTSet(reducedSet);
        return fin;
    }//Runtime:nlog(n)

    public BSTSet difference(BSTSet s){
        int[] set= new int[this.size()];
        int[] thisArr= new int[size()];
        intoArray(root, thisArr);
        int j=0;
        for(int i=0; i<size(); i++){
            if(!s.isIn(thisArr[i])){
                set[j]=thisArr[i];
                j++;
            }
        }
        int[] reducedSet= new int[j];
        for(int k=0; k<j; k++){
            reducedSet[k]=set[k];
        }
        BSTSet fin= new BSTSet(reducedSet);
        return fin;
    }//Runtime:nlog(n)

    public void intoArray(TNode t, int[] set){
        if (t != null) {
            intoArray(t.left,set);

            int j=0;
            while(set[j]!=0){
                j++;
            }
            set[j]=t.element;
            intoArray(t.right,set);
        }
    }


    public int size(){
        if(root == null)
            return 0;
        return(sizeRecursive(root, 0));
    }//Runtime:n
    private int sizeRecursive(TNode start, int iterations){ 
        if(start!=null) {
            iterations++; //
            iterations = sizeRecursive(start.left, iterations); //
            iterations = sizeRecursive(start.right, iterations); //
        }
        return iterations; //
    }

    public int height () { //
        return heightRecursive(this.root); //
    }

    private int heightRecursive(TNode start) {
        if (start == null) {
            return -1;
        } else {
            int h=(int)((Math.log(size())) / (Math.log(2)));
            return h;
            //
        }
    }

    public void printBSTSet() {
        if (root == null) {
            System.out.println("Empty Set");
        } else {
            printBSTSet(root);
            System.out.print("\n");
        }
    }
    private void printBSTSet(TNode t) {
        if (t != null) {
            printBSTSet(t.left);
            System.out.print(t.element + "\t");
            printBSTSet(t.right);
        }
    }

    public void printNonRec() {
        System.out.print("The following shows the printed set:");
        MyStack stack = new MyStack(); //
        TNode current = root;
        while (current != null) {
            stack.push(current); //
            current = current.left;
        }
        current = stack.top();
        while (current != largest(root)) { //
            current = stack.pop(); //
            
            System.out.print((current.element) + ", "); //
            if (current.right != null) { //
                current = current.right;
                while (current != null) { //
                    stack.push(current);
                    current = current.left;
                }
            }
        }
    }
}
   