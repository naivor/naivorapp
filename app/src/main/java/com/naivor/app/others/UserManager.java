package com.naivor.app.others;


import com.naivor.app.common.model.User;

/**
 * 用户管理器,保存用户的信息
 * <p>
 * Created by tianlai on 17-3-23.
 */

public class UserManager {

    private static UserManager manager;

    private User user;

    private UserManager() {
        user = User.Builder().build();
    }

    public static UserManager get() {
        if (manager == null) {
            synchronized (UserManager.class) {
                if (manager == null) {
                    manager = new UserManager();
                }
            }
        }

        return manager;
    }

    public User getUser() {
        return user;
    }

    public void update(User newUser) {
        this.user = newUser;
    }
}
