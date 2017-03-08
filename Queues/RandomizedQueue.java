import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by CaTheother on 3/2/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N=0;//the number of items
    private int size=2;//the size of array
    private int first=0;//first position of queue
    private int last=-1;//last position of queue
    //if initial last=0:
    // after first call of enqueue(),Error:first=0,last=1,N=1;

    private void reSize(int newsize){
        //if (N==size): newsize=size*2;
        //if (N==size/4)and(N>0): newsize=size/2;
            Item[] newa = (Item[]) new Object[newsize];
            for (int i = 0; i < N; i++) {
                newa[i] = a[(first + i) % size];
            }
            a = newa;
            size =newsize;
            first = 0;
            last = N - 1;
            //reset:
            //newa[0] will be the first of queue;
    }

    private int getRandomPosition(){
        //get a random position from the valid area
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
        last=last%size;//if last==size: a[0] will be the last
        a[last]=item;
        N++;
        if (N==size){//if full, double size
            reSize(size*2);
        }
    }
    public Item dequeue(){
        if (N==0){
            throw new NoSuchElementException();
        }
        int x=getRandomPosition();
        Item item=a[x];
        a[x]=a[first];
        a[first]=null;
        first++;
        N--;
        first=first%size;
        if ((N==size/4)&&(N>0)){//release loitering memories
            reSize(size/2);
        }
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
            int x=StdRandom.uniform(current,N);//[0,current) is the invalid area
            Item item=b[x];
            b[x]=b[current];//after using b[x],b[x] will be replaced by b[current]
            current++;//so old b[current] will still be in the valid area
            return item;
        }

    }
    public static void main(String[] args){
    }
}
