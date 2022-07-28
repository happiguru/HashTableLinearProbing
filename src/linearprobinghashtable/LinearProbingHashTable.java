package linearprobinghashtable;

import java.util.Scanner;

public class LinearProbingHashTable {

    private int presentSize, maxSize;
    private String[] keys;
    private String[] values;

    // create a constructor
    public LinearProbingHashTable(int extent){
        presentSize = 0;
        maxSize = extent;
        keys = new String[maxSize];
        values = new String[maxSize];
    }

    // Empty hash table function
    public void empty() {
        presentSize = 0;
        keys = new String[maxSize];
        values = new String[maxSize];
    }

    // Get the size of the hash table
    public int getSize() {
        return presentSize;
    }
    // Check if hash table is full
    public boolean isEmpty() {
        return getSize() == 0;
    }
    // Check if hash table contains a key
    public  boolean containKey(String key)
    {
        return getKey(key) != null;
    }

    // Get hash code for a given key
    private int hash(String key)
    {
        return key.hashCode() % maxSize;
    }
    // Insert key-value pair

    public void insertKeyValuePair(String key, String value) {
        int tempValue = hash(key);
        int counter = tempValue;
        do {
            if(keys[counter] == null)
            {
                keys[counter] = key;
                values[counter] = value;
                presentSize++;
                return;
            }
            if (keys[counter].equals(key)){
                values[counter] = value;
                return;
            }
            counter = (counter + 1) % maxSize;
        } while (counter != tempValue);
    }

    public  String getKey(String key)
    {
        int counter = hash(key);
        while (keys[counter] != null)
        {
            if (keys[counter].equals(key))
                return values[counter];
            counter = (counter + 1) % maxSize;
        }
        return  null;
    }

    // Remove key and its value
    public void removeKey(String key){
        if(!containKey(key))
            return;
        // find key position and delete
        int counter = hash(key);
        while (!key.equals(keys[counter]))
            counter = (counter + 1) % maxSize;
        keys[counter] = values[counter] = null;

        // rehash all keys
        for(counter = (counter + 1) % maxSize; keys[counter] != null; counter = (counter + 1) % maxSize)
        {
            String tempValueOne = keys[counter], tempValueTwo = values[counter];
            keys[counter] = values[counter] = null;
            presentSize--;
            insertKeyValuePair(tempValueOne, tempValueTwo);
        }
        presentSize--;
    }

    // print hash table
    public void printHashTable(){
        System.out.println("\n Hash Table: ");
        for(int counter = 0; counter < maxSize; counter++)
            if(keys[counter] != null)
                System.out.println(keys[counter] +" "+ values[counter]);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hash Table Test Using Linear Probing\n\n");
        System.out.println("Enter the hash table size.");
        LinearProbingHashTable hashTable = new LinearProbingHashTable(scanner.nextInt());
        char character;
        do {
            System.out.println("\n Select the Hash Table Operation You Want To Carry Out");
            System.out.println("1. Insertion");
            System.out.println("2. Deletion");
            System.out.println("3. Retrieval");
            System.out.println("4. Empty or Clear");
            System.out.println("5. Get Hash Table Size");

            int selection = scanner.nextInt();
            switch (selection){
                case 1:
                    System.out.println("Enter key and value");
                    hashTable.insertKeyValuePair(scanner.next(), scanner.next());
                    break;
                case 2:
                    System.out.println("Enter key: ");
                    hashTable.removeKey(scanner.next());
                    break;
                case 3:
                    System.out.println("Enter key: ");
                    System.out.println("The Value is "+ hashTable.getKey(scanner.next()));
                    break;
                case 4:
                    hashTable.empty();
                    System.out.println("Hash Table Cleared!");
                    break;
                case 5:
                    System.out.println("Hash Table Size is "+ hashTable.getSize());
                    break;
                default:
                    System.out.println("Wrong Value Entered!");
                    break;
            }
            // Display the hash table
            hashTable.printHashTable();
            System.out.println("\n Do you want to continue (Type Y or N) \n");
            character = scanner.next().charAt(0);
        } while (character == 'Y' || character == 'y');
    }
}