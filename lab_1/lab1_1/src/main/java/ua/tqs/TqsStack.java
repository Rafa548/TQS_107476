package ua.tqs;

import java.util.NoSuchElementException;

class Node {
    int data;
    Node next;
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}



public class TqsStack {
    private Node top;
    private int maxsize;
    private int size;

    public TqsStack() {
        this.top = null;
    }

    public TqsStack(int maxsize) {
        this.top = null;
        this.maxsize = maxsize;
        this.size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(int data) {
        //System.out.println("Maxsize: " + maxsize);
        //System.out.println("Size: " + size);
        if (maxsize == 0) {
            throw new IllegalStateException("Stack is full");
        }
        else if (size == maxsize) {
            throw new IllegalStateException("Stack is full");
        }
        Node newNode = new Node(data);
        size++;
        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    public int pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            size--;
            int data = top.data;
            top = top.next;
            return data;
        }
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            return top.data;
        }
    }

    public int size() {
        int count = 0;
        Node temp = top;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public void print() {
        Node temp = top;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}