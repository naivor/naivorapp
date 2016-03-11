package com.naivor.app.data.model;

import com.naivor.app.data.model.enums.UserType;

/**
 * 用户类
 * <p>
 * Created by tianlai on 16-3-4.
 */
public class User {
    private int id; //用户id

    private String name; //用户名

    private String headIcon; //用户头像

    private long phone; //用户电话

    private UserType userType; //用户类型

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", phone=" + phone +
                ", userType=" + userType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (phone != user.phone) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (headIcon != null ? !headIcon.equals(user.headIcon) : user.headIcon != null) return false;
        return userType == user.userType;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (headIcon != null ? headIcon.hashCode() : 0);
        result = 31 * result + (int) (phone ^ (phone >>> 32));
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }
}
