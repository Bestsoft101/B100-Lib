package b100.lib.client.gui.util;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import b100.lib.client.gui.element.GuiElement;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.mixin.IScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;

public class GuiUtils {
	
	public static final int DEFAULT_TOOLTIP_WIDTH = 170;
	
	public static GuiUtils instance = new GuiUtils();
	
	private GuiGraphics graphics;
	private Font font;
	
	private GuiUtils() {
		
	}
	
	public void setScreen(IScreen screen) {
		Minecraft minecraft = Minecraft.getInstance();
		
		if(minecraft.screen instanceof ScreenWrapper screenWrapper) {
			screenWrapper.screen.onClose();
		}
		
		if(screen instanceof GuiScreen) {
			GuiScreen screen1 = (GuiScreen) screen;
			minecraft.setScreen(new ScreenWrapper(screen1));	
		}else if(screen instanceof Screen) {
			Screen screen1 = (Screen) screen;
			minecraft.setScreen(screen1);
		}else if(screen == null) {
			minecraft.setScreen(null);
		}
	}
	
	////////////////////////////////
	
	public void drawText(String string, int x, int y, int color, boolean shadow) {
		graphics.drawString(font, string, x, y, fixAlpha(color), shadow);
		
		RenderSystem.enableBlend();
	}
	
	public void drawText(Component text, int x, int y, int color, boolean shadow) {
		drawText(text.getString(), x, y, color, shadow);
	}

	public void drawCenteredText(Component text, int x, int y, int color, boolean shadow) {
		int width = font.width(text);
		drawText(text.getString(), x - width / 2, y, color, shadow);
	}
	
	public void drawLines(List<FormattedCharSequence> lines, int x, int y, int lineHeight, int color, boolean shadow) {
		for(FormattedCharSequence line : lines) {
			graphics.drawString(font, line, x, y, fixAlpha(color));
			y += lineHeight;
		}
	}
	
	public void drawWrappedText(String string, int x, int y, int width, int lineHeight, int color, boolean shadow) {
		drawLines(wrap(string, width), x, y, lineHeight, color, shadow);
	}
	
	////////////////////////////////
	
	public List<FormattedCharSequence> wrap(String string, int width) {
		return wrap(FormattedText.of(string), width);
	}
	
	public List<FormattedCharSequence> wrap(FormattedText text, int width) {
		return font.split(text, width);
	}
	
	public int textWidth(String string) {
		return font.width(Component.literal(string));
	}
	
	public int textWidth(Component text) {
		return font.width(text);
	}
	
	public int getWrappedLineCount(String string, int width) {
		return getWrappedLineCount(Component.literal(string), width);
	}
	
	public int getWrappedLineCount(Component text, int width) {
		return MultiLineLabel.create(font, width, text).getLineCount();
	}
	
	////////////////////////////////
	
	public void drawSprite(ResourceLocation texture, int x, int y, int width, int height) {
		graphics.blitSprite(texture, x, y, width, height);
	}
	
	public void drawTexture(ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		graphics.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
	}
	
	public void drawRectangle(int x, int y, int w, int h, int color) {
		graphics.fill(x, y, x + w, y + h, color);
	}
	
	////////////////////////////////
	
	public void enableScissor(int x, int y, int w, int h) {
		int x0 = x;
		int y0 = y;
		int x1 = x + w;
		int y1 = y + h;
		graphics.enableScissor(x0, y0, x1, y1);
	}
	
	public void disableScissor() {
		graphics.disableScissor();
	}
	
	////////////////////////////////
	
	public void playSound(SoundEvent sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0f));
	}
	
	public void playSound(Holder.Reference<SoundEvent> sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0f));
	}
	
	public boolean isInWorld() {
		return Minecraft.getInstance().level != null;
	}
	
	////////////////////////////////
	
	public static void setDoubleFooterButtonPositions(GuiScreen screen, int y, GuiElement left, GuiElement right) {
		int p = 2;
		int w = 150;
		int center = screen.width / 2;
		int x0 = center - w - p;
		int x1 = center + p;
		left.setPosition(x0, y).setSize(w, 20);
		right.setPosition(x1, y).setSize(w, 20);
	}
	
	////////////////////////////////
	
	/** 
	 * Set alpha to 255 if set to 0.
	 * 1.21.1 does this already when rendering text, but later versions don't.
	 */
	private int fixAlpha(int color) {
		int alpha = (color >> 24) & 0xFF;
		if(alpha == 0) {
			color |= 0xFF000000;
		}
		return color;
	}
	
	////////////////////////////////
	
	public GuiGraphics getGraphics() {
		return graphics;
	}
	
	public void setGraphics(GuiGraphics graphics) {
		this.graphics = graphics;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}

}
