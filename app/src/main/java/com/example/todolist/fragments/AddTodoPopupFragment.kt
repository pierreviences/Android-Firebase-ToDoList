package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoPopupBinding
import com.example.todolist.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private lateinit var todoData: ToDoData
    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }
    companion object {
        const val TAG = "AddTodoPopUpFragment"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddTodoPopupFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){

            todoData = ToDoData(arguments?.getString("taskId").toString() ,arguments?.getString("task").toString())
            binding.todoEt.setText(todoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents(){
        binding.todoNextBtn.setOnClickListener {
            val todotask = binding.todoEt.text.toString()
            if(todotask.isNotEmpty()){
                if (todoData == null){
                    listener?.onSaveTask(todotask , binding.todoEt)
                }else{
                    todoData?.task = todotask
                    listener?.onUpdateTask(todoData!!, binding.todoEt)
                }
            }else{
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener{
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(todoData: ToDoData, todoEt: TextInputEditText)
    }


}