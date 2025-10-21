package com.yourname.xfiles.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import com.yourname.xfiles.XFiles;
import com.yourname.xfiles.objects.Archive;
import java.util.*;

public class ArchiveManager {
    private final XFiles plugin;
    private final Map<String, Archive> archives;
    private final FileConfiguration config;

    public ArchiveManager(XFiles plugin) {
        this.plugin = plugin;
        this.archives = new HashMap<>();
        this.config = plugin.getConfig();
    }

    public void loadArchives() {
        archives.clear();
        if (config.contains("archives")) {
            for (String key : config.getConfigurationSection("archives").getKeys(false)) {
                String path = "archives." + key;
                String name = config.getString(path + ".name", "Неизвестный архив");
                String description = config.getString(path + ".description", "Описание отсутствует");
                int level = config.getInt(path + ".level", 1);
                List<String> files = config.getStringList(path + ".files");
                
                Archive archive = new Archive(key, name, description, level, files);
                archives.put(key, archive);
            }
        }
        plugin.getLogger().info("Загружено архивов: " + archives.size());
    }

    public boolean createArchive(String id, String name, String description, int level, List<String> files) {
        if (archives.containsKey(id)) {
            return false;
        }
        
        Archive archive = new Archive(id, name, description, level, files);
        archives.put(id, archive);
        saveArchiveToConfig(archive);
        return true;
    }

    private void saveArchiveToConfig(Archive archive) {
        String path = "archives." + archive.getId();
        config.set(path + ".name", archive.getName());
        config.set(path + ".description", archive.getDescription());
        config.set(path + ".level", archive.getLevel());
        config.set(path + ".files", archive.getFiles());
        plugin.saveConfig();
    }

    public Archive getArchive(String id) {
        return archives.get(id);
    }

    public Map<String, Archive> getArchives() {
        return new HashMap<>(archives);
    }

    public boolean removeArchive(String id) {
        if (archives.containsKey(id)) {
            archives.remove(id);
            config.set("archives." + id, null);
            plugin.saveConfig();
            return true;
        }
        return false;
    }
}
