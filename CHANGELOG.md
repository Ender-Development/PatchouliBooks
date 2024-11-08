# Changelog

## [0.1.2] - 2024-11-08

### Changes

- fixed server crash when trying to load the mod

## [0.1.1] - 2024-11-06

### Changes
- fixed the curseforge link in the patchouli books book
- removed the reflection part to register the new page types as it causes a crash when launched outside of the dev environment
- refactored the custom lading page to no longer rely on a mixin

## [0.1.0] - 2024-11-03

### Changes
- implemented pamphlets for books that have only 1 category
- added HEI/JEI integratiopn so you can lookup all recipes and usages of all items rendered in any book
- added a new page type for [Pedestal Crafting](https://www.curseforge.com/minecraft/mc-mods/pedestal-crafting) but should also be useable for stuff like [Extended Crafting](https://www.curseforge.com/minecraft/mc-mods/extended-crafting-nomifactory-edition)
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