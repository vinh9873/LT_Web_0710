package vn.ute.services.impl;

import java.util.List;

import vn.ute.dao.IVideoDao;
import vn.ute.dao.impl.VideoDao;
import vn.ute.entity.Video;
import vn.ute.services.IVideoService;

public class VideoService implements IVideoService {

IVideoDao videDao = new VideoDao();



	
	@Override
	public int count() {
		return videDao.count();
	}

	public List<Video> findAll(int page, int pagesize) {
		return videDao.findAll(page, pagesize);
	}

	public List<Video> findByVideoname(String catname) {
		return videDao.findByVideoname(catname);
	}

	public List<Video> findAll() {
		return videDao.findAll();
	}

	public Video findById(String videid) {
		return videDao.findById(videid);
	}

	@Override
	public void delete(String videid) throws Exception {
		videDao.delete(videid);
	}

	@Override
	public void update(Video video) {
		videDao.update(video);
	}

	@Override
	public void insert(Video video) {
		videDao.insert(video);
	}




	
}
