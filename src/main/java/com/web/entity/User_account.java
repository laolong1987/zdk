package com.web.entity;

/**
 * Created by gaoyang on 2016/5/5.
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
@Table(name = "user_account")
public class User_account{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="user_id")
    private int user_id;
    @Column(name ="status")
    private int status;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="total_money")
    private int total_money;
    @Column(name ="current_money")
    private int current_money;
    @Column(name ="task_money")
    private int task_money;
    @Column(name ="master_money")
    private int master_money;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setUser_id(int user_id){
        this.user_id=user_id;
    }
    public int getUser_id(){
        return this.user_id;
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

    public void setTotal_money(int total_money){
        this.total_money=total_money;
    }
    public int getTotal_money(){
        return this.total_money;
    }

    public void setCurrent_money(int current_money){
        this.current_money=current_money;
    }
    public int getCurrent_money(){
        return this.current_money;
    }

    public void setTask_money(int task_money){
        this.task_money=task_money;
    }
    public int getTask_money(){
        return this.task_money;
    }

    public void setMaster_money(int master_money){
        this.master_money=master_money;
    }
    public int getMaster_money(){
        return this.master_money;
    }

}
