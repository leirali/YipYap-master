package cofc.edu.yipyap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;

public class configActivity extends AppCompatActivity {

    /*
    UI ID's:

    gameTopicBox - Entry Textbox for the Topic
    randomTopicButton - random selects from a list.
    addPlayerButton - Button to put a person in the list
    listPlayers - ListView that displays players in the game
    numTurnsBox - Entry box for the number of turns
    mainMenuButton - Button to return to main menu
    startGameButton - Button to start the game


     */

    ArrayList<String> randomTopicList;
    ArrayAdapter<String> topicAdapter;

    ArrayList<String> playerList;
    ArrayAdapter<String> playerAdapter;

    AlertDialog.Builder topicPopup;

    EditText topicEntry;
    Button randomTopic;
    Button newPerson;
    ListView playerListView;
    EditText turnsBox;
    Button backMenu;
    Button startGame;
    String tbR;
    Button fromListButton;
    boolean fRun = true;
    Random rando = new Random();
    TextView numPB;

    int numPlayers = 0;
    int numTurns = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        topicEntry = (EditText) findViewById(R.id.gameTopicBox);
        randomTopic = (Button) findViewById(R.id.randomTopicButton);
        newPerson = (Button) findViewById(R.id.addPlayerButton);
        playerListView = (ListView) findViewById(R.id.listPlayers);
        backMenu = (Button) findViewById(R.id.mainMenuButton);
        startGame = (Button) findViewById(R.id.startGameButton);
        turnsBox = (EditText) findViewById(R.id.numTurnsBox);
        fromListButton = (Button) findViewById(R.id.fromListButton);
        numPB = (TextView) findViewById(R.id.numplayersbox);


        numPB.setText(Integer.toString(numPlayers));

        if (fRun){
            randomTopicList = new ArrayList<>(6);
            playerList = new ArrayList<>(4);
            topicPopulate();
            fRun = false;
        }

        randomTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tbR = randomTopicList.get(rando.nextInt(randomTopicList.size()));
                topicEntry.setText(tbR);
            }
        });

        playerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,playerList);
        playerListView.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                renamePerson(position);
            }
        });

        playerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String oldName = playerList.get(position).toString();
                playerList.remove(position);
                numPlayers -= 1;
                playerAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), oldName + " has been removed", Toast.LENGTH_SHORT).show();
                numPB.setText(Integer.toString(numPlayers));
                return false;
            }
        });

        newPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPlayers += 1;
                playerList.add("Player " + Integer.toString(numPlayers));
                numPB.setText(Integer.toString(numPlayers));
                playerAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Player added", Toast.LENGTH_SHORT).show();
            }
        });

        turnsBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    numTurns = Integer.valueOf(s.toString());
                }
                else {
                    numTurns = 5;
                }

            }
        });

        topicEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    tbR = s.toString();
                }
            }
        });

        fromListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTopicfromList();
            }
        });

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configActivity.this.startActivity(new Intent(configActivity.this, startActivity.class));
            }
        });

        startGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (numPlayers > 0)
                {
                    if ((tbR == null) || (tbR.length() < 0)){tbR = "Random";}
                    Intent toGame = new Intent(configActivity.this, MainActivity.class);
                    toGame.putExtra("NumTurns", numTurns);
                    toGame.putExtra("PlayerList", playerList);
                    toGame.putExtra("Topic", tbR);
                    configActivity.this.startActivity(toGame);
                }
                else
                {
                    if (numPlayers < 0){Toast.makeText(getApplicationContext(), "Must have players for a game!", Toast.LENGTH_SHORT).show();}
                }
            }
        });

    }

    //Function to populate topic list
    public void topicPopulate()
    {
        randomTopicList.add("Sports");
        randomTopicList.add("Technology");
        randomTopicList.add("News");
        randomTopicList.add("Memes");
        randomTopicList.add("Gaming");
    }

    //Function to rename a player if they're tapped
    public void renamePerson(final int pos)
    {
        final String currentName = playerList.get(pos).toString();
        AlertDialog.Builder renamePopup = new AlertDialog.Builder(this);
        renamePopup.setTitle("Rename " + currentName);

        final EditText renameBox = new EditText(getApplicationContext());
        renameBox.setTextColor(rgb(0,0,0));
        renameBox.setInputType(InputType.TYPE_CLASS_TEXT);
        renamePopup.setView(renameBox);

        renamePopup.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerList.set(pos,renameBox.getText().toString());
                playerAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),currentName + " has been renamed!", Toast.LENGTH_SHORT).show();
            }
        });

        renamePopup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog renam = renamePopup.create();
        renam.show();
    }

    public void getTopicfromList()
    {

        topicPopup = new AlertDialog.Builder(this);
        topicPopup.setTitle("Pick a topic!");

        final ListView viewTopic = new ListView(this);
        topicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,randomTopicList);
        viewTopic.setAdapter(topicAdapter);
        topicPopup.setView(viewTopic);

        viewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tbR = randomTopicList.get(position).toString();
                topicEntry.setText(tbR);

            }
        });

        topicPopup.setPositiveButton("Use this!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             topicEntry.setText(tbR);
                dialog.dismiss();
            }

        });

        AlertDialog lis = topicPopup.create();
        lis.show();




    }





}