import hk.edu.polyu.comp.comp2021.jungle.model.Animal;
import hk.edu.polyu.comp.comp2021.jungle.model.Board;
import hk.edu.polyu.comp.comp2021.jungle.model.Unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**the controller of the whole project*/
public class controller {

    /**the constructor*/
    public controller(){}


     /** the main function to run.
     */
    public static void main() {
        int thisTurn = 1;
        boolean isSaved = false;


        final int NumbersOfAnimals = 16;
        Unit[][] board = new Unit[7][9];
        Animal[] animals = new Animal[NumbersOfAnimals];
        String[] name = new String[2];
        Board.setBoard(board, animals, name);

        System.out.println("Enter 'n' to start a new game,\nEnter 'o' to open a saved game");
        Scanner in = new Scanner(System.in);
        String choice = in.next();

        while (!choice.equals("n") && !choice.equals("o")) {
            System.out.println("Invalid input.");
            System.out.println("Enter 'n' to start a new game,\nEnter 'o' to open a saved game");
            choice = in.next();
        }
        Board.initialize();
        if (choice.equals("n")) {
            System.out.println("Please enter your team name:");
            name[0] = in.next();
            System.out.println("Please enter the other team name:");
            name[1] = in.next();
        }
        if (choice.equals("o")) {
            System.out.println("Please enter your file path to open:");
            String fileName = in.next();
            try {
                FileInputStream fis = new FileInputStream(fileName);
                Board.open(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Board.printBoard();

        while (!Board.ifAllDead() && (Board.thisTurnTeam() == -1 || Board.thisTurnTeam() == 1)) {
            System.out.println("Please enter the command.(open/save/move):");
            String inputCommand = in.next();
            while (!inputCommand.equals("open") && !inputCommand.equals("save") && !inputCommand.equals("move")) {
                System.out.println("Invalid Input!");
                System.out.println("Please enter the command.(open/save/move);");
                inputCommand = in.next();
            }

            if (inputCommand.equals("open")) {
                if (!isSaved) {
                    System.out.println("You haven't save your game, please enter a path to save:");
                    try {
                        FileOutputStream fos = new FileOutputStream(in.next());
                        Board.save(fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Please enter your file path to open:");
                try {
                    FileInputStream fis = new FileInputStream(in.next());
                    Board.open(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (inputCommand.equals("save")) {
                if (!isSaved) {
                    System.out.println("Please enter a path to save:");
                    try {
                        FileOutputStream fos = new FileOutputStream(in.next());
                        Board.save(fos);
                        isSaved = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (inputCommand.equals("move")){
             if (Board.thisTurnTeam() == 1) System.out.printf("%s TEAM PLEASE!\n", name[0]);
                else System.out.printf("%s TEAM PLEASE!\n", name[1]);
                System.out.print("\n");

                System.out.print("move from: ");
                String from = in.next();
                System.out.print("move to: ");
                String to = in.next();

                if(from.length() == 2 && to.length() == 2){
                    int[] From = Unit.getPosition(from);
                    int[] To = Unit.getPosition(to);
                    if(From[0]<=6 && From[0] >= 0 && To[0]<=6 && To[0] >= 0
                            && From[1]<=8 && From[1] >= 0 && To[1]<=8 && To[1] >= 0){
                        Board.move(from, to);
                        Board.ifchangeThisTurn();
                        isSaved = false;
                        if(Board.thisTurnTeam() == 0) System.out.printf("%s WIN!\n", name[1]);
                        else if(Board.thisTurnTeam() == 2) System.out.printf("%s WIN!\n", name[0]);
                        continue;
                    }
                }
                System.out.println("Invalid input");
            }
        }
    }
}
