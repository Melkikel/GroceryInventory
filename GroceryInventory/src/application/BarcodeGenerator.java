package application;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class BarcodeGenerator {

	public static Image generate(String barcodeText) throws Exception {
		Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
		Image i = SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null);
		return i;
	}
}
