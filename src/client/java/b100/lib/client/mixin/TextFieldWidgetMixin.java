package b100.lib.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import b100.lib.client.access.TextFieldWidgetAccess;
import net.minecraft.client.gui.components.EditBox;

@Mixin(value = EditBox.class)
public abstract class TextFieldWidgetMixin implements TextFieldWidgetAccess {

	@Override
	public boolean isFocusable() {
		return isEditable();
	}
	
	@Shadow
	public abstract boolean isEditable();
	
}
