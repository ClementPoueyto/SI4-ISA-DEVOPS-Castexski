package cli.commands;

import cli.utils.TimesOfDay;
import stubs.cart.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddPass extends CartManagement {

	private PassType passType;
	private TimesOfDay timeOfDay;
	private int nbDays;
	private LocalDateTime startDate;
	private PersonType isChild;

	private String cardLinkId = null;
	private String zone;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public void load(List<String> args) {
		super.load(args);
		this.passType = PassType.valueOf(args.get(2).toUpperCase());
		this.cardLinkId = args.get(3);
		this.timeOfDay = TimesOfDay.valueOf(args.get(4).toUpperCase());
		this.nbDays = Integer.parseInt(args.get(5));
		this.startDate = LocalDate.parse(args.get(6), formatter).atStartOfDay();
		this.isChild = PersonType.valueOf(args.get(7).toUpperCase());
		this.zone = args.get(8);
	}

	@Override
	public String identifier() { return "add-pass"; }

	public static String toString(DateSerializable d) {
		return "DateSerializable{" +
				"year=" + d.getYear() +
				", month=" + d.getMonth() +
				", dayOfMonth=" + d.getDayOfMonth() +
				", hour=" + d.getHour() +
				", minute=" + d.getMinute() +
				'}';
	}

	@Override
	public void execute() throws Exception {

		PassItem passItem = new PassItem();
		passItem.setQuantity(quantity);
		passItem.setPassType(this.passType);
		passItem.setCardLinkId(cardLinkId);


		LocalDateTime endDate = startDate;

		if(timeOfDay == TimesOfDay.FULL_DAY || timeOfDay == TimesOfDay.MORNING) {
			this.startDate = this.startDate.withHour(9);
		}
		else if(timeOfDay == TimesOfDay.AFTERNOON) {
			this.startDate = this.startDate.withHour(13);
		}

		if(timeOfDay == TimesOfDay.MORNING) {
			endDate = endDate.withHour(13);
		} else {
			endDate = endDate.withHour(18);
		}

		if(nbDays > 1 && timeOfDay == TimesOfDay.FULL_DAY) {
			endDate = endDate.plusDays(nbDays - 1);
		}

		DateSerializable start = new DateSerializable();
		start.setYear(startDate.getYear()); start.setMonth(startDate.getMonthValue()); start.setDayOfMonth(startDate.getDayOfMonth());
		start.setHour(startDate.getHour()); start.setMinute(startDate.getMinute());
		passItem.setStart(start);

		DateSerializable end  = new DateSerializable();
		end.setYear(endDate.getYear()); end.setMonth(endDate.getMonthValue()); end.setDayOfMonth(endDate.getDayOfMonth());
		end.setHour(endDate.getHour()); end.setMinute(endDate.getMinute());
		passItem.setEnd(end);


		passItem.setPersonType(this.isChild);

		passItem.setZones(this.zone);
		shell.system.carts.addItemToCustomerCart(customerName, passItem);
	}

	@Override
	public String describe() {
		return "Add pass to cart (add-pass CUSTOMER_EMAIL QUANTITY PASS_TYPE [CARD_LINK_ID])";
	}
}
