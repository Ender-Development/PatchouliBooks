# Changelog

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
- fixed the curseforge link in the patchouli books book
- removed the reflection part to register the new page types as it causes a crash when launched outside of the dev environment
- refactored the custom landing page to no longer rely on a mixin

## [0.1.0] - 2024-11-03

### Changes
- implemented pamphlets for books that have only 1 category
- added HEI/JEI integratiopn so you can lookup all recipes and usages of all items rendered in any book
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