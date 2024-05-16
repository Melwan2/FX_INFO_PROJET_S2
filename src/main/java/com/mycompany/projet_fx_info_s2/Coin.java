package com.mycompany.projet_fx_info_s2;



public class Coin {
 
    private double ori_x;
    private double ori_y;
    
    Coin( double ox, double oy) {
      
        this.ori_x = ox;
        this.ori_y = oy;
    }
    
       

    public double getOri_x() {
        return ori_x;
    }

    public void setOri_x(double ori_x) {
        this.ori_x = ori_x;
    }

    public double getOri_y() {
        return ori_y;
    }

    public void setOri_y(double ori_y) {
        this.ori_y = ori_y;
    }

  

    @Override
    public String toString() {
        return "Coin [ ori_x=" + ori_x + ", ori_y=" + ori_y + "]";
    }

    
}