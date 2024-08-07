package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entity.Blog;
import com.blog.service.BlogService;

import java.io.IOException;
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

    @PostMapping("/createblog")
    public Blog createBlog(@RequestParam String title, 
    		@RequestParam String name, 
    		@RequestParam String description,
    		@RequestParam int ratings,
    		@RequestParam MultipartFile image) throws IOException{
    	Blog blog = new Blog();
    	blog.setTitle(title);
    	blog.setName(name);
    	blog.setDescription(description);
    	blog.setRatings(ratings);
        return blogService.createBlog(blog, image);
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

