package io.enderdev.patchoulibooks.pages;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.common.util.ItemStackUtil;

public class RenderObject {
    private final IconType type;
	private final ItemStack stack;
	private final ResourceLocation res;

	public RenderObject(String str) {
		if(str.endsWith(".png")) {
			this.type = IconType.RESOURCE;
			this.stack = null;
			this.res = new ResourceLocation(str);
		} else {
			this.type = IconType.STACK;
			this.stack = ItemStackUtil.loadStackFromString(str);
			this.res = null;
		}
	}

	public RenderObject(ItemStack stack) {
		this.type = IconType.STACK;
		this.stack = stack;
		this.res = null;
	}

	public RenderObject(ResourceLocation res) {
		this.type = IconType.RESOURCE;
		this.stack = null;
		this.res = res;
	}

	public boolean isTexture() {
		return type == IconType.RESOURCE;
	}

	public boolean isItem() {
		return type == IconType.STACK;
	}

	public ItemStack getStack() {
		return stack;
	}

	public ResourceLocation getResource() {
		return res;
	}

	private enum IconType {
		STACK, RESOURCE
	}
}
