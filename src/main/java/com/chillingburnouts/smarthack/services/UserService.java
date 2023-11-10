package com.chillingburnouts.smarthack.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) { // todo change
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setCourses(user.getCourses());
        return userRepository.save(existingUser);
    }

    public User patchUser(Long id, Map<String, Object> updates) {
        // Find the existing user, apply the updates, and save it
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (updates.containsKey("name")) {
            existingUser.setName((String) updates.get("name"));
        }
        if (updates.containsKey("courses")) {
            // Assume that updates.get("courses") returns a List<Course>
            existingUser.setCourses((Set<Course>) updates.get("courses"));
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.deleteById(id);
    }
}
