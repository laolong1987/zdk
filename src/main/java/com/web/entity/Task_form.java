package com.web.entity;

/**
 * Created by gaoyang on 2016/5/8.
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
@Table(name = "task_form")
public class Task_form{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="task_id")
    private int task_id;
    @Column(name ="user_id")
    private int user_id;
    @Column(name ="type")
    private int type;
    @Column(name ="value")
    private String value;
    @Column(name ="check_id")
    private int check_id;
    @Column(name ="createtime")
    private Date createtime;
    @Column(name ="updatetime")
    private Date updatetime;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setTask_id(int task_id){
        this.task_id=task_id;
    }
    public int getTask_id(){
        return this.task_id;
    }

    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public int getUser_id(){
        return this.user_id;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }

    public void setValue(String value){
        this.value=value;
    }
    public String getValue(){
        return this.value;
    }

    public void setCheck_id(int check_id){
        this.check_id=check_id;
    }
    public int getCheck_id(){
        return this.check_id;
    }

    public void setCreatetime(Date createtime){
        this.createtime=createtime;
    }
    public Date getCreatetime(){
        return this.createtime;
    }

    public void setUpdatetime(Date updatetime){
        this.updatetime=updatetime;
    }
    public Date getUpdatetime(){
        return this.updatetime;
    }

}
