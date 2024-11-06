package tn.esprit.tpfoyer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoyerServiceImplMockTest {

    @InjectMocks
    private FoyerServiceImpl foyerService; // Service to be tested

    @Mock
    private FoyerRepository foyerRepository; // Mocked repository

    // Test for retrieving all foyers
    @Test
    @Order(1)
    public void testRetrieveAllFoyers() {
        List<Foyer> foyers = new ArrayList<>();
        foyers.add(new Foyer(1L, "Foyer1", 100L, null, null));
        foyers.add(new Foyer(2L, "Foyer2", 200L, null, null));

        // Mock repository behavior
        when(foyerRepository.findAll()).thenReturn(foyers);

        // Call service method
        List<Foyer> result = foyerService.retrieveAllFoyers();

        // Verify the result and interactions
        assertNotNull(result, "The result should not be null");
        assertEquals(2, result.size(), "The list size should match");
        verify(foyerRepository).findAll();
    }

    // Test for retrieving a specific foyer by ID
    @Test
    @Order(2)
    public void testRetrieveFoyer() {
        Foyer foyer = new Foyer(1L, "Foyer1", 100L, null, null);

        // Mock repository behavior
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Call service method
        Foyer result = foyerService.retrieveFoyer(1L);

        // Verify the result and interactions
        assertNotNull(result, "The retrieved foyer should not be null");
        assertEquals("Foyer1", result.getNomFoyer(), "The name should match");
        verify(foyerRepository).findById(1L);
    }

    // Test for adding a foyer
    @Test
    @Order(3)
    public void testAddFoyer() {
        Foyer foyer = new Foyer(null, "Foyer1", 100L, null, null);

        // Mock repository behavior
        when(foyerRepository.save(Mockito.any(Foyer.class))).thenReturn(foyer);

        // Call service method
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Verify the result and interactions
        assertNotNull(savedFoyer, "The saved foyer should not be null");
        assertEquals("Foyer1", savedFoyer.getNomFoyer(), "The name should match");
        verify(foyerRepository).save(Mockito.any(Foyer.class));
    }

    // Test for modifying a foyer
    @Test
    @Order(4)
    public void testModifyFoyer() {
        Foyer foyer = new Foyer(1L, "Foyer1", 100L, null, null);

        // Mock repository behavior
        when(foyerRepository.save(Mockito.any(Foyer.class))).thenReturn(foyer);

        // Call service method
        Foyer updatedFoyer = foyerService.modifyFoyer(foyer);

        // Verify the result and interactions
        assertNotNull(updatedFoyer, "The modified foyer should not be null");
        assertEquals("Foyer1", updatedFoyer.getNomFoyer(), "The name should match");
        verify(foyerRepository).save(Mockito.any(Foyer.class));
    }

    // Test for removing a foyer by ID
    @Test
    @Order(5)
    public void testRemoveFoyer() {
        Long foyerId = 1L;

        // Call service method
        foyerService.removeFoyer(foyerId);

        // Verify that the repository's deleteById method was called
        verify(foyerRepository).deleteById(foyerId);
    }
}
