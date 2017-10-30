

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Evgeniy on 28.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите кол-во вершин графа: ");
        int n = in.nextInt();
        System.out.print("Введите кол-во ребер графа:  ");
        int m = in.nextInt();
        System.out.println("Введите ребра графа! в виде a b c, где a,b - вершины с-расстояние между ними");
        ArrayList<ArrayList<Pair>> graph = new ArrayList<>(n);
        for (int i = 0; i <n ; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i <m ; i++) {
            insert(graph,in.nextInt()-1,in.nextInt()-1,in.nextInt());
        }
        ArrayList<ArrayList<Pair>> res = buildOstTree(n,graph);
        System.out.println("Исходный граф:");
        printGraph(graph,n);
        System.out.println("Минимальное остовное дерево");
        printGraph(res,n);
        System.out.println("Готово!");
        //findMin(new int[]{1,2,3},3);

    }

    public static void printGraph(ArrayList<ArrayList<Pair>> graph,int n)
    {
        for (int i = 0; i < n ; i++) {
            System.out.print((i+1)+": ");
            for(Pair p : graph.get(i))
            {
                System.out.print("("+(p.getKey()+1)+" -> "+p.getVal()+") ");
            }
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Pair>> buildOstTree(int n, ArrayList<ArrayList<Pair>> graph)
    {
        ArrayList<ArrayList<Pair>> res = new ArrayList<>(n);
        for (int i = 0; i <n ; i++) {
            res.add(new ArrayList<>());
        }
        int mat[][]= new int[n][n];
        int tmat[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n ; j++) {
                mat[i][j] = Integer.MAX_VALUE;
                tmat[i][j] = -1;
            }
        }
        for (int i = 0; i <n ; i++) {
            int finalI = i;
            graph.get(i).forEach(e->{
                mat[finalI][e.getKey()]=e.getVal();
            });
        }
        TreeSet<Integer> stolbs = new TreeSet<>();
        int index[] = new int[2];
        int min = findMin(mat,n,index);
        stolbs.add(index[0]); stolbs.add(index[1]);
        tmat[index[0]][index[1]] = min;
        tmat[index[1]][index[0]] = min;
        for (int i = 0; i <2 ; i++) {
            for (int j = 0; j <n ; j++) {
                mat[index[i]][j]=Integer.MAX_VALUE;
            }
        }

        while (stolbs.size()!=n)
        {
            min = mat[0][stolbs.first()];
            for (int i : stolbs) {
                for (int j = 0; j <n ; j++) {
                    if(mat[j][i]<min)
                    {
                        min=mat[j][i];
                        index[0]=j;
                        index[1]=i;
                    }
                }
            }
            stolbs.add(index[0]);
            tmat[index[0]][index[1]] = min;
            tmat[index[1]][index[0]] = min;
            for (int i = 0; i < n ; i++) {
                mat[index[0]][i]=Integer.MAX_VALUE;
            }

        }
        for (int i = 0; i <n ; i++) {
            for (int j = i+1; j < n; j++) {
                if(tmat[i][j]!=-1)
                {
                    res.get(i).add(new Pair(j,tmat[i][j]));
                    res.get(j).add(new Pair(i,tmat[i][j]));
                }
            }
        }
//        for (int i = 0; i <n ; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.printf("%4d ",mat[i][j]);
//            }
//            System.out.println();
//        }
//        for (int i = 0; i <n ; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.printf("%4d ",tmat[i][j]);
//            }
//            System.out.println();
//        }
        return res;
    }

    public static void insert(ArrayList<ArrayList<Pair>> graph, int a, int b, int val)
    {
        boolean hasKey = false;
        for(Pair p : graph.get(a))
        {
            if(b==p.getKey())
            {
                hasKey=true;
                break;
            }
        }
        if (!hasKey)
        {
            graph.get(a).add(new Pair(b,val));
            graph.get(b).add(new Pair(a,val));
        }
    }

    public static int findMin(int mat[][],int n, int index[] ) {
        int min = mat[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] < min) {
                    min = mat[i][j];
                    index[0] = i;
                    index[1] = j;
                }
            }
        }
        return min;
    }

//    public static Pair findMinS(int[] stolbs, int n)
//    {
//        Pair min = new Pair(stolbs[0],0);
//        Pair tmp;
//        for (int i = 0; i < stolbs.length; i++) {
//            for (int j = 0; j <n ; j++) {
//                if(min.getVal())
//            }
//        }
//        return min;
//    }
}


class Pair
{
    private int key;
    private int val;
    public Pair(int key, int val)
    {
        this.key=key;
        this.val=val;
    }

    public int getKey() {
        return key;
    }

    public int getVal() {
        return val;
    }
}
