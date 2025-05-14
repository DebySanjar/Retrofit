package com.example.retrofitpost.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.retrofitpost.R
import com.example.retrofitpost.databinding.ActivityMainBinding
import com.example.retrofitpost.network.RetrofitCtlien
import com.example.retrofitpost.network.model.MyPost
import com.example.retrofitpost.network.model.MyTodo
import com.example.retrofitpost.ui.adapter.TodoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private var todoList = mutableListOf<MyTodo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoAdapter(todoList, ::onDeleteClick, ::onUpdateClick)
        binding.recy.adapter = adapter

        loadTodos()

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun onDeleteClick(todo: MyTodo) {
        // DELETE so‘rovini yuborish
        RetrofitCtlien.apiServise().deleteTodo(todo.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    loadTodos() // Todo listni yangilash
                    Toast.makeText(this@MainActivity, "Todo o‘chirildi", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Xatolik: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Xatolik: ${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.item_dialog, null)
        val etSarlavha = dialogView.findViewById<EditText>(R.id.edt_title)
        val etIzoh = dialogView.findViewById<EditText>(R.id.edt_desc)
        val switchBajarildi = dialogView.findViewById<Switch>(R.id.my_switch)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save)

        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        btnSave.setOnClickListener {
            val sarlavha = etSarlavha.text.toString().trim()
            val izoh = etIzoh.text.toString().trim()
            val bajarildi = switchBajarildi.isChecked

            if (sarlavha.isNotEmpty() && izoh.isNotEmpty()) {
                val myPost = MyPost(bajarildi = bajarildi, izoh = izoh, sarlavha = sarlavha)

                RetrofitCtlien.apiServise().createTodo(myPost).enqueue(object : Callback<MyTodo> {
                    override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                todoList.add(0, it)
                                adapter.notifyItemInserted(0)
                                binding.recy.scrollToPosition(0)
                            }
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                this@MainActivity, "Saqlashda xatolik", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity, "Tarmoq xatolik: ${t.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Toast.makeText(this, "Ma'lumotlarni to‘ldiring", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun onUpdateClick(todo: MyTodo) {
        val dialogView = layoutInflater.inflate(R.layout.item_dialog, null)
        val etSarlavha = dialogView.findViewById<EditText>(R.id.edt_title)
        val etIzoh = dialogView.findViewById<EditText>(R.id.edt_desc)
        val switchBajarildi = dialogView.findViewById<Switch>(R.id.my_switch)
        val btnSave = dialogView.findViewById<Button>(R.id.btn_save)

        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        etSarlavha.setText(todo.sarlavha)
        etIzoh.setText(todo.izoh)
        switchBajarildi.isChecked = todo.bajarildi

        btnSave.setOnClickListener {
            val sarlavha = etSarlavha.text.toString().trim()
            val izoh = etIzoh.text.toString().trim()
            val bajarildi = switchBajarildi.isChecked

            if (sarlavha.isNotEmpty() && izoh.isNotEmpty()) {
                val updatedTodo = MyTodo(
                    id = todo.id,
                    sarlavha = sarlavha,
                    izoh = izoh,
                    bajarildi = bajarildi,
                    sana = todo.sana
                )

                RetrofitCtlien.apiServise().updateTodo(todo.id, updatedTodo)
                    .enqueue(object : Callback<MyTodo> {
                        override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    val index = todoList.indexOf(todo)
                                    if (index != -1) {
                                        todoList[index] = it
                                        adapter.notifyItemChanged(index)
                                    }
                                }
                                dialog.dismiss()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Yangilashda xatolik",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                            Toast.makeText(
                                this@MainActivity,
                                "Tarmoq xatolik: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Ma'lumotlarni to‘ldiring", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun loadTodos() {
        RetrofitCtlien.apiServise().getallTodo().enqueue(object : Callback<List<MyTodo>> {
            override fun onResponse(call: Call<List<MyTodo>>, response: Response<List<MyTodo>>) {
                if (response.isSuccessful) {
                    binding.myProgress.visibility = View.GONE
                    response.body()?.let {
                        todoList.clear()
                        todoList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity, "Xatolik: ${response.code()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<MyTodo>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity, "Ulanishda xatolik: ${t.localizedMessage}", Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
