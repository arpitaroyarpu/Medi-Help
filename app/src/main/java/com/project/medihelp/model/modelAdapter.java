package com.project.medihelp.model;

public class modelAdapter {
   private String adress,chamber,degree,email,name,phone,pic,specialist;

    public modelAdapter() {
    }

    public modelAdapter(String adress, String chamber, String degree, String email, String name, String phone, String pic, String specialist) {
        this.adress = adress;
        this.chamber = chamber;
        this.degree = degree;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.pic = pic;
        this.specialist = specialist;
    }


    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}