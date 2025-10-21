package com.yourname.xfiles;

import org.bukkit.plugin.java.JavaPlugin;
import com.yourname.xfiles.managers.ConfigManager;
import com.yourname.xfiles.managers.ArchiveManager;
import com.yourname.xfiles.managers.LaboratoryManager;
import com.yourname.xfiles.commands.XFilesCommand;

public class XFiles extends JavaPlugin {
    private static XFiles instance;
    private ConfigManager configManager;
    private ArchiveManager archiveManager;
    private LaboratoryManager laboratoryManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Инициализация менеджеров
        this.configManager = new ConfigManager(this);
        this.archiveManager = new ArchiveManager(this);
        this.laboratoryManager = new LaboratoryManager(this);
        
        // Загрузка конфигураций
        configManager.loadConfigs();
        archiveManager.loadArchives();
        laboratoryManager.loadLaboratories();
        
        // Регистрация команд
        getCommand("xfiles").setExecutor(new XFilesCommand(this));
        
        getLogger().info("§aПлагин The X-Files успешно запущен!");
        getLogger().info("§aЗагружено архивов: " + archiveManager.getArchives().size());
        getLogger().info("§aЗагружено лабораторий: " + laboratoryManager.getLaboratories().size());
    }

    @Override
    public void onDisable() {
        getLogger().info("§cПлагин The X-Files отключен");
    }

    public static XFiles getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ArchiveManager getArchiveManager() {
        return archiveManager;
    }

    public LaboratoryManager getLaboratoryManager() {
        return laboratoryManager;
    }
}
