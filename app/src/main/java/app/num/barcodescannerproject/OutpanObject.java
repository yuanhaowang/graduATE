package app.num.barcodescannerproject;

import org.json.JSONException;
import org.json.JSONObject;

public class OutpanObject {

	public String
		gtin,
		outpan_url,
		valid,
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
			this.valid = json.getString("valid");

			if(valid != "false")
			{
				if (!json.isNull("description"))
					this.name = json.getString("description");
				else
					this.name = json.getString("name");
			}

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}