import org.junit.jupiter.api.Test;
import ua.tqs.TqsStack;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class TqsStackTest {

    //tests to check implementation of methods

    @Test
    void pushTest() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        if (stack.isEmpty()) {
            fail("Stack is empty push failed");
        }
        else {
            assertEquals(3, stack.peek());
        }
    }

    @Test
    void sizeTest() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(4, stack.size());
        stack.pop();
        assertEquals(3, stack.size());
        stack.pop();
        assertEquals(2, stack.size());
        stack.peek();
        assertEquals(2, stack.size());
    }

    @Test
    void popTest() {
        TqsStack stack = new TqsStack();
        stack.pop();
        stack.push(1);
        stack.pop();
        if (!stack.isEmpty()) {
            fail("Stack is not empty pop failed");
        }
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.pop();
        System.out.println(stack.size());
    }

    @Test
    void peekTest() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.peek());
        stack.push(2);
        assertEquals(2, stack.peek());
        stack.print();
    }

    @Test
    void isEmptyTest() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.pop();

        if (!stack.isEmpty()) {
            fail("Stack is not empty");
        }

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.pop();

        assertEquals(2, stack.peek());
        stack.pop();
        stack.pop();

        assertTrue(stack.isEmpty());

    }

    //tests required

    @Test
    //test if the stack is empty on creation
    void testEmptyOnCreation() {
        TqsStack stack = new TqsStack();
        assertTrue(stack.isEmpty());
    }

    @Test
    //test if the size is 0 on creation
    void testSizeOnCreation() {
        TqsStack stack = new TqsStack();
        assertEquals(0, stack.size());
    }

    @Test
    //test if the stack is empty after pushing and the size is correct
    void testEmptyAfterPush() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @Test
    //test if the stack is pushes and pops correctly
    void testEmptyAfterPushAndPop() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.push(2);
        stack.pop();
        assertEquals(1,stack.peek());
    }

    @Test
    //test if the stack remains the same after peeking
    void testStackAfterPeek() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.push(2);
        stack.peek();
        assertEquals(2,stack.size());
    }

    @Test
    //test if the stack is empty after popping all elements
    void testEmptyAfterPop() {
        TqsStack stack = new TqsStack();
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    //Popping from an empty stack does throw a NoSuchElementException
    void testPopEmptyStack() {
        TqsStack stack = new TqsStack();
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    //Peeking from an empty stack does throw a NoSuchElementException
    void testPeekEmptyStack() {
        TqsStack stack = new TqsStack();
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @Test
    //Pushing to a full stack does throw an IllegalStateException
    void testPushFullStack() {
        TqsStack stack = new TqsStack(2);
        stack.push(1);
        stack.push(2);
        //System.out.println("test....");
        assertThrows(IllegalStateException.class, () -> {
            stack.push(3);
        });
    }

}