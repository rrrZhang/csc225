/* NinePuzzle.java
   CSC 225 - Spring 2017
   Assignment 4 - Template for the 9-puzzle
   
   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt
	
   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the 
   empty square. For example, a sample board with the middle square empty is
   
    1 2 3
    4 0 5
    6 7 8
   
   And a solved board is
   
    1 2 3
    4 5 6
    7 8 0
   
   An input file can contain an unlimited number of boards; each will be 
   processed separately.
  
   B. Bird    - 07/11/2014
   M. Simpson - 11/07/2015
*/

import java.util.Scanner;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class NinePuzzle{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;


	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the 
		value 0),return true if the board is solvable and false otherwise. 
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	public static boolean SolveNinePuzzle(int[][] B){
		
		Graph G=BuildNinePuzzleGraph();
		int v=getIndexFromBoard(B);
		int u =0;
		BreadthFirstPaths bfs = new BreadthFirstPaths(G,u);
		if(bfs.hasPathTo(v)){
			
			
	
			
			Iterable<Integer> i=bfs.pathTo(v);
            Iterator it=i.iterator();
            while(it.hasNext()){
                 int path=(Integer)it.next();
                 printBoard(getBoardFromIndex(path));
            }
        }
           
        
    
		return bfs.hasPathTo(v);
	}
	
	public static Graph BuildNinePuzzleGraph(){
		
		Graph G = new Graph(NUM_BOARDS);
		
		int i=0;
		int j=0;
		for(int v=0;v<G.V();v++){
			int [][] B=getBoardFromIndex(v);
        OUTER_LOOP:
			for(i=0;i<3;i++){ 
			    for(j=0;j<3;j++){
				    if(B[i][j]==0){
					    break OUTER_LOOP;
					}
				}
			}

			
			if(i>0){
				newBoard(B,i,j,i-1,j,v,G);
			}else if(i<2){
				newBoard(B,i,j,i+1,j,v,G);
			}else if(j>0){
				newBoard(B,i,j,i,j-1,v,G);
			}else if(j<2){
				newBoard(B,i,j,i,j+1,v,G);
			}
		}
		
		return G;		
	}
	
	public static void newBoard(int [][] B, int oldI, int oldJ, int newI, int newJ, int v, Graph G){
		int n=3;
		int newB [][]=new int[n][n];
		for(int i=0;i<newB.length;i++){
			for(int j=0;j<newB.length;j++){
			    newB[i][j]=B[i][j];
			}
		}
		
		int temp=newB [newI][newJ];
		newB [newI][newJ]=0;
		newB[oldI][oldJ]=temp;
		int u=getIndexFromBoard(newB);
		G.addEdge(u,v);
	}

	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	
	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284. 
	*/
	public static int getIndexFromBoard(int[][] B){
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++){
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for(n = 9; n > 1; n--){
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;
			
			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--){
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for(i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}
	

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read boards until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++){
				for (int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9){
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);

	}
}

class Graph{
     private final int V;
     private Bag<Integer>[] adj;
   
     public Graph(int V){
     	 
         this.V=V;
         adj=(Bag<Integer>[]) new Bag[V];
         for (int v = 0; v < V; v++)
              adj[v] = new Bag<Integer>();
     }
 
     public void addEdge(int v, int w){     	 
         adj[v].add(w);
         adj[w].add(v);
     }
     
     public Iterable<Integer> adj(int v){
          return adj[v]; 
     }
     public int V(){
     	 return V;
     }
}

class BreadthFirstPaths{
       private boolean[] marked; 
       private int[] edgeTo; 
       private final int s; 
       public BreadthFirstPaths(Graph G, int s){      	   
           marked = new boolean[G.V()];
           edgeTo = new int[G.V()];
           this.s = s;
           bfs(G, s);
       }

       private void bfs(Graph G, int s){       	   
           Queue<Integer> queue = new LinkedList<Integer>();
           marked[s] = true; 
           queue.add(s); 
           while (!queue.isEmpty()){           	   
               int v = queue.remove(); 
               for (int w : G.adj(v)){
                    if (!marked[w]){ 
          
                        edgeTo[w] = v; 
                        marked[w] = true; 
                        queue.add(w);
                    }
               }
           }
       }
       public boolean hasPathTo(int v){
           return marked[v]; 
       }
       public Iterable<Integer> pathTo(int v){          	   
           if (!hasPathTo(v)) return null;
           Stack<Integer> path = new Stack<Integer>();
           for (int x = v; x != s; x = edgeTo[x]){                 	 
                 path.push(x);
           }
           path.push(s);
           return path;
       }
}
          


class Bag<Item> implements Iterable<Item>{
	
     private Node first; // first node in list
     private class Node{  	 
         Item item;
         Node next;
     }

     public void add(Item item){
         Node oldfirst = first;
         first = new Node();
         first.item = item;
         first.next = oldfirst;
     }
     
     public Iterator<Item> iterator(){
         return new ListIterator(); 
     }
     private class ListIterator implements Iterator<Item>{
     	 
         private Node current = first;
         public boolean hasNext(){
              return current != null; 
         }
         public void remove() { }
         public Item next(){ 
              Item item = current.item;
              current = current.next;
              return item;
         }
     }
}	
