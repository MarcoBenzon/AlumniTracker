package com.example.alumnitr;

public class GraduatesModel {

    String Fullname, Email, PhoneNumber, isUser;
    public GraduatesModel(){}

    public GraduatesModel(String Fullname, String Email, String PhoneNumber, String isUser){
        this.isUser = isUser;
        this.Fullname = Fullname;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }
}
