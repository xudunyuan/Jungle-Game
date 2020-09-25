
import javafx.scene.control.TextField;
public class textEditor extends TextField{
    private String text;

    public textEditor(){
        super();
    }

    public void setString(String input){
        text = input;
    }

    public String  getString(){
        return text;
    }
}
