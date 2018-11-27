/**
 * Implements a Menu Item absract data type
 * 
 * @file MenuItem.java
 * @author Yaminah Qureshi
 * @version 1
 * @date 03/31/2018
 */

package cas2xb3_A2_qureshi_YQ;


public class MenuItem implements Comparable<MenuItem>{
	private String meal;
	private double price;
	private String comment;
	
	
	/**
	 * constructs a menu item
	 * 
	 * @param theMeal the name of the menu item
	 * @param thePrice the price of the menu item
	 * @param theComment a comment further explaining the menu item
	 */
	MenuItem(String theMeal, double thePrice, String theComment) {
		this.meal = theMeal;
		this.price = thePrice;
		this.comment = theComment;
	}

	/**
	 * Getter method for Meal
	 * 
	 * @return the name of the menu item
	 */
	public String getMeal() {
		return this.meal;
	}
	
	/**
	 * Getter method for Comment
	 * 
	 * @return the comment further explaining the menu item
	 */
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Getter method for price
	 * 
	 * @return the price of the menu item
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Compare method for menu item
	 * compares the menu item to another menu item for equality based on their price
	 * 
	 * @param that the menu item this menu item will be compared to 
	 * @return -1 if this item is less than the second, +1 if its greater and 0 if they are equal in price
	 */
	public int compareTo(MenuItem that)
	   {
	      if      (this.getPrice() < that.getPrice()) return -1;
	      else if (this.getPrice() > that.getPrice()) return +1;
	      else return  0;
	}
	
	/**
	 * Returns a string representation of the menu item
	 * 
	 * @return a string displaying the name, price and comment associated with the menu item
	 */
	public String toString() {
		String menuItem = this.meal + ", " + this.price + ", " + this.comment;
		return menuItem;
		
	}
}
