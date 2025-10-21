package com.yourname.xfiles.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.yourname.xfiles.XFiles;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private final XFiles plugin;
    private FileConfiguration messages;
    private File messagesFile;

    public ConfigManager(XFiles plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Сохранение конфига по умолчанию
        plugin.saveDefaultConfig();
        
        // Загрузка messages.yml
        loadMessages();
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        
        // Загрузка дефолтных сообщений из ресурсов
        YamlConfiguration defaultMessages = YamlConfiguration.loadConfiguration(
            new InputStreamReader(plugin.getResource("messages.yml"), StandardCharsets.UTF_8));
        messages.setDefaults(defaultMessages);
        messages.options().copyDefaults(true);
        
        try {
            messages.save(messagesFile);
        } catch (Exception e) {
            plugin.getLogger().warning("Не удалось сохранить messages.yml: " + e.getMessage());
        }
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        loadMessages();
    }

    public String getMessage(String path) {
        return messages.getString(path, "&cСообщение не найдено: " + path).replace('&', '§');
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
