package za.ac.iie.flashmind

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Link to the layout file

        // Find the Start button by its ID
        val startButton: Button = findViewById(R.id.start_button)

        // Set a click listener for the Start button
        startButton.setOnClickListener {
            // Create an Intent to navigate to QuizActivity
            val intent = Intent(this, QuizActivity::class.java)
            // Start the QuizActivity
            startActivity(intent)
            // Optional: finish MainActivity so user can't go back to it
            // finish()
        }
    }
}