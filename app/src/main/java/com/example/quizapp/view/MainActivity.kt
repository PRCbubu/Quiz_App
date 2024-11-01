package com.example.quizapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.Question
import com.example.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionsList: List<Question>

    companion object{
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Re-setting of scores
        result = 0
        totalQuestions = 0

        //Getting the response
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        //Displaying the first Question
        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.size > 0)
                {
                    questionsList = it
                    Log.i("TAGGY", "This the 1st question: ${questionsList[0].question}")

                    binding.apply {
                        txtQuestion.text = questionsList!![0].question
                        radio1.text = questionsList!![0].option1
                        radio2.text = questionsList!![0].option2
                        radio3.text = questionsList!![0].option3
                        radio4.text = questionsList!![0].option4
                    }
                }
            })
        }

        //Adding Functionality to Next BTN
        var i = 1
        binding.apply {
            btnNext.setOnClickListener(View.OnClickListener {
              val selectedOption = radioGroup?.checkedRadioButtonId

              if(selectedOption != -1)
              {
                  val radioButton = findViewById<View>(selectedOption!!) as RadioButton

                  questionsList.let {

                      if(i<it.size!!)
                      {
                          //Getting number of questions
                          totalQuestions = it.size
                          if(radioButton.text.toString().equals(it[i-1].correct_option))
                          {
                              result++
                              txtResult?.text = "Correct: $result"
                          }
                          else
                          {
                              txtResult.text = "Wrong"
                          }

                          //Displaying the next question
                          txtQuestion.text = "Questions ${i+1}: "+ it[i].question
                          radio1.text = it[i].option1
                          radio2.text = it[i].option2
                          radio3.text = it[i].option3
                          radio4.text = it[i].option4

                          //checking if it is the last question
                          if(i == it.size!!.minus(1))
                          {
                              btnNext.text = "Finish"

                          }

                          radioGroup?.clearCheck()
                          i++
                      }
                      else
                      {
                          if(radioButton.text.toString().equals(it[i-1].correct_option))
                          {
                              result++
                              txtResult?.text = "Correct: $result"
                          }

                          val intent = Intent(this@MainActivity, ResultActivity::class.java)
                          startActivity(intent)
                          finish()
                      }
                  }
              }
                else
              {
                  txtResult.text = "Please select an option"
              }
            })
        }
    }
}