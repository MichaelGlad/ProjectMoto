package net.glm.motohelp;

import java.util.List;

/**
 * Created by Michael on 10/09/2017.
 */

public class User {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String motorbikeKind;
    private Boolean isBringFuel,isRepairFlatTire,isRepairChain,isRepairEngine,isRepairAnotherProblems;
    private Integer id;




    public User(String fullName, String phoneNumber, String email, String motorbikeKind,
                Boolean isBringFuel, Boolean isRepairFlatTire, Boolean isRepairChain, Boolean isRepairEngine, Boolean isRepairAnotherProblems) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.motorbikeKind = motorbikeKind;
        this.isBringFuel = isBringFuel;
        this.isRepairFlatTire = isRepairFlatTire;
        this.isRepairChain = isRepairChain;
        this.isRepairEngine = isRepairEngine;
        this.isRepairAnotherProblems = isRepairAnotherProblems;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String lastName) {
        this.fullName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotorbikeKind() {
        return motorbikeKind;
    }

    public void setMotorbikeKind(String motorbikeKind) {
        this.motorbikeKind = motorbikeKind;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
