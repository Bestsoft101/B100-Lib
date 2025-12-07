package b100.lib.client.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.translate.Translate;
import net.minecraft.text.Text;

class SelectTranslationFileScreen extends BasicScrollableScreen {
	
	public SelectTranslationFileScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		
		title = B100LibClient.trans.asText("screen.translations");
	}
	
	@Override
	public void initScrollElements() {
		String currentLanguage = Translate.getCurrentLanguage();
		Set<String> paths = Translate.getAllLanguageFilePaths(currentLanguage);
		if(!currentLanguage.equals("en_us")) {
			paths.addAll(Translate.getAllLanguageFilePaths("en_us"));
		}
		
		List<String> sortedPaths = new ArrayList<>();
		sortedPaths.sort(String.CASE_INSENSITIVE_ORDER);
		
		for(String path : paths) {
			GuiButton button = new GuiButton(this, Text.of(path));
			
			button.addActionListener(e -> utils.setScreen(new TranslationFilePreviewScreen(this, path)));
			
			scrollList.add(button);
		}
	}
	
}
