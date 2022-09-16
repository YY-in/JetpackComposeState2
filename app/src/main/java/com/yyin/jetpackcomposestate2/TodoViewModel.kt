package com.yyin.jetpackcomposestate2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//用来处理当前类前端的增删改查
class TodoViewModel : ViewModel() {
    // 创建Livedata，在给定生命周期中观察的数据持有类
    private val _todoItem = MutableLiveData(listOf<TodoItem>())

    //设置只读变量，保证访问的安全
    val todoItems: LiveData<List<TodoItem>> = _todoItem

    fun addItem(item: TodoItem) {
        //只有重新使得_todoItem.value指向新的集合，则compose才会认为状态发生改变
        //在原来的所指向的集合中增改数据，状态不变
       _todoItem.value = _todoItem.value!! + listOf(item)
    }

    fun removeItem(item: TodoItem) {
        //toMutableList()转换成一个可变的集合
        //also可用于不更改对象的其他操作，例如记录或者打印调试信息
        _todoItem.value = _todoItem.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}
