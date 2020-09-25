import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.DirectoryChooserBuilder;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;
import java.lang.Math;
import java.util.Arrays;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Demo extends Application implements EventHandler<ActionEvent>{
    //一进来的界面
    private BorderPane welroot = new BorderPane();
    private GridPane welgridpane = new GridPane();
    private Scene welcome = new Scene(welroot, 200, 230, Color.WHITE);

    //游戏的主界面
    private String xPlayer = "x's \nname";
    private String yPlayer = "y's \nname";

    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, 600, 600, Color.WHITE);
    private GridPane gridpane = new GridPane();

    private animal redLion, blueLion, redTiger, blueTiger, redCat, blueCat, redDog, blueDog,
            redEle, blueEle, redMouse, blueMouse, redLeopard, redWolf, blueLeopard, blueWolf;
    private grid[] allGrid = new grid[63];
    private int SIZE = 47;
    private animal[] allAnimals = new animal[16];
    //显示在棋盘上的三个选择button
    private Button mainSave = new Button("save");
    private Button mainOpen = new Button("open");
    private Button mainQuit = new Button("quit");
    //用来记录是否已经保存这盘棋了
    private boolean hasSaved = false;

    //用来记录上一个点击的位置，上一次点击的必须是动物
    private animal recordAnimal = null;
    //记录是否准备下棋
    boolean readySwap;
    //记录轮到谁下棋
    //true红方，false蓝方
    boolean turn = true;
    //非法移动棋子弹窗
    private Alert alert = new Alert(AlertType.WARNING);
    //游戏结束弹窗
    private Alert endGame = new Alert(AlertType.INFORMATION);
    //未保存提示弹窗
    private Alert notSaved = new Alert(AlertType.CONFIRMATION, "You haven't saved the game. Do you want to save it now?");
    //确认保存弹窗
    private Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION,"You want to save this game?");
    //确认退出弹窗
    private Alert confirmQuit = new Alert(Alert.AlertType.CONFIRMATION,"You want to quit this game?");
    //确认打开文件弹窗
    private Alert confirmOpen = new Alert(Alert.AlertType.CONFIRMATION,"You want to open a new game?");

    private int[][] array = new int[9][7];


    //画棋盘和场景函数，这400行没什么具体意义
    @Override
    public void start(Stage primaryStage) throws Exception {
        //场景1的设计
        Button Start = new Button("Start");
        Button Open = new Button("Open");
        Button Quit = new Button("Quit");
        Label label1 = new Label("Jungle Chess");
        welgridpane.setPadding(new Insets(10,10,10,10));
        //设置横竖间距
        welgridpane.setHgap(12);
        welgridpane.setVgap(19);
        GridPane.setConstraints(label1, 1, 1);
        welgridpane.add(label1, 1, 1);
        GridPane.setConstraints(Start, 1, 2);
        welgridpane.add(Start, 1, 2);
        GridPane.setConstraints(Open, 1, 3);
        welgridpane.add(Open, 1, 3);
        GridPane.setConstraints(Quit, 1, 4);
        welgridpane.add(Quit, 1, 4);
        welroot.setCenter(welgridpane);

        //点击开始新游戏，跳进获取姓名的场景
        Start.setOnAction(e->{
            Stage getNameStage = new Stage();
            //跳出获取文件名的框框
            BorderPane palyerNameroot = new BorderPane();
            GridPane palyerNamegridpane = new GridPane();
            palyerNamegridpane.setPadding(new Insets(10,10,10,10));
            palyerNamegridpane.setHgap(12);
            palyerNamegridpane.setVgap(30);
            Label xx = new Label("red side palyer name: ");
            Label yy = new Label("blue side palyer name: ");
            TextField xxName = new TextField();
            TextField yyName = new TextField();
            xxName.setPromptText("x's name here");
            yyName.setPromptText("y's name here");
            Button back = new Button("back");
            Button OK = new Button("Confirm");
            GridPane.setConstraints(xx, 1, 1);
            palyerNamegridpane.add(xx, 1, 1);
            GridPane.setConstraints(yy, 1, 2);
            palyerNamegridpane.add(yy, 1, 2);
            GridPane.setConstraints(xxName, 3, 1);
            palyerNamegridpane.add(xxName, 3, 1);
            GridPane.setConstraints(yyName, 3, 2);
            palyerNamegridpane.add(yyName, 3, 2);
            GridPane.setConstraints(back, 1, 3);
            palyerNamegridpane.add(back, 1, 3);
            GridPane.setConstraints(OK, 3, 3);
            palyerNamegridpane.add(OK, 3, 3);
            Scene FilegetName = new Scene(palyerNameroot, 400, 230);
            palyerNameroot.setCenter(palyerNamegridpane);
            getNameStage.setTitle("get players' name");
            getNameStage.setScene(FilegetName);
            getNameStage.show();
            //点击back，关闭这个stage
            back.setOnAction(b->{
                getNameStage.close();
            });
            //点击ok，把这两个input记录并跳进主场景
            OK.setOnAction(l->{
                xPlayer = xxName.getText();
                yPlayer = yyName.getText();
                getNameStage.close();
                setPriBoard();
                drawBoard();
                primaryStage.setScene(scene);
            });
        });
        //点击open，打开本地文件
        Open.setOnAction(e->{
            open();
            primaryStage.setScene(scene);
        });
        //点击退出按钮，程序退出
        Quit.setOnAction(e->{
            Optional<ButtonType> result = confirmQuit.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(1);
            }
        });

        if(array == null){
            setPriBoard();
        }
        //场景2的设计
        //创建47个空格对象
        for(int i = 0; i < SIZE; i ++){
            allGrid[i] = new grid();
            allGrid[i].setPrefSize(57, 47);
            allGrid[i].setStyle(" -fx-base: transparent;");
            //添加鼠标点击动作处理
            allGrid[i].setOnAction(this);
        }
        //设置窗口大小不可变
        primaryStage.setResizable(false);
        gridpane.setPadding(new Insets(10,10,10,10));
        //设置横竖间距
        gridpane.setHgap(10);
        gridpane.setVgap(19);

        //读取背景图
        ImageView background = new ImageView("file:../oopMaterials/background.jpeg");

        //读取红色棋子背景图
        ImageView redlion = new ImageView("file:../oopMaterials/redLion.jpeg");
        ImageView redtiger = new ImageView("file:../oopMaterials/redTiger.jpeg");
        ImageView redcat = new ImageView("file:../oopMaterials/redCat.jpeg");
        ImageView reddog = new ImageView("file:../oopMaterials/redDog.jpeg");
        ImageView redele = new ImageView("file:../oopMaterials/redEle.jpeg");
        ImageView redwolf = new ImageView("file:../oopMaterials/redWolf.jpeg");
        ImageView redleopard = new ImageView("file:../oopMaterials/redLeopard.jpeg");
        ImageView redmouse = new ImageView("file:../oopMaterials/redMouse.jpeg");
        //读取蓝色棋子背景图
        ImageView bluelion = new ImageView("file:../oopMaterials/blueLion.jpeg");
        ImageView bluetiger = new ImageView("file:../oopMaterials/blueTiger.jpeg");
        ImageView bluecat = new ImageView("file:../oopMaterials/blueCat.jpeg");
        ImageView bluedog = new ImageView("file:../oopMaterials/blueDog.jpeg");
        ImageView blueele = new ImageView("file:../oopMaterials/blueEle.jpeg");
        ImageView bluewolf = new ImageView("file:../oopMaterials/blueWolf.jpeg");
        ImageView blueleopard = new ImageView("file:../oopMaterials/blueLeopard.jpeg");
        ImageView bluemouse = new ImageView("file:../oopMaterials/blueMouse.jpeg");
        //设置背景图大小
        background.setFitWidth(600);
        background.setFitHeight(600);
        root.getChildren().add(background);
        //设置红棋大小
        redlion.setFitHeight(38);
        redlion.setFitWidth(38);
        redtiger.setFitHeight(38);
        redtiger.setFitWidth(38);
        redcat.setFitHeight(38);
        redcat.setFitWidth(38);
        reddog.setFitHeight(38);
        reddog.setFitWidth(38);
        redele.setFitHeight(38);
        redele.setFitWidth(38);
        redwolf.setFitHeight(38);
        redwolf.setFitWidth(38);
        redleopard.setFitHeight(38);
        redleopard.setFitWidth(38);
        redmouse.setFitHeight(38);
        redmouse.setFitWidth(38);
        //设置蓝棋大小
        bluelion.setFitHeight(38);
        bluelion.setFitWidth(38);
        bluetiger.setFitHeight(38);
        bluetiger.setFitWidth(38);
        bluecat.setFitHeight(38);
        bluecat.setFitWidth(38);
        bluedog.setFitHeight(38);
        bluedog.setFitWidth(38);
        blueele.setFitHeight(38);
        blueele.setFitWidth(38);
        bluewolf.setFitHeight(38);
        bluewolf.setFitWidth(38);
        blueleopard.setFitHeight(38);
        blueleopard.setFitWidth(38);
        bluemouse.setFitHeight(38);
        bluemouse.setFitWidth(38);

        mainSave.setStyle(" -fx-base: transparent;");
        mainSave.setOnAction(this);
        mainOpen.setPrefSize(50, 45);
        mainOpen.setStyle(" -fx-base: transparent;");
        mainOpen.setOnAction(this);
        mainQuit.setPrefSize(50, 45);
        mainQuit.setStyle(" -fx-base: transparent;");
        mainQuit.setOnAction(this);

        //设置所有棋子图片和格式和动作
        redEle = new animal("", redele, 8);
        redEle.setPrefSize(45, 45);
        redEle.setStyle(" -fx-base: transparent;");
        redEle.setOnAction(this);
        allAnimals[0] = redEle;
        blueEle = new animal("", blueele, -8);
        blueEle.setPrefSize(45, 45);
        blueEle.setStyle(" -fx-base: transparent;");
        blueEle.setOnAction(this);
        allAnimals[1] = blueEle;
        redLion = new animal("", redlion, 7);
        redLion.setPrefSize(45, 45);
        redLion.setStyle(" -fx-base: transparent;");
        redLion.setOnAction(this);
        allAnimals[2] = redLion;
        blueLion = new animal("", bluelion, -7);
        blueLion.setPrefSize(45, 45);
        blueLion.setStyle(" -fx-base: transparent;");
        blueLion.setOnAction(this);
        allAnimals[3] = blueLion;
        redTiger = new animal("", redtiger, 6);
        redTiger.setPrefSize(45, 45);
        redTiger.setStyle(" -fx-base: transparent;");
        redTiger.setOnAction(this);
        allAnimals[4] = redTiger;
        blueTiger = new animal("", bluetiger, -6);
        blueTiger.setPrefSize(45, 45);
        blueTiger.setStyle(" -fx-base: transparent;");
        blueTiger.setOnAction(this);
        allAnimals[5] = blueTiger;
        redLeopard = new animal("", redleopard, 5);
        redLeopard.setPrefSize(45, 45);
        redLeopard.setStyle(" -fx-base: transparent;");
        redLeopard.setOnAction(this);
        allAnimals[6] = redLeopard;
        blueLeopard = new animal("", blueleopard, -5);
        blueLeopard.setPrefSize(45, 45);
        blueLeopard.setStyle(" -fx-base: transparent;");
        blueLeopard.setOnAction(this);
        allAnimals[7] = blueLeopard;
        redWolf = new animal("", redwolf, 4);
        redWolf.setPrefSize(45, 45);
        redWolf.setStyle(" -fx-base: transparent;");
        redWolf.setOnAction(this);
        allAnimals[8] = redWolf;
        blueWolf = new animal("", bluewolf, -4);
        blueWolf.setPrefSize(45, 45);
        blueWolf.setStyle(" -fx-base: transparent;");
        blueWolf.setOnAction(this);
        allAnimals[9] = blueWolf;
        redDog = new animal("", reddog, 3);
        redDog.setPrefSize(45, 45);
        redDog.setStyle(" -fx-base: transparent;");
        redDog.setOnAction(this);
        allAnimals[10] = redDog;
        blueDog = new animal("", bluedog, -3);
        blueDog.setPrefSize(45, 45);
        blueDog.setStyle(" -fx-base: transparent;");
        blueDog.setOnAction(this);
        allAnimals[11] = blueDog;
        redCat = new animal("", redcat, 2);
        redCat.setPrefSize(45, 45);
        redCat.setStyle(" -fx-base: transparent;");
        redCat.setOnAction(this);
        allAnimals[12] = redCat;
        blueCat = new animal("", bluecat, -2);
        blueCat.setPrefSize(45, 45);
        blueCat.setStyle(" -fx-base: transparent;");
        blueCat.setOnAction(this);
        allAnimals[13] = blueCat;
        redMouse = new animal("", redmouse, 1);
        redMouse.setPrefSize(45, 45);
        redMouse.setStyle(" -fx-base: transparent;");
        redMouse.setOnAction(this);
        allAnimals[14] = redMouse;
        blueMouse = new animal("", bluemouse, -1);
        blueMouse.setPrefSize(45, 45);
        blueMouse.setStyle(" -fx-base: transparent;");
        blueMouse.setOnAction(this);
        allAnimals[15] = blueMouse;

        //初始化警告框
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("Invalid movement!");
        endGame.setTitle("Game over!");
        endGame.setHeaderText(null);

        root.setCenter(gridpane);
        primaryStage.setScene(welcome);
        primaryStage.show();
    }

    //执行鼠标点击函数
    @Override
    public void handle(ActionEvent event){
        //记录棋盘上有没有红蓝棋子，结束条件之一
        boolean hasBlue = false;
        boolean hasRed = false;
        if(event.getSource() == mainOpen){
            Optional<ButtonType> result1 = confirmOpen.showAndWait();
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                //如果打开文件前没保存，先保存
                if (!hasSaved) {
                    Optional<ButtonType> result = notSaved.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        save();
                    }
                }
                open();
            }
        }

        if(event.getSource() == mainSave){
            save();
        }
        if(event.getSource() == mainQuit){
            Optional<ButtonType> result1 = confirmQuit.showAndWait();
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                //如果退出没保存，先去保存
                if (!hasSaved) {
                    Optional<ButtonType> result = notSaved.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        save();
                    }
                }
                System.exit(1);
            }
        }

        //还没准备好交换
        if(readySwap == false){
            //如果先点到动物，就记录下来
            for (int i = 0; i < 16; i ++){
                if(event.getSource() == allAnimals[i]){
                    //如果点到对方的动物就return
                    if((turn && allAnimals[i].getValue() < 0) || (!turn && allAnimals[i].getValue() > 0 )) {
                        alert.showAndWait();
                        return;
                    }
                    recordAnimal = allAnimals[i];
                    readySwap = true;
                    return;

                }
            }
            //如果先点到格子，就假装没点到
            for (int i = 0; i < SIZE; i ++) {
                if (event.getSource() == allGrid[i]) {
                    return;
                }
            }
        }

        //已经准备好交换
        else {
            //如果之前没点到动物就返回
            if(recordAnimal == null) return;
            //如果点到是动物和动物
            //记录点的动物的位置
            int tempx = recordAnimal.getRow();
            int tempy = recordAnimal.getCol();
            for (int i = 0; i < 16; i ++){
                if(event.getSource() == allAnimals[i]){
                    //获取第二次次点击的位置
                    int nowx = allAnimals[i].getRow();
                    int nowy = allAnimals[i].getCol();
                    //用来判断被吃的是不是在陷阱里
                    boolean inTrap = false;
                    //如果两次点到一样的动物或者同方的动物
                    //或者下一步被对方吃
                    if((allAnimals[i] == recordAnimal || recordAnimal.getAbsValue() < allAnimals[i].getAbsValue()
                            ||(recordAnimal.getValue() * allAnimals[i].getValue() > 0)
                            ||(recordAnimal.getAbsValue() == 8 && allAnimals[i].getAbsValue()== 1))
                            && (recordAnimal.getAbsValue() != 1 || allAnimals[i].getAbsValue()!= 8)){
                        //判断陷阱
                        if((recordAnimal.getValue() < 0 && (nowx == 2 || nowx == 4) && nowy == 0)
                                ||(recordAnimal.getValue() < 0 && nowx == 3 && nowy == 1)
                                ||(recordAnimal.getValue() > 0 && (nowx == 2 || nowx == 4) && nowy == 8)
                                ||(recordAnimal.getValue() > 0 && nowx == 3 && nowy == 7))
                        {
                            inTrap = true;
                        }
                        //如果不在陷阱
                        //不做任何动作
                        if(!inTrap) {
                            //跳出警告
                            alert.showAndWait();
                            //还原参数
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                    }

                    //1，两格子不是相邻
                    //2，只有狮子老虎可以跳河
                    if(!((Math.abs(nowx-tempx) == 1 && nowy==tempy) || (Math.abs(nowy-tempy) == 1 && nowx==tempx))){
                        if(!(recordAnimal.getAbsValue() == 7 || recordAnimal.getAbsValue() == 6)){
                            alert.showAndWait();
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                        else {
                            //如果不是跳河
                            //或者河里有老鼠
                            if(!((    (nowx == 1 && tempx == 1 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2)))
                                    && array[3][1] == 0 && array[4][1] == 0 && array[5][1] == 0)
                                    ||(nowx ==2 && tempx == 2 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][2] == 0 && array[4][2] == 0 && array[5][2] == 0)
                                    ||(nowx ==4 && tempx == 4 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][4] == 0 && array[4][4] == 0 && array[5][4] == 0)
                                    ||(nowx ==5 && tempx == 5 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][5] == 0 && array[4][5] == 0 && array[5][5] == 0)
                                    ||(nowy ==3 && tempy == 3 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[3][1] == 0 && array[3][2] == 0)
                                    ||(nowy ==3 && tempy == 3 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[3][4] == 0 && array[3][5] == 0)
                                    ||(nowy ==4 && tempy == 4 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[4][1] == 0 && array[4][2] == 0)
                                    ||(nowy ==4 && tempy == 4 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[4][4] == 0 && array[4][5] == 0)
                                    ||(nowy ==5 && tempy == 5 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[5][1] == 0 && array[5][2] == 0)
                                    ||(nowy ==5 && tempy == 5 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[5][4] == 0 && array[5][5] == 0)))
                            {
                                alert.showAndWait();
                                recordAnimal = null;
                                readySwap = false;
                                return;
                            }
                        }
                    }
                    //3，只有老鼠可以下水
                    if((nowx == 1 || nowx == 2 || nowx == 4 || nowx == 5) && ( nowy<=5 && nowy >=3)){
                        if(recordAnimal.getAbsValue() != 1){
                            alert.showAndWait();
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                    }
                    //老鼠不可以从水里吃东西
                    if(recordAnimal.getAbsValue() == 1){
                        if((tempx == 1 || tempx == 2 || tempx == 4 || tempx == 5) && (tempy <= 5 && tempy >= 3)){
                            alert.showAndWait();
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                    }

                    //如果下一步是吃对方的棋子，允许
                    //在数组里改变数据
                    int previous = array[tempy][tempx];
                    array[nowy][nowx] = previous;
                    array[tempy][tempx] = 0;
                    //重新画这两个button
                    grid newGrid = new grid();
                    newGrid.setRow(tempx);
                    newGrid.setCol(tempy);
                    newGrid.setPrefSize(57, 47);
                    newGrid.setStyle(" -fx-base: transparent;");
                    newGrid.setOnAction(this);
                    grid[] temp = new grid[allGrid.length];
                    allGrid[SIZE] = newGrid;
                    SIZE++;
                    gridpane.getChildren().remove(allAnimals[i]);
                    gridpane.getChildren().remove(recordAnimal);
                    allAnimals[i].click(recordAnimal);
                    GridPane.setConstraints(recordAnimal, recordAnimal.getRow() + 1, recordAnimal.getCol());
                    GridPane.setConstraints(newGrid, allAnimals[i].getRow()+1, allAnimals[i].getCol());
                    //把被吃掉的棋子的value改成0
                    allAnimals[i].setValue(0);
                    gridpane.getChildren().add(recordAnimal);
                    gridpane.getChildren().add(newGrid);
                    //判断输赢
                    if(recordAnimal.getValue() > 0 && recordAnimal.getRow() == 3 && recordAnimal.getCol() == 8){
                        endGame.setContentText("Red side is Winner!");
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    if(recordAnimal.getValue() < 0 && recordAnimal.getRow() == 3 && recordAnimal.getCol() == 0){
                        endGame.setContentText("Blue side is Winner!");
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    //如果某一方无棋可走
                    for(int k = 0; k < allAnimals.length; k ++){
                        if(allAnimals[k].getValue() < 0){
                            hasBlue = true;
                        }
                        if(allAnimals[k].getValue() > 0){
                            hasRed = true;
                        }
                    }
                    if(!hasBlue){
                        endGame.setContentText(String.format("%s is Winner!", xPlayer));
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    if(!hasRed){
                        endGame.setContentText(String.format("%s is Winner!", yPlayer));
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    break;
                }
            }
            //如果点到的是动物和格子
            for (int i = 0; i < SIZE; i ++){
                if(event.getSource() == allGrid[i]){
                    //获取两次点击的位置
                    int nowx = allGrid[i].getRow();
                    int nowy = allGrid[i].getCol();
                    //如果位移不合法，返回
                    //不合法的位移：
                    //1，两格子不是相邻
                    //2，只有狮子老虎可以跳河
                    if(!((Math.abs(nowx-tempx) == 1 && nowy==tempy) || (Math.abs(nowy-tempy) == 1 && nowx==tempx))){
                        if(!(recordAnimal.getAbsValue() == 7 || recordAnimal.getAbsValue() == 6)){
                            alert.showAndWait();
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                        else {
                            //如果不是跳河
                            //或者河里有老鼠
                            if(!(((nowx == 1 && tempx == 1 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2)))
                                    && array[3][1] == 0 && array[4][1] == 0 && array[5][1] == 0)
                                    ||(nowx ==2 && tempx == 2 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][2] == 0 && array[4][2] == 0 && array[5][2] == 0)
                                    ||(nowx ==4 && tempx == 4 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][4] == 0 && array[4][4] == 0 && array[5][4] == 0)
                                    ||(nowx ==5 && tempx == 5 && ((nowy == 2 && tempy == 6)||(nowy == 6 && tempy == 2))
                                    && array[3][5] == 0 && array[4][5] == 0 && array[5][5] == 0)
                                    ||(nowy ==3 && tempy == 3 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[3][1] == 0 && array[3][2] == 0)
                                    ||(nowy ==3 && tempy == 3 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[3][4] == 0 && array[3][5] == 0)
                                    ||(nowy ==4 && tempy == 4 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[4][1] == 0 && array[4][2] == 0)
                                    ||(nowy ==4 && tempy == 4 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[4][4] == 0 && array[4][5] == 0)
                                    ||(nowy ==5 && tempy == 5 && ((nowx == 0 && tempx == 3)||(nowx == 3 && tempx == 0))
                                    && array[5][1] == 0 && array[5][2] == 0)
                                    ||(nowy ==5 && tempy == 5 && ((nowx == 3 && tempx == 6)||(nowx == 6 && tempx == 3))
                                    && array[5][4] == 0 && array[5][5] == 0)))
                            {
                                alert.showAndWait();
                                recordAnimal = null;
                                readySwap = false;
                                return;
                            }
                            //如果河里有生物
                            //搭配棋盘的array来写比较简单

                        }
                    }
                    //3，只有老鼠可以下水
                    if((nowx == 1 || nowx == 2 || nowx == 4 || nowx == 5) && ( nowy<=5 && nowy >=3)){
                        if(recordAnimal.getAbsValue() != 1){
                            alert.showAndWait();
                            recordAnimal = null;
                            readySwap = false;
                            return;
                        }
                    }
                    // 不可以进入己方兽穴
                    if(nowx == 3 && nowy == 0 && recordAnimal.getValue() > 0){
                        alert.showAndWait();
                        recordAnimal = null;
                        readySwap = false;
                        return;
                    }
                    if(nowx == 3 && nowy == 8 && recordAnimal.getValue() < 0){
                        alert.showAndWait();
                        recordAnimal = null;
                        readySwap = false;
                        return;
                    }
                    //在数组里改变数据
                    int previous = array[tempy][tempx];
                    array[nowy][nowx] = previous;
                    array[tempy][tempx] = 0;
                    //重新画这两个button
                    gridpane.getChildren().remove(allGrid[i]);
                    gridpane.getChildren().remove(recordAnimal);
                    recordAnimal.click(allGrid[i]);
                    GridPane.setConstraints(allGrid[i], allGrid[i].getRow() + 1, allGrid[i].getCol());
                    GridPane.setConstraints(recordAnimal, recordAnimal.getRow() + 1, recordAnimal.getCol());
                    gridpane.getChildren().add(allGrid[i]);
                    gridpane.getChildren().add(recordAnimal);
                    //判断输赢
                    if(recordAnimal.getValue() > 0 && recordAnimal.getRow() == 3 && recordAnimal.getCol() == 8){
                        endGame.setContentText(String.format("%s is Winner!", xPlayer));
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    if(recordAnimal.getValue() < 0 && recordAnimal.getRow() == 3 && recordAnimal.getCol() == 0){
                        endGame.setContentText(String.format("%s is Winner!", yPlayer));
                        endGame.showAndWait();
                        System.exit(1);
                    }
                    break;
                }
            }
            //还原参数
            recordAnimal = null;
            readySwap = false;
            //换人下棋
            turn = !turn;
            //新的棋局没保存
            hasSaved = false;
        }
    }

    public void save() {
        //选择存文件的路径
        Stage primaryStage = new Stage();
        primaryStage.setTitle("choose directory");
        DirectoryChooserBuilder builder = DirectoryChooserBuilder.create();
        builder.title("Hello World");
        String cwd = System.getProperty("user.dir");
        File file = new File(cwd);
        builder.initialDirectory(file);
        DirectoryChooser chooser = builder.build();
        File chosenDir = chooser.showDialog(primaryStage);
        if(chosenDir == null) {
            return;
        }
        String Path = chosenDir.getAbsolutePath();
        //跳出获取文件名的框框
        BorderPane FileNameroot = new BorderPane();
        GridPane FileNamegridpane = new GridPane();
        TextField FileNameInput = new TextField();
        FileNameInput.setPrefColumnCount(15);
        Label lab = new Label("FileName: ");
        Button confirm = new Button("Save");
        confirm.setPrefSize(100,30);
        GridPane.setConstraints(lab, 1, 1);
        FileNamegridpane.add(lab, 1, 1);
        GridPane.setConstraints(FileNameInput, 2, 1);
        FileNamegridpane.add(FileNameInput, 2, 1);
        GridPane.setConstraints(confirm, 2, 2);
        FileNamegridpane.add(confirm, 2, 2);
        Scene FilegetName = new Scene(FileNameroot, 300, 90);
        FileNameroot.setCenter(FileNamegridpane);
        primaryStage.setTitle("Set Filename");
        primaryStage.setScene(FilegetName);
        primaryStage.show();
        confirm.setOnAction(e-> {
            String fileName = FileNameInput.getText();
                try {
                File writename = new File(Path + "/" + fileName + ".txt");
                writename.createNewFile(); // 创建新文件
                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
                //把棋盘信息转成string存进txt
                StringBuilder board = new StringBuilder();
                for(int i = 0; i < array.length; i ++){
                    for(int j = 0; j < array[i].length; j ++){
                        board.append(array[i][j]);
                        board.append("\n");
                    }
                }
                //保存轮到谁下棋，1红分2蓝方
                if(turn == true){
                    board.append("1\n");
                }
                else if(turn == false){
                    board.append("0\n");
                }
                board.append(xPlayer+"\n");
                board.append(yPlayer+"\n");
                out.write(board.toString()); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                out.close();
            }
            catch (Exception x) {
                x.printStackTrace();
            }
            primaryStage.close();
        });
        hasSaved = true;
    }

    public void open(){
        gridpane.getChildren().clear();
        //选择存文件的路径
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader);
            for(int i = 0; i < 9; i ++){
                for (int j = 0; j < 7; j ++){
                    int num = Integer.valueOf(br.readLine());
                    array[i][j] = num;
                }
            }
            int last = Integer.valueOf(br.readLine());
            if(last == 1){
                turn = true;
            }
            else if(last == 0){
                turn = false;
            }
            xPlayer = String.valueOf(br.readLine());
            yPlayer = String.valueOf(br.readLine());
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        hasSaved = true;
        drawBoard();
    }

    public void drawBoard(){
        System.out.println(xPlayer);
        Label xpalyer = new Label(xPlayer);
        Label ypalyer = new Label(yPlayer);
        xpalyer.setPrefSize(45,45);
        ypalyer.setPrefSize(45,45);
        mainSave.setPrefSize(50, 45);
        GridPane.setConstraints(mainSave, 0, 3);
        gridpane.add(mainSave, 0, 3);
        GridPane.setConstraints(mainOpen, 0, 4);
        gridpane.add(mainOpen, 0, 4);
        GridPane.setConstraints(mainQuit, 0, 5);
        gridpane.add(mainQuit, 0, 5);
        //GridPane.setConstraints(xpalyer, 0, 0);
        gridpane.add(xpalyer, 0, 0);
        //GridPane.setConstraints(ypalyer, 0, 8);
        gridpane.add(ypalyer, 0, 8);
        //开始绘制棋盘
        int index = 0;
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 7; row++) {
                if (array[col][row] == 0) {
                    allGrid[index].setCol(col);
                    allGrid[index].setRow(row);
                    GridPane.setConstraints(allGrid[index], row + 1, col);
                    gridpane.add(allGrid[index], row + 1, col);
                    index++;
                }
                if (array[col][row] == 1) {
                    //记录位置用于判断是否相邻
                    redLion.setCol(col);
                    redLion.setRow(row);
                    GridPane.setConstraints(redLion, row + 1, col);
                    gridpane.add(redLion, row + 1, col);
                }

                if (array[col][row] == 2) {
                    redTiger.setCol(col);
                    redTiger.setRow(row);
                    GridPane.setConstraints(redTiger, row + 1, col);
                    gridpane.add(redTiger, row + 1, col);
                }
                if (array[col][row] == 3) {
                    redDog.setCol(col);
                    redDog.setRow(row);
                    GridPane.setConstraints(redDog, row + 1, col);
                    gridpane.add(redDog, row + 1, col);
                }
                if (array[col][row] == 4) {
                    redCat.setCol(col);
                    redCat.setRow(row);
                    GridPane.setConstraints(redCat, row + 1, col);
                    gridpane.add(redCat, row + 1, col);
                }
                if (array[col][row] == 5) {
                    redMouse.setCol(col);
                    redMouse.setRow(row);
                    GridPane.setConstraints(redMouse, row + 1, col);
                    gridpane.add(redMouse, row + 1, col);
                }
                if (array[col][row] == 6) {
                    redLeopard.setCol(col);
                    redLeopard.setRow(row);
                    GridPane.setConstraints(redLeopard, row + 1, col);
                    gridpane.add(redLeopard, row + 1, col);
                }
                if (array[col][row] == 7) {
                    redWolf.setCol(col);
                    redWolf.setRow(row);
                    GridPane.setConstraints(redWolf, row + 1, col);
                    gridpane.add(redWolf, row + 1, col);
                }
                if (array[col][row] == 8) {
                    redEle.setCol(col);
                    redEle.setRow(row);
                    GridPane.setConstraints(redEle, row + 1, col);
                    gridpane.add(redEle, row + 1, col);
                }
                if (array[col][row] == -1) {
                    blueLion.setCol(col);
                    blueLion.setRow(row);
                    GridPane.setConstraints(blueLion, row + 1, col);
                    gridpane.add(blueLion, row + 1, col);
                }
                if (array[col][row] == -2) {
                    blueTiger.setCol(col);
                    blueTiger.setRow(row);
                    GridPane.setConstraints(blueTiger, row + 1, col);
                    gridpane.add(blueTiger, row + 1, col);
                }
                if (array[col][row] == -3) {
                    blueDog.setCol(col);
                    blueDog.setRow(row);
                    GridPane.setConstraints(blueDog, row + 1, col);
                    gridpane.add(blueDog, row + 1, col);
                }
                if (array[col][row] == -4) {
                    blueCat.setCol(col);
                    blueCat.setRow(row);
                    GridPane.setConstraints(blueCat, row + 1, col);
                    gridpane.add(blueCat, row + 1, col);
                }
                if (array[col][row] == -5) {
                    blueMouse.setCol(col);
                    blueMouse.setRow(row);
                    GridPane.setConstraints(blueMouse, row + 1, col);
                    gridpane.add(blueMouse, row + 1, col);
                }
                if (array[col][row] == -6) {
                    blueLeopard.setCol(col);
                    blueLeopard.setRow(row);
                    GridPane.setConstraints(blueLeopard, row + 1, col);
                    gridpane.add(blueLeopard, row + 1, col);
                }
                if (array[col][row] == -7) {
                    blueWolf.setCol(col);
                    blueWolf.setRow(row);
                    GridPane.setConstraints(blueWolf, row + 1, col);
                    gridpane.add(blueWolf, row + 1, col);
                }
                if (array[col][row] == -8) {
                    blueEle.setCol(col);
                    blueEle.setRow(row);
                    GridPane.setConstraints(blueEle, row + 1, col);
                    gridpane.add(blueEle, row + 1, col);
                }
            }
        }
    }

    public void setPriBoard(){
        array = new int[][]{{1,0,0,0,0,0,2},
                {0,3,0,0,0,4,0},
                {5,0,6,0,7,0,8},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {-8,0,-7,0,-6,0,-5},
                {0,-4,0,0,0,-3,0},
                {-2,0,0,0,0,0,-1},
        };
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}





