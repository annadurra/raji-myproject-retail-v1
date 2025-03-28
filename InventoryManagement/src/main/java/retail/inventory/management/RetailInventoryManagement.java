package retail.inventory.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



public class RetailInventoryManagement {
	public  static List<sortProduct> productList=new ArrayList<>();

	public static void main(String[] args) {
		createDatabaseConnection();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter module which you want to explore, value expected :{ sort / search / display } ");
		String operationType = scanner.nextLine();        
		switch(operationType){
		case "sort":        	
			sortProducts(scanner);
			break;        
		case "search":
			searchProduct(scanner);
			break;
		case "display":
			displayProductByCategory(scanner);
			break;
		default:
			System.out.println("Invalid Entry");
			break;
		}
		scanner.close();
	}

	private static void displayProductByCategory(Scanner scanner) {
		System.out.println("Enter category to display items, expected value : {electrical / grocery / clothing}");
		String inputCategory=scanner.nextLine();
		switch(inputCategory){
		case "electrical":
			ElectricalItem electricalobj=new ElectricalItem();
			electricalobj.displayProducts(productList);
			checkdisplayExit(scanner);
			break;
		case "grocery":
			GroceryItem groceryobj=new GroceryItem();
			groceryobj.displayProducts(productList);
			checkdisplayExit(scanner);
			break;
		case "clothing":
			ClothingItem clothingObj=new ClothingItem();
			clothingObj.displayProducts(productList);
			checkdisplayExit(scanner);
			break;
		default:
				System.out.println("Invalid Input");
				checkdisplayExit(scanner);
				break;
		}

	}


	private static void searchProduct(Scanner scanner) {
		System.out.println("integer value assigned to each field as 1:itemid,2:name,3:category,4:price,5:qty");
		System.out.println("expected value:{ 1, / 2, / 3, / 4, / 5, / 1,2 / 2,3,4 ...any combination of above reference}");
		String searchBy=scanner.nextLine();
		String[] splittedValue=searchBy.split(",");
		HashMap<String,String> searchCriteriaMap=new HashMap<>();
		HashMap<String,String> searchInputDataMap=new HashMap<>();
		searchCriteriaMap.put("1", "ITEMID");
		searchCriteriaMap.put("2", "NAME");
		searchCriteriaMap.put("3", "CATEGORY");
		searchCriteriaMap.put("4", "PRICE");
		searchCriteriaMap.put("5", "QTY");
		for(String crtiteria:splittedValue){
			if(searchCriteriaMap.get(crtiteria)!=null && !searchCriteriaMap.get(crtiteria).isEmpty()){
				System.out.println("Enter "+ searchCriteriaMap.get(crtiteria)+ " to search:");
				String searchInputData=scanner.nextLine();
				searchInputDataMap.put(searchCriteriaMap.get(crtiteria), searchInputData);
			}

		}
		if(!searchInputDataMap.isEmpty() && searchInputDataMap!=null){
			ClothingItem cProductObj=new ClothingItem();
			cProductObj.searchProductByCriteria(searchInputDataMap,productList);
		}else{
			System.out.println("Invalid Input");
		}
		checksearchExit(scanner);
		
		
	}


	private static void sortProducts(Scanner scanner) {		
		System.out.println("Enter sort by value ,value expected :{ itemid / name / category } ");
		String sortBy=scanner.nextLine();		
		GroceryItem gProductObj=new GroceryItem();
		switch(sortBy){
		case "itemid":			
			gProductObj.sortProductsById(productList);
			checkExit(scanner);
			break;
		case "name":
			gProductObj.sortProductsByName(productList);
			checkExit(scanner);
			break;
		case "category":
			gProductObj.sortProductsByCategory(productList);
			checkExit(scanner);
			break;
		default:
			System.out.println("Invalid type");
			checkExit(scanner);
			break;

		}
	}

	private static void checkExit(Scanner argScanner) {
		System.out.println("do you want to exit sorting module (Y/N):");
		String exitInput=argScanner.nextLine();
		switch(exitInput){
		case "Y":
			break;
		case "N":
			sortProducts(argScanner);
			break;
		default:
			System.out.println("Enter correct value");
			checkExit(argScanner);
			break;
		}		
	}

	private static void checkdisplayExit(Scanner argScanner) {
		System.out.println("do you want to exit display module (Y/N):");
		String exitInput=argScanner.nextLine();
		switch(exitInput){
		case "Y":
			break;
		case "N":
			displayProductByCategory(argScanner);
			break;
		default:
			System.out.println("Enter correct value");
			checkdisplayExit(argScanner);
			break;
		}

	}
	
	private static void checksearchExit(Scanner argScanner) {
		System.out.println("do you want to exit search module (Y/N):");
		String exitInput=argScanner.nextLine();
		switch(exitInput){
		case "Y":
			break;
		case "N":
			searchProduct(argScanner);
			break;
		default:
			System.out.println("Enter correct value");
			checksearchExit(argScanner);
			break;
		}
		
	}
	
	private static void createDatabaseConnection() {
		try {   
			final String user = "sa";
			final String password = "";
			Class.forName("org.h2.Driver");          
			//String url = "jdbc:h2:~/retail";  // File-based
			String url = "jdbc:h2:mem:testdb";  // In-memory       

			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Database Connected");
			createTableInDatabase(conn);
			conn.close();
		}  catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private static void createTableInDatabase(Connection argConnection) throws Exception {	        
		Statement stat = argConnection.createStatement();	

		stat.execute("create table RETAIL_ITEM (ITEMID int primary key, NAME varchar(255), CATEGORY varchar(255), PRICE int, QTY int)");
		String insertSql="insert into RETAIL_ITEM (ITEMID,NAME,CATEGORY,PRICE,QTY)values(?,?,?,?,?)";

		PreparedStatement setValue = argConnection.prepareStatement(insertSql); 
		int noOfRecords=10;
		List<Integer> itemIds=new ArrayList<>();

		//to prepare data for products in store
		String electricalProductsName="LAPTOP,MOBILE,TABLET,HEADPHONE,CHARGER,CABLES,SWITCHES,FITTINGS,WIRES,PLUGS";
		String groceryProductsName="RICE,POTATO,ORANGE,APPLE,TOMATTO,PAPAYA,BANANA,CABBAGE,ONION,EGG";
		String clothingProductsName="SHIRT,TROUSERS,SKIRT,SCARF,JACKETS,GOWN,FROCK,COAT,SWEATER,T-SHIRT";
		insertRecord(noOfRecords,"ELECTRICAL",electricalProductsName,setValue,itemIds);	
		insertRecord(noOfRecords,"GROCERY",groceryProductsName,setValue,itemIds);	
		insertRecord(noOfRecords,"CLOTHING",clothingProductsName,setValue,itemIds);

		//to display inserted data / to load data into list
		ResultSet rs = stat.executeQuery("SELECT ITEMID,NAME,CATEGORY,PRICE,QTY FROM RETAIL_ITEM");	      
		while (rs.next()) {		    	 
			sortProduct sorting= new sortProduct(rs.getInt("ITEMID"),rs.getString("NAME"),rs.getString("CATEGORY"),rs.getInt("PRICE"),rs.getInt("QTY"));
			productList.add(sorting); 	  
			System.out.printf("%d  || %-15s || %-15s || %-15s || %s%n", sorting.getItemID(), sorting.getName(), sorting.getCategory(),sorting.getPrice(),sorting.getQty());
		}  
	}

	private static void insertRecord(int argNoOfRecords, String argCategory, String argProductsName, PreparedStatement argSetValue, List<Integer> argIds) {
		String[] productNameList=argProductsName.split(",");
		while(argNoOfRecords>0){			
			try {
				argNoOfRecords--;
				argSetValue.setInt(1, getIntegerValue(argIds));
				argSetValue.setString(2, productNameList[argNoOfRecords]);
				argSetValue.setString(3, argCategory);
				argSetValue.setInt(4, getIntegerValue(argIds));
				argSetValue.setInt(5, getIntegerValue(argIds));				
				argSetValue.executeUpdate();
			} catch (SQLException exception) {				
				exception.printStackTrace();
			}			
		}		
	}

	private static int getIntegerValue(List<Integer> argIds) {
		boolean isIdFound=Boolean.FALSE;
		int id=createIntergetValue();
		if(!argIds.contains(id)){
			isIdFound=Boolean.TRUE;
			argIds.add(id);
		}		
		return isIdFound ? id : getIntegerValue(argIds);
	}

	private static int createIntergetValue() {
		Random random = new Random();
		return random.nextInt(2000);		
	}

}


