import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

interface Find
{
    void method(Integer[] arr, int val);
}

public class Main {

    public static long measure(Find find, Integer[] arr, int val ) {
        long st, en;
        st = System.nanoTime();
        find.method(arr,val);
        en = System.nanoTime();
        //System.out.println((en - st)/1000000 +" мс");
        return (en - st)/1000000;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new File("output.txt"));
        System.out.print("Введите кол-во случайных массивов: ");
        int n = in.nextInt();
        ArrayList<Find> finds = new ArrayList<>(5);
        finds.add((arr1,val1) -> consistent0Find(arr1,val1));
        finds.add((arr1,val1) -> consistent1Find(arr1,val1));
        finds.add((arr1,val1) -> consistent2Find(arr1,val1));
//        Integer[] a = {5,null};
//        consistent0Find(a,5);
        pw.printf("%5s%15s%15s%15s%15s\n","Номер","Размер массива","Способ 1","Способ 2","Способ 3");
        for (int i = 0; i <n ; i++) {
            System.out.print("Введите размер " + (i + 1) + "-го массива: ");
            int size = in.nextInt();
            Integer arr[] = new Integer[size + 1];
            for (int j = 0; j < size; j++) {
                arr[j] = (int) (Math.random() * 10000000);
            }
            pw.printf("%5d%15d", i + 1, size);
            for (Find e : finds) {
                Integer[] tmp = new Integer[arr.length];
                for (int j = 0; j < arr.length; j++) {
                    tmp[j] = arr[j];
                }
                pw.printf("%15s", measure(e, tmp, tmp[size - 1]) + " ms");
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
//        sorts.add((arr1 -> insertSort(arr1)));
//        sorts.add((arr1 -> mergerSort(arr1)));
    }

    public static void consistent0Find(Integer arr[], int val)
    {
        int i=0,n=arr.length;
        boolean mustCont = true;
        while (i<n-1 && mustCont)
        {
            mustCont = arr[i]!=val;
            i++;
        }
        if(!mustCont) System.out.println("Запись найдена!");
        else System.out.println("Поиск неудачен!");
    }

    public static void consistent1Find(Integer arr[], int val)
    {
        int i=0,n=arr.length;
        while (true)
        {
            if(arr[i]==null)
            {
                if(i<n) {System.out.println("Запись найдена!"); break;}
                else {System.out.println("Поиск неудачен!"); break;}
            }
            i++;
        }
    }
    public static void consistent2Find(Integer arr[], int val)
    {
        int i=0,n=arr.length;
        if(arr[i]==null)
        {
            System.out.println("Массив пуст!"); return;
        }
        if(arr[i+1]==null)
        {
            if(arr[i]==val) System.out.println("Запись найдена!"); return;
        }
        while (true)
        {
            i+=2;
            if(arr[i]==null) break;
            if(arr[i+1]!=null) continue;
            i++;
        }
        if(i<n) System.out.println("Запись найдена!");
        else System.out.println("Поиск неудачен!");
    }
}
