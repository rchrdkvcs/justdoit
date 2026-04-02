package com.example.justdoit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.justdoit.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val taskTitle = binding.editTextTask.text.toString().trim()
            val taskDescription = binding.editTextDescription.text.toString().trim()
            val taskDeadline = binding.editTextDeadline.text.toString().trim()

            if (taskTitle.isNotEmpty()) {
                val newTask = Task(
                    title = taskTitle,
                    description = if (taskDescription.isNotEmpty()) taskDescription else null,
                    deadline = if (taskDeadline.isNotEmpty()) taskDeadline else null
                )
                viewModel.insert(newTask)
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Veuillez entrer un titre", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
