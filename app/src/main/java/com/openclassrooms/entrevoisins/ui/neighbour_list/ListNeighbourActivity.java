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
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

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

    public static final String bundleListNeighbour = "bundleListNeighbour";

    public List<Neighbour> mNeighboursFavorisList;
    public List<Neighbour> mNeighboursList;
    private Neighbour currentNeighbour;

    NeighbourApiService mNeighbourApiService;

    ListNeighbourPagerAdapter mPagerAdapter;

    private NeighbourFragment mNeighbourFragment;
    private NeighbourFragment mNeighbourFragmentFavoris;

    private void initList1(){
        mNeighboursList = mNeighbourApiService.getNeighbours();
        mNeighboursFavorisList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mNeighbourApiService = DI.getNeighbourApiService();
        initList1();

        mNeighbourFragment = NeighbourFragment.newInstance();
        mNeighbourFragmentFavoris = NeighbourFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleListNeighbour, (Serializable)mNeighboursList);
        mNeighbourFragment.setArguments(bundle);

        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());

        mPagerAdapter.addFragment(mNeighbourFragment);
        mPagerAdapter.addFragment(mNeighbourFragmentFavoris);

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NeighbourFragment.DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            currentNeighbour = (Neighbour)data.getSerializableExtra(DetailNeighbourActivity.BUNDLE_EXTRA_FAVORIS);
            Toast.makeText(this, currentNeighbour.getName(), Toast.LENGTH_LONG).show();
            //TODO
            try{
                isFavoris();
                mNeighbourFragmentFavoris.updateList(mNeighboursFavorisList);
            }catch (NullPointerException e){

            }

        }
    }

    private void isFavoris(){
        if(currentNeighbour.getFavoris()){
            if (!mNeighboursFavorisList.contains(currentNeighbour)){
                mNeighboursFavorisList.add(currentNeighbour);
            }
        }
    }
}
