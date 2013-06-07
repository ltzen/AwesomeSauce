/**
 * 
 */
package edu.tamu.srl.util.math.stat;

import java.util.ArrayList;

/**
 * @author hammond
 * @copyright Tracy Hammond, Sketch Recognition Lab, Texas A&M University
 */
public class Stat0D {
	
	
	/**
	 * Returns the factorial of a number
	 * @param num
	 * @return factorial of a number
	 */
	public static int factorial(int num){
		int countfactorial = 1;
		for(int i = 1; i <= num; i++){
			countfactorial *=i;
		}
		return countfactorial;
	}
	
	
	/**
	 * Returns a list of all of the permutations for a list of numbers up to the count
	 * @param count the number of integers in the list
	 * @return returns an array of count! lists
	 */
	public static ArrayList<ArrayList<Integer>> getPermutations(int count){
		return getPermutations(count, new ArrayList<ArrayList<Integer>>());		
	}
	

	/**
	 * Recursive helper method to get all of the permutations of a list of numbers
	 * @param total highest number in the method
	 * @param permutations the permutations thus far
	 * @return a list with all of the permutations
	 */
	private static ArrayList<ArrayList<Integer>> getPermutations(int total, ArrayList<ArrayList<Integer>> permutations){
		ArrayList<ArrayList<Integer>> templist = new ArrayList<ArrayList<Integer>>();//(ArrayList<ArrayList<Integer>>)permutations.clone();
		if(permutations.size() == 0){
			//initialize with 0
			ArrayList<Integer> alist = new ArrayList<Integer>();
			alist.add(1);
			templist.add(alist);
			return getPermutations(total, templist);
		}
		int count = permutations.get(0).size();
		if(count == total){
			//we are done
			return permutations;
		}
		for(ArrayList<Integer> permutation : permutations){
			for(int i = 0; i <= count; i++){
				ArrayList<Integer> newpermutation = (ArrayList<Integer>)permutation.clone();
				newpermutation.add(i, count + 1);
				templist.add(newpermutation);
			}
		}
		return getPermutations(total,templist);
	}

}
