
package hk.edu.polyu.comp.comp2021.jungle.model;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;


public class BoardTest {

    @Test
    public void initializeTest() {
        Board.initialize();
        assertEquals(Board.getUnit(0,1).getCellType(), CellType.GROUND);
    }

    @Test
    public void thisTurnTeamTest() {
        assertEquals(Board.thisTurnTeam(), 1);
    }

    @Test
    public void getUnitTest() {
        Board.getUnit(0, 0);
    }



    @Test
    public void moveTest() {

        //test老鼠
        Board.initialize();
        Board.move("A7", "A6");//不是自己队动物
        assertFalse(Board.getUnit(0, 5).getIfHasAnimal());

        Board.move("G3", "G5");//不是相邻四格
        assertFalse(Board.getUnit(6, 4).getIfHasAnimal());
        Board.move("G3", "G2");
        Board.move("F2", "F3");
        Board.move("G2", "F2");
        Board.move("F2", "E2");
        Board.move("E2", "D2");
        Board.move("D2", "D1");//老鼠进自己兽穴
        assertFalse(Board.getUnit(3, 0).getIfHasAnimal());
        Board.move("D1", "D2");
        Board.move("D2", "E2");
        Board.move("E2", "F2");
        Board.move("F2", "G2");
        Board.move("G2", "G3");
        Board.move("G3", "F3");//老鼠吃自己队的狗
        assertTrue(Board.getUnit(6, 2).getIfHasAnimal());
        Board.move("G3", "G4");
        Board.move("G4", "F4");//老鼠从陆地进河
        assertTrue(Board.getUnit(5, 3).getIfHasAnimal());

        Board.move("F4", "F3");//老鼠从河上有动物的陆地
        assertTrue(Board.getUnit(5, 3).getIfHasAnimal());
        Board.move("F4", "G4");//老鼠从河上陆地
        assertTrue(Board.getUnit(6, 3).getIfHasAnimal());
        Board.move("G4", "G5");
        Board.move("G5", "G6");
        Board.move("G6", "G7");//老鼠吃象

        Board.ifchangeThisTurn();
        Board.move("E7", "E8");
        Board.move("E8", "E9");
        Board.ifchangeThisTurn();
        Board.move("G7", "F7");
        Board.move("F7", "F8");//老鼠吃对面的猫
        assertTrue(Board.getUnit(5, 6).getIfHasAnimal());

        Board.move("F7", "E7");
        Board.move("E7", "E8");
        Board.move("E8", "E9");//老鼠吃陷阱里的狼
        assertFalse(Board.getUnit(4, 7).getIfHasAnimal());

        Board.move("E9", "D9");//老鼠进对方兽穴
        assertTrue(Board.getUnit(3, 8).getIfHasAnimal());

        //以下是狮虎
        Board.initialize();

        Board.move("G1","F1");//狮子移动到平地
        assertTrue(Board.getUnit(5, 0).getIfHasAnimal());

        Board.move("F1","F2");//狮子试图吃掉己方动物
        assertTrue(Board.getUnit(5, 0).getIfHasAnimal());

        Board.move("F1","E1");//狮子进入己方陷阱
        Board.move("E1","D1");//狮子进入己方兽穴
        assertTrue(Board.getUnit(4, 0).getIfHasAnimal());

        Board.move("A9","A8");//试图移动非己方狮子
        assertFalse(Board.getUnit(0, 7).getIfHasAnimal());

        Board.move("E1","G1");//狮子试图移到非相邻格
        assertFalse(Board.getUnit(6, 0).getIfHasAnimal());

        Board.move("E1","E2");
        Board.move("E2","E3");//狮子试图吃掉自己队的动物
        assertTrue(Board.getUnit(4, 1).getIfHasAnimal());

        Board.move("A1","B1");
        Board.move("B1","C1");
        Board.move("C1","C2");
        Board.move("C2","D2");
        Board.move("D2","D3");
        Board.move("D3","D4");
        Board.move("D4","A4");//老虎纵向向上跳河
        assertTrue(Board.getUnit(0, 3).getIfHasAnimal());

        Board.move("A4","B4");
        assertFalse(Board.getUnit(1,3).getIfHasAnimal());

        Board.move("A4","D4");//老虎纵向向下跳河
        assertTrue(Board.getUnit(3, 3).getIfHasAnimal());

        Board.move("G3","G4");
        Board.move("G4","F4");
        Board.move("D4","G4");//河中有老鼠 不能跳
        assertFalse(Board.getUnit(6,3).getIfHasAnimal());

        Board.move("D4","D3");
        Board.move("D3","D7");//中间不是河 不能跳
        assertFalse(Board.getUnit(3,6).getIfHasAnimal());

        Board.move("C3","C2");
        Board.move("D3","C3");
        Board.move("C3","C7");//老虎横向往右跳河且吃对方比自己小的动物
        assertFalse(Board.getUnit(2,2).getIfHasAnimal());

        Board.move("C7","C3");//老虎横向往左
        Board.move("C3","C7");

        Board.move("C7","B7");
        Board.move("B7","A7");
        Board.move("A7","A8");
        Board.move("A8","A9");//老虎试图吃对方比自己大的动物
        assertTrue(Board.getUnit(0,7).getIfHasAnimal());


        Board.move("E2","E1");
        Board.move("E1","F1");
        Board.move("F1","G1");
        Board.move("G1","G2");
        Board.move("G2","G3");
        Board.move("G3","F3");
        Board.move("F3","F7");//狮子试图跳不是河的地方
        assertFalse(Board.getUnit(5,6).getIfHasAnimal());

        Board.move("A8","B8");
        Board.move("B8","B9");
        Board.move("B9","C9");//老虎进入对方陷阱

        Board.ifchangeThisTurn();
        Board.move("A9","A8");
        Board.move("A8","B8");
        Board.move("B8","C8");
        Board.move("C8","D8");
        Board.ifchangeThisTurn();
        Board.move("C9","C8");
        Board.move("C8","D8");//老虎吃对方陷阱的狮子
        assertFalse(Board.getUnit(2,7).getIfHasAnimal());

        Board.move("D8","D9");//老虎进入对方兽穴
        assertTrue(Board.getUnit(3,8).getIfHasAnimal());

        //以下是common animal的
        Board.initialize();

        Board.move("B2","B1");
        Board.move("B1","C1");//移动common到己方陷阱
        assertTrue(Board.getUnit(2,0).getIfHasAnimal());

        Board.move("C1","D1");//试图移动到己方兽穴
        assertFalse(Board.getUnit(3,0).getIfHasAnimal());

        Board.move("D1","C1");
        Board.move("C1","C2");
        Board.move("C2","B2");
        Board.move("B2","B3");//common移动到平地
        assertTrue(Board.getUnit(1,2).getIfHasAnimal());

        Board.move("B8","B7");//试图移动对方common
        assertFalse(Board.getUnit(1,6).getIfHasAnimal());

        Board.move("B3","B1");//试图移动common到非相邻
        assertFalse(Board.getUnit(1,0).getIfHasAnimal());


        Board.move("B3","B4");//试图移动common到河流
        assertFalse(Board.getUnit(1,3).getIfHasAnimal());

        Board.move("B3","C3");//common试图吃掉自己队的动物
        assertTrue(Board.getUnit(1,2).getIfHasAnimal());

        Board.move("C3","D3");
        Board.move("D3","D4");
        Board.move("D4","D5");
        Board.move("D5","D6");
        Board.move("D6","D7");
        Board.move("D7","C7");//试图吃掉对方比自己强的动物
        assertTrue(Board.getUnit(3,6).getIfHasAnimal());

        Board.move("D7","D8");
        Board.move("D8","E8");
        Board.move("E8","F8");//common吃掉对方动物
        assertFalse(Board.getUnit(4,7).getIfHasAnimal());

        Board.move("F8","E8");
        Board.move("E8","D8");
        Board.move("D8","C8");

        Board.ifchangeThisTurn();
        Board.move("A9","B9");
        Board.move("B9","C9");

        Board.ifchangeThisTurn();
        Board.move("C8","C9");//common吃掉对方陷阱里的动物
        assertFalse(Board.getUnit(2,7).getIfHasAnimal());

        Board.move("C9","D9");
        assertTrue(Board.getUnit(3,8).getIfHasAnimal());

        //以下是大象的
        Board.initialize();

        Board.move("G7","G8");//移动对方大象
        assertFalse(Board.getUnit(6, 7).getIfHasAnimal());

        Board.move("A3","A5");//大象移动到非相邻的地方
        assertFalse(Board.getUnit(0, 4).getIfHasAnimal());

        Board.move("A3","B3");
        Board.move("B3","B4");//大象试图移动到河流
        assertFalse(Board.getUnit(1, 3).getIfHasAnimal());

        Board.move("B2","A2");//把自己猫往上移一格

        Board.move("B3","B2");
        Board.move("B2","B1");
        Board.move("B1","C1");
        assertTrue(Board.getUnit(2, 0).getIfHasAnimal());//大象进入己方陷阱
        Board.move("C1","D1");
        assertFalse(Board.getUnit(3, 0).getIfHasAnimal());//大象试图进入己方兽穴

        Board.move("C1","C2");
        Board.move("C2","C3");//大象试图吃掉自己队的动物
        assertTrue(Board.getUnit(2, 1).getIfHasAnimal());

        Board.move("C2","D2");
        Board.move("D2","D3");
        Board.move("D3","D4");
        Board.move("D4","D5");
        Board.move("D5","D6");
        Board.move("D6","D7");
        Board.move("D7","C7");//大象吃掉对方比自己弱的动物
        assertTrue(Board.getUnit(2, 6).getIfHasAnimal());

        Board.move("C7","B7");
        Board.move("B7","A7");//大象试图吃掉对方老鼠
        assertTrue(Board.getUnit(1, 6).getIfHasAnimal());

        Board.move("A7","B7");
        Board.move("B7","C7");
        Board.move("C7","C8");

        Board.ifchangeThisTurn();
        Board.move("A9","B9");
        Board.move("B9","C9");

        Board.ifchangeThisTurn();
        Board.move("C8","C9");//大象吃对方陷阱里的狮子
        Board.move("C9","D9");//大象进入对方兽穴
        assertTrue(Board.getUnit(3, 8).getIfHasAnimal());



    }

    @Test
    public void ifAllDeadTest() {
        Board.initialize();
        assertFalse(Board.ifAllDead());
    }

    @Test
    public void printBoardTest() {
        Board.initialize();
        boolean success = false;
        try {
            Board.printBoard();
            success = true;
        }catch (Exception e){}
        assertTrue(success);
    }

    @Test
    public void getAnimalTest() {
        Board.initialize();
        Animal cat = Board.getAnimal("B2");
        Animal none = Board.getAnimal("N1");

        //assertEquals(Board.getAnimal("B2"), cat);
    }

    @Test
    public void saveTest() {
        Board.initialize();
        boolean success = false;
        try {
            // please change to your own path
            Board.save(new FileOutputStream("../notsave.txt"));
            success = true;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }

    @Test
    public void openTest() {
        Board.initialize();
        boolean success = false;
        try {
            // please change to your own path
            Board.open(new FileInputStream("../notsave.txt"));
            success = true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }

}
