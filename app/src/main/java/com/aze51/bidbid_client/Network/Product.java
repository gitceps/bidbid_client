package com.aze51.bidbid_client.Network;


/**
 * Created by Leekh on 2016-06-30.
 */
public class Product {


    public int register_id;
    public String product_name;
    public String store_name;
    public String product_description;
    public String product_img;
    public int register_minprice;
    public int deal_price;
    public int rtime;

    public int deal_count;

    public int favorite;
   // public String register_stime;
   // public String register_ftime;
    public String register_stime;
    public String register_ftime;
    public String user_phonenum;

    public String store_img;
    public String store_menu;
    public String store_price;
    public String store_time;
    public String store_year;
    public String store_address;
    public String store_parking;
    public String store_number;

    public int product_minprice;
    public int product_maxprice;
    public int register_maxprice;
    //public Date register_stime = new Date();
    //public Date register_ftime;
    public int register_numpeople;


    //public long product_time_start;
    //public long product_time_finish;
    //경매 상황(예정, 진행중, 마감)(product_bid_status)
    //참여인원(product_num_people)
}
/*
public class Products {
    public List<Product> products;
}
*/