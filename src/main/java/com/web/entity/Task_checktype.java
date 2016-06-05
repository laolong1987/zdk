package com.web.entity;

/**
 * Created by gaoyang on 2016/6/2.
 */
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_checktype")
public class Task_checktype{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name ="max")
    private int max;
    @Column(name ="min")
    private int min;
    @Column(name ="status")
    private int status;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="regular")
    private String regular;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setMax(int max){
        this.max=max;
    }
    public int getMax(){
        return this.max;
    }

    public void setMin(int min){
        this.min=min;
    }
    public int getMin(){
        return this.min;
    }

    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return this.status;
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

    public void setRegular(String regular){
        this.regular=regular;
    }
    public String getRegular(){
        return this.regular;
    }

}