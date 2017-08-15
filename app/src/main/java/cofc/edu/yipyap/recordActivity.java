package cofc.edu.yipyap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static android.graphics.Color.rgb;

public class recordActivity extends AppCompatActivity {
    // Button Id's
    /*
Back Button back2
    * */

    Button backButton;
    SharedPreferences stories;
    String storyNameString;
    String[] interNames;
    ArrayList<String> storyName = new ArrayList<>();
    ArrayAdapter<String> toList;
    ListView storyWindow;
    TextView storyDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        backButton = (Button) findViewById(R.id.back2);
        storyWindow = (ListView) findViewById(R.id.recordedStories);

        stories = getSharedPreferences("sstories",MODE_PRIVATE);
        storyNameString = stories.getString("storyList","");

        storyDisplay = (TextView) findViewById(R.id.showStory);

        if (storyNameString.length() > 0){interNames = storyNameString.split("\\|");}
        else {interNames = null;}

        if (interNames != null)
        {
            for (String title: interNames)
            {
                storyName.add(title);
            }

            toList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,storyName);
            storyWindow.setAdapter(toList);
            toList.notifyDataSetChanged();
        }


        storyWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tapKey = storyName.get(position).toString();
                retrieveStory(tapKey);
            }
        });

        storyWindow.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteStory(position);
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor writ = stories.edit();

                String newNameList = "";
                for (String title : storyName){newNameList = newNameList + title + "|";}

                writ.putString("storyList",newNameList);
                writ.apply();

                startActivity(new Intent(recordActivity.this, startActivity.class));
            }
        });
    }

    public void retrieveStory(String key)
    {
        String typedWords = stories.getString(key,"Sorry, nothing here");
        storyDisplay.setText(typedWords);

    }

    public void deleteStory(int pos)
    {
        storyName.remove(pos);
        toList.notifyDataSetChanged();
    }

}
