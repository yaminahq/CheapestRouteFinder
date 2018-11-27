/**
 * This code was retrieved from the textbook, Algorithms 4th Ed
 * Implements Dijkstra's Shortest Path algorithm to find the shortest path in a graph
 * 
 * @file DijkstraSP.java
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;

import java.util.ArrayList;
import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac DijkstraSP.java
 *  Execution:    java DijkstraSP input.txt s
 *  Dependencies: EdgeWeightedDigraph.java IndexMinPQ.java Stack.java DirectedEdge.java
 *  Data files:   https://algs4.cs.princeton.edu/44sp/tinyEWD.txt
 *                https://algs4.cs.princeton.edu/44sp/mediumEWD.txt
 *                https://algs4.cs.princeton.edu/44sp/largeEWD.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraSP tinyEWD.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (1.05)  0->4  0.38   4->5  0.35   5->1  0.32   
 *  0 to 2 (0.26)  0->2  0.26   
 *  0 to 3 (0.99)  0->2  0.26   2->7  0.34   7->3  0.39   
 *  0 to 4 (0.38)  0->4  0.38   
 *  0 to 5 (0.73)  0->4  0.38   4->5  0.35   
 *  0 to 6 (1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   
 *  0 to 7 (0.60)  0->2  0.26   2->7  0.34   
 *
 *  % java DijkstraSP mediumEWD.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (0.71)  0->44  0.06   44->93  0.07   ...  107->1  0.07   
 *  0 to 2 (0.65)  0->44  0.06   44->231  0.10  ...  42->2  0.11   
 *  0 to 3 (0.46)  0->97  0.08   97->248  0.09  ...  45->3  0.12   
 *  0 to 4 (0.42)  0->44  0.06   44->93  0.07   ...  77->4  0.11   
 *  ...
 *
 ******************************************************************************/

/**
 * The {@code DijkstraSP} class represents a data type for solving the
 * single-source shortest paths problem in edge-weighted digraphs where the edge
 * weights are nonnegative.
 * <p>
 * This implementation uses Dijkstra's algorithm with a binary heap. The
 * constructor takes time proportional to <em>E</em> log <em>V</em>, where
 * <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Each call to {@code distTo(int)} and {@code hasPathTo(int)} takes constant
 * time; each call to {@code pathTo(int)} takes time proportional to the number
 * of edges in the shortest path returned.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DijkstraSP {
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;
	ArrayList<City> cities;

	/**
	 * Computes a shortest-paths tree from the source vertex {@code s} to every
	 * other vertex in the edge-weighted digraph {@code G}.
	 *
	 * @param G
	 *            the edge-weighted digraph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             if an edge weight is negative
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public DijkstraSP(EdgeWeightedDiGraph G, int s, ArrayList<City> cities) {
		edgeTo = new DirectedEdge[G.V()];
		distTo = new double[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		this.cities = cities;
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;
		pq.insert(s, 0.0);
		while (!pq.isEmpty())
			relax(G, pq.delMin());
	}

	// relax edge e and update pq if changed
	private void relax(EdgeWeightedDiGraph G, int v) {
		for (DirectedEdge e : G.adj(v)) {
			if (v != 0) {
				// modification made to the textbook code, checks to see if the current edge's
				// meal is equal to the
				// meal of the previous edge in the path and if so changes the meal
				if (edgeTo[v].meal().getMeal().equals(e.meal().getMeal())) {
					e.setWeight(cities.get(e.to()).getSecondCheapestMeal().getPrice());
					e.setMeal(cities.get(e.to()).getSecondCheapestMeal());
				}
			}
			int w = e.to();
			if (distTo[w] > distTo[v] + e.weight()) {
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.change(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}
	}

	/**
	 * Returns the length of a shortest path from the source vertex {@code s} to
	 * vertex {@code v}.
	 * 
	 * @param v
	 *            the destination vertex
	 * @return the length of a shortest path from the source vertex {@code s} to
	 *         vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public double distTo(int v) {
		return distTo[v];
	}

	/**
	 * Returns true if there is a path from the source vertex {@code s} to vertex
	 * {@code v}.
	 *
	 * @param v
	 *            the destination vertex
	 * @return {@code true} if there is a path from the source vertex {@code s} to
	 *         vertex {@code v}; {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
	 *
	 * @param v
	 *            the destination vertex
	 * @return a shortest path from the source vertex {@code s} to vertex {@code v}
	 *         as an iterable of edges, and {@code null} if no such path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<DirectedEdge> pathTo(int v) {
		if (!hasPathTo(v))
			return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
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
