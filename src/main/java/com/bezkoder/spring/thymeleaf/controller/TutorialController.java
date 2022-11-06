package com.bezkoder.spring.thymeleaf.controller;

import java.util.List;

import com.bezkoder.spring.thymeleaf.dto.TutorialDto;
import com.bezkoder.spring.thymeleaf.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bezkoder.spring.thymeleaf.entity.Tutorial;
import com.bezkoder.spring.thymeleaf.repository.TutorialRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TutorialController {

  @Autowired
  private TutorialRepository tutorialRepository;

  @Autowired
  private TutorialService tutorialService;

  @GetMapping("/tutorials")
  public String getAll(Model model, @Param("keyword") String keyword) {
    try {
      if (keyword != null) {
        model.addAttribute("keyword", keyword);
      }

      List<Tutorial> tutorials = tutorialService.findTutorials(keyword);
      model.addAttribute("tutorials", tutorials);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "tutorials";
  }

  @GetMapping("/tutorials/new")
  public String addTutorial(Model model) {
    Tutorial tutorial = new Tutorial();
    tutorial.setPublished(true);

    model.addAttribute("tutorial", tutorial);
    model.addAttribute("pageTitle", "Create new Tutorial");

    return "tutorial_form";
  }

  @GetMapping("/tutorials/new2")
  public String addTutorial(Model model, HttpServletRequest request, HttpServletResponse response) {
    Tutorial tutorial = new Tutorial();
    tutorial.setPublished(true);

    model.addAttribute("tutorial", tutorial);
    model.addAttribute("pageTitle", "Create new Tutorial");

    // Set Http Status Code to Response
    response.setStatus(HttpStatus.ACCEPTED.value());

    return "tutorial_form";
  }

  @PostMapping("/tutorials/save")
  public String saveTutorial(TutorialDto dto, RedirectAttributes redirectAttributes) {
    try {
      Tutorial entity = Tutorial.of(dto);
      tutorialRepository.save(entity);

      redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/tutorials";
  }

  @GetMapping("/tutorials/{id}")
  public String editTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Tutorial tutorial = tutorialRepository.findById(id).get();

      model.addAttribute("tutorial", tutorial);
      model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");

      return "tutorial_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/tutorials";
    }
  }

  @GetMapping("/tutorials/delete/{id}")
  public String deleteTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      tutorialRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/tutorials";
  }

  @DeleteMapping("/tutorials/{id}")
  public String deleteTutorial2(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      tutorialRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/tutorials";
  }

  @GetMapping("/tutorials/{id}/published/{status}")
  public String updateTutorialPublishedStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean published,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      tutorialRepository.updatePublishedStatus(id, published);

      String status = published ? "published" : "disabled";
      String message = "The Tutorial id=" + id + " has been " + status;

      redirectAttributes.addFlashAttribute("message", message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/tutorials";
  }
}
