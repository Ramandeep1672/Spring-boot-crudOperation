package com.techtez.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name="EMPLOYEE_TABLE")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
	
	@Id
	@Column(name="EMP_ID")
	@SequenceGenerator(name="seq1",sequenceName="EMP_SEQ",initialValue=1000,allocationSize=1)
	@GeneratedValue(generator="seq1", strategy=GenerationType.SEQUENCE)
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="EMp_CODE")
	@NonNull
	@NotNull(message = "Employee code is required")
    @Min(value = 1000, message = "Employee code must be minimum 1000")
	private Long employeeCode;
	
	@Column(name="FIRST_NAME")
	@NonNull
	@NotBlank(message="Employee fisrt name Required")
	private String first_name;
	
	@Column(name="LAST_NAME")
	@NonNull
	private String last_name;
	
	@Column(name="EMAIL_ID")
	@NonNull
	 @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email_id;
	
	@Column(name="MOBILE_NUMBER")
	@NonNull
	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
	private String mobile_no;
	
	@Column(name="EMP_DEPT")
	@NonNull
	@NotBlank(message = "Department is required")
	private String department;
	
	@Column(name="EMP_SALARY" ,nullable=false)
	@NonNull
	@NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
	private Double salary;
	
	@Column(name="JOINING_DATE",updatable=false)
	@CreationTimestamp()
	private LocalDateTime created_at;
	
	@Column(name="LASTLY_OPERATED_ON")
	@UpdateTimestamp
	private LocalDateTime updated_at;

}
