package com.example.onsenreviewapp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "onsens")
public class Onsen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "onsen_id")
	private Long id;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "image_url", nullable = false, length = 255)
	private String imageUrl;
	
	@Column(name = "address", nullable = false, length = 255)
	private String address;
	
	@Column(name = "phone", length = 20)
	private String phone;
	
	@Column(name = "business_hours", length = 100)
	private String businessHours;
	
	@Column(name = "holiday", length = 100)
	private String holiday;
	
	@Column(name = "adult_price", nullable = false)
	private Integer adultPrice;
	
	@Column(name = "child_price")
	private Integer childPrice;
	
	@Column(name = "nearest_station", length = 100)
	private String nearestStation;
	
	@Column(name = "station_time")
	private Integer stationTime;

	@Column(name = "prefecture", nullable = false, length = 20)
	private String prefecture;
	
	@Column(name = "family_bath", nullable = false)
	private Boolean familyBath;

	@Column(name = "sauna", nullable = false)
	private Boolean sauna;

	@Column(name = "open_air_bath", nullable = false)
	private Boolean openAirBath;

	@Column(name = "restaurant", nullable = false)
	private Boolean restaurant;

	@Column(name = "alcohol", nullable = false)
	private Boolean alcohol;

	@Column(name = "towel_rental", nullable = false)
	private Boolean towelRental;

	@Column(name = "parking", nullable = false)
	private Boolean parking;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}
