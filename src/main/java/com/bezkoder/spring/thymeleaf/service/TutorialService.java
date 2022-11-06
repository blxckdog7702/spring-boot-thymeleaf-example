package com.bezkoder.spring.thymeleaf.service;

import com.bezkoder.spring.thymeleaf.entity.Tutorial;
import com.bezkoder.spring.thymeleaf.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Transactional
    public List<Tutorial> findTutorials(String keyword) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (keyword == null) {
            tutorials.addAll(tutorialRepository.findAll());
        } else {
            tutorials.addAll(tutorialRepository.findByTitleContainingIgnoreCase(keyword));
        }

        return tutorials;
    }
}
