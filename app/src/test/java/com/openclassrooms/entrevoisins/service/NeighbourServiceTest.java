package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //TODO
    @Test
    public void deleteNeighbourFavorisWithSuccess() throws Exception {
        //Arrange
        Neighbour neighbourFavorisToDelete = service.getNeighbours().get(0);
        service.addNeighbourToListFavoris(neighbourFavorisToDelete);
        //Act
        service.deleteNeighbourFavoris(neighbourFavorisToDelete);
        //Assert
        assertFalse(service.getNeighboursFavoris().contains(neighbourFavorisToDelete));
    }

    @Test
    public void deleteNeighbourFromBothLists() throws Exception {
        //Arrange
        Neighbour neighbourToDeleteInBothList = service.getNeighbours().get(0);
        service.addNeighbourToListFavoris(neighbourToDeleteInBothList);
        //Act
        service.deleteNeighbour(neighbourToDeleteInBothList);
        //Assert
        assertFalse(service.getNeighbours().contains(neighbourToDeleteInBothList) && service.getNeighboursFavoris().contains(neighbourToDeleteInBothList));
    }

    @Test
    public void addNeighbourToListFavorisWithsuccess() throws Exception {
        //Arrange
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        //Act
        service.addNeighbourToListFavoris(neighbourToAdd);
        //Assert
        assertTrue(service.getNeighboursFavoris().contains(neighbourToAdd));
    }
}
