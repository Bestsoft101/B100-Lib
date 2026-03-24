package b100.lib.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import b100.lib.client.gui.screen.GuiScreen;

public class ScreenWrapper extends Screen {

	public final GuiScreen screen;
	
	private GuiUtils utils = GuiUtils.instance;
	private boolean screenOpened = true;
	private boolean enableBackground = true;
	
	private boolean enableBackgroundScissor = false;
	private int backgroundScissorX;
	private int backgroundScissorY;
	private int backgroundScissorWidth;
	private int backgroundScissorHeight;
	
	public ScreenWrapper(GuiScreen screen) {
		super(null);
		
		this.screen = screen;
	}
	
	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		
		utils.drawContext = context;
		utils.textRenderer = font;
		
		screen.setWrapper(this);
		
		screen.mouseX = mouseX;
		screen.mouseY = mouseY;
		
		if(!screen.isInitialized()) {
			screen.init();
		}
		
		if(screen.width != this.width || screen.height != this.height) {
			screen.setSize(width, height);
			screen.onResize();
		}
		
		if(screenOpened) {
			screenOpened = false;
			screen.onScreenOpened();
		}

		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		
		screen.draw();
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return screen.keyEvent(keyCode, scanCode, modifiers, true);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return screen.keyEvent(keyCode, scanCode, modifiers, false);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return screen.mouseEvent(button, true, mouseX, mouseY);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return screen.mouseEvent(button, false, mouseX, mouseY);
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return screen.scrollEvent(horizontalAmount, verticalAmount, mouseX, mouseY);
	}
	
	@Override
	public boolean charTyped(char chr, int modifiers) {
		screen.charEvent(chr, modifiers);
		return false;
	}
	
	@Override
	protected void updateNarrationState(NarrationElementOutput messageBuilder) {
		// TODO
	}
	
	@Override
	public void added() {
		screenOpened = true;
	}
	
	@Override
	public Component getTitle() {
		return Component.nullToEmpty("");
	}
	
	public void setBackgroundEnabled(boolean enableBackground) {
		this.enableBackground = enableBackground;
	}
	
	@Override
	public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
		if(enableBackground || minecraft.level == null) {
			boolean scissor = false;
			if(enableBackgroundScissor && minecraft.level != null) {
				utils.drawContext.enableScissor(backgroundScissorX, backgroundScissorY, backgroundScissorX + backgroundScissorWidth, backgroundScissorY + backgroundScissorHeight);
				scissor = true;
			}
			
			super.renderBackground(context, mouseX, mouseY, delta);
			
			if(scissor) {
				utils.drawContext.disableScissor();
			}
		}
	}
	
	public void setBackgroundScissorEnabled(boolean enableBackgroundScissor) {
		this.enableBackgroundScissor = enableBackgroundScissor;
	}
	
	public void setBackgroundScissorArea(int x, int y, int w, int h) {
		backgroundScissorX = x;
		backgroundScissorY = y;
		backgroundScissorWidth = w;
		backgroundScissorHeight = h;
	}
	
}
