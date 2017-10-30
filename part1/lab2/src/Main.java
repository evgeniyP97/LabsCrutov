import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Evgeniy on 23.09.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        infix_to_postfix();
        calc_postfix();
    }

    static void infix_to_postfix() throws IOException {
        Scanner fin = new Scanner(new File("infix.txt"));
        FileWriter fw = new FileWriter("postfix.txt");
        Stack<String> op = new Stack<>();
        while(fin.hasNext())
        {
            if(fin.hasNextInt())
            {
                fw.write(fin.nextInt()+" ");
            }
            else
            {
                String s = fin.next();
                if(s.equals("+") || s.equals("-")){
                    if(op.isEmpty()){
                        op.push(s);
                    }
                    else if( !op.peek().equals("+") && !op.peek().equals("-") && !op.peek().equals("("))
                    {
                        while (!op.isEmpty() && !op.peek().equals("("))
                        {
                            fw.write(op.pop()+" ");
                        }
                        if(!op.isEmpty() && op.peek().equals("(")) op.pop();
                        op.push(s);
                    }
                    else
                    {
                        op.push(s);
                    }
                }
                else if(!s.equals(")"))
                {
                    op.push(s);
                }
                else
                {
                    while (!op.isEmpty() && !op.peek().equals("("))
                    {
                        fw.write(op.pop()+" ");
                    }
                    if(!op.isEmpty() && op.peek().equals("(")) op.pop();
                }
            }
        }
        while(!op.isEmpty())
        {
            fw.write(op.pop()+" ");
        }
        fw.flush();
    }

    static void calc_postfix() throws FileNotFoundException {
        Stack<Integer> mas = new Stack();
        Scanner fin = new Scanner(new File("postfix.txt"));
        while(fin.hasNext())
        {
            if(fin.hasNextInt())
            {
                mas.push(fin.nextInt());
            }
            else
            {
                operation(fin.next(),mas);
            }
        }
        System.out.println(mas.pop());
    }
    static void operation(String op, Stack<Integer> mas)
    {
        if(op.equals("+"))
        {
            int a = mas.pop();
            int b = mas.pop();
            mas.push(a+b);
        }
        else if(op.equals("-"))
        {
            int a = mas.pop();
            int b = mas.pop();
            mas.push(b-a);
        }
        else if (op.equals("*"))
        {
            int a = mas.pop();
            int b = mas.pop();
            mas.push(b*a);
        }
        else if(op.equals("/"))
        {
            int a = mas.pop();
            int b = mas.pop();
            mas.push(b/a);
        }
        else if(op.equals("^"))
        {
            int a = mas.pop();
            int b = mas.pop();
            mas.push(pow(b,a));
        }
    }

    static int pow(int a, int b)
    {
        int res = 1;
        for (int i = 0; i <b ; i++) {
            res*=a;
        }
        return res;
    }
}
