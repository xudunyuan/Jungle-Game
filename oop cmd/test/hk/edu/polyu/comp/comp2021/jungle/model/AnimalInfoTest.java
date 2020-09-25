package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalInfoTest {

    @Test
    public void TestgetRank() {
        assertEquals(AnimalInfo.TIGER.getRank(), 6);
    }
}