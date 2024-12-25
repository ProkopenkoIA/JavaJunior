package ReflectionApi;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<?> string = Class.forName("java.lang.String");
        Method[] methods = string.getClass().getMethods();

        for (int i = 0; i < methods.length; i++){
            System.out.println(methods[i]);
        }
    }
}

