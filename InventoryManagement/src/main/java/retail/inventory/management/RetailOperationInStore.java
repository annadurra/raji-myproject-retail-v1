package retail.inventory.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RetailOperationInStore {
	sortProduct subclass=new sortProduct();

	public void sortProductsById(List<sortProduct> argProductList){
		if(!argProductList.isEmpty()){
			argProductList.sort(Comparator.comparing(sortProduct::getItemID).reversed());
			argProductList.forEach(products-> printdetails(products));
		}

	}

	public void sortProductsByName(List<sortProduct> argProductList) {
		if(!argProductList.isEmpty()){
			argProductList.sort(Comparator.comparing(sortProduct::getName).reversed());
			argProductList.forEach(products-> printdetails(products));
		}

	}

	public void sortProductsByCategory(List<sortProduct> argProductList) {
		if(!argProductList.isEmpty()){
			argProductList.sort(Comparator.comparing(sortProduct::getCategory).reversed());
			argProductList.forEach(products-> printdetails(products));
		}
	}


	public void printdetails(sortProduct products) {
		System.out.printf("%d  || %-10s || %-15s || %-5s || %s%n", products.getItemID(), products.getName(), products.getCategory(),products.getPrice(),products.getQty());
	}

	public void searchProductsById(String itemId, List<sortProduct> productList) {
		List<sortProduct> resultProductList=productList.stream().filter(obj-> itemId.equalsIgnoreCase(String.valueOf(obj.getItemID()))).collect(Collectors.toList());
		resultProductList.forEach(products-> printdetails(products));

	}

	public void searchProductsByName(String productName, List<sortProduct> productList) {
		List<sortProduct> resultProductList=productList.stream().filter(obj-> productName.equalsIgnoreCase(String.valueOf(obj.getName()))).collect(Collectors.toList());
		resultProductList.forEach(products-> printdetails(products));

	}

	public void searchProductsByCategory(String productCaegory, List<sortProduct> productList) {
		List<sortProduct> resultProductList=productList.stream().filter(obj-> productCaegory.equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
		resultProductList.forEach(products-> printdetails(products));

	}

	public void searchProductByCriteria(HashMap<String, String> searchInputDataMap, List<sortProduct> productList) {
		HashMap<String, String> columnAndReferenceMap=new HashMap<String, String>();
		List<String> dataToProcess=new ArrayList<>();
		columnAndReferenceMap.put("ITEMID", "getItemID");
		columnAndReferenceMap.put("NAME", "getName");
		columnAndReferenceMap.put("CATEGORY", "getCategory");
		columnAndReferenceMap.put("PRICE", "getPrice");
		columnAndReferenceMap.put("QTY", "getQty");
		for(Entry<String, String> searchKey:searchInputDataMap.entrySet()){
			dataToProcess.add(columnAndReferenceMap.get(searchKey.getKey()) +":"+searchKey.getValue());
		}

		List<sortProduct> searchResultList=new ArrayList<>();
		Iterator<sortProduct> iterate=null;

		for(String data:dataToProcess){
			if(subclass.getSearchResultList()!=null&& !subclass.getSearchResultList().isEmpty()){
				iterate=subclass.getSearchResultList().iterator();
			}
			else{
				iterate=productList.iterator();
			}
			List<sortProduct> listToStoreData=new ArrayList<>();
			while(iterate.hasNext()){
				listToStoreData.add(iterate.next());				
			}
			searchResultList=(List<sortProduct>) getSearchResultList(data,listToStoreData);
			subclass.setSearchResultList(searchResultList);
		}

		subclass.getSearchResultList().forEach(products-> printdetails(products));
	}

	private Collection<? extends sortProduct> getSearchResultList(String argData, List<sortProduct> productList) {
		String[] resultArray=argData.split(":");
		String methodName=resultArray[0];
		String inputValue=resultArray[1];
		switch(methodName){
		case "getItemID":		
			return searchProductsById(inputValue,productList,Boolean.TRUE);
		case "getName":
			return searchProductsByName(inputValue,productList,Boolean.TRUE);
		case "getCategory":
			return searchProductsByCategory(inputValue,productList,Boolean.TRUE);
		case "getPrice":
			return searchProductsByPrice(inputValue,productList,Boolean.TRUE);
		case "getQty":
			return searchProductsByQty(inputValue,productList,Boolean.TRUE);
		default:
			System.out.println("Invalid type");
			return new ArrayList<>();
		}

	}

	private Collection<? extends sortProduct> searchProductsByQty(String argQty, List<sortProduct> productList,
			Boolean argElement) {
		return productList.stream().filter(obj-> argQty.equalsIgnoreCase(String.valueOf(obj.getQty()))).collect(Collectors.toList());
	}

	private Collection<? extends sortProduct> searchProductsByPrice(String argPrice, List<sortProduct> productList,
			Boolean argElement) {
		return productList.stream().filter(obj-> argPrice.equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
	}

	private Collection<? extends sortProduct> searchProductsByCategory(String argCategory, List<sortProduct> productList,
			Boolean argElement) {
		return productList.stream().filter(obj-> argCategory.equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
	}

	public  Collection<? extends sortProduct> searchProductsById(String itemId, List<sortProduct> productList,boolean argElement) {
		return productList.stream().filter(obj-> itemId.equalsIgnoreCase(String.valueOf(obj.getItemID()))).collect(Collectors.toList());
	}

	private Collection<? extends sortProduct> searchProductsByName(String productName, List<sortProduct> productList, Boolean argElement) {
		return productList.stream().filter(obj-> productName.equalsIgnoreCase(String.valueOf(obj.getName()))).collect(Collectors.toList());

	}

	public void displayProducts(List<sortProduct> productList) {
		System.out.println("****displaying products from selected category****");		
	}
}

class sortProduct{
	private int itemID;
	private String name;
	private String category;
	private int price;
	private int qty;
	private List<sortProduct> searchResultList;

	public List<sortProduct> getSearchResultList() {
		return searchResultList;
	}


	public void setSearchResultList(List<sortProduct> searchResultList) {
		this.searchResultList = searchResultList;
	}


	public sortProduct(int itemID,String name,String category,int price,int qty){
		this.itemID=itemID;
		this.name=name;
		this.category=category;
		this.price=price;
		this.qty=qty;   	

	}

	public sortProduct(){
		//empty constructor;
	}


	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

}

