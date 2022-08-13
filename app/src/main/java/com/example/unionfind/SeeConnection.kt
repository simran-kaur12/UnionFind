package com.example.unionfind

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.example.unionfind.databinding.SeeConBinding
import com.google.android.material.textfield.TextInputEditText

var node = Array(20) { i: Int -> i * 1 }

class SeeConnection : AppCompatActivity()  {
    private lateinit var binding: SeeConBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = SeeConBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel.setData(node)
        binding.add.setOnClickListener{
            val addFragment = AddConFragment()
            val fragmentManager: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentManager.replace(R.id.fragment,addFragment).addToBackStack(null).commit()
        }

        binding.first.setOnClickListener {
            onStart()
        }
        binding.Second.setOnClickListener {
           onStart()
        }
        binding.Check.setOnClickListener {
            if (checkData(binding.first, binding.Second)) {
                var p = binding.first.text.toString().toInt()-1
                var q = binding.Second.text.toString().toInt()-1
                binding.Second.clearFocus()
                val pRoot = sharedViewModel.root(p)
                val qRoot = sharedViewModel.root(q)
                binding.Check.visibility = View.GONE
                binding.info.visibility = View.VISIBLE
                binding.connect.visibility = View.VISIBLE
                p +=1
                q +=1
                if (pRoot == qRoot)
                    binding.connect.text = "($p,$q) are connected nodes"
                else
                    binding.connect.text = "($p,$q) are not connected "

            }
        }
    }
    //CHECKING VALIDATION OF DATA
    private fun checkData(p: TextInputEditText, q: TextInputEditText): Boolean {
        if (p.text.isNullOrEmpty() || q.text.isNullOrEmpty()) {
                if (p.text?.isEmpty() == true)
                    p.error = "Enter a node"
                else
                    q.error = "Enter a node"
                return false
        }else {
            if (p.text.toString().toInt() > 20 || p.text.toString().toInt() == 0) {
                p.error = "Range (0-20 but not 0)"
                return false
            }
            if(q.text.toString().toInt() > 20 ||q.text.toString().toInt() == 0) {
                q.error = "Range (0-20 but not 0)"
                return false
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.info.visibility = View.GONE
        binding.connect.visibility = View.GONE
        binding.Check.visibility = View.VISIBLE
    }
}
