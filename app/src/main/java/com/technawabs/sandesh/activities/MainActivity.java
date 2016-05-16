package com.technawabs.sandesh.activities;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.tabs.Chat;
import com.technawabs.sandesh.tabs.Inbox;
import com.technawabs.sandesh.tabs.Sent;
import com.technawabs.sandesh.uicomponents.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements Inbox.OnFragmentInteractionListener,Sent.OnFragmentInteractionListener,Chat.OnFragmentInteractionListener{

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"INBOX", "SENT", "CONTACTS"};
    int NumbOfTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating view pager adapter
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, NumbOfTabs);

        //Assign the viewpager view
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        //Assign sliding tab layout
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        //setting custom color
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        tabs.setViewPager(pager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Inbox inbox = new Inbox();
                return inbox;
            case 1:
                Sent sent = new Sent();
                return sent;
            case 2:
                Chat chat = new Chat();
                return chat;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return Titles[position];
    }
}

