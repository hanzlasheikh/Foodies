package com.riaz.sshop;

public class Cart {


    public String productdescription,productimage,productname,productprice,productcode;

    public Cart(){

    }

    public Cart(String productdescription, String productimage, String productname, String productprice, String productcode) {
        this.productdescription = productdescription;
        this.productimage = productimage;
        this.productname = productname;
        this.productprice = productprice;
        this.productcode = productcode;
    }


    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
}
