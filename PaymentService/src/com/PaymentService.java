package com;

import model.Payment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")
public class PaymentService {
	Payment paymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments() {
		return paymentObj.readPayments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("Payment_amount") String Payment_amount,
			@FormParam("Payment_purpose") String Payment_purpose,
			@FormParam("Ruser_ID") String Ruser_ID,
			@FormParam("med_ID") String med_ID) {
		String output = paymentObj.insertPayment(Payment_amount, Payment_purpose, Ruser_ID,
				med_ID);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String PaymentData) {
// Convert the input string to a JSON object
		JsonObject PaymentObject = new JsonParser().parse(PaymentData).getAsJsonObject();
// Read the values from the JSON object
		String Payment_ID = PaymentObject.get("Payment_ID").getAsString();
		String Payment_amount = PaymentObject.get("Payment_amount").getAsString();
		String Payment_purpose = PaymentObject.get("Payment_purpose").getAsString();
		String Ruser_ID = PaymentObject.get("Ruser_ID").getAsString();
		String med_ID = PaymentObject.get("med_ID").getAsString();
		String output = paymentObj.updatePayment(Payment_ID, Payment_amount, Payment_purpose, Ruser_ID,
				med_ID);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String PaymentData) {
// Convert the input string to an XML document
		Document doc = Jsoup.parse(PaymentData, "", Parser.xmlParser());

// Read the value from the element <PaymentID>
		String Payment_ID = doc.select("Payment_ID").text();
		String output = paymentObj.deletePayment(Payment_ID);
		return output;
	}

}
