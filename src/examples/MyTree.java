package examples;

import java.util.Iterator;

public class MyTree<E> implements Tree<E> {

  class TNode implements Position<E> {

    TNode parent;
    E elem;
    MyLinkedList<Position<E>> children = new MyLinkedList<>();
    Position<Position<E>> mySiblingPos;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterator<E> childrenElements(Position<E> parent) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int numberOfChildren(Position<E> parent) {
    // TODO Auto-generated method stub
    return 0;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> addSiblingAfter(Position<E> sibling, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> addSiblingBefore(Position<E> sibling, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void remove(Position<E> p) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isExternal(Position<E> p) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isInternal(Position<E> p) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public E replaceElement(Position<E> p, E o) {
    // TODO Auto-generated method stub
    return null;
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
    Iterator<Position<E>> it = p.children.elements();
    while (it.hasNext())
    {
      print((TNode) it.next(), indent + "--");
    }
  }

  public static void main(String[] args) {
    MyTree<String> t = new MyTree<>();
    Position<String> p = t.createRoot("A");
    Position<String> pB = t.addChild(p, "B");
    t.addChild(p, "C");
    Position<String> pD = t.addChild(p, "D");
    t.addChild(pB, "E");
    t.addChild(pB, "F");
    t.addChild(pD, "G");
    t.print();
  }
}
