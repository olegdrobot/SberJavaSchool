package model;

import java.io.Serializable;

public class CardNumber implements Serializable {
    private int[] number = new int[4];

    public CardNumber() {

    }

    public CardNumber(int[] number) {
        setNumber(number);
    }

    public int[] getNumber() {
        return number;
    }

    public void setNumber(int[] number) {
        this.number = number;
    }
}
