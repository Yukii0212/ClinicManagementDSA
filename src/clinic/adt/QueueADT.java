public interface QueueADT<T> {
    void enqueue(T item);
    T dequeue();
    T peek();
    boolean isFull();
    boolean isEmpty();
    int size();
    void clear();
}