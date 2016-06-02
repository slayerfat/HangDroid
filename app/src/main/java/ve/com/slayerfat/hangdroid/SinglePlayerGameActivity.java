package ve.com.slayerfat.hangdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SinglePlayerGameActivity extends AppCompatActivity {

    /**
     * Public score key
     */
    public static final String SCORE = "ve.com.slayerfat.hangDroid.SinglePlayerGameActivity.score";
    /**
     * The word to guess
     */
    public String word;
    /**
     * Number of guesses
     */
    public int guesses = 0;
    /**
     * The number of times the user guessed wrong.
     */
    public int failCounter = 0;
    /**
     * The score
     */
    public int score = 0;
    /**
     * The layout that holds the letters to be guessed
     */
    LinearLayout lettersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);
        setRandomWord();
        createLettersTextView();
    }

    /**
     * Creates the _ _ _ in the view according to the length of the current word.
     */
    protected void createLettersTextView() {
        lettersLayout = (LinearLayout) findViewById(R.id.layoutLetters);
        assert lettersLayout != null;
        final Float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

        for (int i = 0; i < word.length(); i++) {
            TextView textView = new TextView(this);
            textView.setText("_");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            textView.setPadding(0, 0, padding.intValue(), 0);
            lettersLayout.addView(textView);
        }
    }

    /**
     * Resets the counters and clears the screen
     */
    public void clearScreen() {
        Log.d("Logger", "--- CLEARING THE SCREEN ---");
        Log.d("Logger", "the number of guesses are " + guesses);
        Log.d("Logger", "the number of fails are " + failCounter);
        guesses = failCounter = 0;

        // we have to change the image back
        changeStepImage(R.drawable.step_0);

        // clears the mistakes on screen
        TextView incorrectLetters = (TextView) findViewById(R.id.failedLetters);
        assert incorrectLetters != null;
        incorrectLetters.setText("");

        lettersLayout.removeAllViewsInLayout();
        setRandomWord();
        createLettersTextView();
    }

    /**
     * Retrieves the introduced input in the view.
     *
     * @param view The button
     */
    public void addLetter(View view) {
        EditText input = (EditText) findViewById(R.id.editTextLetter);

        String letter = null;
        if (input != null) {
            letter = input.getText().toString().toLowerCase();
            input.setText("");
        }
        Log.d("Logger", "the letter introduced is " + letter);

        if (letter != null && letter.length() == 1) {
            checkLetter(letter);
        } else {
            Toast.makeText(this, R.string.empty_value, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Iterates trough the word and check each char of it.
     *
     * @param letter the users input.
     */
    public void checkLetter(String letter) {
        char value = letter.charAt(0);
        boolean incorrect = true;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == value) {
                Log.d("Logger", "There is a match!");
                showLetter(i, value);

                guesses++;
                incorrect = !incorrect;
            }
        }

        if (incorrect) {
            if (!addIncorrectLetter(value)) {
                Toast.makeText(this, R.string.value_already_used, Toast.LENGTH_SHORT).show();
                return;
            }

            letterFailed();
        }


        if (guesses >= word.length()) {
            score++;
            clearScreen();
            setRandomWord();
        }

        Log.d("Logger", "Number of guesses: " + guesses);
        Log.d("Logger", "Number of failed guesses: " + failCounter);
        Log.d("Logger", "Word is: " + word);
        Log.d("Logger", "Words length is: " + word.length());
    }

    /**
     * If user fails we change the image and add the value to the failed pool.
     */
    private void letterFailed() {
        failCounter++;

        switch (failCounter) {
            case 1:
                changeStepImage(R.drawable.step_1);
                break;

            case 2:
                changeStepImage(R.drawable.step_2);
                break;

            case 3:
                changeStepImage(R.drawable.step_3);
                break;

            case 4:
                changeStepImage(R.drawable.step_4);
                break;

            case 5:
                changeStepImage(R.drawable.step_5);
                break;

            case 6:
            default:
                Intent gameOver = new Intent(this, GameOverActivity.class);
                gameOver.putExtra(SinglePlayerGameActivity.SCORE, score);
                startActivity(gameOver);
                break;
        }
    }

    /**
     * Changes the background imageView
     *
     * @param resource The drawable id
     */
    public void changeStepImage(int resource) {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        assert image != null;
        image.setImageResource(resource);
    }

    /**
     * Adds the incorrect value to the pool.
     *
     * @param value users letter input.
     */
    private boolean addIncorrectLetter(char value) {
        TextView incorrectLetters = (TextView) findViewById(R.id.failedLetters);
        assert incorrectLetters != null;
        String currentLetters = incorrectLetters.getText().toString();

        for (int i = 0; i < currentLetters.length(); i++) {
            if (currentLetters.charAt(i) == value) {
                return false;
            }
        }

        String results = currentLetters + value;
        incorrectLetters.setText(results);

        return true;
    }

    /**
     * Changes the text according to the guess.
     *
     * @param i     the index of the letter
     * @param value the guessed value
     */
    private void showLetter(int i, char value) {
        TextView textView = (TextView) lettersLayout.getChildAt(i);
        textView.setText(Character.toString(value));
    }

    /**
     * Sets the word to be guessed randomly
     */
    private void setRandomWord() {
        Words words = new Words(getResources().getString(R.string.words));
        word = words.getWord();
    }
}
