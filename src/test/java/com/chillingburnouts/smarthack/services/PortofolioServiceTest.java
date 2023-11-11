package com.chillingburnouts.smarthack.services;

import com.chillingburnouts.smarthack.entities.Portofolio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.PortofolioRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortofolioServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private PortofolioRepository portofolioRepository;

    @Test
    void findAll() {
        List<Portofolio> portofolioList = Arrays.asList(new Portofolio(), new Portofolio());
        when(portofolioRepository.findAll()).thenReturn(portofolioList);

        List<Portofolio> result = userService.findAll();
        assertEquals(portofolioList, result);
    }

    @Test
    void findById() {
        Portofolio portofolio = new Portofolio();
        portofolio.setId(1L);
        when(portofolioRepository.findById(1L)).thenReturn(Optional.of(portofolio));

        Portofolio result = userService.findById(1L);
        assertEquals(portofolio, result);
    }

    @Test
    void save() {
        Portofolio portofolio = new Portofolio();
        when(portofolioRepository.save(portofolio)).thenReturn(portofolio);

        Portofolio result = userService.save(portofolio);
        assertEquals(portofolio, result);
    }

    @Test
    void updateUser() {
        Portofolio existingPortofolio = new Portofolio();
        Portofolio newPortofolio = new Portofolio();
        newPortofolio.setName("New name");
        newPortofolio.setCompanies(new HashSet<>());
        when(portofolioRepository.findById(1L)).thenReturn(Optional.of(existingPortofolio));
        when(portofolioRepository.save(existingPortofolio)).thenReturn(existingPortofolio);

        Portofolio result = userService.updateUser(1L, newPortofolio);
        assertEquals("New name", result.getName());
    }

    @Test
    void patchUser() {
        Portofolio existingPortofolio = new Portofolio();
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated name");
        Set<Company> updatedCours = new HashSet<>();
        updates.put("courses", updatedCours);
        when(portofolioRepository.findById(1L)).thenReturn(Optional.of(existingPortofolio));
        when(portofolioRepository.save(existingPortofolio)).thenReturn(existingPortofolio);

        Portofolio result = userService.patchUser(1L, updates);
        assertEquals("Updated name", result.getName());
        assertEquals(updatedCours, result.getCompanies());
    }

    @Test
    void deleteUser() {
        Portofolio portofolio = new Portofolio();
        portofolio.setId(1L);
        when(portofolioRepository.findById(1L)).thenReturn(Optional.of(portofolio));

        userService.deleteUser(1L);
        verify(portofolioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(portofolioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });
    }
}