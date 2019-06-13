package com.msgpig.notification.entities.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reminder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reminderId;
	private Customer customerInfo;
	private Customer forCustomerInfo;
	private Doctor doctorInfo;
	private HospitalClinic hospitalInfo;
	private Long reminderDateTime;
	private Long originalDueDateTime;
	private List<PrescVaccine> vaccine;
	private Long bookingId;
	private String prescriptionId;
	private Integer reminderCount;
	private Long createdAt;
	private Long updatedAt;

}
