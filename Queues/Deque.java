import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by CaTheother on 3/2/17.
 */
public class Deque<Item> implements Iterable<Item> {
    private class Node{
        public Node(Item it){
            item=it;
        }
        public Item item=null;
        public Node next=null;
        public Node previous=null;
    }
    private Node first=null;
    private Node last=null;
    private int N=0;
    private Item i;
    public Deque(){
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
    public void addFirst(Item item){
        if (item==null){
            throw new NullPointerException();
        }
        Node node=new Node(item);
        node.next=first;
        if (first!=null){
            first.previous=node;
        }
        first=node;
        if (N==0){
            last=node;
        }
        N++;
    }
    public void addLast(Item item){
        if (item==null){
            throw new NullPointerException();
        }
        Node node=new Node(item);
        node.previous=last;
        if (last!=null){
            last.next=node;
        }
        last=node;
        if (N==0){
            first=node;
        }
        N++;

    }
    public Item removeFirst(){
        if (first==null){
            throw new NoSuchElementException();
        }
        Item item=first.item;
        first=first.next;
        N--;
        if (N==0){
            last=null;
        }
        return item;
    }
    public Item removeLast(){
        if (last==null){
            throw new NoSuchElementException();
        }
        Item item=last.item;
        last=last.previous;
        N--;
        if (N==0){
            first=null;
            return item;
        }
        last.next=null;
        return item;
    }
    public Iterator<Item> iterator(){
        return new dequeIterator();
    }
    private class dequeIterator implements Iterator<Item>{
        private Node current=first;
        public boolean hasNext(){
            return current!=null;
        }
        public Item next(){
            if (current==null){
                throw new NoSuchElementException();
            }
            Item item=current.item;
            current=current.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
    }
}
