package examples;


public class MyQueue<E> implements Queue<E> {

  private E[] stor;
  private int in; // points to next free position
  private int out; // points to next element to be dequeued
  private int size = 1;
  
  public MyQueue() {
    this(1);
  }
  
  public MyQueue(int capacity){
    stor = (E[]) new Object[capacity];
  }
  
  @Override
  public void enqueue(E o) {
    if (size == stor.length) expand(); // no more space
    if (in == stor.length) in = 0;
    
    stor[in++] = o;
    size++;
  }

  private void expand() {
    System.out.println("expanding...");
    E[] old = stor;
    stor = (E[]) new Object[stor.length*2];
    
    for(int i=0; i<size; i++){
      stor[i] = old[(out+i)%old.length];
    }
    in=size;
    out=0;
  }

  @Override
  public E dequeue() {
    if(size==0) throw new RuntimeException("Empty Queue!");
    E output = stor[out++];
    if(out==stor.length) out=0;
    size--;
    
    return output;
  }

  @Override
  public E head() {
    return stor[out];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size==0;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Queue q=new MyQueue();
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    q.enqueue(5);
    q.enqueue(6);
    q.enqueue(7);
    q.enqueue(8);
    q.enqueue(9);
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
  }

}
