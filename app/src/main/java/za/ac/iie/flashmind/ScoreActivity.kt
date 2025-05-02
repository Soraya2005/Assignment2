package za.ac.iie.flashmind

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// import androidx.appcompat.app.AlertDialog // Alternative for simple review

class ScoreActivity : AppCompatActivity() {

    // --- UI Elements ---
    private lateinit var scoreTextView: TextView
    private lateinit var scoreFeedbackTextView: TextView
    private lateinit var reviewButton: Button
    private lateinit var exitButton: Button
    private lateinit var reviewScrollView: ScrollView
    private lateinit var reviewContentTextView: TextView

    // Data received from QuizActivity
    private var questions: Array<String>? = null
    private var answers: BooleanArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Initialize UI elements
        scoreTextView = findViewById(R.id.score_text_view)
        scoreFeedbackTextView = findViewById(R.id.score_feedback_text_view)
        reviewButton = findViewById(R.id.review_button)
        exitButton = findViewById(R.id.exit_button)
        reviewScrollView = findViewById(R.id.review_scrollview) // Added ScrollView
        reviewContentTextView = findViewById(R.id.review_content_textview) // TextView inside ScrollView

        // Retrieve data passed from QuizActivity
        val score = intent.getIntExtra("SCORE", 0) // Default score is 0
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 5) // Default total
        questions = intent.getStringArrayExtra("QUESTIONS_ARRAY")
        answers = intent.getBooleanArrayExtra("ANSWERS_ARRAY")


        // Display the final score
        scoreTextView.text = "Your Score: $score / $totalQuestions"

        // Display personalized feedback based on the score
        if (score >= 3) {
            scoreFeedbackTextView.text = "Great job! You have a good grasp of these facts."
        } else {
            scoreFeedbackTextView.text = "Keep practising! Review the answers to learn more."
        }

        // --- Set Click Listeners ---
        reviewButton.setOnClickListener {
            // Toggle review visibility or show a dialog
            displayReview()
        }

        exitButton.setOnClickListener {
            // Terminate the app completely
            finishAffinity() // Closes all activities in the task
        }
    }

    /**
     * Displays the questions and correct answers in the ScrollView.
     */
    private fun displayReview() {
        if (questions == null || answers == null) {
            reviewContentTextView.text = "Error: Could not load review data."
            reviewScrollView.visibility = View.VISIBLE
            return // Exit if data is missing
        }

        // Build the review string
        val reviewText = StringBuilder()
        for (i in questions!!.indices) {
            reviewText.append("Q${i + 1}: ${questions!![i]}\n")
            reviewText.append("Answer: ${answers!![i]}\n\n")
        }

        // Set the text in the TextView inside the ScrollView
        reviewContentTextView.text = reviewText.toString()

        // Make the ScrollView visible
        reviewScrollView.visibility = View.VISIBLE

        // Optional: Change button text or hide it after review
        // reviewButton.text = "Hide Review" // Or implement toggle logic
        // reviewButton.isEnabled = false // Disable after showing once
    }

    /*
    // --- Alternative: Review using an AlertDialog ---
    private fun showReviewDialog() {
        if (questions == null || answers == null) return // Need data

        val reviewText = StringBuilder()
        for (i in questions!!.indices) {
            reviewText.append("Q${i + 1}: ${questions!![i]}\n")
            reviewText.append("Answer: ${answers!![i]}\n\n")
        }

        AlertDialog.Builder(this)
            .setTitle("Review Answers")
            .setMessage(reviewText.toString())
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    */
}