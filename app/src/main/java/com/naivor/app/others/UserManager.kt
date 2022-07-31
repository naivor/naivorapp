package com.naivor.app.others

import com.naivor.app.embedder.repo.UserRepo
import com.naivor.app.embedder.repo.local.bean.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull

/**
 * 用户管理器,保存用户的信息
 *
 *
 * Created by tianlai on 17-3-23.
 */
object UserManager {
    private lateinit var userRepo: UserRepo

    private var user: User? = null

    fun getUser(): User? {
        return user
    }

    fun update(newUser: User) {
        user = newUser
    }


    //初始化
    suspend fun init(repo: UserRepo) {
        userRepo = repo
        update()
    }

    suspend fun update() {
        user = userRepo.local!!.optUsers().firstOrNull()?.firstOrNull()
    }
}