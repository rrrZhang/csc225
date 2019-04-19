
import java.util.Scanner;

public class median {

    static minHeap min;
    static maxHeap max;

    public median() {
        min = new minHeap();
        max = new maxHeap();
    }

    public static int calculateMedian(int x) {
        if (min.isEmpty() && max.isEmpty()) {
            min.insert(x);
        } else {
            if (x < min.peek()) {
                max.insert(x);
            } else {
                min.insert(x);
            }
        }
        if (min.size() - max.size() > 1) {
            int value1 = min.removeMin();
            max.insert(value1);
        } else if (max.size() - min.size() > 1) {
            int value2 = max.removeMax();
            min.insert(value2);
        }

        if (min.size() > max.size()) {
            return min.peek();
        } else {
            return max.peek();
        }
    }

    public static void main(String[] args) {
        median m = new median();

        System.out.println("Enter a list of non negative integers. To end enter a negative integers.");
        Scanner s = new Scanner(System.in);
        int current = s.nextInt();

        while (current >= 0) {
            System.out.println("current median:" + m.calculateMedian(current));
            current = s.nextInt();
            if (current < 0) {
                break;
            }
            m.calculateMedian(current);
            current = s.nextInt();

        }
    }
}

class minHeap {

    private int[] heap;
    private int size;

    public minHeap() {
        heap = new int[10000];
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void insert(int x) {

        size++;
        int value = size;
        heap[value] = x;

        bubbleup(value);
    }

    public void bubbleup(int k) {
        while (k > 1 && heap[k / 2] > heap[k]) {
            exchange(k / 2, k);
            k = k / 2;
        }
    }

    public void exchange(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void bubbledown(int k) {
        if (k * 2 <= size) {
            int smaller;

            int value;
            if (heap[2 * k] < heap[2 * k + 1]) {
                smaller = heap[2 * k];
                value = 2 * k;
            } else {
                smaller = heap[2 * k + 1];
                value = 2 * k + 1;
            }
            if (smaller < heap[k]) {
                exchange(value, k);

                k = value;

                bubbledown(k);
            }
        }
    }

    public int peek() {
        return heap[1];
    }

    public int removeMin() {
        int value = heap[1];
        heap[1] = heap[size];
        size--;
        if (size > 1) {
            bubbledown(1);
        }
        return value;

    }
}

class maxHeap {

    private int[] heap;
    private int size;

    public maxHeap() {
        heap = new int[10000];
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void insert(int x) {
        size++;
        int value = size;
        heap[value] = x;

        bubbleup(value);

    }

    public void bubbleup(int k) {
        while (k > 1 && heap[k] > heap[k / 2]) {
            exchange(k, k / 2);
            k = k / 2;
        }

    }

    public void exchange(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

    }

    public void bubbledown(int k) {
        int bigger;
        int value;
        if (heap[2 * k] > heap[2 * k + 1]) {
            bigger = heap[2 * k];
            value = 2 * k;
        } else {
            bigger = heap[2 * k + 1];
            value = 2 * k + 1;
        }
        if (bigger > heap[k]) {
            exchange(value, k);
            k = value;
            bubbledown(k);
        }
    }

    public int peek() {
        return heap[1];
    }

    public int removeMax() {
        int value = heap[1];
        heap[1] = heap[size];
        size--;
        bubbledown(1);
        return value;
    }
}