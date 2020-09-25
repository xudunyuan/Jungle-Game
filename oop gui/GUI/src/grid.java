import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
public class grid extends Button {
    private boolean readyToSwap;
    private int row;
    private int col;

    grid(){
        super();
        readyToSwap = false;
    }

    public void click(){
        System.out.println("this is gird");
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


    public boolean getReady(){
        return readyToSwap;
    }
}
