package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class NeighbourFragment extends Fragment implements Serializable, FragmentToRVAdapter {

    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private ListActivityToFragment mListActivityToFragment;
    private boolean mFragmentFavoris;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(List<Neighbour> list, boolean favoris) {
        NeighbourFragment fragment = new NeighbourFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AllKeys.BUNDLE_FRAG_INIT_LIST_NEIGHBOUR, (Serializable) list);
        bundle.putSerializable(AllKeys.BUNDLE_FRAG_INIT_ISFAVORIS, favoris);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setInterface(ListActivityToFragment listActivityToFragment){
        mListActivityToFragment = listActivityToFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mNeighbours = (List<Neighbour>) this.getArguments().getSerializable(AllKeys.BUNDLE_FRAG_INIT_LIST_NEIGHBOUR);
            mFragmentFavoris = this.getArguments().getBoolean(AllKeys.BUNDLE_FRAG_INIT_ISFAVORIS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        initList(this.mNeighbours);
        return view;
    }

    /**
     * Init the List of neighbours
     */
    public void initList(List<Neighbour> mNeighbours) {
        MyNeighbourRecyclerViewAdapter myNeighbourRecyclerViewAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours, this);
        mRecyclerView.setAdapter(myNeighbourRecyclerViewAdapter);
    }

    @Override
    public void LaunchMyActivity (Neighbour neighbour) {
        Intent intent = new Intent(getActivity(), DetailNeighbourActivity.class);
        intent.putExtra(AllKeys.INTENT_ENVOIE_DETAIL_NEIGHBOUR, neighbour);
        Objects.requireNonNull(getActivity()).startActivityForResult(intent,AllKeys.DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void callDeleteNeighbour(Neighbour neighbour) {
        if(mFragmentFavoris){
            mListActivityToFragment.callBackDeleteNeighbourFavoris(neighbour);
        }else {
            mListActivityToFragment.callBackDeleteNeighbour(neighbour);
        }
    }
}
