package com.yourname.xfiles.objects;

import java.util.List;

public class Archive {
    private final String id;
    private final String name;
    private final String description;
    private final int level;
    private final List<String> files;

    public Archive(String id, String name, String description, int level, List<String> files) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.files = files;
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

    public List<String> getFiles() {
        return files;
    }

    public String getFormattedInfo() {
        return "§6Архив: §f" + name + "\n" +
               "§6Уровень доступа: §f" + level + "\n" +
               "§6Описание: §f" + description + "\n" +
               "§6Файлов: §f" + files.size();
    }
}
