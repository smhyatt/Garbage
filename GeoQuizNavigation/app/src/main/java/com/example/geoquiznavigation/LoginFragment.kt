package com.example.geoquiznavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class LoginFragment : Fragment() {
    private lateinit var addNameButton: Button
    private lateinit var nameField: EditText
    private lateinit var gameDB: GameDB

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
                             ): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        gameDB = GameDB.get(context)

        nameField = v.findViewById(R.id.name_text)
        nameField.hint = gameDB.playerName

        addNameButton = v.findViewById(R.id.register_button)
        addNameButton.setOnClickListener{
            val name = nameField.text.toString().trim()
            if (name.isNotEmpty()) {
                gameDB.playerName = name
                v.findNavController().navigate(R.id.action_loginFrag_to_mainFrag)
            } else {
                Toast.makeText(activity,"Please type something in the field",
                               Toast.LENGTH_LONG).show();
            }
        }

        return v
    }

}