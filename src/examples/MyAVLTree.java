/**
 * 
 */
package examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author ps
 *
 */
public class MyAVLTree<K extends Comparable<? super K>, E> implements
		OrderedDictionary<K, E> {

	class AVLNode implements Locator<K, E>{
		
		AVLNode parent,left,right;
		Object creator = MyAVLTree.this;
		E elem;
		K key;
		int height;

		/* (non-Javadoc)
		 * @see examples.Position#element()
		 */
		@Override
		public E element() {
			return elem;
		}

		/* (non-Javadoc)
		 * @see examples.Locator#key()
		 */
		@Override
		public K key() {
			return key;
		}
		
		boolean isExternal(){
			return left==null; // is also true for right
		}
		
		boolean isLeftChild(){
			return parent != null && parent.left==this;
		}
		
		boolean isRightChild(){
			return parent != null && parent.right==this;
		}
		
		void expand(K key,E elem){
			this.elem = elem;
			this.key = key;
			left = new AVLNode();
			right = new AVLNode();
			left.parent = this;
			right.parent = this;
			height = 1;
		}
	}

	
	
	// istance variables:
	private AVLNode root = new AVLNode();
	private int size;
	
	private AVLNode checkAndCast(Locator<K,E> p){
		try {
			AVLNode n = (AVLNode) p;			
			if (n.creator == null) throw new RuntimeException(" allready removed locator!");
			if (n.creator != this) throw new RuntimeException(" locator belongs to another AVLTree instance");
			return n;
		} catch (ClassCastException e) {
			throw new RuntimeException(" locator belongs to another container-type ");
		}
	}

	
	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#size()
	 */
	@Override
	public int size() {
		return size;
	}

	public Locator<K, E> find(K key) {
		// returns the leftmost occurence of
		// 'key' or null

		return find(root, key);
	}

  private AVLNode find(AVLNode n, K key){
    if(n.isExternal()) return n;
    if(key.compareTo(n.left.key) >= 0)
      ;
    return null;
  }

	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#findAll(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E>[] findAll(K key) {
		return null;
	}

	/**
	 * @param n
	 * @param al
	 */
	private void findAll(K key,AVLNode n, ArrayList<Locator<K, E>> al) {
	}





	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#insert(java.lang.Comparable, java.lang.Object)
	 */
	@Override
	public Locator<K, E> insert(K key, E o) {
    AVLNode n = root;
    while(!n.isExternal()){
      if(n.key.compareTo(key) >= 0)
        n = n.left;
      else
        n = n.right;
    }
    n.expand(key, o);
    adjustHeightAboveAndRebalance(n);
    size++;

		return n;
	}





	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#remove(examples.Locator)
	 */
	@Override
	public void remove(Locator<K, E> loc) {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#closestBefore(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E> closestBefore(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#closestAfter(java.lang.Comparable)
	 */
	@Override
	public Locator<K, E> closestAfter(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#next(examples.Locator)
	 */
	@Override
	public Locator<K, E> next(Locator<K, E> loc) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#previous(examples.Locator)
	 */
	@Override
	public Locator<K, E> previous(Locator<K, E> loc) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#min()
	 */
	@Override
	public Locator<K, E> min() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#max()
	 */
	@Override
	public Locator<K, E> max() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see examples.OrderedDictionary#sortedLocators()
	 */
	@Override
	public Iterator<Locator<K, E>> sortedLocators() {
		// TODO Auto-generated method stub
		return null;
	}

  private void print(AVLNode r, String in){
    if(r.isExternal()) return;
    print(r.right, in+"..");
    System.out.println(in+r.key+ "(h="+r.height+")");
    print(r.left, in+"..");
  }

  public void print(){
    print(root, "");
  }

  private void adjustHeightAboveAndRebalance(AVLNode n){
    //correct height of all parents
    int height = 1;
    n.height = height;
    n = n.parent;
    while (n!=null){
      height++;
      n.height = height;
      n = n.parent;
    }
  }

	public static void main(String[] argv){
		MyAVLTree<Integer, String> t = new MyAVLTree<>();
		Random rand = new Random(3434534);
		int n  = 10;
		Locator<Integer,String>[] locs = new Locator[n];
		long time1 = System.currentTimeMillis();
		for (int i=0;i<n;i++) {
      int k = rand.nextInt(n);
      //System.out.println("insert key: " + k);
			locs[i]=t.insert(k,""+i);
			//locs[i]=t.insert(i, "bla");
		}
    t.print();
//		for (int i=0;i<n/2;i++) {
//			t.remove(t.find(locs[i].key()));
//		}
	}

}
