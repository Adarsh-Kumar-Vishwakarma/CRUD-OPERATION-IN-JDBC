package Adarsh; // Package declaration for the Adarsh package

import java.sql.*; // Import necessary Java SQL libraries
import java.util.Scanner; // Import the Scanner class for user input

public class ProductManagement extends Thread { // Define a class named ProductManagement that extends Thread
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Define a constant for the JDBC driver
	private static final String URL = "jdbc:mysql://localhost:3306/adarsh"; // Define a constant for the database URL
	private static final String USER = "root"; // Define a constant for the database user
	private static final String PASSWORD = "root"; // Define a constant for the database password

	public static void main(String[] args) { // Main method
		try {
			Class.forName(DRIVER); // Load the JDBC driver
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD); // Establish a database connection
			Scanner sc = new Scanner(System.in); // Create a Scanner object for user input

			int choice; // Declare a variable to store user choice
			System.out.println("    !!!!CRUD OPERATION!!!!    "); // Print a title
			System.out.println();// Empty line for formatting

			do {
				System.out.println("Product Management Menu:"); // Display the product management menu
				System.out.println();// Empty line for formatting
				System.out.println("1. Create Product"); // Option 1: Create Product
				System.out.println("2. Read Product"); // Option 2: Read Product
				System.out.println("3. Update Product"); // Option 3: Update Product
				System.out.println("4. Delete Product"); // Option 4: Delete Product
				System.out.println("5. View Products(CategoriesWise)"); // Option 5: View Products by Category
				System.out.println("6. Exit"); // Option 6: Exit
				System.out.println();// Empty line for formatting
				System.out.print("Enter your choice: "); // Prompt user for choice
				System.out.println();// Empty line for formatting

				choice = sc.nextInt(); // Read user choice

				switch (choice) { // Switch statement based on user choice
				case 1:
					Thread.sleep(1000); // Pause for 1 second
					createProduct(con); // Call the createProduct method
					break;
				case 2:
					Thread.sleep(1000); // Pause for 1 second
					readProducts(con); // Call the readProducts method
					break;
				case 3:
					Thread.sleep(1000); // Pause for 1 second
					updateProduct(con); // Call the updateProduct method
					break;
				case 4:
					Thread.sleep(1000); // Pause for 1 second
					deleteProduct(con); // Call the deleteProduct method
					break;
				case 5:
					Thread.sleep(1000); // Pause for 1 second
					CategoryWiseProducts(con); // Call the CategoryWiseProducts method
					break;
				case 6:
					Thread.sleep(1000); // Pause for 1 second
					System.out.println("Exiting From Product Management."); // Print an exit message
					break;
				default:
					Thread.sleep(1000); // Pause for 1 second
					System.out.println("Invalid choice. Please try again."); // Print an error message for invalid
																				// choice
					break;
				}
			} while (choice != 6); // Continue the loop until the user chooses to exit
			con.close(); // Close the database connection
		} catch (Exception e) { // Catch any exceptions and print the stack trace
			e.printStackTrace();
		}
	}

	public static void createProduct(Connection con) throws Exception { // Method to create a new product
		Scanner sc = new Scanner(System.in); // Create a Scanner object for user input
		System.out.println("Enter Product Details:"); // Prompt user for product details
		System.out.println();// Empty line for formatting
		System.out.print("Product Name: "); // Prompt for product name
		String productName = sc.nextLine(); // Read product name
		System.out.print("Quantity: "); // Prompt for quantity
		int quantity = sc.nextInt(); // Read quantity
		System.out.print("Price: "); // Prompt for price
		double price = sc.nextDouble(); // Read price
		System.out.print("Category: "); // Prompt for category
		String category = sc.next(); // Read category

		String qur = "INSERT INTO products (P_Name, Quantity, Price, Category) VALUES (?, ?, ?, ?)"; // SQL query
		PreparedStatement ps = con.prepareStatement(qur); // Create a prepared statement
		ps.setString(1, productName); // Set product name parameter
		ps.setInt(2, quantity); // Set quantity parameter
		ps.setDouble(3, price); // Set price parameter
		ps.setString(4, category); // Set category parameter

		int rowsInserted = ps.executeUpdate(); // Execute the SQL query
		if (rowsInserted > 0) {
			System.out.println("Product inserted successfully."); // Print success message
		} else {
			System.out.println("Failed to insert product."); // Print failure message
		}
		ps.close(); // Close the prepared statement
		System.out.println(); // Print a newline
	}

	public static void readProducts(Connection con) throws Exception { // Method to read products from the database
		Statement st = con.createStatement(); // Create a statement
		String qur = "SELECT * FROM Products"; // SQL query to select all products
		ResultSet rs = st.executeQuery(qur); // Execute the query
		ResultSetMetaData rsmd = rs.getMetaData(); // Get the result set metadata

		int columnCount = rsmd.getColumnCount(); // Get the number of columns in the result set
		System.out.println("-----------------------------------------------------------------------"); // Print a
																										// separator
		for (int i = 1; i <= columnCount; i++) { // Loop through columns
			String ColumnName = rsmd.getColumnName(i); // Get column name
			System.out.printf("%-15s", ColumnName); // Print column name

		}
		System.out.println();// Empty line for formatting
		System.out.println("-----------------------------------------------------------------------"); // Print a
																										// separator

		while (rs.next()) { // Loop through rows
			int id = rs.getInt("P_Id"); // Get product ID
			String name = rs.getString("P_Name"); // Get product name
			int quantity = rs.getInt("Quantity"); // Get product quantity
			double price = rs.getDouble("Price"); // Get product price
			String category = rs.getString("Category"); // Get product category

			System.out.printf("%-13d", id); // Print product ID
			System.out.printf("%-21s", name); // Print product name
			System.out.printf("%-11d", quantity); // Print product quantity
			System.out.printf("%,-15.1f", price); // Print product price
			System.out.printf("%10s", category); // Print product category
			System.out.println(); // Print a newline
		}
		System.out.println("-----------------------------------------------------------------------"); // Print a
																										// separator

		rs.close(); // Close the result set
		st.close(); // Close the statement
		System.out.println(); // Print a newline
	}

	public static void updateProduct(Connection con) throws Exception { // Method to update a product
		readProducts(con); // Call the readProducts method to display existing products
		Scanner sc = new Scanner(System.in); // Create a Scanner object for user input
		System.out.print("Enter the ID of the product you want to update: "); // Prompt for product ID
		int productId = sc.nextInt(); // Read product ID

		System.out.print("New Quantity: "); // Prompt for new quantity
		int newQuantity = sc.nextInt(); // Read new quantity
		System.out.print("New Price: "); // Prompt for new price
		double newPrice = sc.nextDouble(); // Read new price

		String qur = "UPDATE Products SET Quantity = ?, Price = ? WHERE P_Id = ?"; // SQL query to update product
		PreparedStatement ps = con.prepareStatement(qur); // Create a prepared statement
		ps.setInt(1, newQuantity); // Set new quantity parameter
		ps.setDouble(2, newPrice); // Set new price parameter
		ps.setInt(3, productId); // Set product ID parameter

		int rowsUpdated = ps.executeUpdate(); // Execute the SQL query
		if (rowsUpdated > 0) {
			System.out.println("Product updated successfully."); // Print success message
			readProducts(con); // Call the readProducts method to display updated products
		} else {
			System.out.println("Product not found or update failed."); // Print error message
		}
		ps.close(); // Close the prepared statement
		System.out.println(); // Print a newline
	}

	public static void deleteProduct(Connection con) throws Exception { // Method to delete a product
		readProducts(con); // Call the readProducts method to display existing products
		Scanner sc = new Scanner(System.in); // Create a Scanner object for user input

		System.out.print("Enter the ID of the product you want to delete: "); // Prompt for product ID to delete
		int productId = sc.nextInt(); // Read product ID

		String qur = "DELETE FROM Products WHERE P_Id = ?"; // SQL query to delete product
		PreparedStatement ps = con.prepareStatement(qur); // Create a prepared statement
		ps.setInt(1, productId); // Set product ID parameter

		int rowsDeleted = ps.executeUpdate(); // Execute the SQL query
		if (rowsDeleted > 0) {
			System.out.println("Product deleted successfully."); // Print success message
			readProducts(con); // Call the readProducts method to display updated products
		} else {
			System.out.println("Product not found or delete failed."); // Print error message
		}
		ps.close(); // Close the prepared statement
		System.out.println(); // Print a newline
	}

	public static void CategoryWiseProducts(Connection con) throws Exception { // Method to display products by category

		/*
		 * SELECT Category,GROUP_CONCAT(DISTINCT P_Name )AS ProductsInCategories FROM
		 * Products GROUP BY Category ORDER BY GROUP_CONCAT(DISTINCT Category) ASC;
		 */

		Statement st = con.createStatement(); // Create a statement
		String qur = "SELECT Category, GROUP_CONCAT(P_Name ORDER BY P_Name ASC) AS ProductsInCategories "
				+ "FROM Products GROUP BY Category ORDER BY Category ASC"; // SQL query to group products by category
		ResultSet rs = st.executeQuery(qur); // Execute the query
		ResultSetMetaData rsmd = rs.getMetaData(); // Get the result set metadata

		int columnCount = rsmd.getColumnCount(); // Get the number of columns in the result set
		System.out.println("-------------------------------------------"); // Print a separator
		for (int i = 1; i <= columnCount; i++) { // Loop through columns
			String ColumnName = rsmd.getColumnName(i); // Get column name
			System.out.printf("%-20s", ColumnName); // Print column name
		}
		System.out.println(); // Print a newline
		System.out.println("-------------------------------------------"); // Print a separator

		while (rs.next()) { // Loop through rows
			String category = rs.getString("Category"); // Get category
			String ProductsInCategories = rs.getString("ProductsInCategories"); // Get products in categories

			System.out.printf("%-20s", category); // Print category
			System.out.printf(ProductsInCategories); // Print products in categories
			System.out.println(); // Print a newline
		}
		System.out.println("-------------------------------------------"); // Print a separator
		rs.close(); // Close the result set
		st.close(); // Close the statement
		System.out.println(); // Print a newline
	}
}
