package sort;

import edu.princeton.cs.algs4.Queue;

import java.util.Random;

public class MergeBU extends ASort {
    private final Random _rand = new Random();
    private Queue<Queue> mainQueue;
    private int _counter = 0;

    public static void main(String[] args) {
        Comparable[] comparables1 = new Comparable[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Comparable[] comparables2 = new Comparable[]{11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int n = comparables1.length + comparables2.length;
        Comparable[] comparables3 = new Comparable[n];
        Queue<Comparable> queue1 = new Queue<>();
        Queue<Comparable> queue2 = new Queue<>();
        for (Comparable comparable : comparables1) {
            queue1.enqueue(comparable);
        }
        for (Comparable comparable : comparables2) {
            queue2.enqueue(comparable);
        }

        MergeBU sort = new MergeBU();
        Queue<Comparable> queue3 = sort.combineComparables(queue1, queue2);
        for (int i = 0, length = queue3.size(); i < length; i++) {
            comparables3[i] = queue3.dequeue();
        }

        sort.start(comparables3);
        System.out.println("Theo: " + (6 * n * Math.log(n)));
        System.out.println("Prakti: " + sort.getCounter());
    }

    public int getCounter() {
        return this._counter;
    }

    private Queue<Comparable> combineComparables(Queue<Comparable> q1, Queue<Comparable> q2) {
        Queue<Comparable> q3 = new Queue<>();
        int length = q1.size() + q2.size();
        for (int i = 0; i < length; ++i) {
            int selectedQue = this._rand.nextInt(2);
            if (selectedQue == 1 && q1.size() > 0 || q2.size() == 0) {
                q3.enqueue(q1.dequeue());
            } else {
                q3.enqueue(q2.dequeue());
            }
        }
        return q3;
    }

    public void sort(Comparable[] comparables) {
        this.mainQueue = new Queue<>();
        for (Comparable comparable : comparables) {
            Queue<Comparable> wrapQueForComparable = new Queue<>();
            wrapQueForComparable.enqueue(comparable);
            mainQueue.enqueue(wrapQueForComparable);
        }
        while (mainQueue.size() > 1) {
            mainQueue.enqueue(this.merge(mainQueue.dequeue(), mainQueue.dequeue()));
        }

        Queue<Comparable> b = mainQueue.dequeue();
        for (int i = 0; i < comparables.length; i++) {
            comparables[i] = b.dequeue();
        }
    }

    private Queue<Comparable> merge(Queue<Comparable> a, Queue<Comparable> b) {
        Queue<Comparable> c = new Queue<>();
        while (!a.isEmpty() || !b.isEmpty()) {
            if (a.isEmpty()) {
                c.enqueue(b.dequeue());
                this._counter += 2;
            } else if (b.isEmpty()) {
                c.enqueue(a.dequeue());
                this._counter += 2;
            } else if (a.peek().compareTo(b.peek()) < 0) {
                c.enqueue(a.dequeue());
                this._counter += 4;
            } else {
                c.enqueue(b.dequeue());
                this._counter += 4;
            }
        }
        return c;
    }
}