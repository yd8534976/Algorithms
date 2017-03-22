import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

/**
 * Created by CaTheother on 3/18/17.
 */
public class Board {
    private int[][] blocks;
    private final int n;
    private int emptyX;
    private int emptyY;

    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (this.blocks[i][j] == 0) {
                    emptyX = i;
                    emptyY = j;
                }
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                if (num != (i * n + j + 1)) {
                    hamming++;
                }
            }
        }
        hamming--;
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                if (num == 0) {
                    continue;
                }
                int goalX = (num - 1) / n;
                int goalY = (num - 1) % n;
                int distance = Math.abs(goalX - i) + Math.abs(goalY - j);
                manhattan = manhattan + distance;
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        boolean result = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = blocks[i][j];
                if (num == 0) {
                    continue;
                }
                if (num != (i * n + j + 1)) {
                    result = false;
                }
            }
        }
        return result;
    }

    public Board twin() {
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = this.blocks[i][j];
            }
        }
        while (true) {
            int x1 = StdRandom.uniform(0, n);
            int y1 = StdRandom.uniform(0, n);
            int x2 = StdRandom.uniform(0, n);
            int y2 = StdRandom.uniform(0, n);
            if (blocks[x1][y1] != blocks[x2][y2]) {
                if ((!((x1 == emptyX) && (y1 == emptyY))) && (!((x2 == emptyX) && (y2 == emptyY)))) {
                    int temp = blocks[x1][y1];
                    blocks[x1][y1] = blocks[x2][y2];
                    blocks[x2][y2] = temp;
                    Board board = new Board(blocks);
                    return board;
                }
            }
        }
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        Board by;
        try {
            by = (Board) y;
        } catch (Exception e) {
            return false;
        }
        if (by.n != this.n) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != by.blocks[i][j]) {
                    result = false;
                }
            }
        }
        return result;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        if (emptyX - 1 >= 0) {
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocks[i][j] = this.blocks[i][j];
                }
            }
            blocks[emptyX][emptyY] = blocks[emptyX - 1][emptyY];
            blocks[emptyX - 1][emptyY] = 0;
            Board up = new Board(blocks);
            neighbors.add(up);
        }
        if (emptyX + 1 < n) {
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocks[i][j] = this.blocks[i][j];
                }
            }
            blocks[emptyX][emptyY] = blocks[emptyX + 1][emptyY];
            blocks[emptyX + 1][emptyY] = 0;
            Board down = new Board(blocks);
            neighbors.add(down);
        }
        if (emptyY - 1 >= 0) {
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocks[i][j] = this.blocks[i][j];
                }
            }
            blocks[emptyX][emptyY] = blocks[emptyX][emptyY - 1];
            blocks[emptyX][emptyY - 1] = 0;
            Board left = new Board(blocks);
            neighbors.add(left);
        }
        if (emptyY + 1 < n) {
            int[][] blocks = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    blocks[i][j] = this.blocks[i][j];
                }
            }
            blocks[emptyX][emptyY] = blocks[emptyX][emptyY + 1];
            blocks[emptyX][emptyY + 1] = 0;
            Board right = new Board(blocks);
            neighbors.add(right);
        }
        return neighbors;
    }

    public String toString() {
        String str = "" + n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str = str + blocks[i][j] + " ";
            }
            str = str + "\n";
        }
        return str;
    }

    public static void main(String[] args) {
    }
}
