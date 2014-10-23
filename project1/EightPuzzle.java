package edu.csupomona.cs420.project1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class EightPuzzle {

	private final String goalState = "012345678";
	private final char[] goalAsChar = goalState.toCharArray();
	private final int initSize = 11;
	private final int puzzleSize = 3;
	private int totalNodes;
	private final boolean heuristicChoice;
	private PriorityQueue<Node> frontier;
	private HashMap<String, Integer> explored;

	public EightPuzzle(Node initial, boolean heuristic) {
		frontier = new PriorityQueue<Node>(initSize, new NodeComparator());
		explored = new HashMap<String, Integer>();
		heuristicChoice = heuristic;
		totalNodes = 0;
		setHeuristic(initial);
		frontier.add(initial);
	}

	public void runSearch() {
		Node current;
		while (!frontier.isEmpty()) {
			current = frontier.poll();

			if (goalCheck(current)) {

				printPath(current);
				System.out.println("Goal Reached! Total nodes: " + totalNodes);
				return;
			}

			if (!inExploredList(current)) {
				generateSuccessors(current);

				explored.put(current.getState(),
						current.getTotalCost(heuristicChoice));
			}
		}
	}

	private boolean goalCheck(Node current) {
		return (current.getState().equals(goalState));
	}

	private void printPath(Node current) {
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(current);
		while (current.getParent() != null) {
			list.add(current.getParent());
			current = current.getParent();
		}
		System.out.println("Path steps: " + (list.size() - 1));
		for (int i = list.size() - 1; i > -1; i--) {
			System.out.println(list.get(i).getState());
		}
	}

	private void generateSuccessors(Node current) {
		char[] currentState = current.getState().toCharArray();
		ArrayList<char[]> successors = new ArrayList<char[]>();

		getZeroPosition(current);
		int x = current.getZeroPosx();
		int y = current.getZeroPosy();

		swap(currentState, successors, x, y, x + 1, y); // all possible moves													
		swap(currentState, successors, x, y, x - 1, y); // from the position,
		swap(currentState, successors, x, y, x, y + 1); // not all will be legal
		swap(currentState, successors, x, y, x, y - 1);

		addSuccessors(successors, current);

	}

	private void addSuccessors(ArrayList<char[]> successors, Node parent) {
		Node newNode;
		String nextState = "";
		int nextCost;
		char[] currentState = parent.getState().toCharArray();

		for (int i = 0; i < successors.size(); i++) {
			currentState = successors.get(i);
			for (int j = 0; j < currentState.length; j++) {
				nextState = nextState + currentState[j];
			}
			nextCost = parent.getGCost();
			newNode = new Node(nextCost + 1, nextState);
			setHeuristic(newNode);

			if (inExploredList(newNode)) {
				if (explored.get(newNode.getState()) > newNode
						.getTotalCost(heuristicChoice)) {
					explored.remove(newNode.getState());
					explored.put(newNode.getState(),
							newNode.getTotalCost(heuristicChoice));
				}
			} else {
				frontier.add(newNode);
				newNode.setParent(parent);
			}
			nextState = "";
		}
	}

	private boolean inExploredList(Node current) {
		return (explored.containsKey(current.getState()));
	}

	private void swap(char[] state, ArrayList<char[]> successors, int posx,
			int posy, int newposx, int newposy) {
		char[] currentState = Arrays.copyOf(state, state.length);

		if (newposx > puzzleSize - 1 || newposy > puzzleSize - 1) { // don't attempt swap if
			return;													// out of bounds
		} else if (newposx < 0 || newposy < 0) {
			return;
		}

		int oldPos = (posx * puzzleSize) + posy;
		int newPos = (newposx * puzzleSize) + newposy;
		char temp = currentState[newPos];
		currentState[newPos] = currentState[oldPos];
		currentState[oldPos] = temp;

		successors.add(currentState);
		totalNodes++;

	}

	private void setHeuristic(Node current) {
		if (heuristicChoice) {
			misplacedTiles(current);
		} else {
			manhattan(current);
		}
	}

	private void manhattan(Node current) {
		int manhattanDistance = 0;
		char[] currentState = current.getState().toCharArray();
		char currentChar;
		int posx, posy;
		int x, y;
		for (x = 0; x < puzzleSize; x++) {
			for (y = 0; y < puzzleSize; y++) {
				currentChar = currentState[(x * puzzleSize) + y];
				if (currentChar != '0') {
					posx = Integer.parseInt(Character.toString(currentChar)) / puzzleSize;
					posy = Integer.parseInt(Character.toString(currentChar)) % puzzleSize;
					manhattanDistance += Math.abs(posx - x) + Math.abs(posy - y);
				}
			}
			
			
		}
		current.setManhattan(manhattanDistance);
	}

	private void misplacedTiles(Node current) {
		char[] currentState = current.getState().toCharArray();

		int count = 0;
		for (int i = 0; i < goalAsChar.length; i++) {
			if (currentState[i] == '0') {
				continue;
			}
			if (currentState[i] != goalAsChar[i]) {
				count++;
			}
		}

		current.setMisplaced(count);
	}


	private void getZeroPosition(Node current) { //modify to get position of any tile
		char[] currentState = current.getState().toCharArray();
		int x, y;
		for (y = 0; y < puzzleSize; y++) {
			for (x = 0; x < puzzleSize; x++) {

				if ((currentState[(x * puzzleSize) + y]) == '0') {
					current.setZeroPosx(x);
					current.setZeroPosy(y);
				}
			}
		}
	}

	private class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node arg0, Node arg1) {

			return arg0.getTotalCost(heuristicChoice)
					- arg1.getTotalCost(heuristicChoice);
		}

	}
}
