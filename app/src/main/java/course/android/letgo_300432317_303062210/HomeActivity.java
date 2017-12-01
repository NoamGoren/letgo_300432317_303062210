package course.android.letgo_300432317_303062210;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction t= fm.beginTransaction();
        ProductsListFragment ProductsFragment= new ProductsListFragment();
        t.replace(R.id.root_layout, ProductsFragment);
        t.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAlpha(0.75f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));

            }
        });

        ImageButton m1 = (ImageButton) findViewById(R.id.cars);
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrag(1);
            }
        });
        ImageButton m2=(ImageButton) findViewById(R.id.home);
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m3 =(ImageButton) findViewById(R.id.motors);
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m4=(ImageButton) findViewById(R.id.fashion);
        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m5=(ImageButton) findViewById(R.id.other);
        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m6=(ImageButton) findViewById(R.id.child);
        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m7=(ImageButton) findViewById(R.id.entertai);
        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });

        ImageButton m8=(ImageButton) findViewById(R.id.leisure);
        m8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumber= Integer.parseInt(v.getTag().toString());
                changeFrag(pageNumber);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void changeFrag(int number){


        Bundle bundle = new Bundle();
        bundle.putInt("message", number );
        FragmentManager fm = getFragmentManager();
        FragmentTransaction t= fm.beginTransaction();
        ProductsListFragment ProductsFragment= new ProductsListFragment();
        ProductsFragment.setArguments(bundle);
        t.replace(R.id.root_layout, ProductsFragment);
        t.commit();
                    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:{
                return true;
            }
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id==R.id.app_bar_search){
            EditText editText=(EditText) findViewById(R.id.searchEditText);
            editText.setVisibility(View.INVISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_home) {
            Intent i = new Intent(HomeActivity.this,HomeActivity.class);
            startActivity(i);

        }  else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_notifaction) {

        }
        else if (id == R.id.nav_categories) {
            Intent i = new Intent(HomeActivity.this,CategoriesActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_profile) {
            Intent i = new Intent(HomeActivity.this,ProfileActivity.class);
            startActivity(i);


        }
        else if (id == R.id.nav_invite) {


        }
        else if (id == R.id.nav_help) {
            startActivity(new Intent(HomeActivity.this,CameraActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
