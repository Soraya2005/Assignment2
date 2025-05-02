package za.ac.iie.flashmind

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat // For colors (optional)

class QuizActivity : AppCompatActivity() {

    // --- UI Elements ---
    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var nextButton: Button

    // --- Quiz Data ---
    // Parallel arrays for questions and answers
    private val questions = arrayOf(
        "The Great Wall of China is visible from the Moon.", // False
        "Cleopatra VII was of Greek descent.", // True
        "The Roman Empire fell in 476 AD.", // True (Western Roman Empire)
        "Nelson Mandela was the first black president of South Africa in 1994.", // True
        "World War I started in 1916." // False (1914)
    )
    // Corresponding answers (true/false)
    private val answers = booleanArrayOf(false, true, true, true, false)

    // --- Quiz State ---
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Initialize UI elements
        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        feedbackTextView = findViewById(R.id.feedback_text_view)
        nextButton = findViewById(R.id.next_button)

        // Display the first question
        displayQuestion()

        // --- Set Click Listeners ---
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            // Move to the next question
            currentQuestionIndex++
            // Check if there are more questions
            if (currentQuestionIndex < questions.size) {
                displayQuestion() // Display the next question
            } else {
                // End of quiz - navigate to Score screen
                navigateToScoreScreen()
            }
        }
    }

    /**
     * Displays the current question and resets button states.
     */
    private fun displayQuestion() {
        // Set the question text
        questionTextView.text = questions[currentQuestionIndex]

        // Reset UI for the new question
        feedbackTextView.visibility = View.INVISIBLE // Hide feedback
        nextButton.visibility = View.INVISIBLE     // Hide Next button
        trueButton.isEnabled = true               // Enable answer buttons
        falseButton.isEnabled = true
    }

    /**
     * Checks the user's answer against the correct answer.
     * @param userAnswer The boolean value representing the user's choice (true/false).
     */
    private fun checkAnswer(userAnswer: Boolean) {
        // Disable answer buttons after an answer is chosen
        trueButton.isEnabled = false
        falseButton.isEnabled = false

        // Get the correct answer for the current question
        val correctAnswer = answers[currentQuestionIndex]

        // Compare user's answer with the correct answer
        if (userAnswer == correctAnswer) {
            // Correct answer logic
            score++ // Increment score
            feedbackTextView.text = "Correct!"
            // Optional: Set text color for feedback
            // feedbackTextView.setTextColor(ContextCompat.getColor(this, R.color.correct_green)) // Define colors in res/values/colors.xml
        } else {
            // Incorrect answer logic
            feedbackTextView.text = "Incorrect!"
            // Optional: Set text color for feedback
            // feedbackTextView.setTextColor(ContextCompat.getColor(this, R.color.incorrect_red)) // Define colors in res/values/colors.xml
        }

        // Show feedback and the Next button
        feedbackTextView.visibility = View.VISIBLE
        nextButton.visibility = View.VISIBLE
    }

    /**
     * Navigates to the ScoreActivity, passing the final score.
     */
    private fun navigateToScoreScreen() {
        val intent = Intent(this, ScoreActivity::class.java)
        // Pass the final score and total number of questions to the ScoreActivity
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL_QUESTIONS", questions.size)
        // Pass questions and answers for the review feature
        intent.putExtra("QUESTIONS_ARRAY", questions)
        intent.putExtra("ANSWERS_ARRAY", answers)
        startActivity(intent)
        finish() // Finish QuizActivity so the user can't navigate back to it
    }
}