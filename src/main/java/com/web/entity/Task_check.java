package com.web.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_check")
public class Task_check{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="type")
    private int type;
    @Column(name ="imgsize")
    private int imgsize;
    @Column(name ="name")
    private String name;
    @Column(name ="minlength")
    private int minlength;
    @Column(name ="maxlength")
    private int maxlength;
    @Column(name ="regular")
    private String regular;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="taskid")
    private int taskid;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }

    public void setImgsize(int imgsize){
        this.imgsize=imgsize;
    }
    public int getImgsize(){
        return this.imgsize;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setMinlength(int minlength){
        this.minlength=minlength;
    }
    public int getMinlength(){
        return this.minlength;
    }

    public void setMaxlength(int maxlength){
        this.maxlength=maxlength;
    }
    public int getMaxlength(){
        return this.maxlength;
    }

    public void setRegular(String regular){
        this.regular=regular;
    }
    public String getRegular(){
        return this.regular;
    }

    public void setCreate_time(Date create_time){
        this.create_time=create_time;
    }
    public Date getCreate_time(){
        return this.create_time;
    }

    public void setUpdate_time(Date update_time){
        this.update_time=update_time;
    }
    public Date getUpdate_time(){
        return this.update_time;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }
}