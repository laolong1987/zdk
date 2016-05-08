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
@Table(name = "user_info")
public class User_info{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="nick_name")
    private String nick_name;
    @Column(name ="email")
    private String email;
    @Column(name ="real_name")
    private String real_name;
    @Column(name ="birthday")
    private Date birthday;
    @Column(name ="referee_id")
    private int referee_id;
    @Column(name ="create_time")
    private Date create_time;
    @Column(name ="update_time")
    private Date update_time;
    @Column(name ="userid")
    private int userid;
    @Column(name ="remark")
    private String remark;
    @Column(name ="sex")
    private int sex;
    @Column(name ="province")
    private String province;
    @Column(name ="city")
    private String city;
    @Column(name ="country")
    private String country;
    @Column(name ="headimgurl")
    private String headimgurl;
    @Column(name ="privilege")
    private String privilege;
    @Column(name ="unionid")
    private String unionid;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setNick_name(String nick_name){
        this.nick_name=nick_name;
    }
    public String getNick_name(){
        return this.nick_name;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setReal_name(String real_name){
        this.real_name=real_name;
    }
    public String getReal_name(){
        return this.real_name;
    }

    public void setBirthday(Date birthday){
        this.birthday=birthday;
    }
    public Date getBirthday(){
        return this.birthday;
    }

    public void setReferee_id(int referee_id){
        this.referee_id=referee_id;
    }
    public int getReferee_id(){
        return this.referee_id;
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

    public void setUserid(int userid){
        this.userid=userid;
    }
    public int getUserid(){
        return this.userid;
    }

    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getRemark(){
        return this.remark;
    }

    public void setSex(int sex){
        this.sex=sex;
    }
    public int getSex(){
        return this.sex;
    }

    public void setProvince(String province){
        this.province=province;
    }
    public String getProvince(){
        return this.province;
    }

    public void setCity(String city){
        this.city=city;
    }
    public String getCity(){
        return this.city;
    }

    public void setCountry(String country){
        this.country=country;
    }
    public String getCountry(){
        return this.country;
    }

    public void setHeadimgurl(String headimgurl){
        this.headimgurl=headimgurl;
    }
    public String getHeadimgurl(){
        return this.headimgurl;
    }

    public void setPrivilege(String privilege){
        this.privilege=privilege;
    }
    public String getPrivilege(){
        return this.privilege;
    }

    public void setUnionid(String unionid){
        this.unionid=unionid;
    }
    public String getUnionid(){
        return this.unionid;
    }

}