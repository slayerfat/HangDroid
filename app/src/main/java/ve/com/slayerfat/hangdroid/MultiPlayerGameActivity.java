package ve.com.slayerfat.hangdroid;

import android.os.Bundle;

public class MultiPlayerGameActivity extends SinglePlayerGameActivity {

    public static final String INPUT = "ve.com.slayerfat.hangdroid.MultiPlayerGameActivity.input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);

        // since the multiPlayer doesn't need a random word we can get it from the input.
        word = getIntent().getStringExtra(MultiPlayerGameActivity.INPUT);
        createLettersTextView();
    }
}
