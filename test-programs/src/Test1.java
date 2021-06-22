import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Test1 {

	public static void main(String[] args) {
		
		
		List<String> list = Arrays.asList("abc","xyz","dfr","abd","www");
		
    	
		Map<String, Integer> map = new HashMap<>();
		map.put("A", 1);
		List<Integer> list1 = map.values().stream().collect(Collectors.toList());
		System.out.println(list1);
		
		List<String> names = Arrays.asList("Ugama", "Sharma", "Amit", "Sujeet"); 
		List<String> nameWithAA = names.stream().filter(name -> name.contains("a")).filter(name-> name.contains("a")).collect(Collectors.toList()); 
		 
		System.out.println(nameWithAA);
		Map<Object,Object> map1 = names.stream().collect(Collectors.toMap(val->val, val->val.length()));
		System.out.println(map1);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Runnable r1 = ()->{
			for(int i = 65;i<=90;i++){
				if(i%2!=0) {
					System.out.println((char)i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Runnable r2 = ()->{
			for(int i = 65;i<=90;i++){
				if(i%2==0) {
					System.out.println((char)i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		//executor.execute(r1);
		//executor.execute(r2);
		//executor.shutdown();
		
		
		List<Employee> listdto = Arrays.asList(new Employee(1, "Dinesh", 30, 600000.00),
												new Employee(2, "Kavita", 20, 2000.00),
												new Employee(3, "Ravi", 19, 4000.00),
												new Employee(4, "Pinku", 40, 800000.00));
		
		double avgSal = listdto.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
		
		List<String> namesList = listdto.stream().filter(emp->emp.getAge()>=30).filter(emp->emp.getSalary()>avgSal).map(Employee::getName).collect(Collectors.toList());
		System.out.println(namesList);
		
		String url = "AP_SSC_URL";
		System.out.println(url.split("=").length);
       
	}
}
