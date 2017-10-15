package luckyfish.programs.minepet.pet.v1_0R0.display;

import luckyfish.programs.minepet.crash.CrashReport;
import luckyfish.programs.minepet.utils.v1_0R0.crash.CrashDialog;

import java.awt.image.BufferedImage;

public class PetWindow {
	static {
		try {
			System.loadLibrary("PetWindowManager");
		} catch (UnsatisfiedLinkError error) {
			CrashReport crashReport = new CrashReport(error, "Initializing pet", true);
			CrashDialog crashDialog = new CrashDialog(crashReport);
			crashDialog.pack();
			crashDialog.setVisible(true);
		}
	}

	private long windowID;
	private PetWindow(long windowID) {
		this.windowID = windowID;
	}

	public static PetWindow makePetWindow() {
		long windowID = getWindowID();
		return new PetWindow(windowID);
	}
	public void displayAndMaskWindow(BufferedImage image) {
		displayAndMaskWindow(image, windowID);
	}

	private static native long getWindowID();
	private static native void displayAndMaskWindow(BufferedImage image, long windowID);
}
