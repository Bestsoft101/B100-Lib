package b100.lib.client.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b100.lib.client.gui.GuiListButton;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.translate.Translate;
import b100.lib.client.util.ConfigUtil;
import net.minecraft.client.font.MultilineText;
import net.minecraft.text.Text;

class TranslationFilePreviewScreen extends BasicScrollableScreen {
	
	private Map<String, String> translations;
	private List<String> sortedTranslationKeys;
	
	public TranslationFilePreviewScreen(IScreen parentScreen, String path) {
		super(parentScreen);
		
		InputStream stream = Translate.getTranslationFileInputStream(path);
		try {
			translations = new HashMap<>();
			
			ConfigUtil.loadConfig(stream, translations::put, '=');
			
			sortedTranslationKeys = new ArrayList<>(translations.keySet());
			sortedTranslationKeys.sort(String.CASE_INSENSITIVE_ORDER);
		}catch (Exception e) {
			// TODO show error on screen
			e.printStackTrace();
			translations = null;
		}finally {
			try {
				stream.close();
			}catch (Exception e) {}
		}
	}

	@Override
	public void initScrollElements() {
		if(sortedTranslationKeys != null) {
			for(String key : sortedTranslationKeys) {
				String value = translations.get(key);
				
				TranslationElement e = new TranslationElement(this, key, value);
				scrollList.add(e);
			}	
		}
	}
	
	@Override
	public int getInnerPadding() {
		return 0;
	}
	
	static class TranslationElement extends GuiListButton {
		
		public String key;
		public String value;
		
		public MultilineText wrappedText;
		public int wrappedTextWidth = -1;
		
		public TranslationElement(GuiScreen screen, String key, String value) {
			super(screen);
			this.key = key;
			this.value = value;
			
			width = 280;
			height = 20;
		}
		
		@Override
		public void draw() {
			super.draw();
			wrappedText.drawWithShadow(utils.drawContext, posX + 3, posY + 3, 10, 0xFFFFFF);
			utils.drawString(key, posX + 3, posY + height - 11, 0x606060, true);
		}
		
		@Override
		public void onResize() {
			wrappedText = MultilineText.create(utils.textRenderer, width, Text.of(value));
			
			int newHeight = (wrappedText.count() + 1) * 10 + 4;
			if(height != newHeight) {
				height = newHeight;
				screen.onResize();
			}
		}
		
	}
	
}
