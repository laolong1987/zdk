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
@Table(name = "task")
public class Task{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name ="begintime")
    private Date begintime;
    @Column(name ="endtime")
    private Date endtime;
    @Column(name ="type")
    private int type;
    @Column(name ="status")
    private int status;
    @Column(name ="unitprice")
    private int unitprice;
    @Column(name ="total")
    private int total;
    @Column(name ="keyword")
    private String keyword;
    @Column(name ="description")
    private String description;
    @Column(name ="content")
    private String content;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="logoimg")
    private String logoimg;
    @Column(name ="imgfile")
    private int imgfile;
    @Column(name ="checktype")
    private String checktype;
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

    public void setBegintime(Date begintime){
        this.begintime=begintime;
    }
    public Date getBegintime(){
        return this.begintime;
    }

    public void setEndtime(Date endtime){
        this.endtime=endtime;
    }
    public Date getEndtime(){
        return this.endtime;
    }

    public void setType(int type){
        this.type=type;
    }
    public int getType(){
        return this.type;
    }

    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return this.status;
    }

    public void setUnitprice(int unitprice){
        this.unitprice=unitprice;
    }
    public int getUnitprice(){
        return this.unitprice;
    }

    public void setTotal(int total){
        this.total=total;
    }
    public int getTotal(){
        return this.total;
    }

    public void setKeyword(String keyword){
        this.keyword=keyword;
    }
    public String getKeyword(){
        return this.keyword;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
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

    public void setLogoimg(String logoimg){
        this.logoimg=logoimg;
    }
    public String getLogoimg(){
        return this.logoimg;
    }

    public void setImgfile(int imgfile){
        this.imgfile=imgfile;
    }
    public int getImgfile(){
        return this.imgfile;
    }

    public void setChecktype(String checktype){
        this.checktype=checktype;
    }
    public String getChecktype(){
        return this.checktype;
    }

}