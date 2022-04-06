/**
 * 2021F CS 570-B. Homework Assignment 3.
 * This assignment consists in implementing a double-linked list with fast accessing. Fast accessing is provided by an
 * internal index. An index is just an array-based list that stores references to nodes.
 *
 * CWID: 20007427
 * @Truong
 * @Date 10/01/2021
 */

import java.util.*;

public class IDLList<E> {
    //four data fields
    private Node<E> head;
    private Node<E> tail;
    private int size;
    ArrayList<Node<E>> indices = new ArrayList<>();

    //Declaration of the inner private class Node<E>
    private static class Node<E> {
        //three data fields
        private final E data;
        private Node<E> next = null; //Initially, head and tail is set to null
        private Node<E> prev = null;

        //Create a node holding elem
        public Node(E elem) {
            this.data = elem;
        }

        //Create a node holding elem, with "next" as next and "prev" as prev
        Node(E elem, Node<E> prev, Node<E> next) {
            this.data = elem;
            this.prev = prev;
            this.next = next;
        }
    }

    //Create an empty double-liked list
    public IDLList() {
        head = null;
        tail = null;
        size = 0;
    }

    //Add "elem" at the position "index" (counting from wherever head is). It uses the index for fast access
    public boolean addAtIndex(int index, E elem) {
        //in case index is out of bound of Arraylist indices
        if (index > size || index < 0) return false;

        if (index == 0) return add(elem); //add elem at the head and return true (boolean add(E elem))
        else if (index == size) return append(elem); //add elem at the tail and return true (boolean append(E elem))
        else {
            //create new elem that will be added to new ArrayList
            Node<E> newElem = new Node<>(elem);

            Node<E> prev = indices.get(index - 1);
            Node<E> next = indices.get(index);

            //Create new links
            prev.next = newElem;
            newElem.prev = prev;
            newElem.next = next;
            next.prev = newElem;

            indices.add(index, newElem);
            size++;
            return true;
        }
    }

    //Add "elem" at the head (for example: it becomes the first element of the list)
    public boolean add(E elem) {
        Node<E> newNode = new Node<E>(elem);
        if (head == null) { //if the list is empty, head and tail points to newNode
            head = tail = newNode;
        } else {
            Node<E> tmp = head;
            head = newNode;      //newNode becomes head
            newNode.next = tmp; //newNode->next set to tmp (old head)
            tmp.prev = newNode; //tmp (old head)->prev point to new node
        }
        indices.add(0, newNode);
        size++;
        return true;
    }

    //Add "elem" as the new last element of the list (for example: at the tail)
    public boolean append(E elem) {
        Node<E> newNode = new Node<E>(elem);

        if (head == null) {
            head = tail = newNode;
        } else {
            Node<E> tmp = tail;
            tail = newNode; //set tail to newNode
            tmp.next = newNode; //tmp(old tail)->next point to newNode
            newNode.prev = tmp; //newNode->prev point to tmp(old tail)

            indices.add(size, newNode);
            size++;
        }
        return true;
    }

    //Return the object at position "index" from the head. It uses the index for fast access.
    //Index starts from 0, thus get(0) return the head element of the list
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return indices.get(index).data;
    }

    //Return the object at the head
    public E getHead() {
        if (head == null) return null;
        return head.data;
    }

    //Return the object at the tail
    public E getLast() {
        if (tail == null) return null;
        return tail.data;
    }

    //Return the list size
    public int size() {
        return size;
    }

    //Remove and return the element at the head
    public E remove() {
        if (head == null) return null;

        E removingElem = head.data;
        head = indices.get(1);
        head.prev = null;

        indices.remove(0);
        size--;

        return removingElem;
    }

    //Remove and return element at the tail
    public E removeLast() {
        if (tail == null) return null;

        E removedElem = tail.data;
        tail = tail.prev;
        tail.next = null;

        indices.remove(size - 1);
        size--;

        return removedElem;
    }

    //Remove and return the element at the index "index". Use the index for fast access
    public E removeAt(int index) {
        //in case index is out of bound of Arraylist indices
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();

        E removedElem = indices.get(index).data;
        if (index == 0) remove();
        else if (index == size - 1) removeLast();
        else if (index < size - 1) {
            indices.get(index - 1).next = indices.get(index + 1); //node at (index-1)'s next point to node at (index+1) after remove node at (index)
            indices.get(index + 1).prev = indices.get(index - 1); //node at (index+1)'s prev point to node at (index-1) after remove node at (index)

            indices.remove(index);
            size--;
        }
        return removedElem;
    }

    //Remove the first occurrence of "elem" in the list and return true. Return false if "elem" was not in the list
    public boolean remove(E elem) {
        Node<E> removingElem = null;
        int removingIndex = -1;
        for (int i = 0; i < size; i++) {
            if (indices.get(i).data.equals(elem)) { //get the first occurrence of "elem" if the elem in the list
                removingElem = indices.get(i);
                removingIndex = i;
                break;
            }
        }

        if (removingElem == null) {
            return false;
        } else {
            indices.remove(removingIndex);
            size--;

            removingElem.prev.next = removingElem.next;
            removingElem.next.prev = removingElem.prev;
            return true;
        }
    }

    //Present a string representation of the list
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> cur = head;
        while (cur != null) {
            sb.append(cur.data.toString());
            cur = cur.next;
        }

        return sb.toString();
    }
}