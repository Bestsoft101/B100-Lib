package b100.lib.client.test;

import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScrollListScreen;
import b100.lib.client.gui.GuiScrollableList.ListLayout;
import b100.lib.client.gui.GuiScrollableList.ListLayout.Align;
import b100.lib.client.mixin.IScreen;
import net.minecraft.text.Text;

public class ButtonTestScreen extends GuiScrollListScreen {

	public GuiButton doneButton;
	
	public ButtonTestScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();

		title = Text.of("Buttons");
		
		doneButton = add(new GuiButton(this, Text.of("Done")));
		doneButton.addActionListener((e) -> back());
		
		scrollList.layout = new ListLayout().setInnerPadding(4).setOuterPadding(8).setAlign(Align.CENTER);
	}

	@Override
	public void initScrollElements() {
		for(int i=0; i < 16; i++) {
			scrollList.add(new GuiButton(this, Text.of("Button " + (i + 1))));
		}
	}
	
	@Override
	public void onResize() {
		super.onResize();
		setFooterButtonPosition(doneButton);
	}
	
}
