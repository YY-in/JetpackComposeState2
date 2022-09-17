package com.yyin.jetpackcomposestate2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 状态容器，重组之后内部值仍旧存在
 */
class TodoViewModel : ViewModel() {
    /*
       //创建Livedata，在给定生命周期中观察的数据持有类
       private val _todoItem = MutableLiveData(listOf<TodoItem>())

       //设置只读变量，保证访问的安全
       val todoItems: LiveData<List<TodoItem>> = _todoItem
    */

    //TodoItem集合只读，此时直接创建了一个State对象，只要进程没有被杀死
    //remember用于将状态转移到内存
    var todoItems = mutableStateListOf<TodoItem>()
        private set

    //当前正在编辑的TodoItem的索引位置
    private var currentEditPostion by mutableStateOf(-1)

    //正在编辑的TodoItem的对象
    val currentEditItem: TodoItem?
        //当结果为-1的时候返回一个空，解决溢出问题
        get() = todoItems.getOrNull(currentEditPostion)

    fun addItem(item: TodoItem) {
        /*
        // 只有重新使得_todoItem.value指向新的集合，则compose才会认为状态发生改变
        // 在原来的所指向的集合中增改数据，状态不变
        _todoItem.value = _todoItem.value!! + listOf(item)
        */

        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
    /*
        // toMutableList()转换成一个可变的集合
        // also可用于不更改对象的其他操作，例如记录或者打印调试信息
        _todoItem.value = _todoItem.value!!.toMutableList().also {
            it.remove(item)
        }
    */
        todoItems.remove(item)
        onEditDone()
    }

    fun onEditDone(){
        currentEditPostion = -1
    }

    // 当TodoItem列表中的条目被选中时，传入参数，获取它在列表中的索引位置
    fun onEditItemSelected(item:TodoItem){
        currentEditPostion = todoItems.indexOf(item)
    }

    // TodoItem编辑完成，重新给集合中的TodoItem赋值
    // id属性值不能修改，进行校验
    fun onEditItemChange(item: TodoItem){
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id){
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPostion] = item
    }
}
