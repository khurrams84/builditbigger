package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.javajokeslib.Jokes;
import com.udacity.jokedisplay.JokeActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        //Toast.makeText(this, Jokes.getNewJoke(), Toast.LENGTH_SHORT).show();
        new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Intent intent = new Intent(MainActivity.this,  JokeActivity.class);
                intent.putExtra("joke", output);
                MainActivity.this.startActivity(intent);
            }

            @Override
            public void processError(Exception error) {
                Toast.makeText(MainActivity.this, "Cannot connect to api: " + error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }).execute(this);

    }


}
