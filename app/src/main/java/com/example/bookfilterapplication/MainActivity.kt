package com.example.bookfilterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val authorInput = findViewById<TextInputLayout>(R.id.authors).editText?.text
        val countryInput = findViewById<TextInputLayout>(R.id.country).editText?.text
        val ClickmeButton = findViewById<TextView>(R.id.clickme)
        val result = findViewById<TextView>(R.id.display_op)

        ClickmeButton.setOnClickListener {
            val booksApplication = application as BookApplication
            val booksService = booksApplication.books
            var count = 0

            CoroutineScope(Dispatchers.IO).launch {
                val decodedBookResult = initBooksService().getMyBooks()

                withContext(Dispatchers.Main)
                {
                    val myStringBuilder = StringBuilder()

                    for(data in decodedBookResult)
                    {
                        if(authorInput.toString() == data.author && countryInput.toString() == data.country)
                        {
                            if (count<3)
                            {
                                myStringBuilder.append("Result : " +data.title)
                                myStringBuilder.append("\n")
                            }
                            count++
                        }
                    }

                    result.text = "Result : "+count+ "\n$myStringBuilder"
                }

            }
        }
    }
}