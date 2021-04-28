package com.example.geoquiznavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var gameDB: GameDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_game, container, false)

        gameDB = GameDB.get(context)
        gameDB.initializeNewGame()

        trueButton = v.findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            updateWithAnswer(true)
        }

        falseButton = v.findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            updateWithAnswer(false)
        }

        questionTextView = v.findViewById(R.id.question_text_view)
        val questionText = gameDB.getQuestion()
        questionTextView.text = questionText

        return v
    }

    private fun updateWithAnswer(userAnswer: Boolean) {
        gameDB.checkAnswer(userAnswer)
        if (gameDB.hasNextQuestion()) {
            updateQuestion()
        } else {
            trueButton.isEnabled  = false
            falseButton.isEnabled = false
            requireView().findNavController().navigate(R.id.action_gameFrag_to_resultFrag)
        }
    }

    private fun updateQuestion() {
        gameDB.nextQuestion()
        val questionText = gameDB.getQuestion()
        questionTextView.text = questionText
    }
}