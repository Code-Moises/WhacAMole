import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
    //first we need a windows with width and height
    int boardWidth = 600;
    int boardHeight = 650;

    //score
    int score;

    //create the window
    JFrame frame = new JFrame("WhacAMole Game");
    
    //Constructor
    WhacAMole(){
        /*make the frame visible frame.setVisible(true); --> at the end to make sure everything is loaded when visible */
        //set the width and height 
        frame.setSize(boardWidth, boardHeight);
        //center the window
        frame.setLocationRelativeTo(null);
        //not allow to change the size
        frame.setResizable(false);
        //close the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout
        frame.setLayout(new BorderLayout());


        //After creating the Label 
        //give font
        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        //set and center
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        //text
        textLabel.setText("Score: 0");
        //make the background visible
        textLabel.setOpaque(true);

        //After creating the Panel
        //Layout´
        textPanel.setLayout(new BorderLayout());
        //add the textLabel to the textPanel
        textPanel.add(textLabel);
        //add the textPanel to the frame and put it on top
        frame.add(textPanel, BorderLayout.NORTH);

        //board has a GridLayout of 3x3
        boardPanel.setLayout(new GridLayout(3, 3));
        //add it to the frame
        frame.add(boardPanel);

        //after initializing the images
        //load images
        //we have to scale it 
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        

        //set score
        score = 0;

        //after creating the JButton[]
        //create 9 JButtons
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            //not let the rectangule when pressing visible
            tile.setFocusable(false);

            //Increase score or GAME OVER
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource();
                    //increase
                    if (tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score " + Integer.toString(score));
                    }
                    //Game Over
                    else if(tile == currPlantTile){
                        textLabel.setText("GAME OVER: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int j = 0; j < 9; j++) {
                            board[j].setEnabled(false);
                        }
                    }
                }
            });
        }

        //after creating Timer
        setMoleTimer = new Timer(1000, new ActionListener() { //value in miliseconds --> 1000ms = 1seg
            //every 1s it is going to call 
            public void actionPerformed(ActionEvent e){
                if(currMoleTile != null){
                    //if the tile is not empty it is going to remove it and make it no longer visible
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                //random number from 0 to 8
                int num = random.nextInt(9);
                JButton tile = board[num];

                //if tile is occupied by plant, skip tile for this turn
                if (currPlantTile == tile) return;

                //Set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
          public void actionPerformed(ActionEvent e){
            if (currPlantTile != null) {
                currPlantTile.setIcon(null);
                currPlantTile = null;
            }
            int num = random.nextInt(9);
            JButton tile = board[num];

            if(currMoleTile == tile) return;

            currPlantTile = tile;
            currPlantTile.setIcon(plantIcon);
          }  
        });

        //set the timer
        setMoleTimer.start();
        setPlantTimer.start();

        //make the frame visible
        frame.setVisible(true);
    }

    //For the text --> Label
    JLabel textLabel = new JLabel();
    //Panel to hold the label
    JPanel textPanel = new JPanel();

    //boardPanel
    JPanel boardPanel = new JPanel();

    //array to keep on track of all 9 buttons
    JButton[] board = new JButton[9];

    //images
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    //variable to keep track which tile has the mole and plant
    JButton currMoleTile;//current
    JButton currPlantTile; //current

    //place the mole and plant randomly
    Random random = new Random();

    //move with the time
    Timer setMoleTimer;
    Timer setPlantTimer;
}
