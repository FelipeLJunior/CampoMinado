package br.com.fjp.campominado.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class FieldTest {
    private Field field;

    @BeforeEach
    void inicializeFields() {
        field = new Field(3, 3);
    }

    @Test
    void TestNeighborUp() {
        Field neighbor = new Field(2, 3);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborDown() {
        Field neighbor = new Field(4, 3);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborLeft() {
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborRight() {
        Field neighbor = new Field(3, 4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighbordiagonal() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void TestWrongNeighbor() {
        Field neighbor = new Field(1, 1);
        boolean result = field.addNeighbor(neighbor);

        assertFalse( result);
    }
}
