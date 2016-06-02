package ve.com.slayerfat.hangdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AskMultiPlayerWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_multi_player_word);
    }

    public void startMultiPlayerGame(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text_multiplayer_word);
        assert editText != null;
        String input = editText.getText().toString();

        Intent intent = new Intent(this, MultiPlayerGameActivity.class);
        intent.putExtra(MultiPlayerGameActivity.INPUT, input);

        startActivity(intent);
    }
}
