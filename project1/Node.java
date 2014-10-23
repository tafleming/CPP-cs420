package edu.csupomona.cs420.project1;

public class Node {
	
	private int manhattan;
	private int misplaced;
	private int gCost;
	private String state;
	private Node parent;
	private int zeroPosx;
	private int zeroPosy;
	
	
	public Node(int gCost, String newState) {
		this.gCost = gCost;
		state = newState;
	}
	public int getManhattan() {
		return manhattan;
	}
	public void setManhattan(int manhattan) {
		this.manhattan = manhattan;
	}
	public int getMisplaced() {
		return misplaced;
	}
	public void setMisplaced(int misplaced) {
		this.misplaced = misplaced;
	}
	
	public void setGCost(int cost) {
		gCost = cost;
	}
	
	public int getGCost() {
		return gCost;
	}
	
	public int getTotalCost(boolean heuristicChoice) {
		if (heuristicChoice) {
			return gCost + misplaced;
		} else {
			return gCost + manhattan;
		}
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public void display() {
		System.out.println("manhattan: " + manhattan);
		System.out.println("misplaced: " + misplaced);
		System.out.println("gcost: " + gCost);
		System.out.println("state: " + state);
	}
	
	public int getZeroPosx() {
		return zeroPosx;
	}
	public void setZeroPosx(int zeroPosx) {
		this.zeroPosx = zeroPosx;
	}
	public int getZeroPosy() {
		return zeroPosy;
	}
	public void setZeroPosy(int zeroPosy) {
		this.zeroPosy = zeroPosy;
	}	
	
	@Override
	public boolean equals(Object current) {
		return (((Node) current).getState()).equals(this.getState());
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
}
