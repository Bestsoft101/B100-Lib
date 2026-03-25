package b100.lib.client.gui.util;

import net.minecraft.resources.Identifier;

public class Textures {
	
	public static final Textures INSTANCE = new Textures();
	
	private Textures() {
		
	}
	
	public final Identifier buttonHover = Identifier.withDefaultNamespace("widget/button_highlighted");
	public final Identifier buttonNormal = Identifier.withDefaultNamespace("widget/button");
	public final Identifier buttonDisabled = Identifier.withDefaultNamespace("widget/button_disabled");

	public final Identifier sliderNormal = Identifier.withDefaultNamespace("widget/slider");
	public final Identifier sliderHighlighted = Identifier.withDefaultNamespace("widget/slider_highlighted");
	
	public final Identifier sliderHandle = Identifier.withDefaultNamespace("widget/slider_handle");
	public final Identifier sliderHandleHighlighted = Identifier.withDefaultNamespace("widget/slider_handle_highlighted");

	public final Identifier scroller = Identifier.withDefaultNamespace("widget/scroller");
	public final Identifier scrollerBackground = Identifier.withDefaultNamespace("widget/scroller_background");
	
	public final GuiTextures guiTextures = new GuiTextures(false);
	public final GuiTextures guiTexturesInWorld = new GuiTextures(true);
	
	public GuiTextures getCurrentGuiTextures() {
		return GuiUtils.instance.isInWorld() ? guiTexturesInWorld : guiTextures;
	}
	
	public static class GuiTextures {
		
		public final Identifier menuBackground;
		public final Identifier menuListBackground;
		public final Identifier headerSeparator;
		public final Identifier footerSeparator;
		
		public GuiTextures(boolean inWorld) {
			menuBackground = Identifier.withDefaultNamespace(inWorld ? "textures/gui/inworld_menu_background.png" : "textures/gui/menu_background.png");
			menuListBackground = Identifier.withDefaultNamespace(inWorld ? "textures/gui/inworld_menu_list_background.png" : "textures/gui/menu_list_background.png");
			headerSeparator = Identifier.withDefaultNamespace(inWorld ? "textures/gui/inworld_header_separator.png" : "textures/gui/header_separator.png");
			footerSeparator = Identifier.withDefaultNamespace(inWorld ? "textures/gui/inworld_footer_separator.png" : "textures/gui/footer_separator.png");
		}
	}

}
