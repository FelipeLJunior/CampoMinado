package br.com.fjp.campominado.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTest {
    private Field field;

    @Test
    void neighborTestHorizontal() {
        field = new Field(3, 3);
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }
}
