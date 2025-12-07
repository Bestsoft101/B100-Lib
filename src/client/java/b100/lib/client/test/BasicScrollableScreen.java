package b100.lib.client.test;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScrollListScreen;
import b100.lib.client.gui.GuiScrollableList.ListLayout;
import b100.lib.client.gui.GuiScrollableList.ListLayout.Align;
import b100.lib.client.mixin.IScreen;

abstract class BasicScrollableScreen extends GuiScrollListScreen {

	public GuiButton doneButton;

	public BasicScrollableScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		
		doneButton = add(new GuiButton(this, B100LibClient.trans.asText("button.done")));
		doneButton.addActionListener((e) -> back());
		
		scrollList.layout = new ListLayout().setInnerPadding(getInnerPadding()).setOuterPadding(getOuterPadding()).setAlign(getAlign());
	}
	
	@Override
	public void onResize() {
		super.onResize();
		setFooterButtonPosition(doneButton);
	}
	
	public int getInnerPadding() {
		return 4;
	}
	
	public int getOuterPadding() {
		return 8;
	}
	
	public Align getAlign() {
		return Align.CENTER;
	}
	
}
