/**
 * 
 */
package examples;

import java.util.Arrays;

/**
 * @author ps
 *
 */
public class MyStack<E> implements Stack<E> {
	
	private E[] stor;
	private int p; // stack pointer (index) points to next free position
	
	public MyStack(){
		this(16);
	}

	public MyStack(int capacity){
		stor =(E[]) new Object[capacity];
	}

	
	/* (non-Javadoc)
	 * @see examples.Stack#push(java.lang.Object)
	 */
	@Override
	public void push(E o) {
		if (p==stor.length) expand();
		stor[p++]=o;
	}

	/**
	 * 
	 */
	private void expand() {
		stor = Arrays.copyOf(stor,stor.length*2);
		
	}

	/* (non-Javadoc)
	 * @see examples.Stack#pop()
	 */
	@Override
	public E pop() {
		if (p==0) throw new RuntimeException("Stack underflow"); 
		return stor[--p];
	}

	/* (non-Javadoc)
	 * @see examples.Stack#top()
	 */
	@Override
	public E top() {
		if (p==0) throw new RuntimeException("Stack underflow"); 
		return stor[p-1];
	}

	/* (non-Javadoc)
	 * @see examples.Stack#size()
	 */
	@Override
	public int size() {
		return p;
	}

	/* (non-Javadoc)
	 * @see examples.Stack#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return p==0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stack<Integer> s = new MyStack<>();
		s.push(3);
		s.push(4);
		System.out.println(s.pop()+","+s.top());
	}

}
