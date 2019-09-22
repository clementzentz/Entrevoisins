package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNeighbourActivity extends AppCompatActivity implements Serializable {

    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    public List<Neighbour> mNeighboursFavorisList;
    public List<Neighbour> mNeighboursList;
    private Neighbour currentNeighbour;

    ListNeighbourPagerAdapter mPagerAdapter;

    private void initList(){
        mNeighboursFavorisList = new ArrayList<>();
        mNeighboursList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        initList();

        try{
            isFavoris();
        }catch (NullPointerException e){

        }

        TabLayout tabFavoris = findViewById(R.id.tabs);
        tabFavoris.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1){
                    //mettre à jour la recyclerview
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NeighbourFragment.DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            currentNeighbour = (Neighbour)data.getSerializableExtra(DetailNeighbourActivity.BUNDLE_EXTRA_FAVORIS);
            Toast.makeText(this, currentNeighbour.getName(), Toast.LENGTH_LONG).show();
        }
    }

    private void isFavoris(){
        if(currentNeighbour.getFavoris()){
            mNeighboursFavorisList.add(currentNeighbour);
        }
    }
}
