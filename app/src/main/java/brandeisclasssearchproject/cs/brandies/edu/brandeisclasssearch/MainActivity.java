package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

/*
Tutorials about phrasing XML:
https://developer.android.com/training/basics/network-ops/xml.html
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default generated code.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //declare the fab buttom, which is the round button floating on the screen

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });//set the on Click of fab buttom

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Find the drawer
        //which is the entire layout we can see

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //default generated code.


        //The list view is the one we put all the infomations
        ListView lv = (ListView) findViewById(R.id.theContentList);
        SearchView sv = (SearchView) findViewById(R.id.searchClass);


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){


            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<String> userInput=inpInterpreter.phraseRowInput(query);
                //String targetURL
                return false;
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });




    }





    /*
    The method defines the behaviour of user clicking the back button
    The back button is the triangle button on the bottom of the screen
    it can be on the right or left depending on the systems
    usually on the left
     */
    @Override
    public void onBackPressed() {
        Log.i("mylog","onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //Toast.makeText(getApplicationContext(), "close", Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }






    /*
    Option Menu stuffs
    set up and inflate option menu
    and defines select behaviors
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    /*
    Option Menu stuffs
     */





    /*
    This method defines the behavior of click items in the navigation bar
    Important
    implement it later
    after implementation of the searching mechanisms
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
