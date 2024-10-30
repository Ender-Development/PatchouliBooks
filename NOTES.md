# Notes

Just a few things I encountered while working on this project.

---

![Note](https://img.shields.io/badge/-Note-lightgrey) This is a note. It's just a little piece of information that I found interesting or funny.

![Warning](https://img.shields.io/badge/-Warning-red) This is a warning. It's a piece of information about something that isn't fully implemented yet or could cause issues under certain circumstances.

![Important](https://img.shields.io/badge/-Important-blue) This is important. It's a piece of information that is crucial to the understanding why I did things the way I did.

---

## Table of Contents

- [Patchouli](#patchouli)
- [Patchouli Books](#patchouli-books)
- [Moar Boats](#moar-boats)

## Patchouli

![Note](https://img.shields.io/badge/-Note-lightgrey)
I thought after working so much with this mod in my modpack [Zerblands-Remastered](https://github.com/MasterEnderman/Zerblands-Remastered) I would know most of its features. But I was wrong. I didn't know that you could disable entries or whole categories in the book by using the flag system that allows checking for a loaded mod by default. This was one of the first features I wanted to implement on my end. Looks like this isn't necessary anymore.

![Note](https://img.shields.io/badge/-Note-lightgrey)
I also learned about `ComponentProcessors` they allow a developer to provide a book with actual values straight from the source code. No idea why nobody uses this feature. I really like to have some numbers, when it comes to working with various different mods. Most of the time I have to look them up myself. Now I may be able to use some Accessor Mixins to get access to various values of the mods I plan to document. That way all information would still be correct, even if someone alters some values in the config files. I anyone wants to read about them [here](https://vazkiimods.github.io/Patchouli/docs/patchouli-advanced/component-processors/) is the link to the documentation.

## Patchouli Books

![Important](https://img.shields.io/badge/-Important-blue)
**Why does this need to be a mod in the first place?** Well technically I could release all of these books as a resource pack. But this would mean it would load all books in every instance instead of only the ones that are present. This is one of the first things I implemented in `PatchouliBooks`. I wrote a little mixins that allows me to check if the mod for one of my books is actually loaded, before I allow Patchouli to load the book for it. Now I can bundle as many books as I want and it still only loads the ones that are actually needed. The second most important thing is the use of `ComponentProcessors`. That way I can access most of the internal mod values and come up with special templates to represent them to the player. This is something that wouldn't be possible with a resource pack. Lastly there are a few ideas I have in mind that I may want to implement. I looked through some of the newer add-ons in the modern Minecraft version and encountered [PatchouliButton](https://www.curseforge.com/minecraft/mc-mods/patchoulibutton). This replaces the inventory book with a GUI list that shows all available Patchouli books without the need of having them in your inventory. Pretty need of you ask me. But I've never done anything GUI related yet, so this may be something for the future. I already have a list of more possible features in my [README](README.md#possible-features).

## Mixins

![Important](https://img.shields.io/badge/-Important-blue)
I already talked about them, but mixins can't be underestimated. They are a powerful tool to access the internal values of a mod at tweak things as you see fit. I thought about going through my mixins and explaining why I added them in the first place, so this would be a good place to start.

### [BookContentsMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookContentsMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
Patchouli comes with a "version" system by default, that means the book gets a tooltip that say `x`th Edition. I didn't like this, as I prefer to use [SemVer](https://semver.org). So I added a special check to allow the usage of [SemVer](https://semver.org) instead of the default version system. All it does is checking the input matches it against a regex and return a translated I18n string.

### [BookEntryMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookEntryMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
By default you have to set a flag in your entry.json if you want to mark it as "already-read", so it displays without the notification symbol. For the amount of entries I'm going to write I want to make the writing progress as quick as possible. The more stuff that's set correctly by default the better. So i circumvented the need of setting the flag in the entry.json by checking if the loaded entry is from one of my books and if it is, I set the flag to true.

### [BookRegistryMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookRegistryMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
I already mentioned this one before, when I talked about the mixin to disable my book if the related mod isn't loaded. This was all it did for the longest time, but I wasn't satisfied with the limited option the default `Book.class` offered. That's why I created the [BookExtension.class](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/extension/Bookextension.java). This offers me all possibilities to tweak my books as I see fit. Then there was the problem that I need to register my books as instance of `BookExtension.class` instead of `Book.class`. This is where this mixin plays a role again. Whenever Patchouli tries to register a book and it's one of mine, I replace the instance with the correct one.

### [BookTextParserMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookTextParserMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
All this mixin does is adding some logging, when the creation of an internal link fails. This is useful for debugging purposes, as it allows me to see if I made a mistake in the link creation and i don't find the default error message informative enough.

### [GuiBookLandingMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/GuiBookLandingMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
Like I mentioned before, if I can unify something across all my books, I will do it. This mixin replaced the default Landing Page text with a default I18n String, which gets populated with the data from my `BookExtension.class`. This way I can have a unified landing page for all my books, without the need of setting it in every single book.

### [ItemModBookMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/ItemModBookMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
By default each Patchouli Book comes with a tooltip that displays the version. This mixin replaces the tooltip for all instances of `BookExtension.class` with the author of the book, which can be set in the `book.json` and defaults to `MasterEnderman`(me). 

### [PageCraftingMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/PageCraftingMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
This is a good one. If you want to display a crafting recipe in a book, you need to provide the recipe id. The problem with that is if a mod like [CraftTweaker](https://www.curseforge.com/minecraft/mc-mods/crafttweaker) or [GroovyScript](https://www.curseforge.com/minecraft/mc-mods/groovyscripts) changes the recipe, the id changes and the book cant't display the recipe anymore. If Patchouli can't find a recipe with the provided String my mixin queries the recipe registry and looks for a crafting recipe with that item as output. If it finds one it returns it's recipe id. This way I can display the recipe without the need of providing the recipe id and I only need to provide the item id.

### [PageSpotlightMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/PageSpotlightMixin.java)

![Note](https://img.shields.io/badge/-Note-lightgrey)
Another minor tweak. It decreases the empty space between the text and the item in the spotlight page.

## Moar Boats

![Note](https://img.shields.io/badge/-Note-lightgrey)
The `Boats` in Moar Boats don't inherit from the Minecraft `EntityBoat` class. That is why you can't "pick" the boat entities via middle-clicking. I encountered this while contemplating if I should implement the looking up of entities in Patchouli.

