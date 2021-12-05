package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Bookmark;
import com.example.demo.domain.BookmarkRepository;

@RestController
public class BookmarkController {
	@Autowired
	private BookmarkRepository repo;

	@RequestMapping("bookmarks")
	public Iterable<Bookmark> getBookmarks() {
		return repo.findAll();
	}
}
