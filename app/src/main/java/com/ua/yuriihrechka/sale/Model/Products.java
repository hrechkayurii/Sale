package com.ua.yuriihrechka.sale.Model;

public class Products {

    private String mCategory;
    private String mDescription;
    private String mPrice;
    private String mImage;
    private String mName;
    private String mPid;
    private String mDate;
    private String mTime;

    public Products(){

    }

    public Products(String category, String description, String price, String image, String name, String pid, String date, String time) {
        mCategory = category;
        mDescription = description;
        mPrice = price;
        mImage = image;
        mName = name;
        mPid = pid;
        mDate = date;
        mTime = time;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPid() {
        return mPid;
    }

    public void setPid(String pid) {
        mPid = pid;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
