/**
 * Reads in data corresponding to cheapest trip problem and solves the problem
 * 
 * @file ReadData.java
 * @author Yaminah Qureshi
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;


/** 
 * Uses the ArrayList, BufferedReader, FileReader, FileWriter and IOException libraries by Java
 */
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadData {

	public static void main(String[] args) throws IOException {
		
		ArrayList<MenuItem> mcDonalds = new ArrayList<MenuItem>(), wendys = new ArrayList<MenuItem>(),
				burgerKing = new ArrayList<MenuItem>(); //array list storing all the menu items at each type of restaurant
		ArrayList<City> cities = new ArrayList<City>(); // stores all the possible cities
		ArrayList<String> citiesKey = new ArrayList<String>(); // assigns a integer value to each city 
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

	
		//create a graph to represent the cities and their connections
		EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(cities.size());
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

		String bfsPath = "";
		
		//construct a breadth first search of the graph form Boston to Minneapolis
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, citiesKey.indexOf("BOSTON"));
		//build a string containing the nodes in the path generated by the bfs
		for (int x : bfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
			if (x == 0)
				bfsPath = citiesKey.get(0) + " " + bfsPath;
			else
				bfsPath = citiesKey.get(x) + " " + bfsPath;
		}
		bfsPath = "BFS: " + bfsPath;
		
		String dfsPath = "";
		//construct a depth first search of the graph form Boston to Minneapolis
		DirectedDFS dfs = new DirectedDFS(G, citiesKey.indexOf("BOSTON"));
		//build a string containing the nodes in the path generated by the dfs
		for (int x : dfs.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
			if (x == 0)
				dfsPath = citiesKey.get(0) + " " + dfsPath;
			else
				dfsPath = citiesKey.get(x) + " " + dfsPath;
		}
		dfsPath = "DFS: " + dfsPath;

		//construct a dijkstra's shortest path of the graph to get from boston to minneapolis taking into consideration the cost of meals in each city
		DijkstraSP sp = new DijkstraSP(G, citiesKey.indexOf("BOSTON"), cities);

		// construct a string containing the shortest path in a table form with info about each city, the meal at each city and the price of the meal
		String dspTable = "";
		for (DirectedEdge e : sp.pathTo(citiesKey.indexOf("MINNEAPOLIS"))) {
			dspTable = String.format("%-20s %-30s %-10s%n", citiesKey.get(e.to()), e.meal().getMeal(), e.weight())
					+ dspTable;
		}
		dspTable = String.format("%-20s %-30s %-10s%n", "BOSTON", "-", "-") + dspTable;
		dspTable = "                                                 \n" + dspTable;
		dspTable = String.format("%-20s %-30s %-10s", "City", "Meal Choice", "Cost Of Meal") + dspTable;

		//output the results of the bfs, dfs and dijkstras algorithm to a file
		FileWriter writer = new FileWriter("data//a2_out.txt");
		writer.write(bfsPath + "\n\n");
		writer.write(dfsPath + "\n\n");
		writer.write(dspTable + "\n\n");
		writer.close();

	}

}