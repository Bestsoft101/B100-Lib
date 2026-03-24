package b100.lib.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import b100.lib.client.gui.element.GuiElement;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.mixin.IScreen;

public class GuiUtils {
	
	public static final int DEFAULT_TOOLTIP_WIDTH = 170;
	
	public static GuiUtils instance = new GuiUtils();
	
	public GuiGraphics drawContext;
	public Font textRenderer;
	
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
	
	public void drawString(String string, int x, int y, int color, boolean shadow) {
		drawContext.drawString(textRenderer, string, x, y, color, shadow);
		
		RenderSystem.enableBlend();
	}
	
	public void drawString(Component text, int x, int y, int color, boolean shadow) {
		drawString(text.getString(), x, y, color, shadow);
	}

	public void drawCenteredString(Component text, int x, int y, int color, boolean shadow) {
		int width = textRenderer.width(text);
		drawString(text.getString(), x - width / 2, y, color, shadow);
	}
	
	public void drawGuiTexture(ResourceLocation texture, int x, int y, int width, int height) {
		drawContext.blitSprite(texture, x, y, width, height);
	}
	
	public void drawTexture(ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		drawContext.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
	}
	
	public void drawRectangle(int x, int y, int w, int h, int color) {
		drawContext.fill(x, y, x + w, y + h, color);
	}
	
	public void playSound(SoundEvent sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0f));
	}
	
	public void playSound(Holder.Reference<SoundEvent> sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0f));
	}
	
	@SuppressWarnings("resource")
	public boolean isInWorld() {
		return Minecraft.getInstance().level != null;
	}
	
	public static void setDoubleFooterButtonPositions(GuiScreen screen, int y, GuiElement left, GuiElement right) {
		int p = 2;
		int w = 150;
		int center = screen.width / 2;
		int x0 = center - w - p;
		int x1 = center + p;
		left.setPosition(x0, y).setSize(w, 20);
		right.setPosition(x1, y).setSize(w, 20);
	}

}
