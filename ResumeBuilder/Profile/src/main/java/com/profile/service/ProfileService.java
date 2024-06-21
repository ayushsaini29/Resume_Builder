package com.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.dao.ProfileRepository;
import com.profile.entity.Profile;
import com.profile.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, Profile profileDetails) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id :: " + id));
        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setEducation(profileDetails.getEducation());
        profile.setExperience(profileDetails.getExperience());
        profile.setProjects(profileDetails.getProjects());
        profile.setSkills(profileDetails.getSkills());
        profile.setCertifications(profileDetails.getCertifications());
        profile.setAdditionalAchievements(profileDetails.getAdditionalAchievements());
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile not found for this id :: " + id));
        profileRepository.delete(profile);
    }
}

