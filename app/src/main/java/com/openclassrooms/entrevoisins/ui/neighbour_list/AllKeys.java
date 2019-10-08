package com.openclassrooms.entrevoisins.ui.neighbour_list;

import java.io.Serializable;

public abstract class AllKeys implements Serializable {

    //NeighbourFragment to NeighbourFragment
    public static final String BUNDLE_FRAG_INIT_LIST_NEIGHBOUR = "BUNDLE_FRAG_INIT_LIST_NEIGHBOUR";
/*
    public static final String BUNDLE_INIT_INTERFACE_LISTACT_TO_FRAG = "BUNDLE_INIT_INTERFACE_LISTACT_TO_FRAG";
*/
    public static final String BUNDLE_FRAG_INIT_ISFAVORIS = "BUNDLE_FRAG_ISFAVORIS";


    //DetailNeighbourActivity to ListNeighbourActivity
    public static final int DETAILNEIGHBOUR_ACTIVITY_REQUEST_CODE = 21;


    //DetailNeighbourActivity to ListNeighbourActivity
    public static final String INTENT_ENVOIE_DETAIL_NEIGHBOUR = "INTENT_ENVOIE_DETAIL_NEIGHBOUR";

    //ListNeighbourActivity to DetailNeighbourActivity
    public static final String INTENT_DETAIL_RETOUR_FAVORIS = "INTENT_DETAIL_RETOUR_FAVORIS";

}
