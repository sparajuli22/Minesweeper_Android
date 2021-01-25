package com.example.minesweeper;

import java.util.Random;

public class MineBoard 
{
    // array of mineboards
    Square[][] mineBoard;
    int row;
    int column;
    int mines;
    int noOfClicks;
    // constructor function
    public MineBoard(int row, int column, int mines){

        this.row = row;
        this.column = column;
        this.mines = mines;
        mineBoard = new Square[row][column];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                mineBoard[i][j] = new Square(i,j);
            }
        }

    }

    // randomly palces the mines in the cells
    public void placeMines(int frow, int fcol){
        for(int i =0; i < mines; i++) {
            Random rand = new Random();
            int rm = rand.nextInt(row);
            int cm = rand.nextInt(column);
            if (!(mineBoard[rm][cm].hasMine() || (rm >= frow - 1 && rm <= frow + 1 ) || (cm >= fcol && cm <= fcol ))) {
                mineBoard[rm][cm].addMine();
            }
            else{
                i--;
            }
        }
        // checks if all the cells have mines or a bomb
        checkAllSquares();

    }


    public boolean isValidSquare(int row, int column){
        if (row < 0 || column < 0){
            return false;
        }
        if (row >= this.row || column >= this.column ){
            return false;
        }
        return true;
    }

    public void checkMines(int row, int column){
        // this adds the mines or the bomb until there is no mine woth either one left
        if (mineBoard[row][column].hasMine())
        {
            mineBoard[row][column].addMine();
        }
        else
        // if there are no mines left 
        {
            int count = 0;
            for (int i = row - 1; i <= row + 1; i++){
                for (int j = column - 1; j <= column + 1; j++){
                    if (isValidSquare(i, j)){
                        if (mineBoard[i][j].hasMine()){
                            count++;
                        }
                    }
                }
            }
            mineBoard[row][column].setMineNumber(count);
        }
    }

    // reveals a cell has a mine or a bimb based on the click
    public void revealMine(int row, int column){
        mineBoard[row][column].reveal();
    }

   
    // check all the cells of they have a mine
    public void checkAllSquares() 
    {
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                checkMines(i,j);

            }
        }
    }
    
    // flag the mine with bomb
    public void flagMine(int row, int column){
        mineBoard[row][column].flag();
    }
    // deflag the mine 
    public void deFlagMine(int row, int column){
        mineBoard[row][column].deFlag();
    }
    
    public Square[][] getBoard(){
        return mineBoard;
    }

    public void print() {
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                checkMines(i,j);
                System.out.printf("%d " , mineBoard[i][j].getMineNumber());
            }
            System.out.printf("\n");
        }
    }

    // hides all the mineboard if the game is over
    public void hideAll() {
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                mineBoard[i][j].hide();
            }
        }
    }
}

