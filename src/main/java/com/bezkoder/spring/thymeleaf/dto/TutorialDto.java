package com.bezkoder.spring.thymeleaf.dto;

public class TutorialDto {

    private Integer id;

    private String title;

    private String description;

    private int level;

    private boolean published;

    public TutorialDto(Integer id, String title, String description, int level, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public boolean isPublished() {
        return published;
    }
}
