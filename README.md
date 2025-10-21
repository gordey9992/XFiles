# XFiles - The X-Files Plugin for Minecraft

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.8-purple?style=for-the-badge)
![Purpur](https://img.shields.io/badge/Server-Purpur-orange?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

Плагин добавляет в Minecraft систему архивов и лабораторий в стиле "Секретных материалов" (The X-Files).

## 🎯 Возможности

- **📁 Система архивов** - Засекреченные документы и отчеты
- **🔬 Научные лаборатории** - Исследования инопланетных технологий
- **🛡️ Уровни доступа** - Разграничение прав доступа к информации
- **🌍 Локализация** - Полная поддержка русского языка
- **⚡ Легковесность** - Оптимизированная работа сервера

## 📦 Установка

1. Скачайте последнюю версию плагина из [Releases](https://github.com/yourname/XFiles/releases)
2. Поместите файл `XFiles.jar` в папку `plugins/` вашего сервера
3. Перезапустите сервер
4. Настройте плагин через файлы в папке `plugins/XFiles/`

## 🎮 Команды

### Основные команды:
| Команда | Описание | Права |
|---------|-----------|--------|
| `/xfiles help` | Показать справку | `xfiles.use` |
| `/xfiles reload` | Перезагрузить конфигурацию | `xfiles.reload` |
| `/xfiles archives` | Список всех архивов | `xfiles.archives.list` |
| `/xfiles archive <id>` | Информация об архиве | `xfiles.archives.view` |
| `/xfiles laboratories` | Список лабораторий | `xfiles.laboratories.list` |
| `/xfiles laboratory <id>` | Информация о лаборатории | `xfiles.laboratories.view` |

## 🔧 Конфигурация

### Структура архива:
```yaml
archives:
  archive_id:
    name: "Название архива"
    description: "Описание архива"
    level: 3  # Уровень доступа (1-10)
    files:
      - "Файл 1"
      - "Файл 2"
