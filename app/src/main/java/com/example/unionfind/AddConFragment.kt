package com.example.unionfind

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.unionfind.databinding.FragmentAddConBinding
import com.google.android.material.textfield.TextInputEditText

class AddConFragment : Fragment() {

    private lateinit var binding: FragmentAddConBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddConBinding.inflate(inflater, container, false)

        binding.addMore.setOnClickListener {
            binding.first.text?.clear()
            binding.Second.text?.clear()
            onStart()
        }
        binding.back.setOnClickListener {
            val intent = Intent(context, SeeConnection::class.java)
            startActivity(intent)
        }
        binding.union.setOnClickListener {
            if (checkData(binding.first, binding.Second)) {
                var p = binding.first.text.toString().toInt()-1  // As Array index is Starting from 0
                var q = binding.Second.text.toString().toInt()-1 // To compensate that issue
                binding.first.requestFocus()
                binding.Second.clearFocus()
                val pRoot = sharedViewModel.root(p)
                val qRoot = sharedViewModel.root(q)
                p +=1
                q +=1
                if (pRoot == qRoot) {
                    binding.status.text = "($p,$q) are already connected"
                } else {
                    sharedViewModel.union(pRoot, qRoot)
                    binding.status.text = "($p,$q) are now connected"
                }
                binding.union.visibility = View.GONE
                binding.status.visibility = View.VISIBLE
                binding.addMore.visibility = View.VISIBLE

            }
        }
        return binding.root
    }

    //CHECKING VALIDATION OF DATA
    private fun checkData(p: TextInputEditText, q: TextInputEditText): Boolean {
        if (p.text.isNullOrEmpty() || q.text.isNullOrEmpty()) {
            if (p.text?.isEmpty() == true){
                p.error = "Enter a node"
                p.requestFocus()
            }
            else {
                q.error = "Enter a node"
                q.requestFocus()
            }
            return false
        }else {
            if (p.text.toString().toInt() > 20 || p.text.toString().toInt()==0) {
                p.error = "Range (0-20 but not 0)"
                p.requestFocus()
                return false
            }
            if(q.text.toString().toInt() > 20 || q.text.toString().toInt()==0) {
                q.error = "Range (0-20 but not 0)"
                q.requestFocus()
                return false
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.addMore.visibility = View.GONE
        binding.union.visibility = View.VISIBLE
        binding.status.visibility = View.GONE
    }
}
