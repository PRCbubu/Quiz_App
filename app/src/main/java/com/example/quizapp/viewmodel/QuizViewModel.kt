package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.QuestionsList
import com.example.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel() {
    val repository : QuizRepository = QuizRepository()

    var questionsLiveData : LiveData<QuestionsList> = repository.getQuestionsFromAPI()

    fun getQuestionsFromLiveData(): LiveData<QuestionsList> {
        return questionsLiveData

    }
}