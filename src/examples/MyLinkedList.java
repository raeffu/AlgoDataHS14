package examples;

import java.util.Iterator;

public class MyLinkedList<E> implements List<E> {

  class LNode implements Position<E> {

    E elem;
    LNode next, prev;
    Object creator = MyLinkedList.this; // pointer to outer instance

    @Override
    public E element() {
      return elem;
    }

  }

  private LNode first, last;
  private int size;

  @Override
  public Position<E> first() {
    return first;
  }

  @Override
  public Position<E> last() {
    return last;
  }

  @Override
  public boolean isFirst(Position<E> p) {
    return castToLNode(p) == first;
  }

  @Override
  public boolean isLast(Position<E> p) {
    return castToLNode(p) == last;
  }

  @Override
  public Position<E> next(Position<E> p) {
    return castToLNode(p).next;
  }

  private LNode castToLNode(Position<E> p) {
    LNode n;

    try
    {
      n = (LNode) p;
    }
    catch (ClassCastException e)
    {
      throw new RuntimeException("This is not a Position belonging to MyLinkedList");
    }
    if(n.creator == null) throw new RuntimeException("position was allready deleted!");
    if(n.creator != this) throw new RuntimeException("position belongs to another List instance!");

    return n;
  }

  @Override
  public Position<E> previous(Position<E> p) {
    return castToLNode(p).prev;
  }

  @Override
  public E replaceElement(Position<E> p, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> insertFirst(E o) {
    LNode n = new LNode();
    n.elem = o;
    n.next = first;
    if(first != null)
      first.prev = n;
    else
      last = n;

    size++;
    first = n;

    return n;
  }

  @Override
  public Position<E> insertLast(E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> insertBefore(Position<E> p, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Position<E> insertAfter(Position<E> p, E o) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void remove(Position<E> p) {
    if(size==0) throw new RuntimeException("List is empty!");
    
    LNode n = castToLNode(p);
    size--;
    n.creator = null; // invalidate p

    if(n == first)
    {
      first = n.next;
      if(first != null) first.prev = null;
    }
    else if(n == last)
    {
      last = n.prev;
      if(last != null) last.next = null;
    }
    else
    {
      n.prev.next = n.next;
      n.next.prev = n.prev;
    }

  }

  @Override
  public Iterator<Position<E>> positions() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterator<E> elements() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  public static void main(String[] args) {
    List<String> li = new MyLinkedList<>();
    System.out.println("insert hans");
    li.insertFirst("hans");
  System.out.println("remove " + li.last().element());
    li.remove(li.last());
//    li.insertFirst("heiri");
//    li.insertFirst("susi");
//    li.insertFirst("heidi");
//    Position<String> p1 = li.first();
//    while (p1 != null)
//    {
//      System.out.println(p1.element());
//      p1 = li.next(p1);
//    }
//    System.out.println("remove " + li.last().element());
//    li.remove(li.first());
//    Position<String> p2 = li.first();
//    while (p2 != null)
//    {
//      System.out.println(p2.element());
//      p2 = li.next(p2);
//    }
  }

}
