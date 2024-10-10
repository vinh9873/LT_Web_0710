package vn.ute.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.ute.entity.Category;
import vn.ute.entity.Video;
import vn.ute.services.ICategoryService;
import vn.ute.services.IVideoService;
import vn.ute.services.impl.CategoryService;
import vn.ute.services.impl.VideoService;
import vn.ute.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/edit", "/admin/video/update", 
        "/admin/video/insert", "/admin/video/add", "/admin/video/delete"})
public class VideoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    IVideoService videoService = new VideoService();
    ICategoryService categoryService = new CategoryService(); 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();
        if (url.contains("videos")) {
            
            List<Video> list = videoService.findAll();
            req.setAttribute("listVideo", list);
            req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
            
        } else if (url.contains("edit")) {
            
            List<Category> categories = categoryService.findAll();
            req.setAttribute("listCategories", categories);
            String id = req.getParameter("id");
            Video video = videoService.findById(id);
            req.setAttribute("video", video);
            req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
            
        } else if (url.contains("add")) {
            
            List<Category> categories = categoryService.findAll(); 
            req.setAttribute("listCategories", categories); 
            req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
            
        } else if (url.contains("delete")) {
            
            String id = req.getParameter("id");
            try {
                videoService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();
        
        if (url.contains("update")) {
        	
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            int active = Integer.parseInt(req.getParameter("active"));
            int views = Integer.parseInt(req.getParameter("views"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            
            Video video = videoService.findById(videoId);
            video.setTitle(title);
            video.setDescription(description);
            video.setActive(active);
            video.setViews(views);
            video.setCategory(categoryService.findById(categoryId)); 

            // Handle file upload directly in doPost
            String uploadPath = Constant.UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String fname = "default.jpg"; // Default image
            Part part = req.getPart("poster");
            
            if (part != null && part.getSize() > 0) {
                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int index = filename.lastIndexOf(".");
                String ext = filename.substring(index + 1);
                fname = System.currentTimeMillis() + "." + ext;
                part.write(uploadPath + "/" + fname);
            }
            video.setPoster(fname);

            videoService.update(video);
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            
        } else if (url.contains("insert")) {
        	
            String videoId = req.getParameter("videoId"); 
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            int active = Integer.parseInt(req.getParameter("active"));
            int views = Integer.parseInt(req.getParameter("views")); 
            int categoryId = Integer.parseInt(req.getParameter("categoryId")); 

            if (videoId == null || videoId.trim().isEmpty()) {
                req.setAttribute("errorMessage", "Video ID cannot be empty.");
                req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
                return;
            }

            Video video = new Video();
            video.setVideoId(videoId); 
            video.setTitle(title);
            video.setDescription(description);
            video.setActive(active);
            video.setViews(views);
            video.setCategory(categoryService.findById(categoryId)); 

            // Handle file upload directly in doPost
            String uploadPath = Constant.UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String fname = "default.jpg"; // Default image
            Part part = req.getPart("poster");
            if (part != null && part.getSize() > 0) {
            	
                String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int index = filename.lastIndexOf(".");
                String ext = filename.substring(index + 1);
                fname = System.currentTimeMillis() + "." + ext;
                part.write(uploadPath + "/" + fname);
                
            }
            video.setPoster(fname);

            // Insert to the database
            videoService.insert(video);
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }
}
