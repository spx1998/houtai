package com.houtai.menu.domain;

import java.io.Serializable;

public class Type implements Serializable {
    private static final long serialVersionUID = 7942182560230809046L;
    private  int typeID;
    private  String name;

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
