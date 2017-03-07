import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by CaTheother on 3/2/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N=0;
    private int size=2;
    private int first=0;
    private int last=-1;

    private void reSize(){
        Item[] newa=(Item[]) new Object[size*2];
        for (int i=0;i<N;i++){
            newa[i]=a[(first+i)%size];
        }
        a=newa;
        size*=2;
        first=0;
        last=N-1;
    }

    private int getRandomPosition(){
        int x=StdRandom.uniform(0,N);
        x=(first+x)%size;
        return x;
    }

    public RandomizedQueue(){
        a=(Item[]) new Object[size];
    }
    public boolean isEmpty(){
        if (N==0){
            return true;
        }
        return false;
    }
    public int size(){
        return N;
    }
    public void enqueue(Item item){
        if (item==null){
            throw new NullPointerException();
        }
        last++;
        last=last%size;
        a[last]=item;
        N++;
        if (N==size){
            reSize();
        }
    }
    public Item dequeue(){
        if (N==0){
            throw new NoSuchElementException();
        }
        int x=getRandomPosition();
        Item item=a[x];
        a[x]=a[first];
        first++;
        N--;
        first=first%size;
        return item;
    }
    public Item sample(){
        if (N==0){
            throw new NoSuchElementException();
        }
        int x=getRandomPosition();
        return a[x];

    }
    public Iterator<Item> iterator(){
        return new QueueIterator();

    }
    private class QueueIterator implements Iterator<Item>{
        private int current=0;
        private Item[] b;
        public QueueIterator(){
            b=(Item[]) new Object[N];
            for (int i=0;i<N;i++){
                b[i]=a[(first+i)%size];
            }
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }

        public boolean hasNext(){
            if (current==N){
                return false;
            }
            return true;
        }
        public Item next(){
            if (current==N){
                throw new NoSuchElementException();
            }
            int x=StdRandom.uniform(current,N);
            Item item=b[x];
            b[x]=b[current];
            current++;
            return item;
        }

    }
    public static void main(String[] args){
    }
}
