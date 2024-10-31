package com.example.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.model.QuestionsList
import com.example.quizapp.retrofit.QuestionsAPI
import com.example.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionsAPI: QuestionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)

    fun getQuestionsFromAPI(): LiveData<QuestionsList> {

        var data = MutableLiveData<QuestionsList>()
        var questionsList : QuestionsList

        GlobalScope.launch(Dispatchers.IO) {

            val response = questionsAPI.getQuestions()

            if(response != null)
            {
                questionsList = response.body()!!
                data.postValue(questionsList)
                Log.i("TAGGY", ""+data.value)
            }
        }

        return data

    }
}