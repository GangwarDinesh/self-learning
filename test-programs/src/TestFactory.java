import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Product1{
	public double getProductPrice(String name);
	public boolean checkAvailability(String name);
}

class ElectronicProducts implements Product1{
	@Override
	public double getProductPrice(String name){
		double price = 0.0;
		if("refrigrator".equals(name)){
			price = 20000.00;
		}else if("tv".equals(name)){
			price = 30000.00;
		}else if("mobile".equals(name)){
			price = 15000.00;
		}	
		return price;
	}
	@Override
	public boolean checkAvailability(String name){
		boolean isAvailable = false;
		if("refrigrator".equals(name)){
			isAvailable = false;
		}else if("tv".equals(name)){
			isAvailable = true;
		}else if("mobile".equals(name)){
			isAvailable = true;
		}	
		return isAvailable;
	}
	
	
}
class GarmentProducts implements Product1{
	@Override
	public double getProductPrice(String name){
		double price = 0.0;
		if("tshirt".equals(name)){
			price = 20000.00;
		}else if("jeans".equals(name)){
			price = 30000.00;
		}else if("shirt".equals(name)){
			price = 15000.00;
		}	
		return price;
	}
	@Override
	public boolean checkAvailability(String name){
		boolean isAvailable = false;
		if("tshirt".equals(name)){
			isAvailable = false;
		}else if("jeans".equals(name)){
			isAvailable = true;
		}else if("shirt".equals(name)){
			isAvailable = true;
		}	
		return isAvailable;
	}
	
	
}

class ProductFactory1{
	private static ProductFactory1 productFactory = null;
	private ProductFactory1() {
		
	}
	Product1 products = null;
	Map<String, List<Product1>> prodObjMap = new HashMap<>();
	
	public Product1 getProduct(String productType){
		
		if("electronics".equals(productType)){
			List<Product1> objList = prodObjMap.getOrDefault("electronics", new ArrayList<>());
			if(objList.isEmpty()) {
				products = new ElectronicProducts();
				objList.add(products);
				prodObjMap.put("electronics", objList);
			}else {
				products = objList.get(0);
			}
		}else if("garments".equals(productType)){
			List<Product1> objList = prodObjMap.getOrDefault("garments", new ArrayList<>());
			if(objList.isEmpty()) {
				products = new GarmentProducts();
				objList.add(products);
				prodObjMap.put("garments", objList);
			}else {
				products = objList.get(0);
			}
		}
	
		return products;
	}
	
	public static synchronized ProductFactory1 getInstance() {
		if(null == productFactory) {
			productFactory = new ProductFactory1();
			return productFactory;
		}
		return productFactory;
	}
}

public class TestFactory{
	public static void main(String args[]){
		ProductFactory1 pf = ProductFactory1.getInstance();
		Product1 p = pf.getProduct("electronics");
		Product1 p1 = pf.getProduct("electronics");
		System.out.println("P : "+p);
		System.out.println("P1 : "+p1);
		String prodName = "refrigrator";
		if(null != p){
			if(p.checkAvailability(prodName)){
				System.out.println("Product Price : "+p.getProductPrice(prodName));
			}else{
				System.out.println("Product is not available.");
			}
		}
		
		System.out.println("---------------------------------------------");
		
		Product1 p2 = pf.getProduct("garments");
		Product1 p3 = pf.getProduct("garments");
		System.out.println("P2 : "+p2);
		System.out.println("P3 : "+p3);
		String prodName1 = "jeans";
		if(null != p3){
			if(p3.checkAvailability(prodName1)){
				System.out.println("Product Price : "+p3.getProductPrice(prodName1));
			}else{
				System.out.println("Product is not available.");
			}
		}
	}
}