import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Class<? extends Test> aClass = new Test<>().getClass();
        System.out.println(aClass);
        System.out.println(aClass.getGenericSuperclass());
    }
}

class Test<T extends String> {

}
