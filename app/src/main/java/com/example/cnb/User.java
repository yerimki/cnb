package com.example.cnb;

import java.io.Serializable;

public class User implements Serializable {
    public String local; // field2
    public String name; // field4
    public String content;// field5
    public String type;// field6
    public String field;// field7
    public String owner; // field8
    public String pnum; // field9
    public String address;// field11
    public String cite;// field12

//    public User(String local, String name, String content, String type, String category, String owner, String pnum, String address, String cite) {
//        this.local = local;
//        this.name = name;
//        this.content = content;
//        this.type = type;
//        this.category = category;
//        this.owner = owner;
//        this.pnum = pnum;
//        this.address = address;
//        this.cite = cite;
//    }
//
//    public User() {
//
//    }

    public String getLocal() {
        return local;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getOwner() {
        return owner;
    }

    public String getPnum() {
        return pnum;
    }

    public String getAddress() {
        return address;
    }

    public String getCite() {
        return cite;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

}
