import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.lang.Math;
public class animal extends Button {
    private boolean readyToSwap;
    private int value;
    private int row;
    private int col;
    animal(String a, ImageView s, int input ){
        super(a, s);
        value = input;
        readyToSwap = false;
    }

    public void click(animal anotherAnimal){
        eatAnother(anotherAnimal);
    }

    public void click(grid Grid){
        this.swap(Grid);
    }

    private void swap(grid Grid){
        int tempcol = this.getCol();
        int temprow = this.getRow();
        this.setRow(Grid.getRow());
        this.setCol(Grid.getCol());
        Grid.setCol(tempcol);
        Grid.setRow(temprow);
    }

    private void eatAnother(animal another){
        int tempcol = this.getCol();
        int temprow = this.getRow();
        this.setRow(another.getRow());
        this.setCol(another.getCol());
        another.setCol(tempcol);
        another.setRow(temprow);
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getCol(){
        return this.col;
    }

    public int getRow(){
        return this.row;
    }

    public int getAbsValue(){
        return Math.abs(value);
    }

    public int getValue(){
        return value;
    }

    public void setValue(int a){
        value = a;
    }

    public boolean getReady(){
        return readyToSwap;
    }
}
