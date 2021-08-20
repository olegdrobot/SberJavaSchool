import java.util.Scanner;

public class Main {
    public static int[] arraySort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                int tempItem = 0;
                if (array[i] < array[j]) {
                    tempItem = array[i];
                    array[i] = array[j];
                    array[j] = tempItem;
                }
            }
        }
        return array;
    }

    public static void showArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) {
        Circle circle = new Circle(10);
        Rect rect = new Rect(12.5f, 30);
        Square square = new Square(20);
        Triangle triangle = new Triangle(18, 25, 15, 30, 18);
        TemperatureConvertor tempConvert = new TemperatureConvertor();
        int[] arr = {2, 5, 7, 15, 1, 85, 32};
        double temperature = 0;
        byte choice = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Circle radius = " + circle.a);
        System.out.println("Circle area = " + circle.getArea());
        System.out.println("Rectangle sides: a=" + rect.a + " b= "+ rect.b);
        System.out.println("Rectangle perimetr = " + rect.getPerimetr());
        System.out.println("Square side a=" + square.a);
        System.out.println("Square perimetr = " + square.getPerimetr());
        System.out.println("Triangle sides: a=" + triangle.a + " b= " + triangle.b + " c= " + triangle.c);
        System.out.println("Triangle area = " + square.getArea());

        System.out.println("Array before sorting:");
        showArray(arr);
        arr = arraySort(arr);
        System.out.println("Array after sorting");
        showArray(arr);
        System.out.println("Enter temperature on the Celsius scale: ");
        String str = "";
        while (true) {
            if (scanner.hasNextDouble()) {
                temperature = scanner.nextDouble();
                break;
            } else {
                System.out.println("Incorrect enter");
                str = scanner.nextLine();
            }
        }
        System.out.println("Enter convert scale for converting:");
        System.out.println("1 - Fahrenheit");
        System.out.println("2 - Kelvin");
        boolean flag = true;
        while (flag) {
            if (scanner.hasNextByte()) {
                choice = scanner.nextByte();
                switch (choice) {
                    case 1:
                        System.out.println(tempConvert.toFahrenheit(temperature) + " \u2109");
                        flag = false;
                        break;
                    case 2:
                        System.out.println(tempConvert.toKelvin(temperature) + "K");
                        flag = false;
                        break;
                    default:
                        System.out.println("Incorrect enter");
                        break;
                }

            } else {
                System.out.println("Incorrect enter");
                str = scanner.nextLine();
            }
        }

    }
}
