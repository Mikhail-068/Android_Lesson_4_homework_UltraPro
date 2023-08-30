package com.example.lesson_4_homework_ultrapro

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

private const val TAG = "our application" // тег для отладки через logcat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // находим поле для картинки
        val img = findViewById<ImageView>(R.id.img)
        // находим кнопку
        val btn = findViewById<Button>(R.id.btn_recognizeText)


        // создаем bitmap из картинки (тут необходимо менять картинку, которая будет браться из drawble)
        val bitmap = BitmapFactory.decodeResource(this@MainActivity.resources, R.drawable.img)
        // выводим картинку на экран
        img.setImageBitmap(bitmap)

        // создаем экземпляр НС
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        // подгатавливаем нашу картинку
        val image = InputImage.fromBitmap(bitmap, 0)

        //----------------------------------------------------------
        // обработчик нажатий на кнопку
        btn.setOnClickListener {
            var result = "Результат: " // переменная для хранения результата

            // запускаем распознование текста на картинке и добавляем обработчики результата
            recognizer.process(image).apply {
                // обработчик для УСПЕШНОГО распознавания
                addOnSuccessListener { visionText ->
                    Log.i(TAG, "Success" + visionText.text) // пишем в лог
                    result += visionText.text // модифицируем результат

                    // выводим результат на экран
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                }

                // обработчик для ОШИБКИ в распознавании
                addOnFailureListener { e ->
                    Log.i(TAG, "ERROR!!!:" + e.message.toString()) // пишем в лог
                    result += e.message.toString() // модифицируем результат

                    // выводим результат на экран
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}