package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements Serializable, NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> neighboursFavoris = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public List<Neighbour> getNeighboursFavoris() {
        return neighboursFavoris;
    }

    @Override
    public void addNeighbourToListFavoris(Neighbour neighbour) {
        if (!neighboursFavoris.contains(neighbour)){
            neighboursFavoris.add(neighbour);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
            neighbours.remove(neighbour);
    }

    @Override
    public void deleteNeighbourFavoris(Neighbour neighbour) {
        neighboursFavoris.remove(neighbour);
    }
}
