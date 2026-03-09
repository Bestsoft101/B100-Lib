package b100.lib.client.gui.util;

import net.minecraft.client.gui.DrawContext;

public class GuiScrollbarSprite implements GuiSprite {

	public static final int TYPE_BG = 0;
	public static final int TYPE_HANDLE = 1;
	
	public int type;
	
	public GuiScrollbarSprite(int type) {
		this.type = type;
	}
	
	@Override
	public void draw(DrawContext context, int x, int y, int w, int h) {
		if(type == TYPE_BG) {
			context.fill(x, y, x + w, y + h, -16777216);
			
		}else if(type == TYPE_HANDLE) {
			context.fill(x, y, x + w, y + h, -8355712);
			context.fill(x, y, x + w - 1, y + h - 1, -4144960);
		}
	}
	
}
