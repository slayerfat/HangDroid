package ve.com.slayerfat.hangdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra(SinglePlayerGameActivity.SCORE, 0);
        String scoreString = String.valueOf(score);
        String point = getResources().getString(R.string.point);
        String points = getResources().getString(R.string.points);

        String message = score == 1 ? scoreString + " " + point : scoreString + " " + points;

        TextView textViewPoints = (TextView) findViewById(R.id.final_score);
        assert textViewPoints != null;
        textViewPoints.setText(message);
    }
}
