package com.yourname.xfiles.objects;

import java.util.List;

public class Laboratory {
    private final String id;
    private final String name;
    private final String description;
    private final int level;
    private final List<String> research;

    public Laboratory(String id, String name, String description, int level, List<String> research) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.research = research;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public List<String> getResearch() {
        return research;
    }

    public String getFormattedInfo() {
        return "§bЛаборатория: §f" + name + "\n" +
               "§bУровень безопасности: §f" + level + "\n" +
               "§bОписание: §f" + description + "\n" +
               "§bИсследований: §f" + research.size();
    }
}
