package b100.lib.client.gui.util;

import net.minecraft.resources.ResourceLocation;

public class Textures {
	
	public static final Textures INSTANCE = new Textures();
	
	private Textures() {
		
	}
	
	public final ResourceLocation buttonHover = ResourceLocation.withDefaultNamespace("widget/button_highlighted");
	public final ResourceLocation buttonNormal = ResourceLocation.withDefaultNamespace("widget/button");
	public final ResourceLocation buttonDisabled = ResourceLocation.withDefaultNamespace("widget/button_disabled");

	public final ResourceLocation sliderNormal = ResourceLocation.withDefaultNamespace("widget/slider");
	public final ResourceLocation sliderHighlighted = ResourceLocation.withDefaultNamespace("widget/slider_highlighted");
	
	public final ResourceLocation sliderHandle = ResourceLocation.withDefaultNamespace("widget/slider_handle");
	public final ResourceLocation sliderHandleHighlighted = ResourceLocation.withDefaultNamespace("widget/slider_handle_highlighted");

	public final ResourceLocation scroller = ResourceLocation.withDefaultNamespace("widget/scroller");
	public final ResourceLocation scrollerBackground = ResourceLocation.withDefaultNamespace("widget/scroller_background");
	
	public final GuiTextures guiTextures = new GuiTextures(false);
	public final GuiTextures guiTexturesInWorld = new GuiTextures(true);
	
	public GuiTextures getCurrentGuiTextures() {
		return GuiUtils.instance.isInWorld() ? guiTexturesInWorld : guiTextures;
	}
	
	public static class GuiTextures {
		
		public final ResourceLocation menuBackground;
		public final ResourceLocation menuListBackground;
		public final ResourceLocation headerSeparator;
		public final ResourceLocation footerSeparator;
		
		public GuiTextures(boolean inWorld) {
			menuBackground = ResourceLocation.withDefaultNamespace(inWorld ? "textures/gui/inworld_menu_background.png" : "textures/gui/menu_background.png");
			menuListBackground = ResourceLocation.withDefaultNamespace(inWorld ? "textures/gui/inworld_menu_list_background.png" : "textures/gui/menu_list_background.png");
			headerSeparator = ResourceLocation.withDefaultNamespace(inWorld ? "textures/gui/inworld_header_separator.png" : "textures/gui/header_separator.png");
			footerSeparator = ResourceLocation.withDefaultNamespace(inWorld ? "textures/gui/inworld_footer_separator.png" : "textures/gui/footer_separator.png");
		}
	}

}
