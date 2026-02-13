package b100.lib.client.util.supplier;

import java.util.function.Supplier;

@FunctionalInterface
public interface IntSupplier extends Supplier<Integer> {
	
	public int getInt();
	
	@Deprecated
	@Override
	default Integer get() {
		return getInt();
	}
	
}
