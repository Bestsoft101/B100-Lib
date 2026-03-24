package b100.lib.client.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import b100.lib.client.B100LibClient;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.mixin.IScreen;
import b100.lib.translate.Translate;

class SelectTranslationFileScreen extends BasicScrollableScreen {
	
	public SelectTranslationFileScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		
		title = B100LibClient.TRANS.asText("screen.translations");
	}
	
	@Override
	public void initScrollElements() {
		String currentLanguage = B100LibClient.getCurrentLanguage();
		Set<String> paths = Translate.getAllLanguageFilePaths(currentLanguage);
		if(!currentLanguage.equals("en_us")) {
			paths.addAll(Translate.getAllLanguageFilePaths("en_us"));
		}
		
		List<String> sortedPaths = new ArrayList<>();
		sortedPaths.sort(String.CASE_INSENSITIVE_ORDER);
		
		for(String path : paths) {
			GuiButton button = new GuiButton(this, Component.nullToEmpty(path));
			
			button.addActionListener(e -> utils.setScreen(new TranslationFilePreviewScreen(this, path)));
			
			scrollList.add(button);
		}
	}
	
}
