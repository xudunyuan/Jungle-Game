package hk.edu.polyu.comp.comp2021.jungle.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    Animal tigerTest = new Animal("B3",1, AnimalInfo.TIGER);
    Animal ratTest = new Animal("B7", -1, AnimalInfo.RAT);

    @Test
    public void rationalMove() {
        assertTrue(tigerTest.rationalMove("B3","B2"));
        assertFalse(tigerTest.rationalMove("A1","B9"));
    }

    @Test
    public void jumpMove() {
        assertTrue(tigerTest.jumpMove("B3","B7"));
    }

    @Test
    public void getTeam() {
        assertEquals(ratTest.getTeam(), -1);
        assertEquals(tigerTest.getTeam(), 1);
    }

    @Test
    public void getAnimalInfo() {
        assertEquals(ratTest.getAnimalInfo(), AnimalInfo.RAT);
    }

    @Test
    public void getFromPosition() {
        assertEquals(ratTest.getFromPosition(), "B7");
    }

    @Test
    public void setPosition() {

    }

    @Test
    public void move() {
        tigerTest.move("B3", "B2");

    }
}