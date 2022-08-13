package com.example.unionfind

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var sz = Array(20) { 1 }

    var nodeLive: MutableLiveData<Array<Int>> = MutableLiveData()
    fun setData(node: Array<Int>){
        nodeLive.value = node
    }
        fun union(pRoot:Int,qRoot:Int){
            if (sz[pRoot] < sz[qRoot]) {
                nodeLive.value?.set(pRoot, qRoot)
                sz[qRoot] += sz[pRoot]

            } else {
                nodeLive.value?.set(qRoot, pRoot)
                sz[pRoot] += sz[qRoot]
            }
        }

    fun root(i: Int): Int {
        val r :String
        val p1:Int = i
        r = nodeLive.value?.elementAt(i).toString()
        return if (r.toInt() == p1)
            r.toInt()
        else {
            root(r.toInt())
        }
    }
}