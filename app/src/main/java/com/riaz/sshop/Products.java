package com.riaz.sshop;

public class Products {


    public String uid,productimage,productprice,productdescription,productquantity,stddept,productname;

   public Products(){

   }

    public Products(String uid, String productimage, String productprice, String productdescription, String productquantity, String stddept,String productname) {
        this.uid = uid;
        this.productimage = productimage;
        this.productprice = productprice;
        this.productname = productname;
        this.productdescription = productdescription;
        this.productquantity = productquantity;
        this.stddept = stddept;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(String productquantity) {
        this.productquantity = productquantity;
    }

    public String getStddept() {
        return stddept;
    }

    public void setStddept(String stddept) {
        this.stddept = stddept;
    }
}
