package com.project.medihelp.model;

public class PsychiatristHelper {
    private String  Name,Specialist,MobileNumber,Email,Adrees,curentUID,Pic,Degree,Chamber;


    public PsychiatristHelper() {
    }
    public PsychiatristHelper(String name, String Specialist, String mobileNumber, String Email, String Adrees, String curentUID, String pic, String Degree, String Chamber) {
        Name = name;
        this.Specialist = Specialist;
        MobileNumber = mobileNumber;
        this.Email = Email;
        this.Adrees = Adrees;
        this.curentUID = curentUID;
        Pic = pic;
        this.Degree = Degree;
        this.Chamber = Chamber;
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

    public String getAdrees() {
        return Adrees;
    }

    public void setAdrees(String adrees) {
        Adrees = adrees;
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



    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }


    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }
}
