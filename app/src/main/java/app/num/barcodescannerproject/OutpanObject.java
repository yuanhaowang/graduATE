package app.num.barcodescannerproject;

import org.json.JSONException;
import org.json.JSONObject;

public class OutpanObject {

	public String
		gtin,
		outpan_url,
		name;

	public OutpanObject() {
		this.gtin = "";
		this.outpan_url = "";
		this.name = "";
	}
	
	public OutpanObject(JSONObject json) {
		this();
		try {

			//this.gtin = json.getString("gtin");
			//this.outpan_url = json.getString("outpan_url");

			/*if (!json.isNull("name"))
				this.name = json.getString("name");*/

			if (!json.isNull("description"))
				this.name = json.getString("description");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}