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
import b100.lib.config.properties.DetailedPropertiesWriter;
import b100.lib.config.properties.PropertiesFile;
import b100.lib.config.property.BooleanProperty;
import b100.lib.config.property.EnumProperty;
import b100.lib.config.property.FloatProperty;
import b100.lib.config.property.IntProperty;

class ConfigElementTestScreen extends GuiScrollListScreen {
	
	public final PropertiesFile properties = new PropertiesFile(new File(B100Lib.CONFIG_FOLDER, B100Lib.MODID + "_config_demo.properties"));

	public final BooleanProperty booleanToggle = properties.add("booleanToggle", BooleanProperty.create(false));
	public final IntProperty intTextField = properties.add("intTextField", IntProperty.create(100));
	public final IntProperty intSlider = properties.add("intSlider", IntProperty.create(1));
	public final FloatProperty floatSlider = properties.add("floatSlider", FloatProperty.create(0.25f));
	public final EnumProperty<TestEnum> enumToggle = properties.add("enumToggle", EnumProperty.create(TestEnum.class));
	public final EnumProperty<TestEnum> enumSlider = properties.add("enumSlider", EnumProperty.create(TestEnum.class));

	public final BooleanProperty disabledBooleanToggle = BooleanProperty.create(false);
	public final IntProperty disabledIntTextField = IntProperty.create(0);
	public final FloatProperty disabledFloatSlider = FloatProperty.create(0.0f);
	
	protected GuiButton cancelButton;
	protected SaveConfigButton saveConfigButton;
	
	public ConfigElementTestScreen(IScreen parentScreen) {
		super(parentScreen);
		
		title = B100LibClient.TRANS.asText("screen.configTest");
		
		properties.setWriter(new DetailedPropertiesWriter(properties).setCommentProvider(key -> B100LibClient.TRANS.asStringOrNull("configDemo." + key + ".tooltip")));
		
		Print.print("Load: " + properties.getFile().getAbsolutePath());
		properties.load();
	}
	
	@Override
	protected void onInit() {
		saveConfigButton = new SaveConfigButton(this);
		saveConfigButton.addActionListener(source -> {
			Print.print("Save: " + properties.getFile().getAbsolutePath());
			properties.save();
			back();
		});
		
		super.onInit();
		
		cancelButton = add(new GuiButton(this, B100LibClient.TRANS.asText("button.cancel")));
		cancelButton.addActionListener((e) -> back());
		
		add(saveConfigButton);
		
		scrollList.layout = new ListLayout();
	}

	@Override
	public void initScrollElements() {
		scrollList.add(BooleanToggleElement.create(this, "b100lib.configDemo.booleanToggle", booleanToggle, UpdateMode.ON_SAVE));
		scrollList.add(IntegerTextFieldElement.create(this, "b100lib.configDemo.intTextField", intTextField, UpdateMode.ON_SAVE));
		scrollList.add(IntegerSliderElement.create(this, "b100lib.configDemo.intSlider", 1, 5, intSlider, UpdateMode.ON_SAVE));
		scrollList.add(FloatSliderElement.create(this, "b100lib.configDemo.floatSlider", floatSlider, UpdateMode.ON_SAVE));
		scrollList.add(EnumToggleElement.create(this, "b100lib.configDemo.enumToggle", enumToggle, UpdateMode.ON_SAVE));
		scrollList.add(EnumSliderElement.create(this, "b100lib.configDemo.enumSlider", enumSlider, UpdateMode.ON_SAVE));

		BooleanToggleElement e0 = BooleanToggleElement.create(this, "b100lib.configDemo.disabledBooleanToggle", disabledBooleanToggle, UpdateMode.ON_SAVE);
		e0.setEnabled(false);
		scrollList.add(e0);
		
		FloatSliderElement e1 = FloatSliderElement.create(this, "b100lib.configDemo.disabledFloatSlider", disabledFloatSlider, UpdateMode.ON_SAVE);
		e1.setEnabled(false);
		scrollList.add(e1);
		
		IntegerTextFieldElement e2 = IntegerTextFieldElement.create(this, "b100lib.configDemo.disabledIntTextField", disabledIntTextField, UpdateMode.ON_SAVE);
		e2.setEnabled(false);
		scrollList.add(e2);
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
