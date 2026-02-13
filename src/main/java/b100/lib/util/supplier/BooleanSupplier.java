package b100.lib.util.supplier;

import java.util.function.Supplier;

@FunctionalInterface
public interface BooleanSupplier extends Supplier<Boolean> {
	
	public boolean getBoolean();
	
	@Deprecated
	@Override
	default Boolean get() {
		return getBoolean();
	}
	
}
