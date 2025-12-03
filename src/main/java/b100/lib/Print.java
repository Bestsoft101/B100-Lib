package b100.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Print {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(B100Lib.MODID);
	
	public static void debugPrint(String str) {
		if(B100Lib.INDEV) {
			System.out.print("[B100Lib-Debug] " + str + "\n");	
		}
	}
	
	public static void print(String str) {
		if(B100Lib.INDEV) {
			System.out.print("[B100Lib] " + str + "\n");	
		}else {
			LOGGER.info("[B100Lib] " + str);
		}
	}
}
