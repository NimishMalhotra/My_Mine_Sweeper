package com.example.nimish.minesweeper;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.*;
import java.util.ArrayList;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    public ArrayList<LinearLayout> rows;
    public myButton [][] board;
    public int SIZE = 10;
    LinearLayout rootlayout;
    int [] arr1 = {-1,-1,-1,0,0,1,1,1};
    int [] arr2 = {-1,0,1,-1,1,-1,0,1};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootlayout = findViewById(R.id.rootLayout);
        setupboard();
    }
    public void setupboard() {
        rootlayout.removeAllViews();
        rows = new ArrayList<>();
        board = new myButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            rootlayout.addView(linearLayout);
            rows.add(linearLayout);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                final myButton myButton = new myButton(this);
                myButton.setID(i,j);
                myButton.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        myButton myButton = (myButton)view;
                        if(myButton.isFlaged==false){
                            myButton.setText("F");
                            myButton.setTextColor(Color.RED);
                            myButton.isFlaged=true;
                        }else{
                            myButton.setText(null);
                            myButton.isFlaged=false;
                        }
                        return true;
                    }
                });
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                myButton.setLayoutParams(layoutParams);
                myButton.setOnClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(myButton);
                board[i][j] = myButton;
            }
        }
        Randomise();
    }
    public void Randomise(){
            Random random = new Random();
            int num = SIZE*2;
            int i=0;
            while(i<num){
                int index1 = random.nextInt(SIZE);
                int index2 = random.nextInt(SIZE);
                myButton myButton = board[index1][index2];
                myButton.setVal(-1);
                i++;
            }
            Arrange();
    }
    public void Arrange() {
        for(int a=0;a<SIZE;a++){
            for(int b=0;b<SIZE;b++){
                myButton myButton = board[a][b];
                if(myButton.getVal()==-1){
                    setNeighbours(myButton);
                }
            }
        }
        zerosetting();
    }
    public void zerosetting(){
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                myButton myButton = board[i][j];
                if(myButton.getVal()==0){
                    set0neighbours(myButton);
                }
            }
        }
    }
    public void set0neighbours(myButton myButton){
        int num1 = myButton.getIndex1();
        int num2 = myButton.getIndex2();
        for(int i=0;i<8;i++){
            int neighbour_1 = num1 + arr1[i];
            int neighbour_2 = num2 + arr2[i];
            if(checkBound(neighbour_1,neighbour_2)){
                int count = myButton.arrcount;
                int [] array1 = myButton.getArr1();
                int [] array2 = myButton.getArr2();
                array1[count] = neighbour_1;
                array2[count] = neighbour_2;
                count++;
                myButton.setArrcount(count);
            }
        }
    }
    public  void setNeighbours(myButton mybutton){
        int num1 = mybutton.getIndex1();
        int num2 = mybutton.getIndex2();
        for(int i=0;i<8;i++){
            int neighbour_1 = num1+arr1[i];
            int neighbour_2 = num2+arr2[i];
            if(checkBound(neighbour_1,neighbour_2)){
                myButton button = board[neighbour_1][neighbour_2];
                if(button.getVal()==-1){
                    continue;
                }
                int count = mybutton.getNcount();
                int [] neighbour1 = mybutton.getNeighbours1();
                int [] neighbour2 = mybutton.getNeighbours2();
                neighbour1[count] = neighbour_1;
                neighbour2[count] = neighbour_2;
                count++;
                mybutton.setNcount(count);
                int x = button.getVal();
                x++;
                button.setVal(x);
            }
        }
    }
    public boolean checkBound(int a , int b){
        if(a>=0 && a<SIZE && b>=0 && b<SIZE){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onClick(View view) {
        myButton myButton = (myButton)view;
        if(myButton.isFlaged==false){
            reveal(myButton);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.resetGame){
            setupboard();
        }else if (id==R.id.size5){
            SIZE = 5;
            setupboard();
        }else if (id==R.id.size10){
            SIZE = 10;
            setupboard();
        }else if (id == R.id.size15){
            SIZE = 12;
            setupboard();
        }
        return super.onOptionsItemSelected(item);
    }
    public void reveal(myButton button){
        if(button.visited()){
            return;
        }
        if(button.getVal()!=0 && button.getVal()!=-1){
            button.setText(Integer.toString(button.getVal()));
            button.setTextColor(Color.BLUE);
            button.isRevealed = true;
            button.setEnabled(false);
        }else if(button.getVal()==-1){
            button.isRevealed = true;
            button.setEnabled(false);
            for(int i=0;i<SIZE;i++){
                for(int j=0;j<SIZE;j++){
                    myButton myButton = board[i][j];
                    if(myButton.getVal()==-1) {
                        myButton.setBackgroundResource(R.drawable.bomb);
                    }else if (myButton.getVal()==0){
                        myButton.setBackgroundResource(android.R.drawable.btn_default);
                    }else{
                        myButton.setText(Integer.toString(myButton.getVal()));
                        myButton.setTextColor(Color.MAGENTA);
                    }
                    myButton.isRevealed = true;
                    myButton.setEnabled(false);
                }
            }
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
            return;
        }else if(button.getVal()==0) {
            button.isRevealed=true;
            button.setEnabled(false);
            button.setBackgroundResource(android.R.drawable.btn_default);
            int [] list1 = button.getArr1();
            int [] list2 = button.getArr2();
            int count = button.arrcount;
            for(int i=0;i<count;i++){
                int num1 = list1[i];
                int num2 = list2[i];
                myButton z = board[num1][num2];
                if(!z.visited()){
                    reveal(z);
                }
            }
        }
        checkGameStatus();
    }
    public void checkGameStatus(){
        int indicator = 0 ;
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                myButton myButton = board[i][j];
                if(myButton.getVal()!=-1){
                    if(myButton.isRevealed==false){
                        indicator++;
                        break;
                    }
                }
            }
        }
        if(indicator==0){
            Toast.makeText(this, "You Won The Game", Toast.LENGTH_LONG).show();
            disableAll();
        }else{
            return;
        }

    }
    public void disableAll(){
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                myButton myButton = board[i][j];
                myButton.setEnabled(false);
            }
        }
    }
}