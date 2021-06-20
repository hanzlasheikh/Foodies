package com.riaz.sshop;

public class Orders {


    public String opcode,number,name,address;

    public Orders(){

    }

    public Orders(String opcode, String number, String name, String address) {
        this.opcode = opcode;
        this.number = number;
        this.name = name;
        this.address = address;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
