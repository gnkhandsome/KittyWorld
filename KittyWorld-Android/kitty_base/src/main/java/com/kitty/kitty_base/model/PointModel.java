package com.kitty.kitty_base.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointModel {
    public  int  id;
    public  String  address_string;
    public  String  desc;
    public  String  address_icon;
    public  int  kilometers;
    public  int  gift_amount;
    public  float  gift_percent;
    public  int  X;
    public  int  Y;
    public  int  total_kilometers;
}
