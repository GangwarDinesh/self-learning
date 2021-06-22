import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class LimitClass {
    private static List<LimitClass> objectPool = new ArrayList<>();
    public static int objCount = 0;
    private static Random randomizer = new Random();
    private LimitClass(){
        objCount++;
    }
    public static synchronized LimitClass getLimInstance(){
        if(objCount < 3 ){
            LimitClass lc = new LimitClass();
            objectPool.add(lc);
            return lc;
        }
        return objectPool.get(Math.abs(randomizer.nextInt()%3));
    }
}

public class LimitObjectCreationTest {
    public static void main(String[] args) {
        LimitClass obj1 = LimitClass.getLimInstance();
        LimitClass obj2 = LimitClass.getLimInstance();
        LimitClass obj3 = LimitClass.getLimInstance();
        LimitClass obj4 = LimitClass.getLimInstance();
        LimitClass obj5 = LimitClass.getLimInstance();
        LimitClass obj6 = LimitClass.getLimInstance();
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
        System.out.println(obj4);
        System.out.println(obj5);
        System.out.println(obj6);
        System.out.println(obj3);  
    }
}
