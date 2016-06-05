package com.web.entity;

/**
 * Created by gaoyang on 2016/5/11.
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
@Table(name = "user_account_detail")
public class User_account_detail{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="user_id")
    private String user_id;
    @Column(name ="status")
    private int status;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="money")
    private int money;
    @Column(name ="type")
    private int type;
    @Column(name ="sourceid")
    private int sourceid;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setUser_id(String user_id){
        this.user_id=user_id;
    }
    public String getUser_id(){
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

    public void setMoney(int money){
        this.money=money;
    }
    public int getMoney(){
        return this.money;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }

    public void setSourceid(int sourceid){
        this.sourceid=sourceid;
    }
    public int getSourceid(){
        return this.sourceid;
    }

}