package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.fragments.ClientAccountfragment;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.fragments.ClientProfileFragment;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels.ViewPagerAdapter;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView goback;


    private ClientAccountfragment clientAccountfragment;
    private ClientProfileFragment clientProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbarClientProfile);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        goback = findViewById(R.id.goBackClientProfile);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpagerClientProfile);
        viewPager.setOffscreenPageLimit(2);

        //Initializing the tablayout
        tabLayout = findViewById(R.id.tablayoutClientProfile);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        clientProfileFragment = new ClientProfileFragment();
        clientAccountfragment = new ClientAccountfragment();
        adapter.addFragment(clientProfileFragment, "Profile");
        adapter.addFragment(clientAccountfragment, "Account");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Log Out")
                    .setMessage("Are you really want to do this")
                    .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPrefManagerClient.getInstance(ProfileActivity.this).logout();
                        }
                    });
            android.app.AlertDialog ok = builder.create();
            ok.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
