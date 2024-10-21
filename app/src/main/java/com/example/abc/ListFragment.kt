package com.example.abc

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_tasks)
        taskAdapter = TaskAdapter(tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter

        loadTasks()

        val addTaskButton: Button = view.findViewById(R.id.button_return)
        addTaskButton.setOnClickListener {
            (activity as Screen).navigateToFragment(DetailFragment())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadTasks() {
        val sharedPreferences = requireActivity().getSharedPreferences("tasks", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("task_list", null)
        if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            val savedTasks: List<Task> = Gson().fromJson(json, type)
            tasks.clear()
            tasks.addAll(savedTasks)
            taskAdapter.notifyDataSetChanged()
        }
    }



        companion object {
            fun addTask(context: Context, task: Task) {
                val sharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
                val json = sharedPreferences.getString("task_list", null)
                val taskList = if (json != null) {
                    val type = object : TypeToken<MutableList<Task>>() {}.type
                    val savedTasks: MutableList<Task> = Gson().fromJson(json, type)
                    savedTasks
                } else {
                    mutableListOf()
                }
                taskList.add(task)
                sharedPreferences.edit().putString("task_list", Gson().toJson(taskList)).apply()
            }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}