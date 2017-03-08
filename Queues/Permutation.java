/**
 * Created by CaTheother on 3/2/17.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args){
        int k=0;
        k=new Integer(args[0]);
        RandomizedQueue<String> rq=new RandomizedQueue<>();
            String[] sts = StdIn.readAllStrings();
            for (int i = 0; i < k; i++) {
                //to get bonus score:
                //reduce the size of objects added to queue
                int x=StdRandom.uniform(i,sts.length);
                rq.enqueue(sts[x]);
                sts[x]=sts[i];
                //same as the implementation of iterator of RandomizedQueue
        }
        for (int i=0;i<k;i++){
            StdOut.println(rq.dequeue());
        }
    }
}
