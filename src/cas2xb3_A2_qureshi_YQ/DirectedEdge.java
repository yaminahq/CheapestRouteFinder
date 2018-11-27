/**
 * This code was retrieved from the textbook, Algorithms 4th Ed
 * Implements a Directed Edge abstract data type for a graph
 * 
 * @file DirectedEdge.java
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;

/******************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted directed edge.
 *
 ******************************************************************************/
/**
 * The {@code DirectedEdge} class represents a weighted edge in an
 * {@link EdgeWeightedDigraph}. Each edge consists of two integers (naming the
 * two vertices) and a real-value weight. The data type provides methods for
 * accessing the two endpoints of the directed edge and the weight.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DirectedEdge {
	private final int v;// edge source
	private final int w; // edge target
	private double weight; // edge weight
	private MenuItem meal; // modification to textbook code: assigns a menu item to each edge

	/**
	 * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
	 * the given {@code weight}.
	 * 
	 * @param v
	 *            the tail vertex
	 * @param w
	 *            the head vertex
	 * @param weight
	 *            the weight of the directed edge
	 * @throws IllegalArgumentException
	 *             if either {@code v} or {@code w} is a negative integer
	 * @throws IllegalArgumentException
	 *             if {@code weight} is {@code NaN}
	 */
	public DirectedEdge(int v, int w, double weight, MenuItem meal) {
		this.v = v;
		this.w = w;
		this.weight = weight;
		this.meal = meal;
	}

	/**
	 * Returns the weight of the directed edge.
	 * 
	 * @return the weight of the directed edge
	 */
	public double weight() {
		return weight;
	}

	// modification to textbook code: can update the value of the weight of an edge
	public void setWeight(double newWeight) {
		weight = newWeight;
	}

	/**
	 * Returns the tail vertex of the directed edge.
	 * 
	 * @return the tail vertex of the directed edge
	 */
	public int from() {
		return v;
	}

	/**
	 * Returns the head vertex of the directed edge.
	 * 
	 * @return the head vertex of the directed edge
	 */
	public int to() {
		return w;
	}

	// modification: returns the menu item corresponding to the edge
	public MenuItem meal() {
		return meal;
	}

	// modification: changes the menu item corresponding to the edge
	public void setMeal(MenuItem newMeal) {
		meal = newMeal;
	}

	/**
	 * Returns a string representation of the directed edge.
	 * 
	 * @return a string representation of the directed edge
	 */
	public String toString() {
		return String.format("%d->%d %.2f", v, w, weight);
	}
}
/******************************************************************************
 * Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 * This file is part of algs4.jar, which accompanies the textbook
 *
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 *
 *
 * algs4.jar is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * algs4.jar is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * algs4.jar. If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
