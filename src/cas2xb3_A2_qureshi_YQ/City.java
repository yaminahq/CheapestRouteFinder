/**
 * Implements a City absract data type
 * 
 * @file City.java
 * @author Yaminah Qureshi
 * @version 1
 * @date 03/31/2018
 */
package cas2xb3_A2_qureshi_YQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class City {
	
	private int stateCode;
	private int zipCode;
	private String stateAbbrv;
	private String name;
	private double lattitude;
	private double longitude;
	private ArrayList<String> restaurants;
	private ArrayList<MenuItem> meals = new ArrayList<MenuItem>();
	
	/**
	 * constructs a city
	 * 
	 * @param theStateCode 
	 * @param theZipCode
	 * @param theStatAbbrv An abbreviation of the city's state name
	 * @param theName the name of the city
	 * @param theLattitude
	 * @param theLongitude
	 * @param theRestaurants a list of restaurants in the city
	 */
	City(int theStateCode, int theZipCode, String theStateAbbrv, String theName, 
		double theLattitude, double theLongitude, ArrayList<String> theRestaurants){
		this.stateCode = theStateCode;
		this.zipCode = theZipCode;
		this.stateAbbrv = theStateAbbrv;
		this.name = theName;
		this.lattitude = theLattitude;
		this.longitude = theLongitude;
		this.restaurants = theRestaurants;
	}
	
	/**
	 * Adds unique restaurant to list of restaurants in city
	 * 
	 * @param newRest the new restaurant to add to the list
	 * @param newMeals the meals at the new restaurant to be added to the list
	 */
	public void addRestaurant(String newRest, ArrayList<MenuItem> newMeals) {
		if (!restaurants.contains(newRest)) { // only adds restaurant if it does not already exist in the list or restaurants
			restaurants.add(newRest);
			meals.addAll(newMeals); // updates the list of meals available in the city
		}
		
	}
	
	/**
	 * Returns the cheapest meal available in the city
	 * 
	 * @return the menu item that is the cheapest
	 */
	public MenuItem getCheapestMeal() {
		Collections.sort(meals); // sorts the meals available in the city by price smallest to greatest
		return meals.get(0); // gets the cheapest meal
	}
	
	/**
	 * Returns the second cheapest meal available in the city
	 * 
	 * @return the menu item that is the second cheapest
	 */
	public MenuItem getSecondCheapestMeal() {
		Collections.sort(meals); // sorts the meals available in the city by price smallest to greatest
		return meals.get(1); // gets the second cheapest meal
	}
	
	
	/**
	 * Getter Methods
	 * Returns the values corresponding to the appropriate state variables
	 */
	
	
	public int getStatecode() {
		return this.stateCode;
	}
	
	public int getZipCode() {
		return this.zipCode;
	}
	
	public String getStateAbbrv() {
		return this.stateAbbrv;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getLatitude() {
		return this.lattitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public ArrayList<String> getRestaurants() {
		return this.restaurants;
	}
	
	public ArrayList<MenuItem> getMeals() {
		return this.meals;
	}
}
