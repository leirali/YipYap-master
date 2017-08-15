package cofc.edu.yipyap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.view.KeyEvent;

import java.util.ArrayList;


//Recieves number of turns, PlayerList, and Topic from the config actifity. They are named
//     as follors, respectively: "NumTurns","PlayerList","Topic"

public class MainActivity extends AppCompatActivity {


    // Variable initialization
    //Placed outisde of the function so they can persist through the whole activity
    TextView topic;
    TextView playerTurn;
    TextView enterWordsText;
    Button home;
    Button done;
    TextView wordsPosted;
    TextView roundsLeft;
    TextView wordHistory;
    TextView wordsLeft;
    ProgressBar gameLength;


    int roundCount = 1;
    int totalWords;
    int playerNum = 0;
    String player;
    ArrayList<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        topic = (TextView) findViewById(R.id.topicBox);
        playerTurn = (TextView) findViewById(R.id.playerTurn);
        enterWordsText = (TextView) findViewById(R.id.enterWordsText);
        home = (Button) findViewById(R.id.home);
        done = (Button) findViewById(R.id.done);
        wordsPosted = (EditText) findViewById(R.id.wordsPosted);
        roundsLeft = (TextView) findViewById(R.id.roundsLeft);
        wordHistory = (TextView) findViewById(R.id.wordHistory);
        wordsLeft = (TextView) findViewById(R.id.wordsLeft);
        gameLength = (ProgressBar) findViewById(R.id.gameProgress);

        // retrieving bundle from configActivity

        Intent extras = getIntent();

        roundCount = extras.getIntExtra("NumTurns",5);
        final ArrayList<String> playerList = extras.getStringArrayListExtra("PlayerList");
        totalWords = (roundCount*(playerList.size()));
        final String gameTopic = extras.getStringExtra("Topic");


        gameLength.setMax(totalWords);
        gameLength.setProgress(0);
        topic.setText("Topic: " + gameTopic);
        roundsLeft.setText("Rounds Left: " + roundCount);
        wordsLeft.setText("Words Left: " + totalWords);
        playerTurn.setText("Player's Turn: " + playerList.get(playerNum));

        /* To restrict Space Bar in Keyboard */
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }

        };
        wordsPosted.setFilters(new InputFilter[] { filter });

        wordsPosted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                //Out of turns; don't accept any more input
                if (roundCount == 0) {Toast.makeText(getApplicationContext(), "The jig is up! Click 'Done' to see your full story.", Toast.LENGTH_SHORT).show();}

                //Accept input and let the game play
                else
                    {
                        String word = wordsPosted.getText().toString(); //Grab input from the user

                        //Only continue if the input is valid.
                        if (word.length() > 0)
                        {
                            words.add(word.toString()); //Add the word to the array list


                            playerNum++;  //Go to the next player
                            String oldHis = wordHistory.getText().toString();
                            wordHistory.setText(oldHis + " " + word);
                            totalWords--;
                            wordsLeft.setText("Words Left: " + totalWords);
                            gameLength.incrementProgressBy(1);


                            if (playerNum < playerList.size()) {playerTurn.setText("Player's Turn: " + playerList.get(playerNum));}

                            else {
                                playerNum = 0;
                                playerTurn.setText("Player's Turn: " + playerList.get(playerNum));
                                roundCount--;
                                roundsLeft.setText("Rounds Left: " + roundCount);
                                oldHis = wordHistory.getText().toString();
                                }
                            wordsPosted.setText("");
                        }
                    }
                return handled;
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, startActivity.class));
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(roundCount == 0))
                {
                    Toast.makeText(getApplicationContext(), "The game's not over yet! Look at your friends and get creative!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent toResult = new Intent(MainActivity.this, saveActivity.class);
                    toResult.putExtra("NumRounds", roundCount);
                    toResult.putExtra("PlayerList",playerList);
                    toResult.putExtra("Topic", gameTopic);
                    toResult.putExtra("WordStory",words);
                    MainActivity.this.startActivity(toResult);
                }
            }
        });






    }
}
