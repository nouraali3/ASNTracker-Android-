package com.example.user.asntracker;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.user.asntracker.DataTypes.Tracker;

public class HomeActivity extends AppCompatActivity {

    private static Tracker currentUser;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private Fragment homeFragment , requestFragment , findFriendsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = (Tracker) getIntent().getSerializableExtra("currentUser");
        homeFragment = HomeFragment.newInstance(currentUser);
        requestFragment = RequestsFragment.newInstance(currentUser);
        findFriendsFragment = FindFriendsFragment.newInstance(currentUser);

        setFragment(homeFragment);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_requests:
                        setFragment(requestFragment);
                        return true;

                    case R.id.nav_search:
                        setFragment(findFriendsFragment);
                        return true;

                    default:
                        return false;
                }


            }
        });
    }


    public void setFragment(Fragment fragment)
    {
        Bundle args = new Bundle();
        args.putSerializable("currentUser",currentUser);
        fragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

}
