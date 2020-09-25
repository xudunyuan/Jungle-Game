package hk.edu.polyu.comp.comp2021.jungle.model;

/**all kinds of unit.*/
enum CellType {
    /**ground*/
    GROUND(0),

    /**river*/
    RIVER(2),

    /**trap*/
    TRAP(-1),

    /**red den*/
    REDDEN(-2),

    /**blue den*/
    BLUEDEN(-3);

    private int type;

    CellType(int type) {
        this.type = type;
    }

    /**@return unit type*/
    public int getTyoe() {
        return this.type;
    }
}
