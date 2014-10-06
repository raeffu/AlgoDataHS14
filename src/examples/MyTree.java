package examples;

import java.util.ArrayList;
import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MyTree<E> implements Tree<E> {

  class TNode implements Position<E> {

    TNode parent;
    E elem;
    MyLinkedList<TNode> children = new MyLinkedList<>();
    Position<TNode> mySiblingPos;
    Object creator = MyTree.this;

    @Override
    public E element() {
      return elem;
    }

  }

  private TNode root;
  private int size;

  private TNode castToTNode(Position<E> p) {
    TNode n;
    try
    {
      n = (TNode) p;
    }
    catch (ClassCastException e)
    {
      throw new RuntimeException("This is not a Position belonging to MyTree");
    }
    if(n.creator == null) throw new RuntimeException("position was allready deleted!");
    if(n.creator != this) throw new RuntimeException("position belongs to another MyLinkedList instance!");
    return n;
  }

  @Override
  public Position<E> root() {
    return root;
  }

  @Override
  public Position<E> createRoot(E o) {
    if(root != null) throw new RuntimeException("already a root node present");
    TNode n = new TNode();
    n.elem = o;
    size++;
    root = n;
    return n;
  }

  @Override
  public Position<E> parent(Position<E> child) {
    return castToTNode(child).parent;
  }

  @Override
  public Iterator<Position<E>> childrenPositions(Position<E> parent) {
    final TNode p = castToTNode(parent);
    return new Iterator<Position<E>>() {
      Iterator<Position<TNode>> it = p.children.positions();

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @SuppressWarnings("unchecked")
      @Override
      public Position<E> next() {
        return (Position<E>) it.next();
      }

      @Override
      public void remove() {
        throw new NotImplementedException();
      }
    };
  }

  @Override
  public Iterator<E> childrenElements(Position<E> parent) {
    final TNode p = castToTNode(parent);
    return new Iterator<E>() {
      Iterator<TNode> it = p.children.elements();

      @Override
      public boolean hasNext() {
        return it.hasNext();
      }

      @Override
      public E next() {
        return it.next().elem;
      }

      @Override
      public void remove() {
        throw new NotImplementedException();
      }
    };
  }

  @Override
  public int numberOfChildren(Position<E> parent) {
    TNode p = castToTNode(parent);
    return p.children.size();
  }

  @Override
  public Position<E> insertParent(Position<E> p, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> addChild(Position<E> parent, E o) {
    TNode par = castToTNode(parent);
    TNode child = new TNode();
    child.elem = o;
    child.parent = par;
    child.mySiblingPos = par.children.insertLast(child);
    size++;
    return child;
  }

  @Override
  public Position<E> addChildAt(int pos, Position<E> parent, E o) {
    TNode par = castToTNode(parent);
    TNode child = new TNode();
    child.elem = o;
    child.parent = par;

    if(pos >= par.children.size())
    {
      child.mySiblingPos = par.children.insertLast(child);
    }
    else
    {
      Position<TNode> position = getSiblingPosition(pos, par);
      if(position == null) throw new RuntimeException("Position not found");
      child.mySiblingPos = par.children.insertBefore(position, child);
    }

    size++;
    return null;
  }

  private Position<TNode> getSiblingPosition(int pos, TNode parent) {
    Iterator<Position<TNode>> it = parent.children.positions();
    int counter = 0;

    while (it.hasNext())
    {
      if(counter == pos) return it.next();
      it.next();
      counter++;
    }
    return null;
  }

  @Override
  public Position<E> addSiblingAfter(Position<E> sibling, E o) {
    TNode sib = castToTNode(sibling);
    if(sib == root) throw new RuntimeException("root can not have siblings");
    TNode n = new TNode();
    n.parent = sib.parent;
    n.elem = o;
    n.mySiblingPos = sib.parent.children.insertAfter(sib.mySiblingPos, n);
    size++;
    return n;
  }

  @Override
  public Position<E> addSiblingBefore(Position<E> sibling, E o) {
    TNode sib = castToTNode(sibling);
    if(sib == root) throw new RuntimeException("root can not have siblings");
    TNode n = new TNode();
    n.parent = sib.parent;
    n.elem = o;
    n.mySiblingPos = sib.parent.children.insertBefore(sib.mySiblingPos, n);
    size++;
    return null;
  }

  @Override
  public void remove(Position<E> p) {
//    TNode n = castToTNode(p);
//    size--;
//    n.creator = null; //invalidate node
    
//    n.parent.children.remove(p);
  }

  @Override
  public boolean isExternal(Position<E> p) {
    return castToTNode(p).children.size() == 0;
  }

  @Override
  public boolean isInternal(Position<E> p) {
    return castToTNode(p).children.size() > 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public E replaceElement(Position<E> p, E o) {
    TNode n = castToTNode(p);
    E temp = n.elem;
    n.elem = o;
    return temp;
  }

  public void print() {
    print(root, "");
  }

  /**
   * @param root2
   */
  private void print(TNode p, String indent) {
    // print the subtree originating at p
    System.out.println(indent + p.elem);
    Iterator<TNode> it = p.children.elements();
    while (it.hasNext())
    {
      print(it.next(), indent + "  ");
    }
  }

  public int height() {
    return height(root);
  }

  private int height(TNode p) {
    // System.out.println(max);
    int h = 0;
    Iterator<TNode> it = p.children.elements();
    while (it.hasNext())
    {
      h = Math.max(height(it.next()), h);
    }
    return h + 1;
  }

  public ArrayList<Position<E>> externalNodes() {
    ArrayList<Position<E>> list = new ArrayList<Position<E>>();
    return externalNodes(root, list);
  }

  private ArrayList<Position<E>> externalNodes(TNode p, ArrayList<Position<E>> list) {
    Iterator<TNode> it = p.children.elements();
    if(it.hasNext() == false) list.add(p);
    while (it.hasNext())
    {
      externalNodes(it.next(), list);
    }
    return list;
  }

  class Helper {
    TNode n;
    int depth = -1;
  }

  public Position<E> deepestNode() {
    Helper he = new Helper();

    deepestNode(root, he.depth, he);
    return he.n;
  }

  private void deepestNode(TNode n, int currentDepth, Helper he) {
    int depth = currentDepth + 1;

    if(isExternal(n))
    {
      if(currentDepth > he.depth)
      {
        he.depth = currentDepth;
        he.n = n;
      }
      return;
    }
    Iterator<TNode> it = n.children.elements();

    while (it.hasNext())
    {
      deepestNode(it.next(), depth, he);
    }
    return;
  }

  public static void main(String[] args) {
    MyTree<String> t = new MyTree<>();
    Position<String> pA = t.createRoot("A");
    Position<String> pB = t.addChild(pA, "B");
    t.addChild(pA, "C");
    Position<String> pD = t.addChild(pA, "D");
    t.addChild(pB, "E");
    t.addChild(pB, "F");
    Position<String> pG = t.addChild(pD, "G");
    t.addChild(pG, "X");
    t.addChild(pG, "Y");
    t.addChildAt(3, pA, "Z");
    t.addChildAt(3, pA, "ZZ");
    t.print();
    System.out.println("--------------------");
    System.out.println("height: " + t.height());
    System.out.println("--------------------");
    System.out.println("external nodes: ");
    ArrayList<Position<String>> all = t.externalNodes();
    for (Position<String> r : all)
      System.out.println(r.element());
    System.out.println("--------------------");
    Position<String> deepest = t.deepestNode();
    System.out.println("deepest node: " + deepest.element());
    System.out.println("--------------------");
    System.out.println("number of children: " + t.numberOfChildren(pA));
  }
}
