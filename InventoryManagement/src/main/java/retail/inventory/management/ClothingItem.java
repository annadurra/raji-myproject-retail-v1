package retail.inventory.management;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ClothingItem extends RetailOperationInStore{
	
	@Override
	public void displayProducts(List<sortProduct> productList) {
		super.displayProducts(productList);
		List<sortProduct> resultProductList=productList.stream().filter(obj-> "CLOTHING".equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
		resultProductList.forEach(products-> super.printdetails(products));
	}

	public void searchProductsById(String itemId, List<sortProduct> productList) {
		System.out.println("******product search initiated******");
		super.searchProductsById(itemId, productList);
		System.out.println("******product search is successfull******");

	}

	public void searchProductsByName(String productName, List<sortProduct> productList) {
		System.out.println("******product search initiated******");
		super.searchProductsByName(productName, productList);
		System.out.println("******product search is successfull******");

	}

	public void searchProductsByCategory(String productCaegory, List<sortProduct> productList) {
		System.out.println("******product search initiated******");
		super.searchProductsByCategory(productCaegory, productList);
		System.out.println("******product search is successfull******");

	}

	public void searchProductByCriteria(HashMap<String, String> searchInputDataMap, List<sortProduct> productList) {
		super.searchProductByCriteria(searchInputDataMap,productList);
	}

}
