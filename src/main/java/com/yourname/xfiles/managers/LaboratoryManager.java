package com.yourname.xfiles.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import com.yourname.xfiles.XFiles;
import com.yourname.xfiles.objects.Laboratory;
import java.util.*;

public class LaboratoryManager {
    private final XFiles plugin;
    private final Map<String, Laboratory> laboratories;
    private final FileConfiguration config;

    public LaboratoryManager(XFiles plugin) {
        this.plugin = plugin;
        this.laboratories = new HashMap<>();
        this.config = plugin.getConfig();
    }

    public void loadLaboratories() {
        laboratories.clear();
        if (config.contains("laboratories")) {
            for (String key : config.getConfigurationSection("laboratories").getKeys(false)) {
                String path = "laboratories." + key;
                String name = config.getString(path + ".name", "Неизвестная лаборатория");
                String description = config.getString(path + ".description", "Описание отсутствует");
                int level = config.getInt(path + ".level", 1);
                List<String> research = config.getStringList(path + ".research");
                
                Laboratory laboratory = new Laboratory(key, name, description, level, research);
                laboratories.put(key, laboratory);
            }
        }
        plugin.getLogger().info("Загружено лабораторий: " + laboratories.size());
    }

    public boolean createLaboratory(String id, String name, String description, int level, List<String> research) {
        if (laboratories.containsKey(id)) {
            return false;
        }
        
        Laboratory laboratory = new Laboratory(id, name, description, level, research);
        laboratories.put(id, laboratory);
        saveLaboratoryToConfig(laboratory);
        return true;
    }

    private void saveLaboratoryToConfig(Laboratory laboratory) {
        String path = "laboratories." + laboratory.getId();
        config.set(path + ".name", laboratory.getName());
        config.set(path + ".description", laboratory.getDescription());
        config.set(path + ".level", laboratory.getLevel());
        config.set(path + ".research", laboratory.getResearch());
        plugin.saveConfig();
    }

    public Laboratory getLaboratory(String id) {
        return laboratories.get(id);
    }

    public Map<String, Laboratory> getLaboratories() {
        return new HashMap<>(laboratories);
    }

    public boolean removeLaboratory(String id) {
        if (laboratories.containsKey(id)) {
            laboratories.remove(id);
            config.set("laboratories." + id, null);
            plugin.saveConfig();
            return true;
        }
        return false;
    }
}
