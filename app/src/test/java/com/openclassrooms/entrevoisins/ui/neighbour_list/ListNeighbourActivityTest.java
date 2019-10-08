package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ListNeighbourActivityTest {

    private Neighbour mNeighbour;
    private List<Neighbour> mNeighbours;

    @Test
    public void neighbour_is_favoris(){
        mNeighbour = new Neighbour(1, "Caroline", "http://i.pravatar.cc/150?u=a042581f4e29026704d", "+33 6 12 34 56 78");
        mNeighbour.setFavoris(true);
        assertEquals(true, mNeighbour.isFavoris());
    }

    @Test
    public void neighbour_is_not_favoris(){
        mNeighbour = new Neighbour(1, "Caroline", "http://i.pravatar.cc/150?u=a042581f4e29026704d", "+33 6 12 34 56 78");
        assertEquals(false, mNeighbour.isFavoris());
    }
}