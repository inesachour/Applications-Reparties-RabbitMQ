import java.util.Random;

public class User {
	private String name;
	Random rd;
	
	User(){
		rd = new Random();
		name = "User-"+ rd.nextInt(9999);
		System.out.println(name);
	}
	
	String getName(){
		return name;
	}
}
