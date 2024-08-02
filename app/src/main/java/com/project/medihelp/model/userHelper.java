package com.project.medihelp.model;

public class userHelper {
  private String  Name,Designation,Aera_of_study,Email,Phone,Adress,curentUID,picurl,Degree,Chamber;

    public userHelper() {
    }


    public userHelper(String name, String designation, String aera_of_study, String email, String phone, String adress, String curentUID, String picurl,String Degree, String Chamber) {
        Name = name;
        Designation = designation;
        Aera_of_study = aera_of_study;
        Email = email;
        Phone = phone;
        Adress = adress;
        this.curentUID = curentUID;
        this.picurl = picurl;
        this.Degree = Degree;
        this.Chamber = Chamber;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getChamber() {
        return Chamber;
    }

    public void setChamber(String chamber) {
        Chamber = chamber;
    }

    public String getCurentUID() {
        return curentUID;
    }

    public void setCurentUID(String curentUID) {
        this.curentUID = curentUID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAera_of_study() {
        return Aera_of_study;
    }

    public void setAera_of_study(String aera_of_study) {
        Aera_of_study = aera_of_study;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
