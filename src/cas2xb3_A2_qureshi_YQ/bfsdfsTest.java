/**
 * Tests the readdata implementation
 * 
 * @file ReadData.java
 * @author Yaminah Qureshi
 * @version 1
 * @date 03/31/2018
 */
package cas2xb3_A2_qureshi_YQ;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class bfsdfsTest {
	ArrayList<MenuItem> mcDonalds = new ArrayList<MenuItem>(), wendys = new ArrayList<MenuItem>(), burgerKing = new ArrayList<MenuItem>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<String> citiesKey = new ArrayList<String>();
	EdgeWeightedDiGraph G;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		
		double price, lat, lon;
		int stateCode, zipCode;
		
		//read in the list of menu items available at any restaurant
				BufferedReader br = new BufferedReader(new FileReader("data//menu.csv"));
				String line = br.readLine();
				while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
					line = line.trim();
					String[] items = line.split(",", -1); // create an array containing each field in the line
					price = Double.parseDouble(items[2].replaceAll("\\$", "")); //convert the price into a double
					MenuItem newMeal = new MenuItem(items[1], price, items[3]); //construct a menu item object based on the datat in the current line
					
					// add the menu item to the menu for the appropriate restaurant
					if (items[0].equals("McDonald’s"))
						mcDonalds.add(newMeal);
					else if (items[0].equals("Burger King"))
						burgerKing.add(newMeal);
					else if (items[0].equals("Wendy's"))
						wendys.add(newMeal);
				}

				//read in a list of US cities and construct a city object for each
				br = new BufferedReader(new FileReader("data//UScities.csv"));
				line = br.readLine();
				while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
					line = line.trim();
					String[] items = line.split(",", -1); // create an array containing each field in the line
					stateCode = Integer.parseInt(items[0]); //convert state code to int
					zipCode = Integer.parseInt(items[1]); //convert zipcode to int
					lat = Double.parseDouble(items[4]); // convert lattitude to double
					lon = Double.parseDouble(items[5]); // convert longitude to double
					City newCity = new City(stateCode, zipCode, items[2], items[3], lat, lon, new ArrayList<String>()); // construct a new city object for the datat in the current line
					cities.add(newCity);// add the new city to the list of cities
				}
				
				//update the cities key list with the each city and its corresponding int key
				for (int i = 0; i < cities.size(); i++) {
					citiesKey.add(cities.get(i).getName());
				}

				//read in the list of mcdonalds restaurants and add them to the cities they are located in
				br = new BufferedReader(new FileReader("data//mcdonalds.csv"));
				while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
					line = line.trim();
					String[] items = line.split(",", -1); // create an array containing each field in the line
					lat = Double.parseDouble(items[1]);
					lon = Double.parseDouble(items[0]);
					
					//check each city to see if the restaurant is located in it
					for (int j = 0; j < cities.size(); j++) {
						// a restaurant is in a city if its within 0.5 of the city's longitude and lattitude
						if (lat > cities.get(j).getLatitude() - 0.5 & lat < cities.get(j).getLatitude() + 0.5) {
							if (lon > cities.get(j).getLongitude() - 0.5 & lon < cities.get(j).getLongitude() + 0.5) {
								cities.get(j).addRestaurant("McDonald’s", mcDonalds);
							}
						}
					}
				}

				//read in the list of wendys restaurants and add them to the cities they are located in
				br = new BufferedReader(new FileReader("data//wendys.csv"));
				while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
					line = line.trim();
					String[] items = line.split(",", -1); // create an array containing each field in the line
					lat = Double.parseDouble(items[1]);
					lon = Double.parseDouble(items[0]);
					for (int j = 0; j < cities.size(); j++) {
						if (lat > cities.get(j).getLatitude() - 0.5 & lat < cities.get(j).getLatitude() + 0.5) {
							if (lon > cities.get(j).getLongitude() - 0.5 & lon < cities.get(j).getLongitude() + 0.5) {
								cities.get(j).addRestaurant("Wendy's", wendys);
							}
						}
					}
				}

				//read in the list of burgerking restaurants and add them to the cities they are located in
				br = new BufferedReader(new FileReader("data//burgerking.csv"));
				while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
					line = line.trim();
					String[] items = line.split(",", -1); // create an array containing each field in the line
					lat = Double.parseDouble(items[1]);
					lon = Double.parseDouble(items[0]);
					for (int j = 0; j < cities.size(); j++) {
						if (lat > cities.get(j).getLatitude() - 0.5 & lat < cities.get(j).getLatitude() + 0.5) {
							if (lon > cities.get(j).getLongitude() - 0.5 & lon < cities.get(j).getLongitude() + 0.5) {
								cities.get(j).addRestaurant("Burger King", burgerKing);
							}
						}
					}
				}
		
		

		
		G = new EdgeWeightedDiGraph(cities.size());
		//read in the list of city connections
		br = new BufferedReader(new FileReader("data//connectedCities.txt"));
		while ((line = br.readLine()) != null) { // repeat while there are unread lines in the file
			line = line.trim();
			String[] items = line.split(",", -1); // create an array containing each field in the line
			int v = citiesKey.indexOf(items[0].trim().toUpperCase());
			int w = citiesKey.indexOf(items[1].trim().toUpperCase());
			double weight = cities.get(w).getCheapestMeal().getPrice();
			//create an edge out of the city connection and add it to the graph, give it a weight equal to the cheapest meal in the city
			DirectedEdge e = new DirectedEdge(v, w, weight, cities.get(w).getCheapestMeal());
			G.addEdge(e);

		}
		

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Tests if each city is connected to an appropriate city in bfs-generated path
	 * 
	 * @throws java.lang.AssertionError if test fails
	 */
	public void testBFSConnections() {
		int count = 0;
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, citiesKey.indexOf("BOSTON"));
        for (int x : bfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
            count+=1;
        }
		int[] path = new int[count];
		int i = 0;
        for (int x : bfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
            path[i] = x;
            i+=1;
        }

        for (int j = count - 1; j > 0 ; j--) {
            assertTrue(contains(G.adj(path[j]), path[j-1]));
        }
		
	}
	
		@Test
		/**
		 * Tests if each city is connected to an appropriate city in dfs-generated path
		 * 
		 * @throws java.lang.AssertionError if test fails
		 */
	public void testDFSConnections() {
		int count = 0;
		DirectedDFS dfs = new DirectedDFS(G, citiesKey.indexOf("BOSTON"));
        for (int x : dfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
            count+=1;
        }
		int[] path = new int[count];
		int i = 0;
        for (int x : dfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
            path[i] = x;
            i+=1;
        }

        for (int j = count - 1; j > 0 ; j--) {
            assertTrue(contains(G.adj(path[j]), path[j-1]));
        }
		
	}
	
		/**
		 * helper function checks if a city is adjacent to another city
		 * 
		 * @param adj the cities adjacent to a city
		 * @param i the index of the city that should be contained in the adjacent cities
		 */
	private boolean contains(Iterable<DirectedEdge> adj, int i) {
		for (DirectedEdge e : adj) {
        		if (e.to() == i) return true;
        }
		return false;
	}
	
	@Test
	/**
	 * Tests if each city in the graph has been checked when determining a path
	 * 
	 * @throws java.lang.AssertionError if test fails
	 */
	public void testBFSMarked() {
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, citiesKey.indexOf("BOSTON"));
		boolean test = true;
		for (int i = 0; i < bfs.marked().length; i++) {
			test = test & bfs.marked()[i];
		}
		assertTrue(test);
	}
	
	@Test
	/**
	 * Tests if each city in the graph has been checked when determining a path
	 * 
	 * @throws java.lang.AssertionError if test fails
	 */
	public void testDFSMarked() {
		DirectedDFS dfs = new DirectedDFS(G, citiesKey.indexOf("BOSTON"));
		boolean test = true;
		for (int i = 0; i < dfs.marked().length; i++) {
			test = test & dfs.marked()[i];
		}
		assertTrue(test);
	}

}
