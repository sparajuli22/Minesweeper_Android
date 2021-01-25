package com.example.minesweeper;

public class Square {
    
    int mineNumber;
    boolean isRevealed;
    boolean isFlagged;
    int[] location = {0 , 0};
    // initial Constructor
    public Square()
    {
        mineNumber = 0;
        isRevealed = false;
        isFlagged = false;
    }
    // constructor 
    public Square(int row, int column){
        super();
        location[0] = row;
        location[1] = column;
    }
    // a boolean function to see if the mine is revealed 
    public boolean getReveal(){
        return isRevealed;
    }
    // if clicked the mine will be revealed
    public void reveal(){
        isRevealed = true;
    }
    // getter function
    public int getMineNumber(){
        return mineNumber;
    }
    // setter function
    public void setMineNumber(int n){
        mineNumber = n;
    }
    // adds the mine Number
    public void addMine(){
        mineNumber = 9;
    }

    public boolean hasMine(){
        if (mineNumber == 9){
            return true;
        }
        return false;
    }
    // boolean function for flags
    public void flag(){
        isFlagged = true;
    }
    // boolean function for deFLag
    public void deFlag(){
        isFlagged = false;
    }
    // getter function for location
    public int[] getLocation(){
        return location;
    }

    public void hide() { isRevealed = false;    }
}
