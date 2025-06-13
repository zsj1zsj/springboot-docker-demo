package org.example.model;

import java.util.Date;

public class TestModel {
    private int age;
    private Date birth;
    private String name;
    private boolean good;
    private long times;
    // 为了测试自定义Dept编辑器，这里也需要一个Dept属性
    private Dept dept;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Date getBirth() {
        return birth;
    }
    public void setBirth(Date birth) {
        this.birth = birth;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isGood() {
        return good;
    }
    public void setGood(boolean good) {
        this.good = good;
    }
    public long getTimes() {
        return times;
    }
    public void setTimes(long times) {
        this.times = times;
    }
    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    @Override
    public String toString() {
        return "TestModel{" +
                "age=" + age +
                ", birth=" + birth +
                ", name='" + name + '\'' +
                ", good=" + good +
                ", times=" + times +
                ", dept=" + dept +
                '}';
    }
}
