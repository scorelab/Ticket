package com.qtkt.ServiceClasses;

import java.io.ByteArrayOutputStream;

import org.json.simple.JSONObject;

import com.qtkt.ServiceClasses.HelperClasses.EncryptionService;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class QrCodeService {

	private final String image = "data:image/png;base64,";
	private String output = "";
	private String data = "";
	private ByteArrayOutputStream out;
	private EncryptionService es;

	public String getImage(JSONObject jobj) {
		es = new EncryptionService();
		data = es.doEncryption(jobj.toJSONString());
		out = QRCode.from(data).to(ImageType.JPG).stream();
		output = image + Base64.encode(out.toByteArray());
		return output;
	}
}
