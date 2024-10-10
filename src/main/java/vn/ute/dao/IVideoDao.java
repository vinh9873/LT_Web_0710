package vn.ute.dao;

import java.util.List;

import vn.ute.entity.Video;

public interface IVideoDao {
	
	int count();
	List<Video> findAll(int page, int pagesize);
	List<Video> findByVideoname(String videname);
	List<Video> findAll();
	Video findById(String videid);
	void delete(String videid) throws Exception;
	void update(Video videgory);
	void insert(Video videgory);

}
