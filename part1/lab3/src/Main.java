import java.lang.StringBuilder;
import java.util.Scanner;


class Tree {

    protected static class Node {

        protected Integer  element;
        protected Node left;
        protected Node right;
        protected int height;
        public Node (int theElement){
            this (theElement, null, null);
        }

        public Node (int theElement, Node lt, Node rt){
            element = theElement;
            left = lt;
            right = rt;
        }
    }

    public Node root;
    public int countInsertions;
    public int countSingleRotations;
    public int countDoubleRotations;

    public Tree (){
        root = null;

        countInsertions = 0;
        countSingleRotations = 0;
        countDoubleRotations = 0;
    }

    public int height (Node t){
        return t == null ? -1 : t.height;
    }

    public int max (int a, int b){
        if (a > b)
            return a;
        return b;
    }

    public boolean insert (int x){
        try {
            root = insert (x, root);

            countInsertions++;
            return true;
        } catch(Exception e){
            return false;
        }
    }
    protected Node insert (int x, Node t) throws Exception{
        if (t == null)
            t = new Node(x);
        else if (x < t.element){
            t.left = insert (x, t.left);
        }
        else if (x> t.element){
            t.right = insert (x, t.right);
        }
        else {
            throw new Exception("Attempting to insert duplicate value");
        }

        t.height = max (height (t.left), height (t.right)) + 1;
        return t;
    }

    public String print(){
        StringBuilder str = new StringBuilder();
        print (root, str, " ");
        return str.toString();
    }
    protected void print(Node t, StringBuilder str, String sep){
        if (t != null){
            print (t.left, str, sep);
            str.append(t.element);
            str.append(sep);
            print (t.right, str, sep);
        }
    }

    public void printTree()
    {
        StringBuilder str = new StringBuilder("Г");
        printTree(root,str);
    }
    public void printTree(Node t, StringBuilder str)
    {
        if(t!=null) {
            System.out.println(str + " - " + t.element);
            str.append("Л");
            printTree(t.left, str);
            str.deleteCharAt(str.length()-1);
            str.append("П");
            printTree(t.right, str);
            str.deleteCharAt(str.length()-1);
        }
    }
    private Node findMax( Node t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }


    public void remove( int x ) {
        root = remove(x, root);
    }

    public Node remove(int x, Node t) {
        if (t==null)    {
            System.out.println("Удаляемого элемента не существует!\n");
            return null;
        }

        if (x < t.element ) {
            t.left = remove(x,t.left);
        }
        else if (x > t.element) {
            t.right = remove(x,t.right);
        }
        else if(t.left != null) {
            t.element = findMax(t.left).element;
            t.left = remove(t.element, t.left);

        }

        else
            t = (t.left != null) ? t.left : t.right;

        if(t != null) {
            int leftHeight = t.left != null ? t.left.height : -1;
            int rightHeight = t.right!= null ? t.right.height : -1;
            t.height = Math.max(leftHeight,rightHeight) + 1;
        }
        return t;
    }
}
public class Main
{
    public static void main (String []args) {
        Scanner in = new Scanner(System.in);
        Tree t = new Tree();


        System.out.println("Введите кол-во элементов в дереве: ");
        int n = in.nextInt();
        System.out.println("Ввод элементов:");
        for (int i = 0; i <n ; i++) {
            t.insert(in.nextInt());
        }
        System.out.println ("Дерево в отсортированном порядке:");
        System.out.println(t.print());
        t.printTree();
        System.out.println("Введите число элементов для удаления: ");
        n=in.nextInt();
        System.out.println("Удаление");
        for (int i = 0; i < n; i++)
        {
            System.out.print("Введите элемент который хотите удалить: ");
            t.remove(in.nextInt());
            System.out.println ("Дерево в отсортированном порядке:");
            System.out.println(t.print());
            t.printTree();
        }
    }
}