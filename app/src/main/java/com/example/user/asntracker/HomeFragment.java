package com.example.user.asntracker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.asntracker.DataTypes.Tracker;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{

    private static Tracker currentUser;
    TextView currentUsernameTV, emailTV, phoneTV, currentUsernameTV2,genderTV;
    static Button displayFriendsBtn;

    public static HomeFragment newInstance(Tracker currentUser) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentUser", currentUser);
        fragment.setArguments(bundle);

        return fragment;
    }

    public HomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setViews();

        displayFriendsBtn = view.findViewById(R.id.display_friends_btn);
        displayFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayFriendsBtn.setBackground(getResources().getDrawable(R.drawable.btn_clicked));
                showFriends();
            }
        });
    }


    private void setViews()
    {
        if(getArguments() != null)
        {
            Log.d("HomeFragment","passed string to the fragment is "+ getArguments().getString("zozi"));
            currentUser  = (Tracker) getArguments().getSerializable("currentUser");
            Log.d("HomeFragment","current user is "+currentUser.toString());
            currentUsernameTV =  getView().findViewById(R.id.current_user_name_tv);
            emailTV = getView().findViewById(R.id.email_tv3);
            phoneTV = getView().findViewById(R.id.phone_tv1);
            currentUsernameTV2 = getView().findViewById(R.id.current_user_name_tv2);
            genderTV = getView().findViewById(R.id.gender_tv);


            currentUsernameTV.setText(currentUser.getUserName());
            emailTV.setText(currentUser.getEmail());
            phoneTV.setText(currentUser.getPhonenumber());
            currentUsernameTV2.setText(currentUser.getUserName());
            genderTV.setText(currentUser.getGender());
        }
    }

    public void showFriends()
    {
       Intent i= new Intent(getView().getContext(),FriendsListActivity.class);
       i.putExtra("currentUser",currentUser);
        displayFriendsBtn.setBackground(getResources().getDrawable(R.drawable.button_border));
        startActivity(i);
    }
}
