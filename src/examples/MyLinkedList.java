package examples;

import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    LNode old = castToLNode(p);
    old.creator = null; // invalidate old node
    LNode n = new LNode();
    n.elem = o;
    n.next = old.next;
    n.prev = old.prev;

    if(old == first)
    {
      first = n;
      if(first.next != null) first.next.prev = n;
      return old.elem;
    }
    if(old == last)
    {
      last = n;
      if(last.prev != null) last.prev.next = n;
      return old.elem;
    }

    old.next.prev = n;
    old.prev.next = n;

    return old.elem;
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
    LNode n = new LNode();
    n.elem = o;
    n.prev = last;

    if(last != null)
      last.next = n;
    else
      first = n;

    size++;
    last = n;

    return n;
  }

  @Override
  public Position<E> insertBefore(Position<E> p, E o) {
    LNode old = castToLNode(p);
    LNode n = new LNode();
    n.elem = o;
    n.next = old;
    n.prev = old.prev;

    if(first == old) first = n;

    if(old.prev != null)
    {
      old.prev.next = n;
    }
    old.prev = n;
    size++;

    return n;
  }

  @Override
  public Position<E> insertAfter(Position<E> p, E o) {
    LNode old = castToLNode(p);
    LNode n = new LNode();
    n.elem = o;
    n.next = old.next;
    n.prev = old;

    if(last == old) last = n;

    if(old.next != null)
    {
      old.next.prev = n;
    }
    old.next = n;
    size++;

    return n;
  }

  @Override
  public void remove(Position<E> p) {
    if(size == 0) throw new RuntimeException("List is empty!");

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
    return new Iterator<Position<E>>() {

      LNode current = first;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public Position<E> next() {
        LNode ret = current;
        current = current.next;
        return ret;
      }

      @Override
      public void remove() {
        throw new NotImplementedException();
      }
    };
  }

  @Override
  public Iterator<E> elements() {
    return new Iterator<E>() {
      
      LNode current = first;
      
      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public E next() {
        E elem = current.elem;
        current = current.next;
        return elem;
      }

      @Override
      public void remove() {
        throw new NotImplementedException();
      }
    };
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  public static void main(String[] args) {
    List<String> li = new MyLinkedList<>();
    System.out.println("insert hans");
    Position<String> p5 = li.insertFirst("hans");
    li.insertBefore(p5, "before");
    li.insertAfter(p5, "after");
    // System.out.println(li.last().element());
    // System.out.println("remove " + li.last().element());
    // li.remove(li.last());
    // Position<String> p4 = li.insertFirst("heiri");
    // Position<String> p = li.insertFirst("susi");
    // li.insertFirst("heidi");
    Position<String> p1 = li.first();
    while (p1 != null)
    {
      System.out.println(p1.element());
      p1 = li.next(p1);
    }
    // System.out.println("-------");
    // li.replaceElement(p, "raffi");
    // li.insertBefore(p4, "danae");
    // Position<String> p3 = li.first();
    // while (p3 != null)
    // {
    // System.out.println(p3.element());
    // p3 = li.next(p3);
    // }
    // System.out.println("remove " + li.last().element());
    // li.remove(li.first());
    // Position<String> p2 = li.first();
    // while (p2 != null)
    // {
    // System.out.println(p2.element());
    // p2 = li.next(p2);
    // }
  }

}
