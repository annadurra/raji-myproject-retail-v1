package retail.inventory.management;

import java.util.List;
import java.util.stream.Collectors;

public class GroceryItem extends RetailOperationInStore{
	@Override
	public void displayProducts(List<sortProduct> productList) {
		super.displayProducts(productList);
		List<sortProduct> resultProductList=productList.stream().filter(obj-> "GROCERY".equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
		resultProductList.forEach(products-> super.printdetails(products));
	}

	@Override
	public void sortProductsById(List<sortProduct> argProductList){		
		super.sortProductsById(argProductList);
		System.out.println("*******Product sorted by ID*******");
	}

	@Override
	public void sortProductsByName(List<sortProduct> argProductList) {
		super.sortProductsByName(argProductList);
		System.out.println("*******Product sorted by Name*******");
	}

	@Override
	public void sortProductsByCategory(List<sortProduct> argProductList) {
		super.sortProductsByCategory(argProductList);
		System.out.println("*******Product sorted by Category*******");

	}

}
