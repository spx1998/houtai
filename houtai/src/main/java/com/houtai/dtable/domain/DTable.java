package com.houtai.dtable.domain;

import java.io.Serializable;

public class DTable implements Serializable {

    private static final long serialVersionUID = 56865822973296356L;
    private int id;
    private int num;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
