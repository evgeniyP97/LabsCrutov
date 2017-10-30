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
//            if(!checkBalance(root))
//            {
//                balance(root);
//            }
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
            if(!checkBalance(t))
            {
                balance(t);
            }
        }
        else if (x> t.element){
            t.right = insert (x, t.right);
            if(!checkBalance(t))
            {
                balance(t);
            }
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
            if(!checkBalance(t))
            {
                balance(t);
            }
        }
        else if (x > t.element) {
            t.right = remove(x,t.right);
            if(!checkBalance(t))
            {
                balance(t);
            }
        }
        else if(t.left != null) {
            t.element = findMax(t.left).element;
            t.left = remove(t.element, t.left);
            if(!checkBalance(t))
            {
                balance(t);
            }
        }
        else t = (t.left != null) ? t.left : t.right;
        if(t != null) {
            int leftHeight = t.left != null ? t.left.height : -1;
            int rightHeight = t.right!= null ? t.right.height : -1;
            t.height = Math.max(leftHeight,rightHeight) + 1;
        }
        //if(!checkBalance(t)) balance(t);
        return t;
    }

    boolean checkBalance(Node t)
    {
        int leftH=t.left==null?-1:t.left.height;
        int rightH=t.right==null?-1:t.right.height;
        if(Math.abs(leftH-rightH)>1) return false;
        return true;
    }

    void balance(Node t)
    {
        Node tmp;
        int leftH=t.left==null?-1:t.left.height;
        int rightH=t.right==null?-1:t.right.height;
        if(leftH>rightH)
        {
            leftH=t.left.left==null?-1:t.left.left.height;
            rightH=t.left.right==null?-1:t.left.right.height;
            if(leftH>=rightH)
            {
                tmp = rotateR(t);
            }
            else
            {
                Node tmp1 = rotateL(t.left);
                Node tmp2=new Node(t.element,tmp1,t.right);
                tmp = rotateR(tmp2);
            }
        }
        else
        {
            leftH=t.right.left==null?-1:t.right.left.height;
            rightH=t.right.right==null?-1:t.right.right.height;
            if(rightH>=leftH)
            {
                tmp = rotateL(t);
            }
            else
            {
                Node tmp1 = rotateR(t.right);
                Node tmp2=new Node(t.element,t.left,tmp1);
                tmp = rotateL(tmp2);
            }

        }
        t.left=tmp.left;
        t.right=tmp.right;
        t.element=tmp.element;
        t.height=tmp.height;
    }
    Node rotateR(Node t)
    {
        Node tmp = new Node(t.element,t.left.right,t.right);
        tmp.height = max(height (tmp.left), height (tmp.right))+1;
        Node ret = new Node(t.left.element,t.left.left,tmp);
        ret.height = max (height (ret.left), height (ret.right)) + 1;
        return ret;
    }
    Node rotateL(Node t)
    {
        Node tmp = new Node(t.element,t.left,t.right.left);
        tmp.height = max(height (tmp.left), height (tmp.right))+1;
        Node ret = new Node(t.right.element,tmp,t.right.right);
        ret.height = max (height (ret.left), height (ret.right)) + 1;
        return ret;
    }
    public boolean checkBalanceOfTree(Node current) {

        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;

        if (current.right != null) {
            balancedRight = checkBalanceOfTree(current.right);
            rightHeight = getDepth(current.right);
        }

        if (current.left != null) {
            balancedLeft = checkBalanceOfTree(current.left);
            leftHeight = getDepth(current.left);
        }

        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }

    public int getDepth(Node n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);

        return Math.max(rightHeight, leftHeight)+1;
    }
    public boolean checkOrderingOfTree()
    {
        if(root==null) return true;
        return checkOrderingOfTree(root);
    }
    private boolean checkOrderingOfTree(Tree.Node current) {
        if(current.left != null) {
            if(current.left.element.compareTo(current.element) > 0)
                return false;
            else
                return checkOrderingOfTree(current.left);
        } else  if(current.right != null) {
            if(current.right.element.compareTo(current.element) < 0)
                return false;
            else
                return checkOrderingOfTree(current.right);
        } else if(current.left == null && current.right == null)
            return true;

        return true;
    }
}
public class Main
{
    public static void main (String []args) {
        Scanner in = new Scanner(System.in);
        Tree t = new Tree();

        System.out.print("Введите кол-во элементов в дереве: ");
        int n = in.nextInt();
        for (int i = 0; i <n ; i++) {
            t.insert(in.nextInt());
        }
        System.out.println("Дерево в отсортированном виде\n"+t.print());
        System.out.println(t.checkOrderingOfTree()?"Дерево сбалансированно!":"Дерево не сбалансированно!");
        t.printTree();
        System.out.println("Введите кол-во элементов для удаления из дерева: ");
        int k = in.nextInt();
        System.out.println("Удаление:");
        for (int i = 0; i <k ; i++) {
            t.remove(in.nextInt());
            System.out.println("Дерево в отсортированном виде\n"+t.print());
            System.out.println(t.checkOrderingOfTree()?"Дерево сбалансированно!":"Дерево не сбалансированно!");
            t.printTree();
        }
        t.printTree();
    }
}