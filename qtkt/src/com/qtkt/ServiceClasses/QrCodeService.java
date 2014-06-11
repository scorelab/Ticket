package com.qtkt.ServiceClasses;

import java.io.ByteArrayOutputStream;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QrCodeService {

	String qrtext = "Hello QR";
	ByteArrayOutputStream out;

	public ByteArrayOutputStream QrGeneration() {
		out = QRCode.from(qrtext).to(ImageType.JPG).stream();
		return out;
	}
}
