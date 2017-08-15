package cofc.edu.yipyap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class saveActivity extends AppCompatActivity {


    Button homeButton;
    Button againButton;
    Button saveTheStory;

    String createdStory = "";
    Boolean storySaved = false;
    TextView wordWindow;

    ArrayList<String> wordsPlayed;
    ArrayList<String> players;
    String topic;
    String sL;
    int numTurns;

    SharedPreferences stories;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_saving);

        homeButton = (Button) findViewById(R.id.home);
        againButton =  (Button) findViewById(R.id.playAgain);
        saveTheStory = (Button) findViewById(R.id.saveStory);

        wordWindow = (TextView) findViewById(R.id.createdStory);

        Intent previousData = getIntent();
        wordsPlayed = previousData.getStringArrayListExtra("WordStory");
        players = previousData.getStringArrayListExtra("PlayerList");
        topic = previousData.getStringExtra("Topic");
        numTurns = previousData.getIntExtra("NumRounds",5);

        stories = getSharedPreferences("sstories", MODE_PRIVATE);
        sL = stories.getString("storyList","");

        for (String word : wordsPlayed)
        {
            createdStory = createdStory + " " + word;
        }

        wordWindow.setText(createdStory);




        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tofinal = new Intent(saveActivity.this, configActivity.class);
                saveActivity.this.startActivity(tofinal);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveActivity.this.startActivity(new Intent(saveActivity.this, startActivity.class));
            }
        });

        saveTheStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {saveFunction();}});

    }
/**
    public void writeStory(String title)
    {
        Calendar curr = Calendar.getInstance();
        String ts = curr.getTime().toString();
        new savedStory(title,topic,players,numTurns,createdStory,ts);
        SharedPreferences.Editor writ = stories.edit();
    }
    */

public void saveFunction(){
    AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
    namePopup.setTitle("Give this story a name!");

    final EditText nameBox = new EditText(this);
    nameBox.setTextColor(rgb(0,0,0));
    namePopup.setView(nameBox);

    namePopup.setPositiveButton("Save it!", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            String storyname = nameBox.getText().toString();

            sL = sL + storyname + "|";
            SharedPreferences.Editor writ = stories.edit();
            writ.putString(storyname,createdStory);
            System.out.println("Saving this story: " + createdStory + " under this name " + sL);
            writ.putString("storyList",sL);
            writ.apply();
            saveTheStory.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), storyname + " has been saved!", Toast.LENGTH_SHORT).show();
        }
    });

    namePopup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });

    AlertDialog nameSave = namePopup.create();
    nameSave.show();

}
}
