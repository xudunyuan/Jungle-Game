package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.*;

/**Board is the stage of most operations*/
public class Board implements Serializable{

    private static int thisTurn = 1;
    //private static boolean isSaved = false;

    private static final int NumbersOfAnimals = 16;
    private static final int AnimalNO11 = 11;
    private static final int AnimalNO12 = 12;
    private static final int AnimalNO13 = 13;
    private static final int AnimalNO14 = 14;
    private static final int AnimalNO15 = 15;

    private static Unit[][] board = new Unit[7][9];
    private static Animal[] animals = new Animal[NumbersOfAnimals];
    private static String[] name = new String[2];

    /**the constructor
     * @param inputboard used for compiler
     * @param inputanimals used for compiler
     * @param inputname used for compiler
     * */
    public static void setBoard(Unit[][] inputboard, Animal[]inputanimals, String[] inputname){
        board = inputboard; animals = inputanimals; name = inputname; }

    /**initialize the board*/
    public static void initialize() {
        board[0][0] = new Unit("A1", CellType.GROUND, true);
        board[0][1] = new Unit("A2", CellType.GROUND, false);
        board[0][2] = new Unit("A3", CellType.GROUND, true);
        board[0][3] = new Unit("A4", CellType.GROUND, false);
        board[0][4] = new Unit("A5", CellType.GROUND, false);
        board[0][5] = new Unit("A6", CellType.GROUND, false);
        board[0][6] = new Unit("A7", CellType.GROUND, true);
        board[0][7] = new Unit("A8", CellType.GROUND, false);
        board[0][8] = new Unit("A9", CellType.GROUND, true);

        board[1][0] = new Unit("B1", CellType.GROUND, false);
        board[1][1] = new Unit("B2", CellType.GROUND, true);
        board[1][2] = new Unit("B3", CellType.GROUND, false);
        board[1][3] = new Unit("B4", CellType.RIVER, false);
        board[1][4] = new Unit("B5", CellType.RIVER, false);
        board[1][5] = new Unit("B6", CellType.RIVER, false);
        board[1][6] = new Unit("B7", CellType.GROUND, false);
        board[1][7] = new Unit("B8", CellType.GROUND, true);
        board[1][8] = new Unit("B9", CellType.GROUND, false);

        board[2][0] = new Unit("C1", CellType.TRAP, false);
        board[2][1] = new Unit("C2", CellType.GROUND, false);
        board[2][2] = new Unit("C3", CellType.GROUND, true);
        board[2][3] = new Unit("C4", CellType.RIVER, false);
        board[2][4] = new Unit("C5", CellType.RIVER, false);
        board[2][5] = new Unit("C6", CellType.RIVER, false);
        board[2][6] = new Unit("C7", CellType.GROUND, true);
        board[2][7] = new Unit("C8", CellType.GROUND, false);
        board[2][8] = new Unit("C9", CellType.TRAP, false);

        board[3][0] = new Unit("D1", CellType.REDDEN, false);
        board[3][1] = new Unit("D2", CellType.TRAP, false);
        board[3][2] = new Unit("D3", CellType.GROUND, false);
        board[3][3] = new Unit("D4", CellType.GROUND, false);
        board[3][4] = new Unit("D5", CellType.GROUND, false);
        board[3][5] = new Unit("D6", CellType.GROUND, false);
        board[3][6] = new Unit("D7", CellType.GROUND, false);
        board[3][7] = new Unit("D8", CellType.TRAP, false);
        board[3][8] = new Unit("D9", CellType.BLUEDEN, false);

        board[4][0] = new Unit("E1", CellType.TRAP, false);
        board[4][1] = new Unit("E2", CellType.GROUND, false);
        board[4][2] = new Unit("E3", CellType.GROUND, true);
        board[4][3] = new Unit("E4", CellType.RIVER, false);
        board[4][4] = new Unit("E5", CellType.RIVER, false);
        board[4][5] = new Unit("E6", CellType.RIVER, false);
        board[4][6] = new Unit("E7", CellType.GROUND, true);
        board[4][7] = new Unit("E8", CellType.GROUND, false);
        board[4][8] = new Unit("E9", CellType.TRAP, false);

        board[5][0] = new Unit("F1", CellType.GROUND, false);
        board[5][1] = new Unit("F2", CellType.GROUND, true);
        board[5][2] = new Unit("F3", CellType.GROUND, false);
        board[5][3] = new Unit("F4", CellType.RIVER, false);
        board[5][4] = new Unit("F5", CellType.RIVER, false);
        board[5][5] = new Unit("F6", CellType.RIVER, false);
        board[5][6] = new Unit("F7", CellType.GROUND, false);
        board[5][7] = new Unit("F8", CellType.GROUND, true);
        board[5][8] = new Unit("F9", CellType.GROUND, false);

        board[6][0] = new Unit("G1", CellType.GROUND, true);
        board[6][1] = new Unit("G2", CellType.GROUND, false);
        board[6][2] = new Unit("G3", CellType.GROUND, true);
        board[6][3] = new Unit("G4", CellType.GROUND, false);
        board[6][4] = new Unit("G5", CellType.GROUND, false);
        board[6][5] = new Unit("G6", CellType.GROUND, false);
        board[6][6] = new Unit("G7", CellType.GROUND, true);
        board[6][7] = new Unit("G8", CellType.GROUND, false);
        board[6][8] = new Unit("G9", CellType.GROUND, true);


        animals[0] = new LionAndTiger("A1", 1, AnimalInfo.TIGER);
        animals[1] = new Rat("G3", 1, AnimalInfo.RAT);
        animals[2] = new CommonAnimal("B2", 1, AnimalInfo.CAT);
        animals[3] = new CommonAnimal("C3", 1, AnimalInfo.WOLF);
        animals[4] = new CommonAnimal("E3", 1, AnimalInfo.LEOPARD);
        animals[5] = new CommonAnimal("F2", 1, AnimalInfo.DOG);
        animals[6] = new LionAndTiger("G1", 1, AnimalInfo.LION);
        animals[7] = new Elephant("A3", 1, AnimalInfo.ELEPHANT);

        animals[8] = new LionAndTiger("G9", -1, AnimalInfo.TIGER);
        animals[9] = new Rat("A7", -1, AnimalInfo.RAT);
        animals[10] = new CommonAnimal("F8", -1, AnimalInfo.CAT);
        animals[AnimalNO11] = new CommonAnimal("E7", -1, AnimalInfo.WOLF);
        animals[AnimalNO12] = new CommonAnimal("C7", -1, AnimalInfo.LEOPARD);
        animals[AnimalNO13] = new CommonAnimal("B8", -1, AnimalInfo.DOG);
        animals[AnimalNO14] = new LionAndTiger("A9", -1, AnimalInfo.LION);
        animals[AnimalNO15] = new Elephant("G7", -1, AnimalInfo.ELEPHANT);


    }

    /**
     * get this turn team.
     *
     * @return this turn team
     * */
    public static int thisTurnTeam() {
        return thisTurn;
    }

    /**
     * judge if this movement is a reasonable move. Move it from fromPosition to toPosition if yes.
     *
     * @param fromPosition the start position.
     * @param toPosition the destination.
     * */
    public static void move(String fromPosition, String toPosition) {
        int[] fromCoordinate = getPosition(fromPosition);

        if (!Board.board[fromCoordinate[0]][fromCoordinate[1]].getIfHasAnimal()) {
            Animal.needDoMoveAgain();
            System.out.println("You must select an animal!");
            Board.printBoard();
            return;
        }

        getAnimal(fromPosition).move(fromPosition, toPosition);

        //isSaved = false;

    }

    /**
     * get the coordination according to the String input by the user.
     *
     * @param Position a position in String type.
     * @return transform this String to an array
     * */
    private static int[] getPosition(String Position) {
        int[] expression = new int[2];

        char fromLetter = Position.charAt(0);
        expression[0] = fromLetter - 'A';
        char fromNumber = Position.charAt(1);
        expression[1] = fromNumber - '1';

        return expression;
    }


    /**
     * judge if all of one team animals were dead.
     *
     * @return whether all of one team animals were dead.
     * */
    public static boolean ifAllDead() {
        boolean red = true;
        boolean blue = true;
        for (Animal animal : animals) {
            if (animal.getTeam() == 1 && animal.getFromPosition() != "Z1") red = false;
            if (animal.getTeam() == -1 && animal.getFromPosition() != "Z1") blue = false;
        }
        return red || blue;
    }

    /**print the board.*/
    public static void printBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board[i][j].getIfHasAnimal()) {
                    if (board[i][j].getCellType() == CellType.GROUND) System.out.print("    ");
                    else if (board[i][j].getCellType() == CellType.RIVER) System.out.print("~~~ ");
                    else if (board[i][j].getCellType() == CellType.TRAP) System.out.print("^^^ ");
                    else if (board[i][j].getCellType() == CellType.REDDEN || board[i][j].getCellType() == CellType.BLUEDEN)
                        System.out.print("[ ] ");

                } else {
                    if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.DOG)
                        System.out.print("DOG ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.CAT)
                        System.out.print("CAT ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.TIGER)
                        System.out.print("TIG ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.LION)
                        System.out.print("LIO ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.ELEPHANT)
                        System.out.print("ELE ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.RAT)
                        System.out.print("RAT ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.LEOPARD)
                        System.out.print("LEO ");
                    else if (getAnimal(board[i][j].getFromPosition()).getAnimalInfo() == AnimalInfo.WOLF)
                        System.out.print("WOL ");
                }
            }
            System.out.print("\n");
        }
    }
    /**
     * get one unit of the board according to its position number.
     *
     * @param col the column number of the unit.
     * @param row the row number of the unit.
     * @return the unit in this position
     * */
    public static Unit getUnit(int col, int row){
        return board[col][row];
    }

    /**
     * get the animal on this position if it exists.
     *
     * @param fromPosition the position on the board.
     * @return the animal on this board if it exists.
     * */
    public static Animal getAnimal(String fromPosition) {
        for (Animal animal : animals) {
            if (animal.getFromPosition().equals(fromPosition))
                return animal;
        }
        return animals[0];
    }

    /**
     * save the file.
     *
     * @param fos the file to be save.
     * @exception IOException if the path to be save is a wrong path.
     * */
    public static void save(FileOutputStream fos) throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    oos.writeObject(board[i][j]);
                }
            }
            for (int i = 0; i < animals.length; i++) {
                oos.writeObject(animals[i]);
            }
            for (int i = 0; i < name.length; i++) {
                oos.writeObject(name[i]);
            }
            //isSaved = true;
            oos.flush();
            oos.close();
            System.out.println("Save successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * open the file.
     *
     * @param fis the file to be open.
     * @exception EOFException Signals that an end of file or end of
     * stream has been reached unexpectedly during input.
     * */
    public static void open(FileInputStream fis) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    board[i][j] = (Unit) ois.readObject();
                }
            }
            for (int i = 0; i < animals.length; i++) {
                animals[i] = (Animal) ois.readObject();
            }
            for (int i = 0; i < name.length; i++) {
                name[i] = (String) ois.readObject();
            }
            System.out.println("Open successfully!");
            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            e.printStackTrace();
        }

    }

    /**return if the turn is changed*/
    public static void ifchangeThisTurn() {
        if (getUnit(3, 0).getIfHasAnimal() || getUnit(3, 8).getIfHasAnimal()) thisTurn++;
        else if (Animal.getIfChangeTeam() == 0) thisTurn = -thisTurn;
    }

}