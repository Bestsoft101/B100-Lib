package b100.lib.client.gui.util;

import b100.lib.client.gui.screen.GuiScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

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
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		
		utils.setGraphics(graphics);
		utils.setFont(font);
		
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
		
		screen.draw();
	}
	
	@Override
	public boolean keyPressed(KeyEvent event) {
		return screen.keyEvent(event.key(), event.scancode(), event.modifiers(), true);
	}
	
	@Override
	public boolean keyReleased(KeyEvent event) {
		return screen.keyEvent(event.key(), event.scancode(), event.modifiers(), false);
	}
	
	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		return screen.mouseEvent(event.button(), true, event.x(), event.y());
	}
	
	@Override
	public boolean mouseReleased(MouseButtonEvent event) {
		return screen.mouseEvent(event.button(), false, event.x(), event.y());
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		return screen.scrollEvent(horizontalAmount, verticalAmount, mouseX, mouseY);
	}
	
	@Override
	public boolean charTyped(CharacterEvent event) {
		screen.charEvent((char) event.codepoint(), 0);
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
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		if(enableBackground || minecraft.level == null) {
			boolean scissor = false;
			if(enableBackgroundScissor && minecraft.level != null) {
				utils.enableScissor(backgroundScissorX, backgroundScissorY, backgroundScissorWidth, backgroundScissorHeight);
				scissor = true;
			}
			
			super.extractBackground(graphics, mouseX, mouseY, a);
			
			if(scissor) {
				utils.disableScissor();
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
