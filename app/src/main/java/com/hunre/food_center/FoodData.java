package com.hunre.food_center;

/**
 * Created by Administrator on 10/01/2017.
 */

public class FoodData {
    public String addr;
    public String des;
    public String name;
    public int price;
    public String urlImg;

    public FoodData( String addr, String des,String name, int price, String urlImg) {
        this.addr = addr;
        this.des = des;
        this.name = name;
        this.price = price;
        this.urlImg = urlImg;
    }

    public FoodData() {
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
