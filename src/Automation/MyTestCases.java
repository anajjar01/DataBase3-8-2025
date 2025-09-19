package Automation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases {
	
	WebDriver driver = new EdgeDriver(); 
	String myWebSite = "https://automationteststore.com/";
	String SignupPage = "https://automationteststore.com/index.php?rt=account/create";
	
	Connection con;
	
	Statement stmt;
	
	ResultSet rs;
	
	String CustomerFirstNameInDataBase;
	String CustomerLastNameInDataBase;
	String email;
	String password;

	
	@BeforeTest
	public void mySetup() throws SQLException {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get(SignupPage);
	
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","P@ssw0rd");
	}
	

	@Test(priority = 1, enabled = true)

	public void AddNewCustomer() throws SQLException {

		String query = "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, city, country, salesRepEmployeeNumber, creditLimit) VALUES (999, 'Abc Company', 'ali', 'ahmad', '962797700235', '123 Main St', 'Los Angeles', 'USA', 1370, 50000.00)";

		stmt = con.createStatement();

		int rowInserted = stmt.executeUpdate(query);

		System.out.println(rowInserted);
	}
	
	@Test(priority = 3, enabled = true)

	public void ReadTheUpdatedData() throws SQLException {
		String query = "select * from customers where customerNumber =999 ";
		stmt = con.createStatement();

		rs = stmt.executeQuery(query);


		while (rs.next()) {
			int customerNumberInDataBase = rs.getInt("customerNumber");

			CustomerFirstNameInDataBase = rs.getString("contactFirstName").toString().trim();

			CustomerLastNameInDataBase = rs.getString("contactLastName").toString().trim();
			;

			email = CustomerFirstNameInDataBase + CustomerLastNameInDataBase + "@gmail.com";
			password = "123!@#P@ssw0rd";

			System.out.println(customerNumberInDataBase);
			System.out.println(CustomerFirstNameInDataBase);
			System.out.println(CustomerLastNameInDataBase);

			System.out.println(email);
			System.out.println(password);
		}
		driver.findElement(By.id("AccountFrm_firstname")).sendKeys(CustomerFirstNameInDataBase);
		;
		driver.findElement(By.id("AccountFrm_lastname")).sendKeys(CustomerLastNameInDataBase);
		;
		driver.findElement(By.id("AccountFrm_email")).sendKeys(email);
		;

	}
	
	@Test(priority = 2, enabled = true)

	public void UpdateCustomerInfo() throws SQLException {

		String query = "UPDATE customers SET contactLastName = 'asaad' WHERE customerNumber = 999;";

		stmt = con.createStatement();

		int rowInserted = stmt.executeUpdate(query);

		System.out.println(rowInserted);

	}
	
	@Test(priority = 4, enabled = true)

	public void DeleteCustomer() throws SQLException {

		String query = "delete from customers where customerNumber =999";

		stmt = con.createStatement();

		int rowInserted = stmt.executeUpdate(query);

		System.out.println(rowInserted);

	}
}
