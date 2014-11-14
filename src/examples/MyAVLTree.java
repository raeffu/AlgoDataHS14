/**
 *
 */
package examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author ps
 */
public class MyAVLTree<K extends Comparable<? super K>, E> implements OrderedDictionary<K, E> {

  class AVLNode implements Locator<K, E> {

    AVLNode parent, left, right;
    Object creator = MyAVLTree.this;
    E elem;
    K key;
    int height;

    /*
     * (non-Javadoc)
     * 
     * @see examples.Position#element()
     */
    @Override
    public E element() {
      return elem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see examples.Locator#key()
     */
    @Override
    public K key() {
      return key;
    }

    boolean isExternal() {
      return left == null; // is also true for right
    }

    boolean isLeftChild() {
      return parent != null && parent.left == this;
    }

    boolean isRightChild() {
      return parent != null && parent.right == this;
    }

    void expand(K key, E elem) {
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

  private AVLNode checkAndCast(Locator<K, E> p) {
    try
    {
      AVLNode n = (AVLNode) p;
      if(n.creator == null)
        throw new RuntimeException(" allready removed locator!");
      if(n.creator != this)
        throw new RuntimeException(" locator belongs to another AVLTree instance");

      return n;
    }
    catch (ClassCastException e)
    {
      throw new RuntimeException(" locator belongs to another container-type ");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#size()
   */
  @Override
  public int size() {
    return size;
  }

  public Locator<K, E> find(K key) {
    // returns the leftmost occurence of
    // 'key' or null

    AVLNode n = root;
    AVLNode match = null;

    while (!n.isExternal())
    {
      int comp = key.compareTo(n.key);
      if(comp == 0)
      {
        match = n;
        n = n.left;
      }
      else if(comp > 0)
        n = n.right;
      else
        n = n.left;
    }

    return match;
  }

  /*
   * (non-Javadoc)
   * 
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
  private void findAll(K key, AVLNode n, ArrayList<Locator<K, E>> al) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#insert(java.lang.Comparable,
   * java.lang.Object)
   */
  @Override
  public Locator<K, E> insert(K key, E o) {
    AVLNode n = root;
    while (!n.isExternal())
    {
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

  private void adjustHeightAboveAndRebalance(AVLNode n) {
    // correct height of all parents

    int height = 1;
    n.height = height;
    n = n.parent;
    while (n != null)
    {
      boolean unbalanced = Math.abs(n.left.height - n.right.height) > 1;
      if(unbalanced)
        n = restructure(n);

      height++;
      n.height = height;
      n = n.parent;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#remove(examples.Locator)
   */
  @Override
  public void remove(Locator<K, E> loc) {
    AVLNode n = checkAndCast(loc);
    AVLNode w = null;

    if(n.left.isExternal() || n.right.isExternal())
      w = removeAboveExternal(n);
    else
    {

    }
    adjustHeightAboveAndRebalance(w);
    size--;
    n.creator = null;
  }

  /**
   * @param n
   */
  private AVLNode removeAboveExternal(AVLNode n) {
    // returns the node which replaces n
    AVLNode w;
    
    if(n.left.isExternal())
    {
      w = n.right;
      w.parent = n.parent;
      if(n.parent.left == n)
        n.parent.left = w;
      else if(n.parent.right == n)
        n.parent.right = w;
      else
        root = w;
    }
    else
    { 
      w = n.left;
      w.parent = n.parent;
      if(n.parent.left == n)
        n.parent.left = w;
      else if(n.parent.right == n)
        n.parent.right = w;
      else
        root = w;
    }

    return w;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#closestBefore(java.lang.Comparable)
   */
  @Override
  public Locator<K, E> closestBefore(K key) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#closestAfter(java.lang.Comparable)
   */
  @Override
  public Locator<K, E> closestAfter(K key) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#next(examples.Locator)
   */
  @Override
  public Locator<K, E> next(Locator<K, E> loc) {
    AVLNode n = checkAndCast(loc);
    
    if(n.left != null){
      return n;
    }
    else if(n.right != null){
      n = n.right;
      while(n.left != null){
        n = n.left;
      }
      
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#previous(examples.Locator)
   */
  @Override
  public Locator<K, E> previous(Locator<K, E> loc) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#min()
   */
  @Override
  public Locator<K, E> min() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#max()
   */
  @Override
  public Locator<K, E> max() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see examples.OrderedDictionary#sortedLocators()
   */
  @Override
  public Iterator<Locator<K, E>> sortedLocators() {
    // TODO Auto-generated method stub
    return null;
  }

  private void print(AVLNode r, String in) {
    if(r.isExternal())
      return;
    print(r.right, in + "..");
    System.out.println(in + r.key + "(h=" + r.height + ")");
    print(r.left, in + "..");
  }

  public void print() {
    print(root, "");
  }

  private AVLNode restructure(AVLNode n) {
    // cnt++;
    // n is unbalanced
    // returns the node that takes the position of n
    AVLNode p = n.parent, z = n, x = null, y = null, a = null, b = null, c = null, t1 = null, t2 = null;
    // t0 and t3 never change their parent,
    // that's why we don't need them
    if(z.left.height > z.right.height)
    {
      // z
      // /
      // y
      c = z;
      y = z.left;
      if(y.left.height >= y.right.height)
      {
        // in case we have two equal branches
        // concidering the length we take alway s the single
        // rotation
        // z
        // /
        // y
        // /
        // x
        x = y.left;
        t1 = x.right;
        t2 = y.right;
        b = y;
        a = x;
      }
      else
      {
        // z
        // /
        // y
        // \
        // x
        x = y.right;
        t1 = x.left;
        t2 = x.right;
        a = y;
        b = x;
      }
    }
    else
    {
      // z
      // \
      // y
      a = z;
      y = z.right;
      if(y.right.height >= y.left.height)
      {
        // z
        // \
        // y
        // \
        // x
        x = y.right;
        b = y;
        c = x;
        t1 = y.left;
        t2 = x.left;
      }
      else
      {
        // z
        // \
        // y
        // /
        // x
        x = y.left;
        b = x;
        c = y;
        t1 = x.left;
        t2 = x.right;
      }
    }
    // umhaengen
    b.parent = p;
    if(p != null)
    {
      if(p.left == z)
      {
        p.left = b;
      }
      else
        p.right = b;
    }
    else
    {
      root = b;
    }
    b.right = c;
    b.left = a;
    // und umgekehrt
    a.parent = b;
    c.parent = b;

    // subtrees:
    a.right = t1;
    t1.parent = a;
    c.left = t2;
    t2.parent = c;

    a.height = Math.max(a.left.height, a.right.height) + 1;
    c.height = Math.max(c.left.height, c.right.height) + 1;
    // now we can calculate the height of b
    b.height = Math.max(b.left.height, b.right.height) + 1;
    return b;
  }

  public static void main(String[] argv) {
    MyAVLTree<Integer, String> t = new MyAVLTree<>();
    Random rand = new Random(3434534);
    int n = 10;
    Locator<Integer, String>[] locs = new Locator[n];
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < n; i++)
    {
      int k = rand.nextInt(n);
      // System.out.println("insert key: " + k);
      locs[i] = t.insert(k, "" + i);
      // locs[i]=t.insert(i, "bla");
    }
    t.print();
    System.out.println(t.find(4).key());
    // for (int i=0;i<n/2;i++) {
    // t.remove(t.find(locs[i].key()));
    // }
  }

}
