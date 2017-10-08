package com.leszczynski.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String title;

	private String originalTitle;

	@Column(nullable = true)
	private Long authorID;

	@Column(length = 13)
	private String ISBN;

	private String year;

	private Long publishingHouseID;// wydawnictwo

	@Column(length = 2000)
	private String description;

	private Long reservationID;// id osoby rezerwujacej

	private Long hireID;// wypozyczenie

	@Column(nullable = true)
	private Boolean isRemoved;

	private Date reservationDate; // data rezerwacji

	private int hireTime;// okres wypożyczenia

	public BookEntity(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public Long getAuthorID() {
		return authorID;
	}

	public void setAuthorID(Long authorID) {
		this.authorID = authorID;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getPublishingHouseID() {
		return publishingHouseID;
	}

	public void setPublishingHouseID(Long publishingHouseID) {
		this.publishingHouseID = publishingHouseID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getReservationID() {
		return reservationID;
	}

	public void setReservationID(Long reservationID) {
		this.reservationID = reservationID;
	}

	public Long getHireID() {
		return hireID;
	}

	public void setHireID(Long hireID) {
		this.hireID = hireID;
	}

	public Boolean getIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(Boolean isRemoved) {
		this.isRemoved = isRemoved;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getHireTime() {
		return hireTime;
	}

	public void setHireTime(int hireTime) {
		this.hireTime = hireTime;
	}
}
