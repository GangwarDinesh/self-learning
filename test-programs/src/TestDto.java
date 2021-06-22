
public class TestDto implements Comparable<TestDto>{

	private int id;
	
	private String name;
	
	

	public TestDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestDto [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int compareTo(TestDto o) {
		return this.id-o.getId();
	}
	
	public void printMe() throws CustomException {
		System.out.println("Print Me");
		
		try {
			int z = 1/0;
		} catch (Exception e) {
			throw new CustomException("This is custom exception");
		}
	}

	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestDto other = (TestDto) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
