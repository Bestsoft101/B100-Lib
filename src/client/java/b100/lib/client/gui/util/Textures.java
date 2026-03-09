package b100.lib.client.gui.util;

import net.minecraft.util.Identifier;

public class Textures {

	private static final Identifier WIDGETS = vanilla("textures/gui/widgets.png");
	private static final Identifier BACKGROUND = vanilla("textures/gui/options_background.png");
	private static final Identifier SLIDER = vanilla("textures/gui/slider.png");
	
	public static Textures INSTANCE = new Textures();
	
	public static void refresh() {
		INSTANCE = new Textures();
	}
	
	public final GuiSprite buttonHover = new GuiTextureSprite(WIDGETS, 0, 0, 86, 200, 20);
	public final GuiSprite buttonNormal = new GuiTextureSprite(WIDGETS, 0, 0, 66, 200, 20);
	public final GuiSprite buttonDisabled = new GuiTextureSprite(WIDGETS, 0, 0, 46, 200, 20);

	public final GuiSprite sliderNormal = new GuiTextureSprite(SLIDER, 0, 0, 0, 200, 20);
	public final GuiSprite sliderHighlighted = new GuiTextureSprite(SLIDER, 0, 0, 20, 200, 20);
	
	public final GuiSprite sliderHandle = new GuiTextureSprite(SLIDER, 0, 0, 40, 200, 20);
	public final GuiSprite sliderHandleHighlighted = new GuiTextureSprite(SLIDER, 0, 0, 60, 200, 20);

	public final GuiSprite scroller = new GuiScrollbarSprite(GuiScrollbarSprite.TYPE_HANDLE);
	public final GuiSprite scrollerBackground = new GuiScrollbarSprite(GuiScrollbarSprite.TYPE_BG);
	
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
			menuBackground = BACKGROUND;
			menuListBackground = BACKGROUND;
			headerSeparator = vanilla("textures/gui/header_separator.png");
			footerSeparator = vanilla("textures/gui/footer_separator.png");
		}
	}
	
	public static Identifier vanilla(String name) {
		return Identifier.of("minecraft", name);
	}

}
