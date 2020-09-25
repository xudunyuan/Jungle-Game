package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTypeTest {

    @Test
    public void getTyoe() {
        assertEquals(CellType.GROUND.getTyoe(), 0);
    }
}