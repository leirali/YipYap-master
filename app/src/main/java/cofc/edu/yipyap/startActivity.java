package cofc.edu.yipyap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class startActivity extends AppCompatActivity {

    // ID's
/*
start button:           start
how to play button:     howto
records button:         records
*/

    Button startButton;
    Button howToButton;
    Button recordsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.start);
        howToButton = (Button) findViewById(R.id.howto);
        recordsButton = (Button) findViewById(R.id.records);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity.this.startActivity(new Intent(startActivity.this, configActivity.class));
            }
        });

        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity.this.startActivity(new Intent(startActivity.this, howToActivity.class));
            }
        });

        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity.this.startActivity(new Intent(startActivity.this, recordActivity.class));
            }
        });
    }


}
