package com.example.roomtutorial.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomtutorial.R
import com.example.roomtutorial.model.User
import com.example.roomtutorial.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    lateinit var button: Button
    lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateFirstName.setText(args.currentUser.firstName)
        view.updateLastName.setText(args.currentUser.lastName)
        view.updateAge.setText(args.currentUser.age.toString())

        button = view.findViewById<Button>(R.id.updateButton)
        button.setOnClickListener {
            updateItem()
        }
        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = updateFirstName.text.toString()
        val lastName = updateLastName.text.toString()
        val age = updateAge.text

        if (inputCheckFirstName(firstName) && inputCheckLastName(lastName) && inputCheckAge(age)) {
            //Create object User
            val updateUser =
                User(
                    args.currentUser.id,
                    firstName,
                    lastName,
                    Integer.parseInt(age.toString())
                )
            //Update user
            mUserViewModel.updateUser(updateUser)
            //Message
            Toast.makeText(requireContext(), "User updated with sucess", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Por favor preencha os campos.", Toast.LENGTH_SHORT)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Usuário ${args.currentUser.firstName} removido com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName} ?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName} ?")
        builder.create().show()
    }
}