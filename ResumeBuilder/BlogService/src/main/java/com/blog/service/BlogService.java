package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.dao.BlogRepository;
import com.blog.entity.Blog;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getTopRatedBlogs() {
        return blogRepository.findAllByOrderByRatingsDesc();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog, MultipartFile file) {
        String imageUrl = fileStorageService.storeFile(file);
        blog.setImageUrl(imageUrl);
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog blogDetails, MultipartFile file) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blog.setTitle(blogDetails.getTitle());
        blog.setName(blogDetails.getName());
        blog.setDescription(blogDetails.getDescription());
        blog.setRatings(blogDetails.getRatings());
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(file);
            blog.setImageUrl(imageUrl);
        }
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}

