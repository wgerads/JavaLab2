public static void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length - 1; j++) {
            if (arr[j] > arr[j+1]) {
                int tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
            }
        }
    }
}
public static void quickSort(int[] arr) {
    quickSortRecursive(arr, 0, arr.length - 1);
}

private static void quickSortRecursive(int[] arr, int left, int right) {
    if (left < right) {
        int pivot = partition(arr, left, right);
        quickSortRecursive(arr, left, pivot - 1);
        quickSortRecursive(arr, pivot + 1, right);
    }
}

private static int partition(int[] arr, int left, int right) {
    int pivot = arr[right];
    int i = left - 1;

    for (int j = left; j < right; j++) {
        if (arr[j] <= pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[right];
    arr[right] = temp;

    return i + 1;
}
public static void mergeSort(int[] arr) {
    if (arr.length <= 1) return;
    int mid = arr.length / 2;

    int[] left = Arrays.copyOfRange(arr, 0, mid);
    int[] right = Arrays.copyOfRange(arr, mid, arr.length);

    mergeSort(left);
    mergeSort(right);

    merge(arr, left, right);
}

private static void merge(int[] arr, int[] left, int[] right) {
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
        if (left[i] <= right[j]) arr[k++] = left[i++];
        else arr[k++] = right[j++];
    }
    while (i < left.length) arr[k++] = left[i++];
    while (j < right.length) arr[k++] = right[j++];
}
public static int[] randomArray(int size) {
    Random rnd = new Random();
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) arr[i] = rnd.nextInt(100);
    return arr;
}

public static int[] partiallySortedArray(int size) {
    int[] arr = randomArray(size);
    quickSort(arr);
    Random rnd = new Random();
    for (int i = 0; i < size / 4; i++) {
        int i1 = rnd.nextInt(size);
        int i2 = rnd.nextInt(size);
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    return arr;
}

public static int[] reverseSortedArray(int size) {
    int[] arr = randomArray(size);
    quickSort(arr);
    for (int i = 0; i < size / 2; i++) {
        int temp = arr[i];
        arr[i] = arr[size - i - 1];
        arr[size - i - 1] = temp;
    }
    return arr;
}

public static int[] arrayWithDuplicates(int size) {
    Random rnd = new Random();
    int uniqueCount = Math.max(1, size / 10);
    int[] uniqueValues = new int[uniqueCount];
    for (int i = 0; i < uniqueCount; i++) uniqueValues[i] = rnd.nextInt(100);
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) arr[i] = uniqueValues[rnd.nextInt(uniqueCount)];
    return arr;
}

public static int[] almostSortedArray(int size) {
    int[] arr = randomArray(size);
    quickSort(arr);
    Random rnd = new Random();
    for (int i = 0; i < size / 10; i++) {
        int i1 = rnd.nextInt(size);
        int i2 = rnd.nextInt(size);
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    return arr;
}
public static int[] chooseArray(int size, int typeNum) {
    int[] arr;
    if (typeNum == 0) arr = randomArray(size);
    else if (typeNum == 1) arr = partiallySortedArray(size);
    else if (typeNum == 2) arr = reverseSortedArray(size);
    else if (typeNum == 3) arr = arrayWithDuplicates(size);
    else arr = almostSortedArray(size);
    return arr;
}
public static long timeTest(int[] arr, int type) {

    int runs = 5;
    long totalTime = 0;

    for (int r = 0; r < runs; r++) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        long start = System.nanoTime();

        if (type == 0) bubbleSort(copy);
        else if (type == 1) quickSort(copy);
        else if (type == 2) mergeSort(copy);

        long end = System.nanoTime();
        totalTime += (end - start);
    }
    return totalTime / runs;
}
void main() {
    int[] sizes = {100, 1000, 10000};
    String[] arrayTypes = {"Случайный", "Частично отсортированный", "Обратно отсортированный",
            "С дубликатами", "Почти отсортированный"};
    String[] sortTypes = {"Пузырьком", "Быстрая", "Слиянием"};

    for (int size : sizes) {
        System.out.println("\n===== Массив размером: " + size + " =====");
        for (int typeNum = 0; typeNum < arrayTypes.length; typeNum++) {
            int[] arr = chooseArray(size, typeNum);
            System.out.println("\nТип массива: " + arrayTypes[typeNum]);
            for (int sort = 0; sort < sortTypes.length; sort++) {
                System.out.println(sortTypes[sort] + " " + timeTest(arr, sort) + " нс");

            }
        }
    }
}