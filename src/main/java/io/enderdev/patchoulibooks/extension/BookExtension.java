package io.enderdev.patchoulibooks.extension;

import com.google.gson.annotations.SerializedName;
import io.enderdev.patchoulibooks.Tags;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import vazkii.patchouli.common.book.Book;

import java.util.HashMap;
import java.util.Map;

public class BookExtension extends Book {

    private final Map<String, String> MY_MACROS = new HashMap<String, String>() {{
        // names
        put("$(pb)", "$(l)$(#606)" + Tags.MOD_NAME + "$()");
        put("<pb>", "$(pb)");
        // minecraft colors
        put("<c_black>", "$(#000)");
        put("<c_dark_blue>", "$(#00A)");
        put("<c_dark_green>", "$(#0A0)");
        put("<c_dark_aqua>", "$(#0AA)");
        put("<c_dark_red>", "$(#A00)");
        put("<c_dark_purple>", "$(#A0A)");
        put("<c_gold>", "$(#FA0)");
        put("<c_gray>", "$(#AAA)");
        put("<c_dark_gray>", "$(#555)");
        put("<c_blue>", "$(#55F)");
        put("<c_green>", "$(#5F5)");
        put("<c_aqua>", "$(#5FF)");
        put("<c_red>", "$(#F55)");
        put("<c_purple>", "$(#F5F)");
        put("<c_yellow>", "$(#FF5)");
        put("<c_white>", "$(#FFF)");
        // formatting
        put("<b>", "$(l)"); // bold
        put("<i>", "$(o)"); // italic
        put("</>", "$()"); // reset formatting
        put("<item>", "$(#b0b)"); // highlight item
        put("<mark>", "$(#490)"); // highlight text
        put("<li>", "$(li)"); // list
        put("<li2>", "$(li2)"); // sublist
        put("<br>", "$(br)"); // line break
        put("<2br>", "$(br)$(br)"); // double line break
        put("<l>(", "$(l:"); // start link
        put("</l>", "$(/l)"); // end link
        put("<k>(", "$(k:"); // start keybind
    }};

    @SerializedName("author")
    public String bookAuthor = "_MasterEnderman_";

    @SerializedName("link_curseforge")
    public String linkCurseforge = "https://www.curseforge.com/minecraft";

    @SerializedName("link_github")
    public String linkGitHub = "https://github.com";

    @SerializedName("link_wiki")
    public String linkWiki = "https://minecraft.wiki";

    @Override
    public void build(ModContainer owner, ResourceLocation resource, boolean external) {
        this.owner = owner;
        this.resourceLoc = resource;
        this.isExternal = external;

        // override some default values
        this.showProgress = false;
        this.model = String.format("%s:book_%s", Tags.MOD_ID, this.resourceLoc.getPath());
        this.name = String.format("%s Guide", getOriginalOwner(this.resourceLoc).getName());
        this.fillerResource = new ResourceLocation(Tags.MOD_ID, String.format("textures/gui/filler/%s.png",this.resourceLoc.getPath()));
        this.version = Tags.VERSION;
        this.landingText = String.format("This Book documents the items and blocks in the <mod> mod. It is part of the <pb> mod that tries to document as many mods as possible.<br>All information about <mod> are taken from these sources:<li>$(l:%s)Curseforge$(/l)<li>$(l:%s)GitHub$(/l)<li>$(l:%s)Wiki$(/l)", this.linkCurseforge, this.linkGitHub, this.linkWiki);

        // add special macros
        this.MY_MACROS.put("$(mod)", "$(l)$(#490)" + getOriginalOwner(this.resourceLoc).getName() + "$()");
        this.MY_MACROS.put("<mod>", "$(mod)");

        isExtension = !extend.isEmpty();

        if (!isExtension) {
            modelResourceLoc = new ModelResourceLocation(this.model, "inventory");

            bookResource = new ResourceLocation(bookTexture);
            craftingResource = new ResourceLocation(craftingTexture);

            textColor = 0xFF000000 | Integer.parseInt(textColorRaw, 16);
            headerColor = 0xFF000000 | Integer.parseInt(headerColorRaw, 16);
            nameplateColor = 0xFF000000 | Integer.parseInt(nameplateColorRaw, 16);
            linkColor = 0xFF000000 | Integer.parseInt(linkColorRaw, 16);
            linkHoverColor = 0xFF000000 | Integer.parseInt(linkHoverColorRaw, 16);
            progressBarColor = 0xFF000000 | Integer.parseInt(progressBarColorRaw, 16);
            progressBarBackground = 0xFF000000 | Integer.parseInt(progressBarBackgroundRaw, 16);

            for (String m : MY_MACROS.keySet())
                if (!macros.containsKey(m))
                    macros.put(m, MY_MACROS.get(m));
        }
    }

    private ModContainer getOriginalOwner(ResourceLocation resourceLoc) {
        return Loader.instance().getActiveModList().stream().filter(modContainer -> modContainer.getModId().equals(resourceLoc.getPath())).findFirst().orElse(this.owner);
    }
}
