package com.example.abc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dateEditText: EditText
    private lateinit var taskEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateEditText = view.findViewById(R.id.ed_date)
        taskEditText = view.findViewById(R.id.ed_name)

        val addButton: Button = view.findViewById(R.id.button_ok)

        addButton.setOnClickListener {

            if (dateEditText.text.toString().isNotEmpty() && taskEditText.text.toString()
                    .isNotEmpty()
            ) {

                if (isValidDate(dateEditText.text.toString())) {
                    val task = Task(
                        dateEditText.text.toString(),
                        taskEditText.text.toString()+" руб.",

                    )
                    ListFragment.addTask(requireContext(), task)

                    (activity as Screen).navigateToFragment(ListFragment())
                } else {
                    Toast.makeText(
                        context,
                        "Введённая дата не является корректной!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(context, "Вы не ввели дату или задачу", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isValidDate(dateString: String, dateFormat: String = "dd.MM.yyyy"): Boolean {
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        sdf.isLenient = false // Устанавливаем строгий режим, чтобы не допускать невалидные даты
        return try {
            sdf.parse(dateString) != null
        } catch (e: ParseException) {
            false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}