import java.util.Scanner;

/**
 * Created by Evgeniy on 23.09.2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner( System.in);
        System.out.print("Введите N: ");
        int n = in.nextInt();
        double a[] = new double[n*(n-1)/2+n];
        double free[] = new double[n];
        double answ[] = new double[n];
        System.out.println("Заполните коэффициенты: "); int k=0;
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                if(j<=i) {a[k]=in.nextDouble(); k++;} else in.next();
            }

        }
        System.out.println("Введите свободные члены: ");
        for (int i = 0; i <n ; i++) {
            free[i]=in.nextDouble();
        }
        double sum=0;
        for (int i = 0; i < n ; i++) {
            sum=0;
            for (int l = 0; l <i ; l++) {
                sum+=a[i*(i+1)/2+l]*answ[l];
            }
            answ[i]=(free[i]-sum)/a[i*(i+1)/2+i];
        }
        System.out.println("Ответ");
        for (int i = 0; i <n ; i++) {
            System.out.println(answ[i]);
        }
    }


}
