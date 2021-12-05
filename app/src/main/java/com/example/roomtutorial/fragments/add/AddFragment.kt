package com.example.roomtutorial.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomtutorial.R
import com.example.roomtutorial.model.User
import com.example.roomtutorial.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    lateinit var edtFirstName: EditText
    lateinit var edtLastName: EditText
    lateinit var edtAge: EditText
    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        edtFirstName = view.findViewById<EditText>(R.id.edtFistName)
        edtLastName = view.findViewById<EditText>(R.id.edtlastName)
        edtAge = view.findViewById<EditText>(R.id.edtAge)
        button = view.findViewById<Button>(R.id.button)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.button.setOnClickListener {
            insertToDatabase()

        }

        return view
    }

    private fun insertToDatabase() {
        val firstName = edtFirstName.text.toString()
        val lastName = edtLastName.text.toString()
        val age = edtAge.text

        if (inputCheckFirstName(firstName) && inputCheckLastName(lastName) && inputCheckAge(age)) {
            //Create user
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            //Add data do database
            mUserViewModel.addUser(user)
            Toast.makeText(context, "Usuario salvo com sucesso!", Toast.LENGTH_SHORT).show()
            //Retorna o fluxo para a lista de usuários
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Favor preencher os campos com valores", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun inputCheckFirstName(firstName: String): Boolean {
        return !(TextUtils.isEmpty(firstName))
    }

    private fun inputCheckLastName(lastName: String): Boolean {
        return !(TextUtils.isEmpty(lastName))
    }

    private fun inputCheckAge(age: Editable): Boolean {
        return !(age.isEmpty())
    }
}