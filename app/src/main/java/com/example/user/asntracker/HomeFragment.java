//package com.example.user.asntracker;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.user.asntracker.DataTypes.Tracker;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class HomeFragment extends Fragment {
//
//    private static Tracker currentUser;
//    TextView currentUsernameTV, emailTV, phoneTV, currentUsernameTV2,genderTV;
//
//    public HomeFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//
//
//        setViews();
//    }
//
//
//    private void setViews()
//    {
//        currentUsernameTV = findViewById(R.id.current_user_name_tv);
//        emailTV = findViewById(R.id.email_tv3);
//        phoneTV = findViewById(R.id.phone_tv1);
//        currentUsernameTV2 = findViewById(R.id.current_user_name_tv2);
//        genderTV = findViewById(R.id.gender_tv);
//
//
//        currentUsernameTV.setText(currentUser.getUserName());
//        emailTV.setText(currentUser.getEmail());
//        phoneTV.setText(currentUser.getPhonenumber());
//        currentUsernameTV2.setText(currentUser.getUserName());
//        genderTV.setText(currentUser.getGender());
//
//    }
//}
