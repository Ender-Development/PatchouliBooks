# 📝Notes

Just a few things I encountered while working on this project.

---

![Note](https://img.shields.io/badge/-Note-green) This is a note. It's just a little piece of information that I found interesting or funny.

![Warning](https://img.shields.io/badge/-Warning-red) This is a warning. It's a piece of information about something that isn't fully implemented yet or could cause issues under certain circumstances. It's also used to point out deprecated features.

![Important](https://img.shields.io/badge/-Important-blue) This is important. It's a piece of information that is crucial to the understanding why I did things the way I did.

---

## 📂Table of Contents

▶ [Patchouli](#patchouli)<br>
▶ [Patchouli Books](#patchouli-books)<br>
▶ [Patchouli (Mixins)](#patchouli-mixins)<br>
▶ [Had Enough Items / Just Enough Items (Mixins)](#had-enough-items--just-enough-items-mixins)<br>
▶ [Moar Boats](#moar-boats)

## 📕 Patchouli

![Note](https://img.shields.io/badge/-Note-green)
I thought after working so much with this mod in my modpack [Zerblands-Remastered](https://github.com/MasterEnderman/Zerblands-Remastered) I would know most of its features. But I was wrong. I didn't know that you could disable entries or whole categories in the book by using the flag system that allows checking for a loaded mod by default. This was one of the first features I wanted to implement on my end. Looks like this isn't necessary anymore.

![Note](https://img.shields.io/badge/-Note-green)
I also learned about `ComponentProcessors` they allow a developer to provide a book with actual values straight from the source code. No idea why nobody uses this feature. I really like to have some numbers, when it comes to working with various different mods. Most of the time I have to look them up myself. Now I may be able to use some Accessor Mixins to get access to various values of the mods I plan to document. That way all information would still be correct, even if someone alters some values in the config files. I anyone wants to read about them [here](https://vazkiimods.github.io/Patchouli/docs/patchouli-advanced/component-processors/) is the link to the documentation.

## 📚 Patchouli Books

![Important](https://img.shields.io/badge/-Important-blue)
**Why does this need to be a mod in the first place?** Well technically I could release all of these books as a resource pack. But this would mean it would load all books in every instance instead of only the ones that are present. This is one of the first things I implemented in `PatchouliBooks`. I wrote a little mixins that allows me to check if the mod for one of my books is actually loaded, before I allow Patchouli to load the book for it. Now I can bundle as many books as I want and it still only loads the ones that are actually needed. The second most important thing is the use of `ComponentProcessors`. That way I can access most of the internal mod values and come up with special templates to represent them to the player. This is something that wouldn't be possible with a resource pack. Lastly there are a few ideas I have in mind that I may want to implement. I looked through some of the newer add-ons in the modern Minecraft version and encountered [PatchouliButton](https://www.curseforge.com/minecraft/mc-mods/patchoulibutton). This replaces the inventory book with a GUI list that shows all available Patchouli books without the need of having them in your inventory. Pretty need of you ask me. But I've never done anything GUI related yet, so this may be something for the future. I already have a list of more possible features in my [README](README.md#possible-features).

![Note](https://img.shields.io/badge/-Note-green)
**Yes there is of course a Patchouli Books book in this mod.** How would you aim to add documentation to as many mods as possible if you don't start with the one you're making? Not that it has any value for any survival player, but maybe there are some packmakers or modauthors, who can find it helpful. Otherwise you can still look at the source code and see how to use the new features I implemented.

## Patchouli (Mixins)

### [BookContentsMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookContentsMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
Patchouli comes with a "version" system by default, that means the book gets a tooltip that say `x`th Edition. It's not my cup of tea as I prefer to use [SemVer](https://semver.org). So I added a special check to allow the usage of [SemVer](https://semver.org) instead of the default version system. All it does is checking the input matches it against a regex and return a translated I18n string.

![Important](https://img.shields.io/badge/-Important-blue)
The `BookContentsMixin` got another functionality in one of the latest commits. I started tinkering with Templates for my books, but I wanted a way to use them across all books without needing to copy them into every single book. This mixin now also hijacks the loading of the templates and redirects them to a central location where I can access them from all of my books.

### [BookEntryMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookEntryMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
By default you have to set a flag in your entry.json if you want to mark it as "already-read", so it displays without the notification symbol. For the amount of entries I'm going to write I want to make the writing progress as quick as possible. The more stuff that's set correctly by default the better. So i circumvented the need of setting the flag in each of the entry files by checking if the loaded entry is from one of my books and if it is, I set the flag to true.

![Important](https://img.shields.io/badge/-Important-blue)
With version `0.2.0` I added the possibility to force lock a book entry. This is useful if you want to create an entry in the book, but don't want the player to access it yet. This is done by adding a `force_lock` field to the entry json.

### [BookRegistryMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookRegistryMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
I already mentioned this one before, when I talked about the mixin to disable my book if the related mod isn't loaded. This was all it did for the longest time, but I wasn't satisfied with the limited options the default `Book.class` offered. That's why I created the [BookExtension.class](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/extension/Bookextension.java). This offers me all possibilities to tweak my books as I see fit. Then there was the problem that I need to register my books as instance of `BookExtension.class` instead of `Book.class`. This is where this mixin comes to play again. Whenever Patchouli tries to register a book and it's one of mine, I replace the instance with the correct one.

### [BookTextParserMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/BookTextParserMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
All this mixin does is adding some logging, when the creation of an internal link fails. This is useful for debugging purposes, as it allows me to see if I made a mistake in the link creation and i don't find the default error message informative enough.

### [ClientBookRegistryMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/ClientBookRegistryMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
This mixin registers my new patchouli pages. I know there is probably a way to register them myself within Patchouli Books, but I don't see a reason to do so. It isn't invasive as it only appends my pages to the default `addPageTypes` method. This may be refactored in the future, but for now it works.

![Warning](https://img.shields.io/badge/-Warning-red)
Small update regarding this mixin. It no longer exists. I refactored the registration process so it doesn't uses a mixin anymore. Instead, I use a custom decorator and some reflection magic to register my pages at the correct loading stage. **[0.1.1]** Now I don't even use reflection or a custom decorator anymore as they caused a crash when launched outside of the dev environment. I now manually register the new page types.

### [GuiBookAccessor](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/GuiBookAccessor.java)

![Note](https://img.shields.io/badge/-Note-green)
A very small mixin that allows access to the item that the book is currently rendering a tooltip for. It allows me to use this value and enables my HEI/JEI integration to know which item is currently being hovered over. Because the lack of HEI/JEI integration was bothering me for the longest time.

### [GuiBookLandingMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/GuiBookLandingMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
Like I mentioned before, if I can unify something across all my books, I will do it. This mixin replaces the default Landing Page text with a default I18n String, which gets populated with the data from my `BookExtension.class`. Now I can have a unified landing page for all my books, without the need of setting it in every single book.

![Warning](https://img.shields.io/badge/-Warning-red) **[0.1.1]**
Small refactor of the custom landing page. I no longer rely on a mixin as it didn't work outside of the dev environment. Now I just set the string for the landing page in the `BookExtension.class` and it gets displayed correctly.

![Important](https://img.shields.io/badge/-Important-blue)
Great news! This mixin now plays a major role as I backported pamphlets from newer versions of Patchouli. Pamphlets a single category books, where all entries are displayed in a list view without having to select a category first. All of the related GUI stuff is now handled by this mixin.

### [ItemModBookMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/ItemModBookMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
By default each Patchouli Book comes with a tooltip that displays the version. This mixin replaces the tooltip for all instances of `BookExtension.class` with the author of the book, which can be set in the `book.json` and defaults to `MasterEnderman`(me). 

### [PageCraftingMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/PageCraftingMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
This is a good one. If you want to display a crafting recipe in a book, you need to provide the recipe id. The problem with that is if a mod like [CraftTweaker](https://www.curseforge.com/minecraft/mc-mods/crafttweaker) or [GroovyScript](https://www.curseforge.com/minecraft/mc-mods/groovyscripts) changes the recipe, the id changes and the book cant't display the recipe anymore. If Patchouli can't find a recipe with the provided String my mixin queries the recipe registry and looks for a crafting recipe with that item as output. If it finds one it returns it's recipe id. This way I can display the recipe without the need of providing the recipe id and I only need to provide the item id.

![Note](https://img.shields.io/badge/-Note-green)
Small addition: After switching to my own version of the book pages I added a config option to enable this mixin for all Patchouli Books. This may be useful, if a modpack maker changes a recipe of an item which is displayed in a book. This would break the display of the recipe, but with this mixin enabled it would still display the recipe correctly (as long as it is a crafting recipe). It is disabled by default, as I haven't tested it with other mods yet.

### [PageSpotlightMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/patchouli/PageSpotlightMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
Another minor tweak. It decreases the empty space between the text and the item in the spotlight page. This will hopefully be deprecated in the future, as I plan to implement a custom page for this so it doesn't break any other patchouli books that originate outside of my mod.

![Warning](https://img.shields.io/badge/-Warning-red)
This mixin is now deprecated. I implemented a custom page for this, so it doesn't break any other Patchouli Books that originate outside of my mod.

## 🛒HEI / JEI (Mixins)

### [RecipeLayoutMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/jei/RecipeLayoutMixin.java)

![Important](https://img.shields.io/badge/-Important-blue)
This mixin is the core of my HEI/JEI integration. It allows me to display each book that contains a page with the item I'm looking at. If there are multiple books with the item, they will be arranged in a grid. The book only shows up if the related entry with the item is unlocked.

### [RecipesGuiMixin](https://github.com/Ender-Development/PatchouliBooks/tree/master/src/main/java/io/enderdev/patchoulibooks/mixins/jei/RecipesGuiMixin.java)

![Note](https://img.shields.io/badge/-Note-green)
This mixin is a small addition to the `RecipeLayoutMixin`. It just registers my new buttons in the right places so that they can be interacted with.

## 🚢Moar Boats

![Note](https://img.shields.io/badge/-Note-green)
The `Boats` in Moar Boats don't inherit from the Minecraft `EntityBoat` class. That is why you can't "pick" the boat entities via middle-clicking. I encountered this while contemplating if I should implement the looking up of entities in Patchouli.

