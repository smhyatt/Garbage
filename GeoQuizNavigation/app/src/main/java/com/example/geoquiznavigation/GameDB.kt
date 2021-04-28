package com.example.geoquiznavigation

import android.content.Context
import kotlin.collections.ArrayList

class GameDB private constructor (context: Context?) {

    private val questionsList = ArrayList<Question>()
    private var currentIndex = 0

    var numberOfCorrectAnswers = 0

    var playerName = "Player's Name"

    init { fillQuestionsDB() }

    companion object {
        private lateinit var sGameDB: GameDB
        fun get(context: Context?): GameDB {
            if (! this::sGameDB.isInitialized)  sGameDB = GameDB(context)
            return sGameDB
        }
    }

    fun initializeNewGame() {
        currentIndex = 0
        numberOfCorrectAnswers = 0
    }

    fun size(): Int {
        return questionsList.size
    }

    fun addQuestion(question: String, answer: Boolean) {
        questionsList.add(Question(question,answer))
    }

    fun getQuestion(): String {
        return questionsList[currentIndex].question
    }

    fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionsList[currentIndex].answer
        if (userAnswer == correctAnswer) {
            numberOfCorrectAnswers++
        }
    }

    fun nextQuestion() {
        currentIndex++;
    }

    fun hasNextQuestion(): Boolean {
        return (questionsList.size > currentIndex + 1)
    }

    fun isWin(): Boolean {
        return (numberOfCorrectAnswers == questionsList.size)
    }


    fun fillQuestionsDB() {
        questionsList.add(Question("Canberra is the capital of Australia.",true))
        questionsList.add(Question("The Pacific Ocean is larger than the Atlantic Ocean.", true))
        questionsList.add(Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false))
        questionsList.add(Question("The source of the Nile River is in Egypt.", false))
        questionsList.add(Question("The Amazon River is the longest river in the Americas.", true))
        questionsList.add(Question("Lake Baikal is the world\'s oldest and deepest freshwater lake.", true))
        currentIndex = 0
    }

    fun listItems(): String {
        var r = ""
        for (q in questionsList)
            r = "$r\n Buy ${q.question} in: ${q.answer}"
        return r
    }

}