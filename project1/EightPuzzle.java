package edu.csupomona.cs420.project1;

import java.util.HashMap;
import java.util.PriorityQueue;

public class EightPuzzle {

	private final char[] goalStae = {'0','1', '2','3','4','5','6','7','8'};
	private Node goalState;
	private PriorityQueue<Node> frontier;
	private HashMap<char[], Integer> explored;
	
	public EightPuzzle(Node initial, Node goal) {
		frontier = new PriorityQueue<Node>();
		explored = new HashMap<char[], Integer>();
		goalState = goal;
		frontier.add(initial);
	}
	
	private void generateSuccessors(Node currentState) {
		
	}
	
	private void manhattan(Node currentState) {
		
	}
	
	private void misplacedTiles(Node currentState) {
		
	}
	
	private int getZeroPosition(Node currentState) {
		int x, y;
		for (y = 0; y< 3; y++) {
			for (x=0; x< 3; x++) {
				if ((goalStae[(y*3)+x]) == '0') {
					return (y*3) + x; //use this to get zero position. or alternatively get coords of it.
				}					  //then you can use that to easily swap positions. 
			}
		}
		return 0;
	}
	
}
