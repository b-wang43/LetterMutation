package letterMutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * LetterMutation.java
 * Printing out the path of conversions from the start word to the end word using BFS
 * ICS4UE
 * @author Blair Wang
 * @version 1.0: December 16, 2021
 */
public class LetterMutation {
	//variables
	static final char BLANK_LETTER = '_';
	static final String TRANSITION = " -> ";
	static final String CANNOT_FIND = "No Possible Solution!";
	static private Map<String, List<String>> adjacencyList; //word variation -> words
	static private Map<String, String> parentMap; //child wordnode -> parent wordnode
	static private Set<String> possibleWords; //all words in dictionary of same lengtj
	static private Stack<String> reversePath;
	private boolean found;
	private String start, end;
	
	//create instant of class to perform mutations
	LetterMutation(String start, String end, Set<String> dictionary){
		this.start = start;
		this.end = end;
		possibleWords = pruneDictionary(dictionary);
		adjacencyList = new HashMap<>();
		parentMap = new HashMap<>();
		reversePath = new Stack<>();
		setFound(false);
	}
	
	//only including words of the same length as start and end
	public HashSet<String> pruneDictionary(Set<String> dictionary) {
		HashSet<String> possible = new HashSet<>();
		for(String word : dictionary) {
			if(word.length() == start.length()) {
				possible.add(word);
			}
		}
		return possible;
	}
	
	//constructing adjacency between words
	public boolean buildGraph() {
		if(!possibleWords.contains(end)) {
			System.out.println(CANNOT_FIND);
			return false;
		}
		
		for(String word : possibleWords) {
			for(int i = 0; i < word.length(); i++) {
				String variation = word.substring(0,i) + BLANK_LETTER + word.substring(i + 1); //creating substring of current word
				if(!adjacencyList.containsKey(variation)) { //creating new arraylist if this variation is new
					adjacencyList.put(variation, new ArrayList<>());
				}
				adjacencyList.get(variation).add(word); //adding word into varation's list
			}
		}
		return true;
	}

	// search through the adjacencies, and stop searching when a path from start to end is found (since it is breadth first, this will always be shortest)
	public void pathSearch() {
		if(!buildGraph()) {
			return; //don't search if graph does not include end
		}
		Queue<String> searchQueue = new LinkedList<>();
		Set<String> visited = new HashSet<>(); //store already visited words
		
		//adding start word into the collections
		searchQueue.add(start);
		visited.add(start);
		while(!searchQueue.isEmpty() && !isFound()) {
			String word = searchQueue.poll();
			for(int i = 0; i < word.length(); i++) {
				String editedWord = word.substring(0,i) + BLANK_LETTER + word.substring(i + 1); //create variations of polled word
				if(adjacencyList.containsKey(editedWord)) { //check all variations of polled word
					for(String nextWord : adjacencyList.get(editedWord)) { //get all adjacent words from shared variations
						if(nextWord.equals(end)) {
							parentMap.put(end, word);
							setFound(true);
						}else {
							if(possibleWords.contains(nextWord) && !visited.contains(nextWord)) {
								searchQueue.add(nextWord);
								visited.add(nextWord);
								parentMap.put(nextWord, word); //add a pathway between the linked words
							}
						}
					}
				}
			}
		}
		
		//print error message if no path can be found
		if(!isFound()) {
			System.out.println(CANNOT_FIND);
		}else {
			printPath(end);
		}
	}

	//recursively output pathway by finding the parents of each word [backwards traversal]
	public void printPath(String word) {
		if(word.equals(start)) {
			reversePath.push(word);
			while(reversePath.size() != 0) {
				// pop all words and print
				String nextWord = reversePath.pop();
				if(!nextWord.equals(end)) {
					System.out.print(nextWord + TRANSITION);
				}else {
					System.out.println(end);
				}
			}
			return;
		}
		//add word into stack [stack will have end words at the bottom, start word on top]
		reversePath.add(word);
		printPath(parentMap.get(word));
	}
	
	public boolean isFound() {
		return this.found;
	}
	
	public void setFound(boolean b) {
		this.found = b;
	}

}