package com.example.geoquiznavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class MainFragment : Fragment() {
    private lateinit var startButton: Button
    private lateinit var registerButton: Button

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
                             ): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        startButton = v.findViewById(R.id.start_game_button)
        startButton.setOnClickListener{
            v.findNavController().navigate(R.id.action_mainFrag_to_gameFrag)
        }

        registerButton = v.findViewById(R.id.register_name_button)
        registerButton.setOnClickListener{
            v.findNavController().navigate(R.id.action_mainFrag_to_loginFrag)
        }

        return v
    }
}