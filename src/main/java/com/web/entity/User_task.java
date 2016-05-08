package com.web.entity;

/**
 * Created by gaoyang on 2016/5/7.
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
@Table(name = "user_task")
public class User_task{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="taskid")
    private int taskid;
    @Column(name ="userid")
    private int userid;
    @Column(name ="status")
    private int status;
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

    public void setTaskid(int taskid){
        this.taskid=taskid;
    }
    public int getTaskid(){
        return this.taskid;
    }

    public void setUserid(int userid){
        this.userid=userid;
    }
    public int getUserid(){
        return this.userid;
    }

    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return this.status;
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