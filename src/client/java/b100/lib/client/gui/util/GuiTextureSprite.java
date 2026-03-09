package b100.lib.client.gui.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class GuiTextureSprite implements GuiSprite {
	
	public final Identifier textureId;

	public int type;
	public int spriteX;
	public int spriteY;
	public int spriteWidth;
	public int spriteHeight;
	
	public GuiTextureSprite(Identifier texture, int type, int x, int y, int width, int height) {
		if(texture == null) {
			throw new RuntimeException("Texture id is null!");
		}
		
		this.textureId = texture;
		this.type = type;
		this.spriteX = x;
		this.spriteY = y;
		this.spriteWidth = width;
		this.spriteHeight = height;
	}

	@Override
	public void draw(DrawContext context, int x, int y, int w, int h) {
		if(w != spriteWidth) {
			int w1 = w / 2;
			int w2 = w - w1;
			
			context.drawTexture(textureId, x, y, spriteX, spriteY, w1, h);
			context.drawTexture(textureId, x + w1, y, spriteX + spriteWidth - w1, spriteY, w2, h);
		}else {
			context.drawTexture(textureId, x, y, spriteX, spriteY, w, h);	
		}
	}
}
