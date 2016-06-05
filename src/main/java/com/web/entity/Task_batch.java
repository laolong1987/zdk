package com.web.entity;

/**
 * Created by gaoyang on 2016/5/25.
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
@Table(name = "task_batch")
public class Task_batch{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name ="phone")
    private String phone;
    @Column(name ="teamname")
    private String teamname;
    @Column(name ="alipay")
    private String alipay;
    @Column(name ="qq")
    private String qq;
    @Column(name ="weixin")
    private String weixin;
    @Column(name ="taskname")
    private String taskname;
    @Column(name ="remark")
    private String remark;
    @Column(name ="filename")
    private String filename;
    @Column(name ="filename2")
    private String filename2;
    @Column(name ="status")
    private int status;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
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

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
    }

    public void setTeamname(String teamname){
        this.teamname=teamname;
    }
    public String getTeamname(){
        return this.teamname;
    }

    public void setAlipay(String alipay){
        this.alipay=alipay;
    }
    public String getAlipay(){
        return this.alipay;
    }

    public void setQq(String qq){
        this.qq=qq;
    }
    public String getQq(){
        return this.qq;
    }

    public void setWeixin(String weixin){
        this.weixin=weixin;
    }
    public String getWeixin(){
        return this.weixin;
    }

    public void setTaskname(String taskname){
        this.taskname=taskname;
    }
    public String getTaskname(){
        return this.taskname;
    }

    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setFilename(String filename){
        this.filename=filename;
    }
    public String getFilename(){
        return this.filename;
    }

    public void setFilename2(String filename2){
        this.filename2=filename2;
    }
    public String getFilename2(){
        return this.filename2;
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

}
