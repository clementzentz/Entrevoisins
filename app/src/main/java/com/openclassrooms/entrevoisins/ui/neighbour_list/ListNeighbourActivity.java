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
import java.util.ArrayList;
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

    public List<Neighbour> mNeighboursFavorisList;
    public List<Neighbour> mNeighboursList;
    private Neighbour currentNeighbour;

    NeighbourApiService mNeighbourApiService;

    ListNeighbourPagerAdapter mPagerAdapter;

    private NeighbourFragment mNeighbourFragment;
    private NeighbourFragment mNeighbourFragmentFavoris;

    private void initListsNeighboursActivity(){
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
        initListsNeighboursActivity();

        mNeighbourFragment = NeighbourFragment.newInstance(mNeighboursList, false);
        mNeighbourFragmentFavoris = NeighbourFragment.newInstance(mNeighboursFavorisList, true);

        mNeighbourFragment.setInterface(this);
        mNeighbourFragmentFavoris.setInterface(this);

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
        if (AllKeys.DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            currentNeighbour = (Neighbour)data.getSerializableExtra(AllKeys.INTENT_DETAIL_RETOUR_FAVORIS);
            addNeighbourToListFavoris(currentNeighbour, mNeighboursFavorisList);
            mNeighbourFragmentFavoris.initList(mNeighboursFavorisList);
        }
    }

    //TODO : implémenter ces methodes dans le service
    public void addNeighbourToListFavoris(Neighbour currentNeighbour, List<Neighbour> neighboursFavorisList){
        if(currentNeighbour.isFavoris()){
            if (!neighboursFavorisList.contains(currentNeighbour)){
                neighboursFavorisList.add(currentNeighbour);
            }
        }
    }

    @Override
    public void removeNeighbour(Neighbour neighbour) {
        updateListAfterRemoveNeighbour(neighbour, mNeighboursList, mNeighboursFavorisList, mNeighbourFragment, mNeighbourFragmentFavoris);
    }

    public void updateListAfterRemoveNeighbour(Neighbour neighbour, List<Neighbour> neighboursList, List<Neighbour> neighboursFavorisList, NeighbourFragment neighbourFragment, NeighbourFragment neighbourFragmentFavoris) {
        neighboursList.remove(neighbour);
        neighbourFragment.initList(neighboursList);
        if (neighboursFavorisList.contains(neighbour)){
            neighboursFavorisList.remove(neighbour);
            neighbourFragmentFavoris.initList(neighboursFavorisList);
        }
    }

    @Override
    public void removeNeighbourFavoris(Neighbour neighbour) {
        updateListAfterRemoveNeighbourFavoris(neighbour, mNeighboursFavorisList, mNeighbourFragmentFavoris);
    }

    public void updateListAfterRemoveNeighbourFavoris(Neighbour neighbour, List<Neighbour> neighboursFavorisList, NeighbourFragment neighbourFragmentFavoris){
        neighboursFavorisList.remove(neighbour);
        neighbourFragmentFavoris.initList(neighboursFavorisList);
    }
}
