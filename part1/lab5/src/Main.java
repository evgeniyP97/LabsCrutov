import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

interface Sort
{
    void method(int[] arr);
}



public class Main {

    public static long measure(Sort sort, int[] arr ) {
        long st, en;
        st = System.nanoTime();
        sort.method(arr);
        en = System.nanoTime();
        //System.out.println((en - st)/1000000 +" мс");
        return (en - st)/1000000;
    }
    public static void main(String[] args) throws  IOException {
        //Thread.sleep(1000);
        //System.out.println("cold start time " + measure() + " ms");
        Scanner in = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new File("output.txt"));
        System.out.print("Введите кол-во случайных массивов: ");
        int n = in.nextInt();
        ArrayList<Sort> sorts = new ArrayList<>(5);
        sorts.add((arr1 -> bubbleSort(arr1)));
        sorts.add((arr1 -> quickSort(arr1)));
        sorts.add((arr1 -> choiceSort(arr1)));
        sorts.add((arr1 -> insertSort(arr1)));
        sorts.add((arr1 -> mergerSort(arr1)));
        pw.printf("%5s%20s%20s%20s%20s%20s%20s\n","Номер","Число элементов","Пузырьком","Быстрая","Выбором","Вставками","Слиянием");
        for (int i = 0; i <n ; i++) {
            System.out.print("Введите размер "+(i+1)+"-го массива: ");
            int size=in.nextInt();
            int arr[] = new int[size];
            for (int j = 0; j <size ; j++) {
                arr[j]=(int)(Math.random()*10000000);
            }
            pw.printf("%5d%20d",i+1,size);
            for (Sort e : sorts) {
                int[] tmp = new int[arr.length];
                for (int j = 0; j < arr.length; j++) {
                    tmp[j] = arr[j];
                }
                pw.printf("%20s",measure(e, tmp)+" ms");
            }
            pw.printf("\n");
//            System.out.println("Исходный массив");
//            for (int j = 0; j <size ; j++) {
//                System.out.print(arr[j]+" ");
//            }
//            System.out.println("");
        }


        pw.flush();
        pw.close();

    }
    //Сортировка Пузырьком (Обменная)
    public static void bubbleSort(int[] arr){
        for(int i = arr.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
                if( arr[j] > arr[j+1] ){
                int tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
                }
            }
        }
    }
    //Быстрая сортировка (Обменная)
    public static void quickSort(int[] array) {
        int startIndex = 0;
        int endIndex = array.length - 1;
        doSort(startIndex, endIndex, array);
    }

    private static void doSort(int start, int end, int[] array) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (array[i] <= array[cur])) {
                i++;
            }
            while (j > cur && (array[cur] <= array[j])) {
                j--;
            }
            if (i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur, array);
        doSort(cur+1, end, array);
    }
    //Сортировка выбором
    public static void choiceSort (int[] arr) {
        for (int min=0;min<arr.length-1;min++) {
            int k = min;
            for (int j=min+1;j<arr.length;j++) {
                if(arr[j] < arr[k]) {
                    k = j;
                }
            }
            int tmp = arr[min];
            arr[min] = arr[k];arr[k] = tmp;
        }
    }
    //Сортировка вставками
    public static void insertSort(int[] arr){
        int n = arr.length;
        for(int i=1;i<n;i++){
            for(int j=i; j>0 && arr[j-1]>arr[j];j--){
                int tmp=arr[j-1];
                arr[j-1]=arr[j];
                arr[j]=tmp;
            }
        }
    }

    //Сортировка слиянием
    public static void mergerSort(int a[])
    {
        doMergeSort(a,0,a.length-1);
    }
    private static void doMergeSort(int[] a, int lo, int hi) {

        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        doMergeSort(a, lo, mid);
        doMergeSort(a, mid + 1, hi);

        int[] buf = Arrays.copyOf(a, a.length);

        for (int k = lo; k <= hi; k++)
            buf[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {

            if (i > mid) {
                a[k] = buf[j];
                j++;
            } else if (j > hi) {
                a[k] = buf[i];
                i++;
            } else if (buf[j] < buf[i]) {
                a[k] = buf[j];
                j++;
            } else {
                a[k] = buf[i];
                i++;
            }
        }
    }
}
