package com.msgpig.notification.entities.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.msgpig.notification.entities.entities.PrescVaccine;
import com.msgpig.notification.entities.entities.Reminder;

public class HandlebarsHelper {

	public static String replaceTemplate(String templateStr, Object payload) {
		if (templateStr != null && payload != null) {
			Handlebars hbr = getHandleBarInstance();
			if (hbr != null) {
				try {
					Template temp = hbr.compileInline(templateStr);
					if (temp != null) {
						return temp.apply(payload).toString();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return templateStr;

	}

	private static Handlebars handlebars;
	
	private static ObjectMapper mapper;

	private static Handlebars getHandleBarInstance() {
		if (handlebars == null) {
			initHandleBar();
		}
		return handlebars;
	}

	private static void initHandleBar() {
		handlebars = new Handlebars();
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		handlebars.registerHelper("convertDate", new Helper<Long>() {
			@Override
			public Object apply(Long arg0, Options arg1) throws IOException {
				return CommonUtils.getDateInMMDDYYYYFromLong(arg0);
			}
		});
		
		handlebars.registerHelper("getOnlyDate", new Helper<Long>() {
			@Override
			public Object apply(Long arg0, Options arg1) throws IOException {
				if(CommonUtils.getDateWithZeroTime(CommonUtils.getCurrentTimeInMillis()).equals(CommonUtils.getDateWithZeroTime(arg0)))
					return "today";
				else
					return "on " + CommonUtils.timestampToDate(arg0, "dd MMM");
			}
		});

		handlebars.registerHelper("providerOffering", new Helper<String>() {
			@Override
			public Object apply(String arg0, Options arg1) throws IOException {
				return CommonUtils.getOfferingByProvider(arg0);
			}
		});

		handlebars.registerHelper("providerType", new Helper<String>() {
			@Override
			public Object apply(String arg0, Options arg1) throws IOException {
				return CommonUtils.getProviderTextProvider(arg0);
			}
		});

		handlebars.registerHelper("stringListToString", new Helper<List<String>>() {
			@Override
			public Object apply(List<String> arg0, Options arg1) throws IOException {
				if (arg0 != null) {
					return StringUtils.join(arg0, ',');
				}
				return "";
			}
		});

		handlebars.registerHelper("getVaccineString", new Helper<List<PrescVaccine>>() {
			@Override
			public Object apply(List<PrescVaccine> arg0, Options arg1) throws IOException {
				if (arg0 != null && arg0.size() == 1) {
					return arg0.get(0).getVaccineName();
				} else
					return "all vaccines";
			}
		});

		handlebars.registerHelper("getCustomerName", new Helper<Object>() {
			@Override
			public Object apply(Object arg0, Options arg1) throws IOException {
				
				Reminder reminder = mapper.convertValue(arg0, Reminder.class);
				if(reminder!=null) {
					if (reminder.getForCustomerInfo() != null && reminder.getForCustomerInfo().getCustomerName() != null)
						return reminder.getForCustomerInfo().getCustomerName();
					else if (reminder.getCustomerInfo() != null && reminder.getCustomerInfo().getCustomerName() != null)
						return reminder.getCustomerInfo().getCustomerName();
				}
				
				return "";
			}
		});
		
//		handlebars.registerHelper("getCustomerName", new Helper<LinkedHashMap<String, Object>>() {
//			@Override
//			public Object apply(LinkedHashMap<String, Object> arg0, Options arg1) throws IOException {
//				
//				LinkedHashMap parentCustomer = (LinkedHashMap)arg0.get("customerInfo");
//				LinkedHashMap forCustomer = (LinkedHashMap)arg0.get("forCustomerInfo");
//				
//				if (forCustomer != null) {
//					String name = (String) forCustomer.get("customerName");
//					return name!=null ? name : "";
//				}else if (parentCustomer != null) {
//					String name = (String) parentCustomer.get("customerName");
//					return name!=null ? name : "";
//				}else
//					return "";
//			}
//		});

		handlebars.registerHelper("url", new Helper<String>() {
			@Override
			public Object apply(String arg0, Options arg1) throws IOException {
				if (arg0 != null)
					return arg0 + " days";
				else
					return "";
			}
		});

	}
}
