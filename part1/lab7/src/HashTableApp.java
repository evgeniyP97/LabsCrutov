import java.io.*;
import java.util.Scanner;

class HashTable
{
    private String[] hashArray;
    private int arraySize;
    public String nonItem;

    public HashTable(int size)
    {
        arraySize = size;
        hashArray = new String[arraySize];
        nonItem = null;
    }

    public void displayTable() throws IOException {
        FileWriter fw = new FileWriter("out.txt");
        System.out.print("Хэш-таблица записана в файл!");
        for(int j=0; j<arraySize; j++)
        {
            if(hashArray[j] != null)
                fw.write(hashArray[j] + " ");
            else
                fw.write("* ");
        }
        System.out.println("");
        fw.flush();
        fw.close();
    }

    public  int hashFunc(String key)
    {
        int hashVal = 0;
        for(int j=0; j<key.length(); j++)
        {
            int letter = key.charAt(j) - 96;
            hashVal = (hashVal * 27 + letter) % arraySize;
        }
        return hashVal;
    }

    public void insert(String item)
    {
        String key = item;
        int hashVal = hashFunc(key);
        while(hashArray[hashVal] != null)
        {
            if(hashArray[hashVal].equals(key)) return;
            ++hashVal;
            hashVal %= arraySize;
        }
        hashArray[hashVal] = item;
    }

    public String find(String key)
    {
        int hashVal = hashFunc(key);
        while(hashArray[hashVal] != null)
        {
            if(hashArray[hashVal].equals(key))
                return hashArray[hashVal];
            ++hashVal;
            hashVal %= arraySize;
        }
        return null;
    }
}


public class HashTableApp {

    public static void main(String[] args) throws IOException {
        HashTable ht = new HashTable(10000);
        Scanner fin = new Scanner(new BufferedReader(new FileReader("input.txt")));
        Scanner in = new Scanner(System.in);
        while(fin.hasNext())
        {
            ht.insert(fin.next().toLowerCase());
        }
        ht.displayTable();
        System.out.print("Сколько раз вы хотите проверить на наличие слова: ");
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Введите слово для проверки вхождения: ");
            System.out.println(ht.find(in.next().toLowerCase())!=null?"Слово найдено!":"Совпадений не найдено!");
        }
    }
}
