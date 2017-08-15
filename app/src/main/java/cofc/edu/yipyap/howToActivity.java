package cofc.edu.yipyap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class howToActivity extends AppCompatActivity {
    // Button Id's
    /*
Back Button     back
Start Button    htStart
    */

    Button startButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        startButton = (Button) findViewById(R.id.htStart);
        backButton = (Button) findViewById(R.id.back);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howToActivity.this.startActivity(new Intent(howToActivity.this, configActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howToActivity.this.startActivity(new Intent(howToActivity.this, startActivity.class));
            }
        });
    }

}
