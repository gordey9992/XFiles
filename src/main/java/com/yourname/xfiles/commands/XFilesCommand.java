package com.yourname.xfiles.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import com.yourname.xfiles.XFiles;
import com.yourname.xfiles.managers.ConfigManager;
import java.util.*;

public class XFilesCommand implements CommandExecutor, TabCompleter {
    private final XFiles plugin;
    private final ConfigManager configManager;

    public XFilesCommand(XFiles plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                sendHelp(sender);
                break;
                
            case "reload":
                if (!sender.hasPermission("xfiles.reload")) {
                    sender.sendMessage(configManager.getMessage("no-permission"));
                    return true;
                }
                configManager.reloadConfigs();
                plugin.getArchiveManager().loadArchives();
                plugin.getLaboratoryManager().loadLaboratories();
                sender.sendMessage(configManager.getMessage("config-reloaded"));
                break;
                
            case "archives":
                if (!sender.hasPermission("xfiles.archives.list")) {
                    sender.sendMessage(configManager.getMessage("no-permission"));
                    return true;
                }
                listArchives(sender);
                break;
                
            case "archive":
                if (args.length < 2) {
                    sender.sendMessage(configManager.getMessage("usage-archive"));
                    return true;
                }
                if (!sender.hasPermission("xfiles.archives.view")) {
                    sender.sendMessage(configManager.getMessage("no-permission"));
                    return true;
                }
                showArchive(sender, args[1]);
                break;
                
            case "laboratories":
                if (!sender.hasPermission("xfiles.laboratories.list")) {
                    sender.sendMessage(configManager.getMessage("no-permission"));
                    return true;
                }
                listLaboratories(sender);
                break;
                
            case "laboratory":
                if (args.length < 2) {
                    sender.sendMessage(configManager.getMessage("usage-laboratory"));
                    return true;
                }
                if (!sender.hasPermission("xfiles.laboratories.view")) {
                    sender.sendMessage(configManager.getMessage("no-permission"));
                    return true;
                }
                showLaboratory(sender, args[1]);
                break;
                
            default:
                sender.sendMessage(configManager.getMessage("unknown-command"));
                break;
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        String[] helpMessages = {
            "§6=== The X-Files - Помощь ===",
            "§f/xfiles help §7- Показать это сообщение",
            "§f/xfiles reload §7- Перезагрузить конфигурацию",
            "§f/xfiles archives §7- Список всех архивов",
            "§f/xfiles archive <id> §7- Информация об архиве",
            "§f/xfiles laboratories §7- Список всех лабораторий",
            "§f/xfiles laboratory <id> §7- Информация о лаборатории"
        };
        
        sender.sendMessage(helpMessages);
    }

    private void listArchives(CommandSender sender) {
        Map<String, com.yourname.xfiles.objects.Archive> archives = plugin.getArchiveManager().getArchives();
        if (archives.isEmpty()) {
            sender.sendMessage(configManager.getMessage("no-archives"));
            return;
        }

        sender.sendMessage("§6=== Список архивов ===");
        for (com.yourname.xfiles.objects.Archive archive : archives.values()) {
            sender.sendMessage("§e" + archive.getId() + " §f- " + archive.getName() + " §7(Уровень: " + archive.getLevel() + ")");
        }
    }

    private void showArchive(CommandSender sender, String archiveId) {
        com.yourname.xfiles.objects.Archive archive = plugin.getArchiveManager().getArchive(archiveId);
        if (archive == null) {
            sender.sendMessage(configManager.getMessage("archive-not-found"));
            return;
        }

        sender.sendMessage("§6=== Информация об архиве ===");
        sender.sendMessage(archive.getFormattedInfo());
        if (!archive.getFiles().isEmpty()) {
            sender.sendMessage("§6Файлы:");
            for (String file : archive.getFiles()) {
                sender.sendMessage("§7- §f" + file);
            }
        }
    }

    private void listLaboratories(CommandSender sender) {
        Map<String, com.yourname.xfiles.objects.Laboratory> laboratories = plugin.getLaboratoryManager().getLaboratories();
        if (laboratories.isEmpty()) {
            sender.sendMessage(configManager.getMessage("no-laboratories"));
            return;
        }

        sender.sendMessage("§6=== Список лабораторий ===");
        for (com.yourname.xfiles.objects.Laboratory laboratory : laboratories.values()) {
            sender.sendMessage("§b" + laboratory.getId() + " §f- " + laboratory.getName() + " §7(Уровень: " + laboratory.getLevel() + ")");
        }
    }

    private void showLaboratory(CommandSender sender, String laboratoryId) {
        com.yourname.xfiles.objects.Laboratory laboratory = plugin.getLaboratoryManager().getLaboratory(laboratoryId);
        if (laboratory == null) {
            sender.sendMessage(configManager.getMessage("laboratory-not-found"));
            return;
        }

        sender.sendMessage("§6=== Информация о лаборатории ===");
        sender.sendMessage(laboratory.getFormattedInfo());
        if (!laboratory.getResearch().isEmpty()) {
            sender.sendMessage("§bИсследования:");
            for (String research : laboratory.getResearch()) {
                sender.sendMessage("§7- §f" + research);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.addAll(Arrays.asList("help", "reload", "archives", "archive", "laboratories", "laboratory"));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "archive":
                    completions.addAll(plugin.getArchiveManager().getArchives().keySet());
                    break;
                case "laboratory":
                    completions.addAll(plugin.getLaboratoryManager().getLaboratories().keySet());
                    break;
            }
        }
        
        return completions;
    }
}
