package b100.lib.client.test;

import java.io.File;

import b100.lib.B100Lib;
import b100.lib.Print;
import b100.lib.client.B100LibClient;
import b100.lib.client.gui.config.SaveConfigButton;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.config.element.BooleanToggleElement;
import b100.lib.client.gui.config.element.EnumSliderElement;
import b100.lib.client.gui.config.element.EnumToggleElement;
import b100.lib.client.gui.config.element.FloatSliderElement;
import b100.lib.client.gui.config.element.IntegerSliderElement;
import b100.lib.client.gui.config.element.IntegerTextFieldElement;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.gui.element.GuiContainer;
import b100.lib.client.gui.element.GuiElement;
import b100.lib.client.gui.element.GuiScrollableList.ListLayout;
import b100.lib.client.gui.screen.GuiScrollListScreen;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.Config;
import b100.lib.config.property.BooleanProperty;
import b100.lib.config.property.EnumProperty;
import b100.lib.config.property.FloatProperty;
import b100.lib.config.property.IntProperty;

class ConfigElementTestScreen extends GuiScrollListScreen {
	
	public static Config config = new Config(new File(B100Lib.CONFIG_FOLDER, B100Lib.MODID + "_config_demo.properties"));

	public static BooleanProperty booleanProperty = config.register("booleanProperty", BooleanProperty.create(false));
	public static IntProperty intProperty = config.register("intProperty", IntProperty.create(100));
	public static IntProperty intSliderProperty = config.register("intSliderProperty", IntProperty.create(0));
	public static EnumProperty<TestEnum> enumProperty1 = config.register("enumProperty1", EnumProperty.create(TestEnum.class));
	public static EnumProperty<TestEnum> enumProperty2 = config.register("enumProperty2", EnumProperty.create(TestEnum.class));
	public static FloatProperty floatProperty = config.register("floatProperty", FloatProperty.create(0.25f));
	
	static {
		config.load();
	}
	
	////////////////////////////////

	protected GuiButton cancelButton;
	protected SaveConfigButton saveConfigButton;
	
	public ConfigElementTestScreen(IScreen parentScreen) {
		super(parentScreen);
		
		title = B100LibClient.trans.asText("screen.configTest");
	}
	
	@Override
	protected void onInit() {
		saveConfigButton = new SaveConfigButton(this);
		saveConfigButton.addActionListener(source -> {
			Print.print("Save: " + config.configFile.getAbsolutePath());
			config.save();
			back();
		});
		
		super.onInit();
		
		cancelButton = add(new GuiButton(this, B100LibClient.trans.asText("button.cancel")));
		cancelButton.addActionListener((e) -> back());
		
		add(saveConfigButton);
		
		scrollList.layout = new ListLayout();
	}

	@Override
	public void initScrollElements() {
		scrollList.add(BooleanToggleElement.create(this, "b100lib.option.test.boolean.toggle", booleanProperty, UpdateMode.ON_SAVE));
		scrollList.add(IntegerTextFieldElement.create(this, "b100lib.option.test.integer.textField", intProperty, UpdateMode.ON_SAVE));
		scrollList.add(IntegerSliderElement.create(this, "b100lib.option.test.integer.slider", 1, 5, intSliderProperty, UpdateMode.ON_SAVE));
		scrollList.add(FloatSliderElement.create(this, "b100lib.option.test.float.slider", floatProperty, UpdateMode.ON_SAVE));
		scrollList.add(EnumToggleElement.create(this, "b100lib.option.test.enum.toggle", enumProperty1, UpdateMode.ON_SAVE));
		scrollList.add(EnumSliderElement.create(this, "b100lib.option.test.enum.slider", enumProperty2, UpdateMode.ON_SAVE));
	}
	
	@Override
	public void elementAdded(GuiContainer parent, GuiElement element) {
		super.elementAdded(parent, element);
		if(element instanceof ConfigElement<?> configElement) {
			configElement.addConfigElementListener(saveConfigButton);
		}
	}
	
	@Override
	public void onResize() {
		super.onResize();
		setDoubleFooterButtonPositions(cancelButton, saveConfigButton);
	}
}
