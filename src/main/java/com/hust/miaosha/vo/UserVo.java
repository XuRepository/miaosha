package com.hust.miaosha.vo;

import com.hust.miaosha.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: miaosha1
 * @description:
 * @author: XuJY
 * @create: 2022-03-09 10:33
 **/
public class UserVo {
    @NotNull
//    @IsMobile//自定义的validation
//    @Length(min=11,max=11)
    private Long id;

//    @NotNull
//    @IsMobile
//    private String mobile;

    private String nickname;

    @NotNull
    @Length(min = 6)
    private String password;

    private String salt;
    private Date registerDate;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", registerDate=" + registerDate +
                ", address='" + address + '\'' +
                '}';
    }
}
