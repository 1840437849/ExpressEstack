package com.kaikeba.bean;

import java.util.ArrayList;
import java.util.List;

public class ResultData<T> {
    //每次查询的数据集合，比如我们要看第一页，那么就存第一页的数据，第二页就放第二页的数据
    private List<T> rows=new ArrayList<>();
    //总数量（就是总条数）
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
