package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

public interface ListActivityToFragment {
    void callBackDeleteNeighbour(Neighbour neighbour);
    void callBackDeleteNeighbourFavoris(Neighbour neighbour);
}
