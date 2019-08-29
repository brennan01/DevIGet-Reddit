package com.brennan.deviget.redditposts.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditDataResponse;
import com.brennan.deviget.redditposts.domain.RedditNewsResponse;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements ListFragment.Listener, GetRedditPostFragment.Listener {

    private static final String TAG_MASTER_FRAGMENT = "TAG_MASTER_FRAGMENT";
    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";
    private static final String TAG = "MainActivity";
    private static final String GET_REDDIT_POSTS_FRAGMENT_TAG = "GET_REDDIT_POSTS_FRAGMENT_TAG";
    private static final String POSTS_LIST = "post_list";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup drawer view
        drawerLayout = findViewById(R.id.drawer_layout);
        int postsListFragmentContainerId;

        if (drawerLayout != null) {
            postsListFragmentContainerId = R.id.nav_view;
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return true;
                }
            });

            // setup menu icon
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            postsListFragmentContainerId = R.id.master_fragment_container;
        }

        // insert detail fragment into detail container
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            Log.d(TAG, "onCrateView. Bundle Null");
            DetailFragment detailFragment = DetailFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_container, detailFragment, TAG_DETAIL_FRAGMENT)
                    .commit();

            // insert master fragment into master container (i.e. nav view)
            ListFragment masterFragment = ListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(postsListFragmentContainerId, masterFragment, TAG_MASTER_FRAGMENT)
                    .commit();

            fragmentManager.executePendingTransactions();


            loadInfo();
        } else {
            ListFragment masterFragment = (ListFragment) fragmentManager.findFragmentByTag(TAG_MASTER_FRAGMENT);
            fragmentManager.beginTransaction().remove(masterFragment).commit();
            fragmentManager.executePendingTransactions();

            fragmentManager.beginTransaction().replace(postsListFragmentContainerId, masterFragment, TAG_MASTER_FRAGMENT).commit();
            fragmentManager.executePendingTransactions();
        }

    }


    private void loadInfo() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        GetRedditPostFragment getRedditPostFragment = (GetRedditPostFragment) getSupportFragmentManager()
                .findFragmentByTag(GET_REDDIT_POSTS_FRAGMENT_TAG);

        if(getRedditPostFragment == null){
            getRedditPostFragment = GetRedditPostFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(getRedditPostFragment, GET_REDDIT_POSTS_FRAGMENT_TAG)
                    .commit();
            getFragmentManager().executePendingTransactions();
        }
        getRedditPostFragment.setListener(this);
        getRedditPostFragment.setContext(getApplicationContext());
        getRedditPostFragment.getRedditPosts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(RedditChildrenResponse item) {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_DETAIL_FRAGMENT);
        detailFragment.onItemClick(item);

        // Close the navigation drawer
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onRedditPostStart() {

    }

    @Override
    public void onRedditPostComplete(RedditNewsResponse redditNewsResponse) {
        Log.d(TAG, "Response: " + redditNewsResponse.getData().getChildren().size());

        setItems(redditNewsResponse.getData());
    }

    private void setItems(RedditDataResponse mRedditDataResponse) {
        ListFragment listFragment = (ListFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_MASTER_FRAGMENT);

        listFragment.setItems(mRedditDataResponse);

    }


    @Override
    public void onRedditPostFailed(Throwable e) {
        Toast.makeText(this, "Error" + e.getStackTrace(), Toast.LENGTH_SHORT).show();

    }
}
