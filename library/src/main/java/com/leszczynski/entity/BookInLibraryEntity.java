package com.leszczynski.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_in_library")
public class BookInLibraryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long bookID;

	private Long LibraryID;

	public Long getBookID() {
		return bookID;
	}

	public void setBookID(Long bookID) {
		this.bookID = bookID;
	}

	public Long getLibraryID() {
		return LibraryID;
	}

	public void setLibraryID(Long libraryID) {
		LibraryID = libraryID;
	}

}
