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
@Table(name = "user")
public class User{
 @Id
 @Basic(optional = false)
 @Column(name = "id")
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;
 @Column(name ="username")
 private String username;
 @Column(name ="pwd")
 private String pwd;
 @Column(name ="name")
 private String name;
 @Column(name ="specialty")
 private String specialty;
 @Column(name ="email")
 private String email;
 @Column(name ="address")
 private String address;
 @Column(name ="position")
 private String position;
 @Column(name ="phone")
 private String phone;
 @Column(name ="role")
 private int role;
 @Column(name ="state")
 private int state;
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

 public void setUsername(String username){
   this.username=username;
 }
 public String getUsername(){
   return this.username;
 }

 public void setPwd(String pwd){
   this.pwd=pwd;
 }
 public String getPwd(){
   return this.pwd;
 }

 public void setName(String name){
   this.name=name;
 }
 public String getName(){
   return this.name;
 }

 public void setSpecialty(String specialty){
   this.specialty=specialty;
 }
 public String getSpecialty(){
   return this.specialty;
 }

 public void setEmail(String email){
   this.email=email;
 }
 public String getEmail(){
   return this.email;
 }

 public void setAddress(String address){
   this.address=address;
 }
 public String getAddress(){
   return this.address;
 }

 public void setPosition(String position){
   this.position=position;
 }
 public String getPosition(){
   return this.position;
 }

 public void setPhone(String phone){
   this.phone=phone;
 }
 public String getPhone(){
   return this.phone;
 }

 public void setRole(int role){
   this.role=role;
 }
 public int getRole(){
   return this.role;
 }

 public void setState(int state){
   this.state=state;
 }
 public int getState(){
   return this.state;
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
