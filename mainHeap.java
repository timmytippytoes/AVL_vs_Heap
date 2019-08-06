import java.util.*;
import java.lang.*;

/*
Timothy Darrow
Assignment 3


***PLEASE NOTE***

1) I implemented my AVL tree code from assignment 2 within this source code.
   This was done to make it easier to compare and contrast AVL tree process
   times with binary heap times.
   
2) The guidelines are somewhat unspecific on how to deal with duplicate random
   numbers, so I added two options.  One allows duplicates and the other does 
   not.  I thought it would be interesting to compare how each structure is 
   affected by this.

*/

class mainHeap{
   public static void main(String[] args){
      long timeAVL = 0;//holds the total time elapsed for AVLTree manipulation
      long timeHeap = 0;//holds the total time elapsed for heap manipulation
      long timeHolder = 0;//temporary time holder
      int problemSize = 4099;//holds the number of integers that will be manipulated 
      
      //the binary heaps
      binaryHeap heap1 = new binaryHeap(problemSize+1);//problem size + 1
      binaryHeap heap2 = new binaryHeap(problemSize+1);//problem size + 1
      
      String entry = "";//will hold user input
      
      //AVL trees for testing
      AVLNode tree1 = new AVLNode();
      AVLNode tree2 = new AVLNode();
      
      Scanner in = new Scanner(System.in);//Scanner for "Enter" requirement
      thing thingy;//generic variable to hold the value that is to be removed, 
      //and to make it easier to convert to integer for the output below
      int[] numbers = new int[problemSize];//establishes an array of ints with a size of 4099
      Random rand = new Random();
      while(true){//forever loop
      
      /*Since the guidelines are not super clear on what is required, I decided to allow 2 options.
        The first option (case: 1) sets up random numbers and allows duplicates.  The second option
        (case: 2) does not allow duplicates and merely sorts numbers in a random order */
      
         System.out.println("Enter '1' for a random set of numbers that includes duplicates (numbers are 0 to 4098)" + 
                             "\nor enter '2' for a set of randomly ordered numbers without duplicates (numbers are 0 to 4098): ");
         entry = in.nextLine();
         switch(entry){
            
            /*CASE 1: will implement a series of random numbers, in this case randoms maybe duplicated*/
            
            case "1":
               for(int i = 0; i < numbers.length; i++){//intitalizes the "numbers" array then inserts the newly created number into the AVL tree and the binary heap
                  numbers[i] = rand.nextInt(problemSize-1);
                  heap1.insert(new thing(numbers[i]));//fills first heap
                  tree1 = tree1.insert(tree1,new thing (numbers[i]));//fills first AVLtree
               }
               while(true){//forever loop
               
               /* AVLTree testing */
               
                  while(tree1 != null){//continues until the 1st tree is empty
                     thingy = tree1.getVal(tree1.findMin(tree1));//thingy is to be used for integer conversion
                     System.out.printf("The process with a priority of %d is now scheduled to run!\n", thingy.getIntVal());//convert generic to integer
                     timeHolder = System.currentTimeMillis();//records current time
                     tree1 = tree1.remove(tree1, thingy);//reduces the size of tree1
                     thingy = new thing(rand.nextInt(problemSize-1));//changes the removed number to a random number, per the instructions
                     tree2 = tree2.insert(tree2, thingy);//this inserts a new random value into tree2
                     timeHolder = System.currentTimeMillis()-timeHolder;//time elapsed this iteration
                     timeAVL += timeHolder;//total time elapsed
                     System.out.printf("The process with a priority of %d has run out of its timeslice!\n", thingy.getIntVal());//output
                  }
                  
                  tree1 = tree2;//reseting for the next loop
                  tree2 = new AVLNode();//reseting for the next loop
                  
                  /* Binary heap testing */
                  
                  while(heap1.isEmpty() != true){//loop while heap1 is not empty
                     thingy = heap1.getMinVal();//thingy is to be used for integer conversion
                     System.out.printf("The process with a priority of %d is now scheduled to run!\n", thingy.getIntVal());//convert generic to integer, per the guidelines
                     timeHolder = System.currentTimeMillis();//records the current time
                     thingy = heap1.deleteMin();//removes the root
                     thingy = new thing(rand.nextInt(problemSize-1));//changes the removed number to a random number, per the instructions
                     heap2.insert(thingy);//inserts a new random number into the 2nd heap
                     timeHolder = System.currentTimeMillis()-timeHolder;//records the time elasped for this iteration
                     timeHeap += timeHolder;//accumulates total time elapsed
                     System.out.printf("The process with a priority of %d has run out of its timeslice!\n", thingy.getIntVal());//output per the guidelines
                  }
                  
                  //this output prints both the time time taken for the AVL trees and the binary heaps. This is done to more efficiently record their times.
                  
                  System.out.println("\nIt took " + timeAVL + " msecs for all processes in the AVLTree to run out of their timeslices."
                                      + "\nIt took " + timeHeap + " msecs for all processes in the binary heap to run out of their timeslices."
                                      + "\nPlease press \"Enter\" to start the heap sort processes");
                  timeAVL = 0;//resets AVL tree total time elapsed
                  timeHeap = 0;//resets binary heap total time elapsed
                  in.nextLine();//pauses the program until user input is placed in
                  heap1 = heap2;//sets heap1 equal to heap2. This is to prepare for another round of testing
                  heap2  = new binaryHeap(problemSize+1);//clears heap2. This is to prepare for another round of testing.
               }
            
            /*CASE 2: will implement a series of numbers, 0 to 4098, the numbers a randomly distributed within an array*/
            
            case "2":
               numbers = createUniqueRandoms(0,problemSize-1);//builds a randomly distribution of numbers (0 to 4098) and saves it into the numbers array.
               for(int i = 0; i < numbers.length; i++){
                  heap1.insert(new thing(numbers[i]));//inserts the numbers from the array into the binary heap
                  tree1 = tree1.insert(tree1,new thing (numbers[i]));//inserts the numbers from the array into the AVL tree
               }
               
               /* AVL tree testing */
               
               while(true){//forever loop
                  
                  int iterator = 0;//iterator to work through the randomly distributed array
                  numbers = createUniqueRandoms(0,problemSize-1);//builds a randomly distribution of numbers (0 to 4098) and saves it into the numbers array.
                  while(tree1 != null){//continues until the 1st tree is empty
                     
                     thingy = tree1.getVal(tree1.findMin(tree1));//thingy is to be used for integer conversion
                     System.out.printf("The process with a priority of %d is now scheduled to run!\n", thingy.getIntVal());//convert generic to integer, per the guidelines
                     timeHolder = System.currentTimeMillis();//records current time
                     tree1 = tree1.remove(tree1, thingy);//reduces the size of tree1
                     thingy = new thing(numbers[iterator]);//changes the removed number to a random number, per the instructions
                     tree2 = tree2.insert(tree2, thingy);//this inserts a new random value into tree2
                     timeHolder = System.currentTimeMillis()-timeHolder;//records the time elasped for this iteration
                     timeAVL += timeHolder;//accumulates total time elapsed
                     System.out.printf("The process with a priority of %d has run out of its timeslice!\n", thingy.getIntVal());//output per the guidelines
                     iterator++;//will cycle through the randomly distributed numbers array
                  }
                  
                  tree1 = tree2;//tree2 is copied into tree1
                  tree2 = new AVLNode();//tree2 is reset to an empty tree
                  
                  /* Binary heap testing */
                  
                  iterator = 0;//reseting the iterator to access the exact same numbers in the same order as the AVL Tree
                  while(heap1.isEmpty() != true){//&& iterator < numbers.length
                     thingy = heap1.getMinVal();//thingy is to be used for integer conversion
                     System.out.printf("The process with a priority of %d is now scheduled to run!\n", thingy.getIntVal());//convert generic to integer, per the guidelines
                     timeHolder = System.currentTimeMillis();//records current time
                     thingy = heap1.deleteMin();//removes the root
                     thingy = new thing(numbers[iterator]);//changes the removed number to a random number, per the instructions
                     heap2.insert(thingy);//inserts a new random number into the 2nd heap
                     timeHolder = System.currentTimeMillis()-timeHolder;//records the time elasped for this iteration
                     timeHeap += timeHolder;//accumulates total time elapsed for all iterations
                     System.out.printf("The process with a priority of %d has run out of its timeslice!\n", thingy.getIntVal());//output per the guidelines
                     iterator++;//works through the random array
                  }
                  
                  //this output prints both the time time taken for the AVL trees and the binary heaps. This is done to more efficiently record their times.
                  
                  System.out.println("\nIt took " + timeAVL + " msecs for all processes in the AVLTree to run out of their timeslices."
                                      + "\nIt took " + timeHeap + " msecs for all processes in the binary heap to run out of their timeslices."
                                      + "\nPlease press \"Enter\" to start the heap sort processes");
                  timeAVL = 0;//resets the total time elapsed for the AVL tree 877-944-1058
                  timeHeap = 0;//resets the total elasped time for the binary heap
                  in.nextLine();//pauses the program until user input is placed in
                  heap1 = heap2;//sets the heap1 equal to heap2
                  heap2  = new binaryHeap(problemSize+1);//resets heap2 to an empty heap with proper size allocation
               }
            default: System.out.println("Invalid input");//if anything other than 1 or 2 is input
         }
      }
   }
   
   //this function randomly disrtibutes a set of sequential numbers into an array
   
   private static int[] createUniqueRandoms(int from, int to){
      Random rand = new Random();
      int size = to - from + 1;//in case there is a need to narrow the range of random numbers used
      int uniqueNumbers[] = new int[size];//this will hold the randomly distributed numbers
      int allNumbers[] = new int[size];//this will hold all the numbers desired in sequential order
      int sizeTwo = size;//the second size will keep track of where we are at in the allNumbers array
      for (int i = 0; i < size; i++){//intializes array with numbers in sequential order
         allNumbers[i] = i;
      }
      for(int i = 0; i < size; i++){//randomly distributes the numbers into a new array
         int temp = rand.nextInt(sizeTwo);
         uniqueNumbers[i] = allNumbers[temp];//copies a random number into an array
         allNumbers[temp] = allNumbers[sizeTwo-1];//replaces the copied number with the number at sizeTwo-1. This elminates the posibility of duplicates.
         sizeTwo--;//this also must be done to eliminate the posibility of duplicates
      }
      return uniqueNumbers;//returns a randomly distributed array
   }
}


/** begin thing class **/


class thing<E extends Comparable<E>> implements Comparable<thing<E>>{//generic to implement comparable for all types
      private E e;//stores our value
      
      public thing(E x){//constructor
         e = x;
      }
      public E getE(){//returns e, no other purpose but to cover all my bases
         return e;
      }
      
      public thing getThing(){//returns the current 'thing' being accessed
         return this;
      }
      
      public int getIntVal(){// used to fit the requested format
         return Integer.parseInt(toString());//parses a the toString method and converts it to an integer
      }
      
      public int compareTo(thing<E> i){//implementation
         return getE().compareTo(i.getE());//used to compare the two generics
      }
      
      public String toString(){//used in the case of a toString() call
         return  "" + e;
      }
}


/** begin binaryHeap class **/


class binaryHeap<E extends Comparable<E>>{
   private int capacity; //max possible size with this problem
   private thing[] heap;//array to hold the 
   private int size;//maintains the size of the heap
   
   binaryHeap(int i){//constructor that sets up the total capacity of the heap
      capacity = i;
      heap = new thing[capacity];
      size = 0;
   }
   
   binaryHeap(){//default constructor, just in case
      capacity = 0;
      heap = null;
      size = 0;
   }
   
   binaryHeap(thing<E>[] thingArray){//builds a heap based on an array passed in
      capacity = thingArray.length;
      for( int i = 0; i < thingArray.length; i++)
         insert(thingArray[i]);
   }
   
   public void insert(thing<E> value){//will not control for duplicates 
      
      if (size == capacity-1){//max size is always capacity-1, therefore this controls for a full heap
         System.out.println("the heap is full");
         return;
      }
      size++;//grows the heap size
      heap[size] = value;//add the value at the end of the heap
      ascendHeap(size);//call decendHeap with the heap size value, 
   }
   
   public void ascendHeap(int index){//using the heap index, this function ascends the binary tree checking for the proper place to put the new value
      if (index == 1)//if at the end of the heap then return
         return;
      if (heap[index/2].compareTo(heap[index]) > 0){//if the parent is bigger than the child then...
         thing temp = heap[index]; //places the child in a temporary variable
         heap[index] = heap[index/2];//move the larger parent to the child position
         heap[index/2] = temp;//the child is then placed in the parent
         ascendHeap(index/2);//recursively ascends the heap, checking for any more violations
      }//will return is there are no violations
   }
   
   public thing deleteMin(){
      if (isEmpty() == true)//checks to ensure the heap contains something
         return null;//if empty then return null
      thing temp = heap[1];//stores the first item in the heap in a temporary object
      heap[1] = heap[size];//stores the last item in the heap in the root
      heap[size] = null;//deletes the last item in the heap
      size--;//reduces the heap size by one
      descendHeap(1);//decends the heap and places the value in the root into the correct location
      return temp;//returns the deleted root value
   }
   
   public void descendHeap(int index){
      if(size == 2)//handles heaps with size 2
         if (heap[1].compareTo(heap[2]) > 0){//checks if the child is greater than the parent
            thing temp = heap[2];//stores the child in temporary variable
            heap[2] = heap[1];//stores the the parent in the child position
            heap[1] = temp;//stores the former child in the parent or root
         }
      if(index*2 >= size)//if the index that we will look at shortly is larger than the size of the heap then return to the caller
         return;
      //the following will handle any problem where the heap size is greater than 2
      if(heap[index*2].compareTo(heap[index*2+1]) == -1 ){//if the right child is larger than left child
         if(heap[index].compareTo(heap[index*2]) == 1){//if the parent is larger than the left child
            thing temp = heap[index];//store the parent in a variable
            heap[index] = heap[index*2];//replace the parent with  the smaller left child
            heap[index*2] = temp;//place the former parent into the left child position
            descendHeap(index*2);//recursively continue down the heap         
         }
      }
      else{//if the left child is larger than the right
         if(heap[index].compareTo(heap[index*2+1]) == 1 ){//if the parent is larger than the right child
            thing temp = heap[index];//store the parent
            heap[index] = heap[index*2+1];//replace the parent with the right child
            heap[index*2+1] = temp;//the former parent is them placed in the right child
            descendHeap(index*2+1);//recursively descend the heap looking for further violations
         }
      }
   }
   
   public boolean isEmpty(){//returns true if the heap is empty
      if (heap[1] == null)
         return true;
      else
         return false;
   }
   
   public boolean isFull(){//returns true if the heap is full
      if (size == capacity-1)
         return true;
      else
         return false;
   }
   
   public thing getMinVal(){//returns the root of the heap
      return heap[1];
   }
   
   public void printHeap(){//prints out the heap array
      System.out.println();
      for(int i = 1; i < size+1; i++)
         System.out.printf(heap[i] + " ");
      System.out.println();
   }
}



/* AVL Tree stuff. Implemented this for added simplicity in testing */


class AVLNode<E extends Comparable<E>>{
   private thing val;//holds data
   private AVLNode left;//left child
   private AVLNode right;//right child
   
   //constructors to cover all posibilities
   
   public AVLNode(){//creates an empty node
      left = null;
      right = null;
      val = null;
   }
   
   public AVLNode(thing<E> x){//creates node with only a value no children
      left = null;
      right = null;      
      val = x;
   }
   
   public AVLNode(AVLNode l, AVLNode r, thing<E> x){//creates a node with two children
      left = l;
      right = r;
      val = x;
   }
   
   public AVLNode(AVLNode t, thing<E> x){//if only one child node is sent in
      if (t.val.compareTo(x) < 0)
         left = t;
      else if (t.val.compareTo(x) < 0)
         right = t;
   }
   
   //end constructors
   
   //public methods
   
   public AVLNode insert(AVLNode t, thing<E> x){//intserts nodes into the AVL tree
      if (t == null || (t.left == null && t.right == null && t.val == null)){// if the node is empty or if the node is empty, then create a new node
         return new AVLNode(null,null, x);
      } 
      if (x.compareTo(t.val) > 0){//tests if x > the node's value
         t.right = insert(t.right, x);//recursive call to insert to find an empty child in the proper location
         if (height(t.right) - height(t.left) == 2)//tests for an AVL tree violation
            if(x.compareTo(t.right.val) > 0)//if right right nodes then rotate left to correct
               t = rotateLeft(t);
            else//else we know that there is a left right violation therefore the violating node is rotated right then left
               t = doubleRotateRight(t);
      }
      else if (x.compareTo(t.val) < 0){
         t.left = insert(t.left, x);//recursive call to insert to find an empty child in the proper location
         
         if (height(t.left) - height(t.right) == 2)//if right right nodes then rotate right to correct
            if(x.compareTo(t.left.val) < 0)
               t = rotateRight(t);
            else//else we know that there is a right left violation therefore the violating node is rotated left then right
               t = doubleRotateLeft(t);
      }
      else{//if the value already exists in the tree then return the tree back to the caller
         return t;
      }
      return t;//java forces me to have this here
   }
   
   public AVLNode remove (AVLNode t, thing<E> x){//removes nodes from an AVL tree
    //  AVLNode t2;//
      if(t == null)//returns null if not found
         return null;
      else if (x.compareTo(t.val) < 0){//if x < the value passed in 
         t.left = remove(t.left, x);// then set the left child = to the recursive call of remove
      }
      else if (x.compareTo(t.val) > 0){//if x < the value passed in
         t.right = remove(t.right, x);// then set the right child = to the recursive call of remove
      }
      else if(t.left != null && t.right != null){//if the value is found then check to see if there are children trees
         t.val = findMin(t.right).val;//copies the minimum value on the right side child tree
         t.right = remove(t.right, t.val);//this will find the copied value and remove it 
      }
      else//if the value is found and the one child is null
         if(t.left != null)//if the left child contains data then return it's left child to the caller, this removes the node
            return t.left;
         else//if the right child contains data then return it's right child to the caller, this removes the node
            return t.right;
      if (height(t.left) - height(t.right) == 2)//checks for violations and does the same as insert's checks
            if(t.val.compareTo(t.left.val) > 0)
               t = rotateRight(t);  
            else
               t = doubleRotateLeft(t);
      else if (height(t.right) - height(t.left) == 2)//checks for violations and does the same as insert's checks
            if(t.val.compareTo(t.right.val) < 0)
               t = rotateLeft(t);
            else
               t = doubleRotateRight(t);    
      return t;//java forces this 
   }
   
   public AVLNode findMin(AVLNode t){//finds the minimum value in a tree
      if (t == null)//if the tree is empty return null
         return null;
      if (t.val.compareTo(new thing (0)) == 0)//if found return the smallest value node I don't need this but since 0 is the lowest this tree will go it made sense to do
         return t;
      else if (t.left == null)//if the left child empty then return the root
         return t;
      else{
         return findMin(t.left);//recursive call to findMin() that continues down until the smallest value is found
      }   
   }
   
   public int max(int a, int b){//finds the largest of two values
      if (a > b)
         return a;
      else
         return b;
   }
   
   public int height(AVLNode t){//finds the height of each child
      int h1;
      int h2;
      if (t == null)//if the passed in node is null then it has a value of -1
         return -1;
      else{
         h1 = height(t.left);//recursive call of height, this will find the left child's height
         h2 = height(t.right);//recursive call of height, this will find the right child's height
         return max(h1,h2)+1;//accumulates as the recursive call acsends the tree after reaching a bottom
      }
   } 
   
   public AVLNode rotateRight(AVLNode t1){//rotates a tree to the Right to correct right violations
      AVLNode t2 = t1.left;//new node to hold the left of the the node sent into the method
      t1.left = t2.right;//this overwrites the passed in node with the right child of t2
      t2.right = t1;//this set the node passed in, to the right side of t2
      return t2;//the new node, t2, is returned
   }
   
   public AVLNode rotateLeft(AVLNode t1){//this is the exact mirror of rotateRight
      AVLNode t2 = t1.right;
      t1.right = t2.left;
      t2.left = t1;
      return t2;
   }
   
   public AVLNode doubleRotateLeft(AVLNode t3){//this method corrects left right violations
      AVLNode t = t3.left;//a new node that is set to the left side of the node passed in
      t3.left = rotateLeft(t);//the new node is rotated left and then assigned to the left child of the node passed in
      return rotateRight(t3);//the node passed into the function is then rotated right
   }
   
   public AVLNode doubleRotateRight(AVLNode t3){//this is an exact mirror of doubleRotateLeft
      AVLNode t = t3.right;
      t3.right = rotateRight(t);
      return rotateLeft(t3);
   }      
   
   public thing getVal(AVLNode t){//accessor moethod to access the value held within the node
      return t.val.getThing();
   }
   
   
   
   /*
         NOTE: The following helper methods are for practice and fun.
               Most of them are to print out a small tree in a nice neat format.
   */
   
   
   
   public String toString(){//for calls toString()
      return toString(this)+"";//this will print out the entire tree in a pyramid format
   }
   
   
   
   public String toString(AVLNode t){//prints the tree in a pyramid format, too complicated for large trees
      Queue<AVLNode> q = new LinkedList<>();
      Queue<AVLNode> q2 = new LinkedList<>();
      q.add(t);
      AVLNode cur;
      String s = "";
      for (int i = 0; i < height(t)+1; i++){
         cur = q.peek();
         q2 = queuer(q);
         s += treePrinter(q, cur);
         cur = q2.peek();
         q = queuer(q2);
         s += treePrinter(q2, cur);
      }
      
      return s;
   }
   
   public String treePrinter(Queue<AVLNode> q, AVLNode cur){//this method prints out a tree line by line in the desired format.  Good for small trees, terrible for large ones
      int totalHeight = height(this)+1;
      int level = height(this)-height(cur);
      int largestTree = largestTreeFinder(q);
      int size = q.size();
      int temp = 0;
      boolean[][] links = new boolean[size][2];
      int counter = 0;
      String s = "";
      for (int i = 0; i < Math.pow(2,largestTree) - 1 ; i++){
         s += " ";
      }

      while(!q.isEmpty()){
         s += q.remove().val;
         for (int i = 0; i < Math.pow(2,largestTree)-1 ; i++){
            s += " ";
         }
         s += " ";
         for (int i = 0; i < Math.pow(2,largestTree) -1; i++){
               s += " ";
         }
         
      }

      if(cur !=null)
         s += "\n";
      return s;
   }
   
   public int largestTreeFinder(Queue<AVLNode> q){//finds the largest tree within a queue of AVLNodes and returns an int height value
      int temp1 = 0;
      int largest = height(q.peek());
      if (q.size() > 1){
         for(int i = 0; i < q.size();i++){
            temp1 = height(q.peek());
            q.add(q.remove());
            if(temp1 > largest)
               largest = temp1;
         }
      }
      if (largest == -1)
         return 0;
      else
         return largest;
   }
  
   public Queue<AVLNode> queuer(Queue<AVLNode> q){//takes a queue of AVLNodes and places each nodes children into a new node
      Queue<AVLNode> q2 = new LinkedList<>();
      thing e = new thing(" ");
      thing e2 = new thing(" ");
      for(int i = 0; i< q.size();i++){
         if(q.peek().left != null)
            q2.add(q.peek().left);
         else if (q.peek().left == null)
            q2.add(new AVLNode(null, null, e));
         if(q.peek().right != null)
            q2.add(q.peek().right);
         else if (q.peek().right == null)
            q2.add(new AVLNode(null, null, e2));
         q.add(q.remove());       
      }
      return q2;
   }

 
   public int heightDifference(AVLNode t){//finds the difference in childern heights
      if(t == null)
         return 0;
      return height(t.left) - height(t.right);
   }



   public AVLNode find(AVLNode t, thing<E> x){//finds a node with a given value
      if (t == null)
         return null;
      if(t.val.compareTo(x) == 0)
         return t;
      else if(t.val.compareTo(x) > 0)
         return find(t.left, x);
      else 
         return find(t.right, x);
   }
   
   /*end of superfluous practice code*/
}
