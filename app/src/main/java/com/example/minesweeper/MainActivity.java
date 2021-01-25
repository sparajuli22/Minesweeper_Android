package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int _rows = 9;
    int _columns = 9;
    int _mines = 12;
    Button[][] buttons = new Button[_rows][_columns];
    MineBoard board = new MineBoard(_rows,_columns,_mines);
    Button restart;
    boolean gameOver;
    int squaresLeft = _rows * _columns - _mines;

    TableLayout boardViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBoard();

        restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardViewer.removeAllViews();
                board = new MineBoard(_rows,_columns, _mines);
                setupBoard();
                squaresLeft = _rows * _columns - _mines;
                board.hideAll();
            }
        });


    }

    private void setupBoard(){
        boardViewer = (TableLayout)findViewById(R.id.MineField);
        gameOver = false;

        TextView t = (TextView)findViewById(R.id.Header);
        t.setText("New Game");
        for (int i = 0; i < _rows; i++){
            TableRow newRow = new TableRow(this);
            TableLayout.LayoutParams y = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
            y.weight = 1;
            newRow.setLayoutParams(y);
            for (int j = 0; j < _columns; j++){
                buttons[i][j] = new Button(this);
                TableRow.LayoutParams x = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                x.weight = 1;
                buttons[i][j].setLayoutParams(x);
                buttons[i][j].setText(" ");
                buttons[i][j].setTypeface(Typeface.DEFAULT_BOLD);
                buttons[i][j].setBackgroundResource(R.drawable.squarebtn);
                //buttons[i][j].setPadding(2,2,2,2);
                buttons[i][j].setEnabled(true);
                int finalRow = i;
                int finalColumn = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (squaresLeft == _rows * _columns - _mines){
                            board.placeMines(finalRow, finalColumn);
                        }
                        reveal(finalRow, finalColumn);
                        endIfMine(finalRow, finalColumn);
                        checkIfWon();
                    }
                });
                newRow.addView(buttons[i][j]);
            }
            boardViewer.addView(newRow);
        }
    }

    private void reveal(int row, int column) {
        if ((row < 0 || column < 0 || row >= 9|| column >= 9)) {
            return;
        }
        if (!board.getBoard()[row][column].getReveal()) {
            int squareValue = board.getBoard()[row][column].getMineNumber();
            board.revealMine(row, column);
            String str;
            switch (squareValue) {
                case 0:
                    str = " ";
                    break;
                default:
                    str = String.valueOf(squareValue);
                    if (squareValue == 1) {
                        buttons[row][column].setTextColor(Color.BLACK);
                    } else if (squareValue == 2) {
                        buttons[row][column].setTextColor(Color.GREEN);
                    } else {
                        buttons[row][column].setTextColor(Color.MAGENTA);
                    }
            }
            if (board.getBoard()[row][column].hasMine()) {
                str = "X";
                buttons[row][column].setTextColor(Color.RED);
            }
            buttons[row][column].setBackgroundResource(R.drawable.pressedbtn);
            buttons[row][column].setText(str);
            squaresLeft--;
            TextView t = (TextView) findViewById(R.id.Header);
            t.setText(String.format("%d Squares left", squaresLeft));


            if (squareValue == 0) {
                reveal(row + 1, column);
                reveal(row, column + 1);
                reveal(row + 1, column + 1);
                reveal(row - 1, column + 1);
                reveal(row , column - 1);
                reveal(row - 1, column - 1);
                reveal(row -1, column);

            } else {
                return;
            }
        }
    }

    private void checkIfWon(){
        if (squaresLeft == 0){
            TextView t = (TextView) findViewById(R.id.Header);
            t.setText(String.format("You won !!!!!!"));
        }
    }

    private void endIfMine(int row, int column){
        if(board.getBoard()[row][column].hasMine()){
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(board.getBoard()[i][j].hasMine()){
                        reveal(i , j);
                    }
                }
            }


            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
            TextView t = (TextView)findViewById(R.id.Header);
            t.setText("Game Over");
        }



    }


}