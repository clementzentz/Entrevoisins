package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

public interface FragmentToRVAdapter {
     void LaunchMyActivity(Neighbour neighbour);
     void callRemoveNeighbour(Neighbour neighbour);
}
