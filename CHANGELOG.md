# Changelog

## [0.2.6] - 2025-08-15

### Changes
- entries can now be locked via gamestages
- entries can now be visible only when the player is in a specific gamemode
- entries can now be visible only when the player is in a specific dimension
- fixed an error with the ic2 book as it's still WIP and should not have been registered

Syntax for your page json files:
```json
{
  "name": "Crafting Test",
  "icon": "minecraft:crafting_table",
  "category": "test_category",
  "requirements": [
    {
      "type": "gamemode",
      "trigger": "creative"
    }, {
      "type": "gamestage",
      "trigger": "test"
    }, {
      "type": "dimension",
      "trigger": "the_nether"
    }
  ],
  "pages": []
}
```

## [0.2.5] - 2025-03-17

### Changes
- fixed display of broken books in JEI and the Booklist
- fixed an issue where the page count in the booklist was higher than the actual page count

## [0.2.4] - 2025-01-29

### Changes
- fixed the JEI integration not working properly, now books should actually show up next to a recipe

## [0.2.3] - 2025-01-07

### Changes
- redid the config system (twice) -> please regen your config
- added my own inventory button, which can be placed freely, addresses [#2](https://github.com/Ender-Development/PatchouliBooks/issues/2)
- fixed the broken refmap

## [0.2.2] - 2024-11-29

### Changes
- fixed crash when a modpack book don't want to use my `BookExtension`

## [0.2.1] - 2024-11-19

### Changes
- added more keys to my ExtendedBooks
  - `book_plus` - enables user created books to default to my version of the patchouli book
  - `disabled` - disables the registration of the book (this is mostly for me to hide WIP books)
  - `pamphlet` - books no longer display as pamphlets by default, this key now allows you to enable it
- added a config option to disable the JEI integration
- removed my Patchouli Books book from the JEI integration as it is not a real book and only shows examples of the new page types
- backported [PatchouliButton](https://www.curseforge.com/minecraft/mc-mods/patchoulibutton) from 1.20
  - if you don't specify an override in the patchouli config, I now add a button that opens a GUI, which displays all loaded Patchouli books and gives you easy access to all of them
  - this comes with its own config option to disable it

### Books
- started working on IC2C
- started working on BuildCraft (FlashyGuitar603)

## [0.2.0] - 2024-11-12

### Changes
- added new JEI integration, which displays books that contain a page with the item you are looking at
- if there are multiple books with the item, they will be arranged in a grid
- the book only shows up if the related entry with the item is unlocked
- added the possibility to force lock a book entry, simply use the `force_lock` field in the entry json
- maybe now I have the time to actually write more books

## [0.1.2] - 2024-11-08

### Changes
- fixed server crash when trying to load the mod

## [0.1.1] - 2024-11-06

### Changes
- fixed the curseforge link in the patchouli books
- removed the reflection part to register the new page types as it causes a crash when launched outside the dev environment
- refactored the custom landing page to no longer rely on a mixin

## [0.1.0] - 2024-11-03

### Changes
- implemented pamphlets for books that have only 1 category
- added HEI/JEI integratiopn so you can look up all recipes and usages of all items rendered in any book
- added a new page type for [Pedestal Crafting](https://www.curseforge.com/minecraft/mc-mods/pedestal-crafting) but should also be usable for stuff like [Extended Crafting](https://www.curseforge.com/minecraft/mc-mods/extended-crafting-nomifactory-edition)
- added my own version for most of the default pages
  - these are more modular and space efficient
  - they allow for more customization and don't simply break if a field is missing
  - furthermore they provide additional debug information
  - these advanced versions of the pages can be accessed by appending a `+` to the page name (e.g. `text+`)

### Books
- added GenDustry
- added Moar Boats
- added Patchouli Books (obviously)
- added Pressure Pipes
- added Thermal Logistics