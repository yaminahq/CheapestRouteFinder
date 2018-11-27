/**
 * This code was retrieved from the textbook, Algorithms 4th Ed
 * Implements a Edge Weighted Directed Graph abstract data type
 * 
 * @file DirectedEdge.java
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;

import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac EdgeWeightedDigraph.java
 *  Execution:    java EdgeWeightedDigraph digraph.txt
 *  Dependencies: Bag.java DirectedEdge.java
 *  Data files:   https://algs4.cs.princeton.edu/44st/tinyEWD.txt
 *                https://algs4.cs.princeton.edu/44st/mediumEWD.txt
 *                https://algs4.cs.princeton.edu/44st/largeEWD.txt
 *
 *  An edge-weighted digraph, implemented using adjacency lists.
 *
 ******************************************************************************/
/**
 * The {@code EdgeWeightedDigraph} class represents a edge-weighted digraph of
 * vertices named 0 through <em>V</em> - 1, where each directed edge is of type
 * {@link DirectedEdge} and has a real-valued weight. It supports the following
 * two primary operations: add a directed edge to the digraph and iterate over
 * all of edges incident from a given vertex. It also provides methods for
 * returning the number of vertices <em>V</em> and the number of edges
 * <em>E</em>. Parallel edges and self-loops are permitted.
 * <p>
 * This implementation uses an adjacency-lists representation, which is a
 * vertex-indexed array of {@link Bag} objects. All operations take constant
 * time (in the worst case) except iterating over the edges incident from a
 * given vertex, which takes time proportional to the number of such edges.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class EdgeWeightedDiGraph {
	private final int V; // number of vertices
	private int E; // number of edges
	private Bag<DirectedEdge>[] adj; // adjacency lists
	private int[] indegree; // indegree[v] = indegree of vertex v

	/**
	 * Initializes an empty edge-weighted digraph with {@code V} vertices and 0
	 * edges.
	 *
	 * @param V
	 *            the number of vertices
	 * @throws IllegalArgumentException
	 *             if {@code V < 0}
	 */
	public EdgeWeightedDiGraph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<DirectedEdge>();
	}

	/**
	 * Returns the number of vertices in this edge-weighted digraph.
	 *
	 * @return the number of vertices in this edge-weighted digraph
	 */
	public int V() {
		return V;
	}

	/**
	 * Returns the number of edges in this edge-weighted digraph.
	 *
	 * @return the number of edges in this edge-weighted digraph
	 */
	public int E() {
		return E;
	}

	/**
	 * Adds the directed edge {@code e} to this edge-weighted digraph.
	 *
	 * @param e
	 *            the edge
	 * @throws IllegalArgumentException
	 *             unless endpoints of edge are between {@code 0} and {@code V-1}
	 */
	public void addEdge(DirectedEdge e) {
		adj[e.from()].add(e);
		E++;
	}

	/**
	 * Returns the directed edges incident from vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the directed edges incident from vertex {@code v} as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<DirectedEdge> adj(int v) {
		return adj[v];
	}

	/**
	 * Returns the number of directed edges incident from vertex {@code v}. This is
	 * known as the <em>outdegree</em> of vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the outdegree of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int degree(int v) {
		return adj[v].size();
	}

	/**
	 * Returns all directed edges in this edge-weighted digraph. To iterate over the
	 * edges in this edge-weighted digraph, use foreach notation:
	 * {@code for (DirectedEdge e : G.edges())}.
	 *
	 * @return all edges in this edge-weighted digraph, as an iterable
	 */
	public Iterable<DirectedEdge> edges() {
		Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
		for (int v = 0; v < V; v++)
			for (DirectedEdge e : adj[v])
				bag.add(e);
		return bag;
	}

	/**
	 * Returns a string representation of this edge-weighted digraph.
	 *
	 * @return the number of vertices <em>V</em>, followed by the number of edges
	 *         <em>E</em>, followed by the <em>V</em> adjacency lists of edges
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + "\n");
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (DirectedEdge e : adj[v]) {
				s.append(e + "  ");
			}
			s.append("\n");
		}
		return s.toString();
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