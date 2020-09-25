package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;

/**Animal super-class*/
public class Animal implements Serializable {
    private String fromPosition;
    private int team;
    private AnimalInfo animalInfo;
    private static int needDoMoveAgain = 0;


    /**
     * construct a new animal.
     *
     * @param fromPosition describes the position of this animal.
     * @param team descibes the team of this animal.
     * @param animalInfo describe
     * */
    Animal(String fromPosition, int team, AnimalInfo animalInfo) {
        this.fromPosition = fromPosition;
        this.team = team;
        this.animalInfo = animalInfo;
    }

    /**
     * check if animal move to adjacent 4 unit.
     *
     * @param fromPosition describes the frompostion of this animal.
     * @param toPosition describes the topostion of this animal.
     * @return whether this movement is rational movement.
     * */
    public boolean rationalMove(String fromPosition, String toPosition){
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);


        if (fromCoordinate[0] - toCoordinate[0] == -1 && fromCoordinate[1] - toCoordinate[1] == 0) return true;
        if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == -1) return true;
        if (fromCoordinate[0] - toCoordinate[0] == 1 && fromCoordinate[1] - toCoordinate[1] == 0) return true;
        if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == 1) return true;

        return false;

    }

    /**check  whether the move is go a river's distance(2, 3).
     *
     *@param fromPosition describes the frompostion of this animal.
     *@param toPosition describes the topostion of this animal.
     *@return whether this movement is a jump movement.
     * */
    public boolean jumpMove(String fromPosition, String toPosition) {
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);

        if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == -4) return true; //横着往右跳
        if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == 4) return true; //横着往左跳
        if (fromCoordinate[0] - toCoordinate[0] == -3 && fromCoordinate[1] - toCoordinate[1] == 0) return true; //纵向往下跳
        if (fromCoordinate[0] - toCoordinate[0] == 3 && fromCoordinate[1] - toCoordinate[1] == 0) return true; //纵向往上跳

        return false;
    }


    /**get the animal's team.
     *
     * @return get the team of this animal.
     * */
    public int getTeam(){
        return team;
    }

    /**get the animalInfo.
     *
     * @return get the animalinfo of this animal.
     * */
    public AnimalInfo getAnimalInfo() {
        return this.animalInfo;
    }

    /**do not change this turn's team.*/
    public static void needDoMoveAgain() {
        needDoMoveAgain = 1;
    }

    /**change this turn's team*/
    public static void changeMoveTeam() {
        needDoMoveAgain = 0;
    }

    /**get the information whether should change this turn's team.
     *
     * @return whether to change this turn team.
     * */
    public static int getIfChangeTeam() {
        return needDoMoveAgain;
    }

    /**get the animal's position.
     *
     * @return the position of this animal.
     * */
    public String getFromPosition(){return this.fromPosition; }

    /**change the animal's position.
     *
     * @param toPosition change the position of this animal to toPosition.
     * */
    public void setPosition(String toPosition){
        this.fromPosition = toPosition;
    }

    /**move the animal from fromPosition to toPosition.
     *
     * @param fromPosition the start position of this animal.
     * @param toPosition move to toPosition.
     * */
    public void move(String fromPosition, String toPosition) {}
}

/**animals except rat, tiger, lion, and elephant*/
class CommonAnimal extends Animal {

    /**
     * construct a common animal.
     *
     * @param fromPosition the position of this animal.
     * @param team the team of this animal,
     * @param animalInfo the animal information of this animal
     * */
    CommonAnimal(String fromPosition, int team, AnimalInfo animalInfo) {
        super(fromPosition, team, animalInfo);
    }
    @Override
    public void move(String fromPosition, String toPosition) {
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);
        changeMoveTeam();

        /**two types of positions*/
        Unit nowPosition = Board.getUnit(fromCoordinate[0],fromCoordinate[1]);
        Unit nextPosition = Board.getUnit(toCoordinate[0],toCoordinate[1]);



        if (Board.getAnimal(fromPosition).getTeam() != Board.thisTurnTeam()) {
            System.out.println("You can only move your own animal!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }

        if (!rationalMove(fromPosition, toPosition)) {
            System.out.println("You can only move to adjacent position! OR You must move every turn!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }

        if (nextPosition.getCellType() == CellType.RIVER) {
            System.out.println("Your " + this.getAnimalInfo().name() + " cannot go onto river!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }

        if ((nextPosition.getCellType() == CellType.REDDEN && Board.getAnimal(fromPosition).getTeam() == 1)
                || nextPosition.getCellType() == CellType.BLUEDEN && Board.getAnimal(fromPosition).getTeam() == -1) {
            System.out.println("You cannot go into your own den!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }


        if (nextPosition.getIfHasAnimal()) {


            if (Board.getAnimal(toPosition).getTeam() == Board.thisTurnTeam()) {
                System.out.println("You cannot capture your own team's animal!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }

            if (Board.getAnimal(toPosition).getAnimalInfo().getRank() > this.getAnimalInfo().getRank() && nextPosition.getCellType() != CellType.TRAP) {
                System.out.println("Your " + this.getAnimalInfo().name() + " cannot fight against opposite" + Board.getAnimal(fromPosition).getAnimalInfo().name());
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (nextPosition.getCellType() == CellType.TRAP) {
                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;



            } else if (Board.getAnimal(toPosition).getAnimalInfo().getRank() <= this.getAnimalInfo().getRank()) {


                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;
            }
        }


        if(nextPosition.getCellType() == CellType.GROUND
                ||nextPosition.getCellType() == CellType.TRAP) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }

        if ((nextPosition.getCellType() == CellType.BLUEDEN && Board.thisTurnTeam() == 1) || (nextPosition.getCellType() == CellType.REDDEN && Board.thisTurnTeam() == -1)) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }
    }
}

/**elephant*/
class Elephant extends Animal {

    /**
     * construct a new elephant.
     *
     * @param fromPosition the position of this animal.
     * @param team the team of this animal,
     * @param animalInfo the animal information of this animal
     * */
    Elephant(String fromPosition, int team, AnimalInfo animalInfo) {
        super(fromPosition, team, animalInfo);
    }
    @Override
    public void move(String fromPosition, String toPosition) {
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);
        changeMoveTeam();
        /**two types of positions*/
        Unit nowPosition = Board.getUnit(fromCoordinate[0],fromCoordinate[1]);
        Unit nextPosition = Board.getUnit(toCoordinate[0],toCoordinate[1]);


        if (Board.getAnimal(fromPosition).getTeam() != Board.thisTurnTeam()) {
            System.out.println("You can only move your own animal!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }



        if (!rationalMove(fromPosition, toPosition)) {
            System.out.println("You can only move to adjacent position! OR You must move every turn!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }


        if (nextPosition.getCellType() == CellType.RIVER) {
            System.out.println("Your " + this.getAnimalInfo().name() + " cannot go onto river!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }


        if ((nextPosition.getCellType() == CellType.REDDEN && Board.getAnimal(fromPosition).getTeam() == 1)
                || nextPosition.getCellType() == CellType.BLUEDEN && Board.getAnimal(fromPosition).getTeam() == -1) {
            System.out.println("You cannot go into your own den!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }


        if (nextPosition.getIfHasAnimal()) {


            if (Board.getAnimal(toPosition).getTeam() == Board.thisTurnTeam()) {
                System.out.println("You cannot capture your own team's animal!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (Board.getAnimal(toPosition).getAnimalInfo() == AnimalInfo.RAT && nextPosition.getCellType() != CellType.TRAP) {
                System.out.println("Your " + this.getAnimalInfo().name() + " cannot fight against opposite" + Board.getAnimal(fromPosition).getAnimalInfo().name());
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (nextPosition.getCellType() == CellType.TRAP) {

                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;


            } else if (Board.getAnimal(toPosition).getAnimalInfo().getRank() <= this.getAnimalInfo().getRank()) { //第二种


                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;
            }
        }


        if(nextPosition.getCellType() == CellType.GROUND
                ||nextPosition.getCellType() == CellType.TRAP) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }


        if ((nextPosition.getCellType() == CellType.BLUEDEN && Board.thisTurnTeam() == 1) || (nextPosition.getCellType() == CellType.REDDEN && Board.thisTurnTeam() == -1)) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;

        }
    }
}

/**rat*/
class Rat extends Animal {

    /**
     * construct a new rat.
     *
     * @param fromPosition the position of this animal.
     * @param team the team of this animal,
     * @param animalInfo the animal information of this animal
     * */
    Rat(String fromPosition, int team, AnimalInfo animalInfo) {
        super(fromPosition, team, animalInfo);
    }
    @Override
    public void move(String fromPosition, String toPosition) {
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);
        changeMoveTeam();
        /**two types of positions*/
        Unit nowPosition = Board.getUnit(fromCoordinate[0],fromCoordinate[1]);
        Unit nextPosition = Board.getUnit(toCoordinate[0],toCoordinate[1]);

        if (Board.getAnimal(fromPosition).getTeam() != Board.thisTurnTeam()) {
            System.out.println("You can only move your own animal!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }



        if (!rationalMove(fromPosition, toPosition)) {
            System.out.println("You can only move to adjacent position! OR You must move every turn!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }



        if ((nextPosition.getCellType() == CellType.REDDEN && Board.getAnimal(fromPosition).getTeam() == 1)
                || nextPosition.getCellType() == CellType.BLUEDEN && Board.getAnimal(fromPosition).getTeam() == -1) {
            System.out.println("You cannot go into your own den!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }



        if ((nowPosition.getCellType() == CellType.GROUND
                && nextPosition.getCellType() == CellType.RIVER)
                || (nowPosition.getCellType() == CellType.RIVER
                && nextPosition.getCellType() == CellType.GROUND)) {
            if(nextPosition.getIfHasAnimal()){
                System.out.println("Your rat cannot go to a place where other animal exists when moving between river and ground! ");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }




        if (nextPosition.getIfHasAnimal()) {


            if (Board.getAnimal(toPosition).getTeam() == Board.thisTurnTeam()) {
                System.out.println("You cannot capture your own team's animal!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (Board.getAnimal(toPosition).getAnimalInfo().getRank() > this.getAnimalInfo().getRank() && Board.getAnimal(toPosition).getAnimalInfo()!= AnimalInfo.ELEPHANT && nextPosition.getCellType() != CellType.TRAP) {
                System.out.println("Your " + this.getAnimalInfo().name() + " cannot fight against opposite" + Board.getAnimal(fromPosition).getAnimalInfo().name());
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (nextPosition.getCellType() == CellType.TRAP) {
                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;


            } else if (Board.getAnimal(toPosition).getAnimalInfo().getRank() <= this.getAnimalInfo().getRank() || Board.getAnimal(toPosition).getAnimalInfo() == AnimalInfo.ELEPHANT) { //第二种

                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;
            }
        }

        if(nextPosition.getCellType() == CellType.GROUND
                || nextPosition.getCellType() == CellType.RIVER
                || nextPosition.getCellType() == CellType.TRAP) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }


        if ((nextPosition.getCellType() == CellType.BLUEDEN && Board.thisTurnTeam() == 1) || (nextPosition.getCellType() == CellType.REDDEN && Board.thisTurnTeam() == -1)) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;

        }
    }
}

/**lion and tiger*/
class LionAndTiger extends Animal {
    /**
     * construct a new lion or tiger.
     *
     * @param fromPosition the position of this animal.
     * @param team the team of this animal,
     * @param animalInfo the animal information of this animal
     * */
    LionAndTiger(String fromPosition, int team, AnimalInfo animalInfo) {
        super(fromPosition, team, animalInfo);
    }

    @Override
    public void move(String fromPosition, String toPosition) {
        int[] fromCoordinate = Unit.getPosition(fromPosition);
        int[] toCoordinate = Unit.getPosition(toPosition);
        changeMoveTeam();


        if (Board.getAnimal(fromPosition).getTeam() != Board.thisTurnTeam()) {
            System.out.println("You can only move your own animal!");
            needDoMoveAgain();
            Board.printBoard();
            return;
        }



        if (jumpMove(fromPosition, toPosition)) {

            if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == -4) {
                for (int position = fromCoordinate[1] + 1; position < toCoordinate[1]; position++) {
                    /**two types of positions*/
                    Unit nowPosition = Board.getUnit(fromCoordinate[0], position);
                    Unit nextPosition = Board.getUnit(position, toCoordinate[1]);
                    if (nowPosition.getCellType() != CellType.RIVER || nowPosition.getIfHasAnimal()) {
                        System.out.println("Invalid movement!");
                        needDoMoveAgain();
                        Board.printBoard();
                        return;
                    }
                }
            }
            if (fromCoordinate[0] - toCoordinate[0] == 0 && fromCoordinate[1] - toCoordinate[1] == 4) {
                for (int position = toCoordinate[1] + 1; position < fromCoordinate[1]; position++) {
                    /**two types of positions*/
                    Unit nowPosition = Board.getUnit(fromCoordinate[0], position);
                    Unit nextPosition = Board.getUnit(position, toCoordinate[1]);
                    if (nowPosition.getCellType() != CellType.RIVER || nowPosition.getIfHasAnimal()) {
                        System.out.println("Invalid movement!");
                        needDoMoveAgain();
                        Board.printBoard();
                        return;
                    }
                }
            }
            if (fromCoordinate[0] - toCoordinate[0] == -3 && fromCoordinate[1] - toCoordinate[1] == 0) {
                for (int position = fromCoordinate[0] + 1; position < toCoordinate[0]; position++) {
                    /**two types of positions*/
                    Unit nowPosition = Board.getUnit(position, fromCoordinate[1]);
                    if (nowPosition.getCellType() != CellType.RIVER || nowPosition.getIfHasAnimal()) {
                        System.out.println("Invalid movement!");
                        needDoMoveAgain();
                        Board.printBoard();
                        return;
                    }
                }
            }
            if (fromCoordinate[0] - toCoordinate[0] == 3 && fromCoordinate[1] - toCoordinate[1] == 0) {
                for (int position = toCoordinate[0] + 1; position < fromCoordinate[0]; position++) {
                    /**two types of positions*/
                    Unit nowPosition = Board.getUnit(position, fromCoordinate[1]);
                    if (nowPosition.getCellType() != CellType.RIVER || nowPosition.getIfHasAnimal()) {
                        System.out.println("Invalid movement!");
                        needDoMoveAgain();
                        Board.printBoard();
                        return;
                    }
                }
            }
        }




        else {
            /**two types of positions*/
            Unit nowPosition = Board.getUnit(fromCoordinate[0], fromCoordinate[1]);
            Unit nextPosition = Board.getUnit(toCoordinate[0], toCoordinate[1]);


            if (!rationalMove(fromPosition, toPosition)) {
                System.out.println("You can only move to adjacent position! OR You must move every turn!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (nextPosition.getCellType() == CellType.RIVER) {
                System.out.println("Your" + this.getAnimalInfo().name() + "cannot go onto river!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if ((nextPosition.getCellType() == CellType.REDDEN && Board.getAnimal(fromPosition).getTeam() == 1)
                    || nextPosition.getCellType() == CellType.BLUEDEN && Board.getAnimal(fromPosition).getTeam() == -1) {
                System.out.println("You cannot go into your own den!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }
        }


        /**two types of positions*/
        Unit nowPosition = Board.getUnit(fromCoordinate[0], fromCoordinate[1]);
        Unit nextPosition = Board.getUnit(toCoordinate[0], toCoordinate[1]);
        if (nextPosition.getIfHasAnimal()) {


            if (Board.getAnimal(toPosition).getTeam() == Board.thisTurnTeam()) {
                System.out.println("You cannot capture your own team's animal!");
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (Board.getAnimal(toPosition).getAnimalInfo().getRank() > this.getAnimalInfo().getRank() && nextPosition.getCellType() != CellType.TRAP) {
                System.out.println("Your" + this.getAnimalInfo().name() + "cannot fight against opposite" + Board.getAnimal(fromPosition).getAnimalInfo().name());
                needDoMoveAgain();
                Board.printBoard();
                return;
            }


            if (nextPosition.getCellType() == CellType.TRAP) {

                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;


            } else if (Board.getAnimal(toPosition).getAnimalInfo().getRank() <= this.getAnimalInfo().getRank()) { //第二种


                nowPosition.changeIfHasAnimal();

                System.out.println("Your " + this.getAnimalInfo().name() + " ate opposite " + Board.getAnimal(toPosition).getAnimalInfo().name());
                Board.getAnimal(toPosition).setPosition("Z1");
                Board.getAnimal(fromPosition).setPosition(toPosition);
                Board.printBoard();
                return;

            }
        }


        if (nextPosition.getCellType() == CellType.GROUND
                || nextPosition.getCellType() == CellType.TRAP) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;
        }


        if ((nextPosition.getCellType() == CellType.BLUEDEN && Board.thisTurnTeam() == 1) || (nextPosition.getCellType() == CellType.REDDEN && Board.thisTurnTeam() == -1)) {
            Board.getAnimal(fromPosition).setPosition(toPosition);
            nowPosition.changeIfHasAnimal();
            nextPosition.changeIfHasAnimal();
            Board.printBoard();
            return;

        }
    }
}
