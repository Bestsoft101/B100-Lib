package b100.lib.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import b100.lib.client.B100LibClient;
import b100.lib.translate.Translate;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.resources.ResourceManager;

@Mixin(value = LanguageManager.class)
public class LanguageManagerMixin {
	
	@Inject(method = "onResourceManagerReload", at = @At("HEAD"))
	private void onReload(ResourceManager resourceManager, CallbackInfo ci) {
		Translate.loadAllTranslations(B100LibClient.getCurrentLanguage());
	}

}
