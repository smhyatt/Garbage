package com.example.geoquiznavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {
    private lateinit var nameTextView : TextView
    private lateinit var resultTextView : TextView
    private lateinit var gameDB : GameDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_result, container, false)

        resultTextView = v.findViewById(R.id.result_text_view)
        nameTextView   = v.findViewById(R.id.name_text_view)

        gameDB = GameDB.get(context)
        nameTextView.text = gameDB.playerName

        if (gameDB.isWin()) {
            resultTextView.setText(R.string.win_msg)
        } else {
            resultTextView.setText(R.string.lose_msg)
        }

        return v
    }

}