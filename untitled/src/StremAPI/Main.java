package StremAPI;

import java.util.Arrays;
import java.util.OptionalDouble;

public class Main {
    public static void main(String[] args) {
        /**
         * Напишите программу, которая использует Stream API для обработки списка чисел.
         * Программа должна вывести на экран среднее значение всех четных чисел в списке.
         */

        OptionalDouble average =Arrays.asList(1,2,3,4,5)
                .stream().filter(n -> n%2 == 0)
                .mapToInt(Integer::intValue)
                .average();

        System.out.println("Среднее значение всех четных чисел в списке: " + average.getAsDouble());
    }
}
