package com.example.nimish.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
public class myButton extends AppCompatButton{

    public   int index1;
    public   int index2;
    public   int value;
    public   boolean isRevealed;
    public int neighbour1[];
    public int neighbour2[];
    public int ncount;
    public int toggle;
    public  int arr1[];
    public int arr2[];
    public int arrcount;
    public boolean isFlaged;

    public myButton (Context context){
        super(context);
        value = 0;
        isRevealed = false;
        neighbour1 = new int[100];
        neighbour2 = new int[100];
        arr1 = new int [100];
        arr2 = new int[100];
        arrcount = 0;
        ncount = 0;
        setEnabled(true);
        toggle = 0;
        isFlaged = false;
    }

    public void setID(int index1 , int index2){
        this.index1 = index1;
        this.index2 = index2;
    }

    public int getIndex1(){
        return this.index1;
    }

    public int getIndex2(){
        return this.index2;
    }

    public void setVal(int value){
        this.value = value;
    }

    public int getVal(){
        return this.value;
    }

    public boolean visited(){
        return isRevealed;
    }

    public int[] getNeighbours1() {
        return neighbour1;
    }

    public int[] getNeighbours2(){
        return neighbour2;
    }

    public int getNcount(){
        return ncount;
    }

    public void setNcount(int ncount){
        this.ncount=ncount;
    }
    public int [] getArr1(){
        return arr1;
    }
    public int[] getArr2() {
        return arr2;
    }
    public void setArrcount(int arrcountt){
        this.arrcount = arrcountt;
    }
}
