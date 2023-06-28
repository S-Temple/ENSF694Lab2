import java.util.Scanner;

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
            for (int i = 0; i < array.length; i++){
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

        double startTime = System.nanoTime();
        linearSearch(array, searchKey);
        double endTime = System.nanoTime();
        double durationLinear = (endTime - startTime);


        // implement sort later
        startTime = System.nanoTime();
        int location = interpolateSearch(array, searchKey);
        if (location == -1){
            System.out.println("Search key NOT FOUND");
        }
        else{
            System.out.println("Search Key FOUND at index " + location);
        }
        endTime = System.nanoTime();
        double durationInterpolate = (endTime - startTime);

        System.out.println("linear took " +durationLinear+ " nanoseconds and interpolate took " + durationInterpolate + " nanoseconds");

        startTime = System.nanoTime();
        linearSearch2(array, searchKey);
        endTime = System.nanoTime();
        double durationLinear2 = (endTime - startTime);

        // something really weird is happening on my machine. The linearSearch2 method is faster when it shouldn't be and the % improvement is impossible.
        // testing with array of size 100,000 with values 0-99,999 searching for key = 0 the original linear search should be faster, but isn't?
        double percent = (100.0 * (durationLinear - durationLinear2)) / durationLinear;
        System.out.println("linear improved took " +durationLinear2+ " nanoseconds and linear original took " +durationLinear+ " nanoseconds\n this is " +percent+ " percent better");
    }
}