package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    Unit ForTest = new Unit("A3", CellType.GROUND, true);
    int[] fromPosition = {0,2};
    @Test
    public void TestgetPosition() {
        assertArrayEquals(ForTest.getPosition("A3"), fromPosition);
    }

    @Test
    public void TestgetCellType() {
        assertEquals(ForTest.getCellType(), CellType.GROUND);
    }

    @Test
    public void TestgetIfHasAnimal() {
        assertEquals(ForTest.getIfHasAnimal(), true);
    }

    @Test
    public void TestchangeIfHasAnimal() {
        ForTest.changeIfHasAnimal();
    }

    @Test
    public void TestgetFromPosition() {
        assertEquals(ForTest.getFromPosition(), "A3");
    }
}