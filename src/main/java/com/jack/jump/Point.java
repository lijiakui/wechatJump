package com.jack.jump;

/**
 * Created by Jack on 2018/1/2.
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public double getDistance(Point p){
        double _x = Math.abs(this.x - p.x);
        double _y = Math.abs(this.y - p.y);
        return Math.sqrt(_x*_x+_y*_y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
