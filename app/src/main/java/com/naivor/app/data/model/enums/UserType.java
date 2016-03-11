package com.naivor.app.data.model.enums;

/**
 * 用户的类型，主要有美容师，美容院，店长（指总店），客户
 * <p>
 * Created by tianlai on 16-3-10.
 */
public enum UserType {
    PERSON("beautician"), SHOP("beauty_parlor"), MANAGER("brand"), CLIENT("client");

    private final String value;

    private UserType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    public static UserType getType(String type) {
        if (type != null) {

            switch (type) {
                case "beautician":

                    return UserType.PERSON;
                case "beauty_parlor":

                    return UserType.SHOP;
                case "brand":

                    return UserType.MANAGER;

                default:
                    break;
            }
        }
        return null;

    }
}