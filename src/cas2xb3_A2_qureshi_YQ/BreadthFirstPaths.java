/**
 * This code was retrieved from the textbook, Algorithms 4th Ed
 * Implements a breadth first search of a graph to find the shortest path to a destination
 * 
 * @file BreadthFirstPaths.java
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;

import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac BreadthFirstPaths.java
 *  Execution:    java BreadthFirstPaths digraph.txt s
 *  Dependencies: Digraph.java Queue.java Stack.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/mediumDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/largeDG.txt
 *
 *  Run breadth-first search on a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 ******************************************************************************/

/**
 * The {@code BreadthFirstPaths} class represents a data type for finding
 * shortest paths (number of edges) from a source vertex <em>s</em> (or set of
 * source vertices) to every other vertex in the digraph.
 * <p>
 * This implementation uses breadth-first search. The constructor takes time
 * proportional to <em>V</em> + <em>E</em>, where <em>V</em> is the number of
 * vertices and <em>E</em> is the number of edges. Each call to
 * {@link #distTo(int)} and {@link #hasPathTo(int)} takes constant time; each
 * call to {@link #pathTo(int)} takes time proportional to the length of the
 * path. It uses extra space (not including the digraph) proportional to
 * <em>V</em>.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BreadthFirstPaths {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // Is a shortest path to this vertex known?
	private int[] edgeTo; // last vertex on known path to this vertex
	private int[] distTo; // distTo[v] = length of shortest s->v path

	/**
	 * Computes the shortest path from {@code s} and every other vertex in graph
	 * {@code G}.
	 * 
	 * @param G
	 *            the digraph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public BreadthFirstPaths(EdgeWeightedDiGraph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		distTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		bfs(G, s);
	}

	// BFS from single source
	private void bfs(EdgeWeightedDiGraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		marked[s] = true; // Mark the source
		distTo[s] = 0;
		q.enqueue(s); // and put it on the queue.
		while (!q.isEmpty()) {
			int v = q.dequeue(); // Remove next vertex from the queue.
			for (DirectedEdge w : G.adj(v)) {
				if (!marked[w.to()]) { // COULD BE FROM()
					edgeTo[w.to()] = v;
					distTo[w.to()] = distTo[v] + 1;
					marked[w.to()] = true;
					q.enqueue(w.to());
				}
			}
		}
	}

	/**
	 * Is there a directed path from the source {@code s} (or sources) to vertex
	 * {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return {@code true} if there is a directed path, {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		return marked[v];
	}

	/**
	 * Returns the number of edges in a shortest path from the source {@code s} (or
	 * sources) to vertex {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return the number of edges in a shortest path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int distTo(int v) {
		return distTo[v];
	}

	// returns the marked array
	public boolean[] marked() {
		return marked;
	}

	/**
	 * Returns a shortest path from {@code s} (or sources) to {@code v}, or
	 * {@code null} if no such path.
	 * 
	 * @param v
	 *            the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

}
/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/

