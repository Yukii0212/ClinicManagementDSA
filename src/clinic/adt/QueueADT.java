package clinic.adt;

public interface QueueADT<T> {
    void enqueue(T data);
    T dequeue();
    T peek();
    boolean isEmpty();
    boolean isFull();
    int size();
    void clear();
}