package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNeighbourActivity extends AppCompatActivity implements ListActivityToFragment ,Serializable {

    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    private List<Neighbour> mNeighboursFavorisList;
    private List<Neighbour> mNeighboursList;

    private NeighbourApiService mNeighbourApiService;

    private NeighbourFragment mNeighbourFragment;
    private NeighbourFragment mNeighbourFragmentFavoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mNeighbourApiService = DI.getNeighbourApiService();

        mNeighboursList = mNeighbourApiService.getNeighbours();
        mNeighboursFavorisList = mNeighbourApiService.getNeighboursFavoris();

        mNeighbourFragment = NeighbourFragment.newInstance(mNeighboursList, false);
        mNeighbourFragmentFavoris = NeighbourFragment.newInstance(mNeighboursFavorisList, true);

        mNeighbourFragment.setInterface(this);
        mNeighbourFragmentFavoris.setInterface(this);

        ListNeighbourPagerAdapter pagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(mNeighbourFragment);
        pagerAdapter.addFragment(mNeighbourFragmentFavoris);

        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AllKeys.DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            Neighbour currentNeighbour = null;
            if (data != null) {
                currentNeighbour = (Neighbour) data.getSerializableExtra(AllKeys.INTENT_DETAIL_RETOUR_FAVORIS);
            }
            if (currentNeighbour != null) {
                callAddNeighbourToListFavoris(currentNeighbour);
            }
        }
    }

    public void callAddNeighbourToListFavoris(Neighbour currentNeighbour){
        if(currentNeighbour.isFavoris()){
            mNeighbourApiService.addNeighbourToListFavoris(currentNeighbour);
            mNeighboursFavorisList = mNeighbourApiService.getNeighboursFavoris();
            mNeighbourFragmentFavoris.initList(mNeighboursFavorisList);
        }
    }

    @Override
    public void callBackDeleteNeighbour(Neighbour neighbour) {
        mNeighbourApiService.deleteNeighbour(neighbour);
        mNeighboursList = mNeighbourApiService.getNeighbours();
        mNeighbourFragment.initList(mNeighboursList);
        if (mNeighboursFavorisList.contains(neighbour)){
            mNeighbourApiService.deleteNeighbourFavoris(neighbour);
            mNeighboursFavorisList = mNeighbourApiService.getNeighboursFavoris();
            mNeighbourFragmentFavoris.initList(mNeighboursFavorisList);
        }
    }

    @Override
    public void callBackDeleteNeighbourFavoris(Neighbour neighbour) {
        mNeighbourApiService.deleteNeighbourFavoris(neighbour);
        mNeighboursFavorisList = mNeighbourApiService.getNeighboursFavoris();
        mNeighbourFragmentFavoris.initList(mNeighboursFavorisList);
    }
}
