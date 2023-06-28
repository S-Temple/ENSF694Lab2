import java.util.Scanner;
import java.util.Arrays;

public class Main {
    // normal linear search from start to finish
    public static void linearSearch(int[] array, int key){
        for (int i = 0; i < array.length; i++){
            if (array[i] == key){
                System.out.println("Search Key FOUND at index " + i);
                return;
            }
        }
        System.out.println("Search key NOT FOUND");
    }

    // cut array in half and linear search half assuming array is sorted.
    public static void linearSearch2(int[] array, int key){
        if (key <= array[array.length / 2] ){
            for (int i = array.length / 2 + 1; i > -1; i--){
                if (array[i] == key){
                    System.out.println("Search Key FOUND at index " + i);
                    return;
                }
            }
        }else {
            for (int i = array.length / 2; i < array.length; i++){
                if (array[i] == key){
                    System.out.println("Search Key FOUND at index " + i);
                    return;
                }
            }
        }

        System.out.println("Search key NOT FOUND");
    }

    public static int interpolateSearch(int[] array, int key){
        // must be sorted array
        int min = 0;
        int max = array.length - 1;

        // while min != max or past and key between values at min and max
        while (min <= max && key >= array[min] && key <= array[max]) {
            if (min == max) {
                if (array[min] == key)
                    return min;
                return -1;
            }
            // pos update min index + ((difference >< key and value at min) * size of locations in max-min) / (value at max - value at min)
            int pos = min + ((key - array[min]) * (max - min)) / (array[max] - array[min]);

            if (array[pos] == key)
                return pos;

            if (array[pos] < key)
                min = pos + 1;
            else
                max = pos - 1;
        }

        return -1;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of elements in the array:");
        int sizeOfArray = input.nextInt();
        int[] array = new int[sizeOfArray];

        // uncomment to auto create sorted large arrays based on user size input
//        for(int i = 0; i < sizeOfArray; i++){
//            array[i] = i;
//        }
        System.out.println("Enter the elements in the array:");
        for(int i = 0; i < sizeOfArray; i++){
            array[i] = input.nextInt();
        }
        System.out.print("Enter the search key:");
        int searchKey = input.nextInt();

        Arrays.sort(array);

        double startTimel1 = System.nanoTime();
        linearSearch(array, searchKey);
        double endTimel1 = System.nanoTime();
        double durationLinear = (endTimel1 - startTimel1);

        double startTimei = System.nanoTime();
        int location = interpolateSearch(array, searchKey);
        if (location == -1){
            System.out.println("Search key NOT FOUND");
        }
        else{
            System.out.println("Search Key FOUND at index " + location);
        }
        double endTimei = System.nanoTime();
        double durationInterpolate = (endTimei - startTimei);

        System.out.println("linear took " +durationLinear+ " nanoseconds and interpolate took " + durationInterpolate + " nanoseconds");

        double startTimel2 = System.nanoTime();
        linearSearch2(array, searchKey);
        double endTimel2 = System.nanoTime();
        double durationLinear2 = (endTimel2 - startTimel2);

        // something really weird is happening on my machine.
        // testing with different sizes of arrays the performance is all over the place and often reduces speed?
        // actually where the method is called changes performance. moving the lines 86 to 89 to after the interpolate code changes the run times.
        // anywhere from -2000% to 99% improvement depending on code placement and size of array.

        double percent = (100.0 * (durationLinear - durationLinear2)) / durationLinear;
        System.out.println("linear improved took " +durationLinear2+ " nanoseconds and linear original took " +durationLinear+ " nanoseconds\nthis is " +percent+ " percent better");
    }
}