package hk.edu.polyu.comp.comp2021.jungle.model;

import java.io.Serializable;

/**all kinds of animal.*/
enum AnimalInfo implements Serializable {
    /**rat*/
    RAT(1),

    /**cat*/
    CAT(2),

    /**dog*/
    DOG(3),

    /**wolf*/
    WOLF(4),

    /**leopard*/
    LEOPARD(5),

    /**tiger*/
    TIGER(6),

    /**lion*/
    LION(7),

    /**elephant*/
    ELEPHANT(8);

    private int rank;


    AnimalInfo(int rank) {
        this.rank = rank;
    }


    int getRank() {
        return this.rank;
    }

}