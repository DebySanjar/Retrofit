package com.example.retrofitpost.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitpost.databinding.TodoItemBinding
import com.example.retrofitpost.network.model.MyTodo

class TodoAdapter(
    private val todos: List<MyTodo>,
    private val onDeleteClick: (MyTodo) -> Unit,
    private val onUpdateClick: (MyTodo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    inner class TodoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]
        val binding = holder.binding

        binding.tvName.text = todo.sarlavha
        binding.tvEmail.text = todo.izoh
        binding.times.text = todo.sana

        // Fon rangi bajarilmagan bo‘lsa
        if (!todo.bajarildi) {
            binding.bgItem.setCardBackgroundColor(Color.rgb(200, 50, 40))
        } else {
            binding.bgItem.setCardBackgroundColor(Color.parseColor("#4FB451"))
        }

        // item bosilganda panel ochish/yopish
        binding.root.setOnClickListener {
            if (binding.hiddePanel.visibility == View.GONE) binding.hiddePanel.visibility =
                View.VISIBLE else {
                binding.hiddePanel.visibility = View.GONE
            }

        }

        // delete tugmasi
        binding.btnDelete.setOnClickListener {
            onDeleteClick(todo)
        }

        // update tugmasi
        binding.btnUpdate.setOnClickListener {
            onUpdateClick(todo)
        }
    }

    override fun getItemCount(): Int = todos.size
}
