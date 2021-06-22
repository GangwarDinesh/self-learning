
public class TestOrder {

	{
		System.out.println("Initialization block.");
	}
	static {
		System.out.println("Static block.");
	}
	public static void m1() {
		System.out.println("Static method.");
	}
	public void m2() {
		System.out.println("Non static method.");
	}
	public TestOrder() {
		System.out.println("Constructor");
		m2();
		TestOrder.m1();
	}
	public static void main(String[] args) {
		
		TestOrder t = new TestOrder();
		
	}

}
