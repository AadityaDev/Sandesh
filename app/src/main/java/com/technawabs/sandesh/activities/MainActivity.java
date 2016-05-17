package com.technawabs.sandesh.activities;

import android.net.Uri;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.Button;

import com.technawabs.sandesh.R;
import com.technawabs.sandesh.SandeshConstants;
import com.technawabs.sandesh.Utility;
import com.technawabs.sandesh.pojo.Sms;
import com.technawabs.sandesh.tabs.Chat;
import com.technawabs.sandesh.tabs.Inbox;
import com.technawabs.sandesh.tabs.Sent;
import com.technawabs.sandesh.uicomponents.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Inbox.OnFragmentInteractionListener, Sent.OnFragmentInteractionListener, Chat.OnFragmentInteractionListener {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    private Button saveToDrive;
    CharSequence Titles[] = {"INBOX", "CHAT", "CONTACTS"};
    int NumbOfTabs = 3;
    private List<Sms> smsList;

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

        saveToDrive = (Button) findViewById(R.id.make_backup);
        saveToDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsList=new ArrayList<Sms>();
                smsList.addAll(Utility.getAllSmsFromProvider(getApplicationContext(),
                        Uri.parse(SandeshConstants.INBOX_SMS)));
                Utility.writeToFile(getApplicationContext(),smsList.toString(),"sandesh_sms_backup.txt");
            }
        });
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
                Chat chat = new Chat();
                return chat;
            case 2:
                Sent sent = new Sent();
                return sent;
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
        return Titles[position];
    }
}

