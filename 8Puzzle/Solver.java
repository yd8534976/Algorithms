import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by CaTheother on 3/18/17.
 */
public class Solver {
    private class Node {//search node structure
        public int priority;
        public int moves;
        public Node parents;
        public Board board;
        public int manhattan;

        public Node(int m, Board b) {
            board = b;
            moves = m;
            manhattan = board.manhattan();

            priority = manhattan + moves;
        }
    }

    //优先队列不能为私有成员变量，否则会导致memory爆炸。。mark：回头研究下。
    private Node last;//解得最后一个节点的引用，可通过追溯父节点输出具体solution步骤
    private int moves = 0;
    private boolean isSolvable = true;//该broad是否有解

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        Board twin = initial.twin();
        Node root = new Node(0, initial);
        root.parents = root;//trick：防止处理（a）时候父节点为null，程序结束前需调回null
        Node root2 = new Node(0, twin);
        root2.parents = root2;
        MinPQ<Node> pq = new MinPQ<>(new ByManhattan());//优先队列存放叶节点
        MinPQ<Node> pq2 = new MinPQ<>(new ByManhattan());
        Node current;
        current = root;
        Node current2;
        current2 = root2;
        /*
        此处所有实例都为双份，current2代表解twin的状态
        twin和initial的可解性相反
        因此同步进行，如果其中一个先完成，则直接判定对方为不可解
         */
        while (current.manhattan != 0) {//此处使用Manhattan值判断比board.isGoal()更快
            if (current2.manhattan == 0) {//if twin is solvable,this will be unsolvable
                last = null;
                moves = -1;
                isSolvable = false;
                return;
            }
            for (Board board : current.board.neighbors()) {
                if (!board.equals(current.parents.board)) {//（a）：防止A->B->A的情况出现
                    Node n = new Node(current.moves + 1, board);
                    n.parents = current;
                    pq.insert(n);
                }
            }
            current = pq.delMin();

            for (Board board : current2.board.neighbors()) {
                if (!board.equals(current2.parents.board)) {
                    Node n = new Node(current2.moves + 1, board);
                    n.parents = current2;
                    pq2.insert(n);
                }
            }
            current2 = pq2.delMin();
        }
        root.parents = null;//对应trick
        root.parents = null;
        isSolvable = true;
        moves = current.moves;
        last = current;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        if (last == null) {
            return null;
        }
        ArrayList<Board> solution = new ArrayList<>();
        Node current = last;
        while (current != null) {
            solution.add(0, current.board);
            current = current.parents;
        }
        return solution;
    }

    private class ByManhattan implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.priority > o2.priority) {
                return 1;
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
