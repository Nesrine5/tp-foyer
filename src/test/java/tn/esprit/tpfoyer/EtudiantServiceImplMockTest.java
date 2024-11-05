package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplMockTest {

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    EtudiantServiceImpl etudiantService;

    Etudiant etudiant = new Etudiant("f1", "l1", 123456L, new Date());

    List<Etudiant> listEtudiants = new ArrayList<Etudiant>() {
        {
            add(new Etudiant("f2", "l2", 654321L, new Date()));
            add(new Etudiant("f3", "l3", 789012L, new Date()));
        }
    };

    @Test
    public void testRetrieveEtudiant() {
        Mockito.when(etudiantRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(etudiant));
        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(1L); // Adjust to match your test setup
        Assertions.assertNotNull(retrievedEtudiant);
        Assertions.assertEquals(etudiant.getCinEtudiant(), retrievedEtudiant.getCinEtudiant());
    }

    @Test
    public void testRetrieveEtudiantNotFound() {
        Mockito.when(etudiantRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            etudiantService.retrieveEtudiant(999L); // ID that does not exist
        });
    }

    @Test
    public void testRetrieveAllEtudiants() {
        Mockito.when(etudiantRepository.findAll()).thenReturn(listEtudiants);
        List<Etudiant> retrievedList = etudiantService.retrieveAllEtudiants();
        Assertions.assertEquals(2, retrievedList.size());
    }

    @Test
    public void testAddEtudiant() {
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);
        Etudiant addedEtudiant = etudiantService.addEtudiant(etudiant);
        Assertions.assertNotNull(addedEtudiant);
        Assertions.assertEquals(etudiant.getCinEtudiant(), addedEtudiant.getCinEtudiant());
    }

    @Test
    public void testModifyEtudiant() {
        Mockito.when(etudiantRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        Etudiant modifiedEtudiant = etudiantService.modifyEtudiant(etudiant);
        Assertions.assertNotNull(modifiedEtudiant);
        Assertions.assertEquals(etudiant.getCinEtudiant(), modifiedEtudiant.getCinEtudiant());
    }

    @Test
    public void testModifyEtudiantNotFound() {
        Mockito.when(etudiantRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            etudiantService.modifyEtudiant(etudiant);
        });
    }

    @Test
    public void testRemoveEtudiant() {
        Mockito.when(etudiantRepository.existsById(Mockito.anyLong())).thenReturn(true);
        etudiantService.removeEtudiant(1L);
        Mockito.verify(etudiantRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testRemoveEtudiantNotFound() {
        Mockito.when(etudiantRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            etudiantService.removeEtudiant(999L); // ID that does not exist
        });
    }
}
