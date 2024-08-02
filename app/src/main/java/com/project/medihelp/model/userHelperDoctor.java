package com.project.medihelp.model;

public class userHelperDoctor {
    String Name,Specialist,Email,Phone,Adress,curentUID,pic,Degree,Chamber;


    public userHelperDoctor(String name, String specialist, String email, String phone, String adress, String curentUID, String pic ,String Degree, String Chamber) {
        Name = name;
        Specialist = specialist;
        Email = email;
        Phone = phone;
        Adress = adress;
        this.curentUID = curentUID;
        this.pic = pic;
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

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
