package com.chillingburnouts.smarthack.services;

import com.chillingburnouts.smarthack.entities.Portofolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.PortofolioRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PortofolioRepository portofolioRepository;

    public List<Portofolio> findAll() {
        return portofolioRepository.findAll();
    }

    public Portofolio findById(Long id) {
        return portofolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public Portofolio save(Portofolio portofolio) {
        return portofolioRepository.save(portofolio);
    }

    public Portofolio updateUser(Long id, Portofolio portofolio) { // todo change
        Portofolio existingPortofolio = portofolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingPortofolio.setName(portofolio.getName());
        existingPortofolio.setCompanies(portofolio.getCompanies());
        return portofolioRepository.save(existingPortofolio);
    }

    public Portofolio patchUser(Long id, Map<String, Object> updates) {
        // Find the existing user, apply the updates, and save it
        Portofolio existingPortofolio = portofolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (updates.containsKey("name")) {
            existingPortofolio.setName((String) updates.get("name"));
        }
        if (updates.containsKey("courses")) {
            // Assume that updates.get("courses") returns a List<Course>
            existingPortofolio.setCompanies((Set<Company>) updates.get("courses"));
        }
        return portofolioRepository.save(existingPortofolio);
    }

    public void deleteUser(Long id) {
        portofolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        portofolioRepository.deleteById(id);
    }
}
