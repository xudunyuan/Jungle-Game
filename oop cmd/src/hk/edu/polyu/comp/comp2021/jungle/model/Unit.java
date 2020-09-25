package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;
import java.util.Arrays;

/**unit is to describe each unit on board*/
public class Unit implements Serializable {
    private final String fromPosition;
    //private int team;
    private CellType cellType;
    //private AnimalInfo animalInfo;
    private boolean ifHasAnimal;

    /**
     * construct a new unit.
     *
     * @param cellType type of this unit.
     * @param ifHasAnimal if there is an animal on it.
     * @param position its position
     * */
    Unit(String position, CellType cellType, boolean ifHasAnimal) {
        this.fromPosition = position;
        this.cellType = cellType;
        this.ifHasAnimal = ifHasAnimal;
    }



    /**
     * get the coordination according to the String input by the user.
     *
     * @param Position a position in String type.
     * @return transform this String to an array
     * */
    public static int[] getPosition(String Position) {
        int[] expression = new int[2];

        char fromLetter = Position.charAt(0);
        expression[0] = fromLetter - 'A';
        char fromNumber = Position.charAt(1);
        expression[1] = fromNumber - '1';

        return expression;
    }

    /**@return cell type of this unit.*/
    public CellType getCellType() {
        return this.cellType;
    }

    /**@return if there is an animal on this unit.*/
    public boolean getIfHasAnimal(){
        return this.ifHasAnimal;
    }

    /**change if there is an animal on this unit.*/
    public void changeIfHasAnimal(){
        this.ifHasAnimal = !this.ifHasAnimal;
    }

    /**@return the position of this unit.*/
    public String getFromPosition(){
        return this.fromPosition;
    }

}
