package retail.inventory.management;

import java.util.List;
import java.util.stream.Collectors;

public class ElectricalItem extends RetailOperationInStore{
	@Override
	public void displayProducts(List<sortProduct> productList) {
		super.displayProducts(productList);
		List<sortProduct> resultProductList=productList.stream().filter(obj-> "ELECTRICAL".equalsIgnoreCase(String.valueOf(obj.getCategory()))).collect(Collectors.toList());
		resultProductList.forEach(products-> super.printdetails(products));
	}

}
