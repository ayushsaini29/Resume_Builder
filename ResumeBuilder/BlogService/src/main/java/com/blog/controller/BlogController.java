package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entity.Blog;
import com.blog.service.BlogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/top-rated")
    public List<Blog> getTopRatedBlogs() {
        return blogService.getTopRatedBlogs();
    }

    @GetMapping("/{id}")
    public Optional<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public Blog createBlog(@RequestParam("blog") Blog blog, @RequestParam("file") MultipartFile file) {
        return blogService.createBlog(blog, file);
    }

    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestParam("blog") Blog blogDetails, @RequestParam(value = "file", required = false) MultipartFile file) {
        return blogService.updateBlog(id, blogDetails, file);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }
}

