
abstract class Product{
	public abstract Product getInstance();
	public abstract int addNumbers(int x, int y);
}

class HomeProducts extends Product{

	private HomeProducts instance = null;
	
	private HomeProducts() {

	}

	@Override
	public Product getInstance() {
		if(instance == null) {
			synchronized (this) {
				return new HomeProducts();
			}
		}else {
			return instance;
		}
	}
	@Override
	public int addNumbers(int x, int y) {
		return x+y;
	}
	
}
class ProductFactory{
	public Product getProducts(String type) {
		if(type.equals("home")) {
			try {
				return HomeProducts.class.newInstance().getInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}else {
			return null;
		}
	}
	
}
public class TestFactoryMethodPattern {

	public static void main(String[] args) {
		ProductFactory factory = new ProductFactory();
		Product p = factory.getProducts("home");
		System.out.println(p.addNumbers(10, 20));
	}

}
