# Patchouli Books

<img src="PatchouliBooks.png" align="left" width="180px"/>

This is an add-on for the [Patchouli](https://www.curseforge.com/minecraft/mc-mods/patchouli-rofl-edition) mod that adds various books for different mods, trying to document them as best as possible. Furthermore there are a few more QoL features I have in mind. This mod will only ever exist for 1.12.2 as I have currently no interest in doing anything on the newer versions of Minecraft.
If you want to take a look at the things I encountered while working on this project, feel free to check out my [project notes](NOTES.md). Furthermore I made non of the art this mods uses. All the art used is either from Patchouli or sourced from various repositories. I've compiled a list of all the sourced [here](https://github.com/Ender-Development/PatchouliBooks/blob/master/src/main/resources/assets/patchoulibooks/textures/sources.md) together with the original authors and their respective licenses.

[![](banner.png)](https://www.akliz.net/enderman)

## Current Features
Stuff I have already implemented:
- added HEI/JEI integration (you can now open JEI for every item rendered inside a book using the same keybind as for JEI)
- added more JEI integration (you can now open a book from JEI for every item that has a book entry)
- backported Pamphlets from the 1.18 version of Patchouli (needs to be enabled in the config) (these will be moved to the base Patchouli mod in the future)
- new Page Types (an iteration of most of the existing ones as well as a new custom one for PedestalCrafting/ExtendedCrafting)
- of course a Patchouli Book explaining the features of this mod
- [documentation](#documented-mods) for various mods (their respective books will only be loaded if the mod is present)

##  Possible Features
Stuff I may work on in the future:
- 🔳 backporting [PatchouliButton](https://www.curseforge.com/minecraft/mc-mods/patchoulibutton)
- 🔳 backporting [PatchouliQuests](https://www.curseforge.com/minecraft/mc-mods/patchouli-quests)
- ⛔ implement the "inventory-scanning-to-open-a-book-entry" thing from [Botania](https://github.com/VazkiiMods/Botania/blob/1.12-final/src/main/java/vazkii/botania/client/core/handler/TooltipAdditionDisplayHandler.java) (this will be added by the base Patchouli mod in the future)
- 🔳 add new page types
  - ✅ [PedestalCrafting](https://www.curseforge.com/minecraft/customization/pedestal-recipe-template-for-patchouli)
  - 🔳 [Boss Info](https://www.curseforge.com/minecraft/customization/patchouli-template-boss-info)
  - 🔳 More Stuff from thanasishadow send to me on discord
- 🔳 implementing proper JEI integration
  - ✅ [Link from JEI into Books](https://github.com/CaliforniaDemise/Patchouli/issues/1) (0.2.0)
  - ✅ Open JEI from inside a book (0.1.0)
- 🔳 increase the GUI size of the book
  - 🔳 decrease the font size of the text
  - 🔳 thus increasing the amount of text that can be displayed on a single page

## Documented Mods

These mods are currently documented in this mod within their own books:
- [Gendustry](https://www.curseforge.com/minecraft/mc-mods/gendustry)
- [MoarBoats](https://www.curseforge.com/minecraft/mc-mods/moar-boats)
- [Pressure Pipes](https://www.curseforge.com/minecraft/mc-mods/pressure-pipes)
- [Thermal Logistics](https://www.curseforge.com/minecraft/mc-mods/thermallogistics)

This list may seem a bit obscure as there aren't any big mods listed yet. This is because this project started with a few book I already made myself for my [own modpack](https://www.curseforge.com/minecraft/modpacks/zerblands-remastered) and these were already done before starting this. Right now I wanted a basic feature set to able to publish the mod on curseforge. This list will most certainly grow in the future. Furthermore, I will greatly appreciate any help with this project. So if you're interested in helping me out, feel free to contact me on [Discord](https://discord.gg/JF7x2vG) or create an [issue](https://github.com/Ender-Development/PatchouliBooks/issues) on this repository.

## [Ender-Development](https://github.com/Ender-Development)

Our Team currently includes:
- `_MasterEnderman_` - Project-Manager, Developer
- `Klebestreifen` - Developer

You can contact us on our [Discord](https://discord.gg/JF7x2vG).

## Contributing
Feel free to contribute to the project. We are always happy about pull requests.
If you want to help us, you can find potential tasks in the [issue tracker](https://github.com/Ender-Development/PatchouliBooks/issues).
Of course, you can also create new issues if you find a bug or have a suggestion for a new feature.
Should you have any questions, feel free to ask us on [Discord](https://discord.gg/JF7x2vG).

## Partnership with Akliz

> It's a pleasure to be partnered with Akliz. Besides being a fantastic server provider, which makes it incredibly easy to set up a server of your choice, they help me to push myself and the quality of my projects to the next level. Furthermore, you can click on the banner below to get a discount. :')

<a href="https://www.akliz.net/enderman"><img src="https://github.com/MasterEnderman/Zerblands-Remastered/raw/master/Akliz_Partner.png" align="center"/></a>

If you aren't located in the [US](https://www.akliz.net/enderman), Akliz now offers servers in:

- [Europe](https://www.akliz.net/enderman-eu)
- [Oceania](https://www.akliz.net/enderman-oce)
