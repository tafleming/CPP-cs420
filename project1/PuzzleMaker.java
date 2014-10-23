package edu.csupomona.cs420.project1;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleMaker {

	private static String generatePuzzle() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < 9; i++) {
			numbers.add(i);
		}

		String newState = "";

		while (!numbers.isEmpty()) {
			newState = newState + numbers.remove(rand.nextInt(numbers.size()));
		}

		return newState;
	}

	private static boolean checkSolveable(String state) {
		char[] charState = state.toCharArray();
		int inversions = 0;

		for (int i = 0; i < charState.length; i++) {
			if (charState[i] == '0') {
				continue; // don't check 0 (blank) for inversions
			}
			for (int j = i + 1; j < charState.length; j++) {
				if (charState[j] != '0') {
					if (charState[i] > charState[j]) {
						inversions++;
					}
				}
			}
		}
		return ((inversions % 2) == 0);
	}

	public static void main(String[] args) {
		String newState = generatePuzzle();
		if (checkSolveable(newState)) {

			Node startNode = new Node(0, newState); // 26 steps: 650127843
			EightPuzzle puzzle = new EightPuzzle(startNode, false);
			puzzle.runSearch();
			System.out.println("Search complete");
		} else {
			System.out.println("Unsolveable puzzle: " + newState);
		}
	}
}
