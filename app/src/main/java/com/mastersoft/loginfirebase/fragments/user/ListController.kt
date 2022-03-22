package com.mastersoft.loginfirebase.fragments.user

import com.mastersoft.loginfirebase.data.task.Task
import com.mastersoft.loginfirebase.data.user.User

class ListController {
    var taskList = emptyList<User>()


    fun getItemCount(): Int {
        return  taskList.size
    }

    fun setData(user: List<User>){
        this.taskList = user
    }
}