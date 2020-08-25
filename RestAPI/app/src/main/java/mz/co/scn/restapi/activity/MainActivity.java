package mz.co.scn.restapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import mz.co.scn.restapi.R;
import mz.co.scn.restapi.fragment.PersonListFragment;
import mz.co.scn.restapi.model.Person;
import mz.co.scn.restapi.sync.ReceivePersonAsync;


public class MainActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        //new ReceivePersonAsync(this).execute();

        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(this, PersonActivity.class));
        });

    }

    public static void update(List<Person> persons) {
        //persons = session.getPersons();
        fragmentManager.beginTransaction().replace(R.id.framLayout, new PersonListFragment(persons)).commit();
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

    @Override
    protected void onResume() {
        super.onResume();
        new ReceivePersonAsync(this).execute();
    }
}