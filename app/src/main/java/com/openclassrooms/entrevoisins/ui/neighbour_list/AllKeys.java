package com.openclassrooms.entrevoisins.ui.neighbour_list;

import java.io.Serializable;

abstract class AllKeys implements Serializable {

    //NeighbourFragment to NeighbourFragment
    static final String BUNDLE_FRAG_INIT_LIST_NEIGHBOUR = "BUNDLE_FRAG_INIT_LIST_NEIGHBOUR";

    static final String BUNDLE_FRAG_INIT_ISFAVORIS = "BUNDLE_FRAG_ISFAVORIS";

    //DetailNeighbourActivity to ListNeighbourActivity
    static final int DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE = 21;


    //DetailNeighbourActivity to ListNeighbourActivity
    static final String INTENT_ENVOIE_DETAIL_NEIGHBOUR = "INTENT_ENVOIE_DETAIL_NEIGHBOUR";

    //ListNeighbourActivity to DetailNeighbourActivity
    static final String INTENT_DETAIL_RETOUR_FAVORIS = "INTENT_DETAIL_RETOUR_FAVORIS";

}
