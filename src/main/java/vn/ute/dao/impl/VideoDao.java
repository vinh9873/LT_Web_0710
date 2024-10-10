package vn.ute.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.ute.configs.JPAConfig;
import vn.ute.dao.IVideoDao;
import vn.ute.entity.Category;
import vn.ute.entity.Video;

public class VideoDao implements IVideoDao{
	
	@Override
	public void insert(Video videgory) {
		
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
	

		try {

			trans.begin();
			enma.persist(videgory); //insert vào bảng
			trans.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			trans.rollback();
			throw e;
			
		} finally {
			enma.close();
		}
	}
	
	@Override
	public void update(Video videgory) {
		
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		
		try {

			trans.begin();
			enma.merge(videgory); //update
			trans.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			trans.rollback();
			throw e;
			
		} finally {
			enma.close();
		}
		
	}
	
	@Override
	public void delete(String videid) throws Exception {

		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		
		try {

			trans.begin();
			Category category = enma.find(Category.class,videid);
			if(category != null) {
				enma.remove(category);
			} else {
				throw new Exception("Không tìm thấy!");
			}
			trans.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			trans.rollback();
			throw e;
			
		} finally {
			enma.close();
		}
		
	}
	@Override
	public Video findById(String videid) {
		EntityManager enma = JPAConfig.getEntityManager();
		Video video = enma.find(Video.class,videid);
		return video;
	}

	@Override
	public List<Video> findAll() {
		EntityManager enma = JPAConfig.getEntityManager();
		TypedQuery<Video> query = enma.createNamedQuery("Video.findAll", Video.class);
		return query.getResultList();
	}
	
	@Override
	public int count() {
		EntityManager enma = JPAConfig.getEntityManager();
		String jpql = "SELECT count(c) FROM Category c";
		Query query = enma.createQuery(jpql);
		return ((Long)query.getSingleResult()).intValue();
	}

	@Override
	public List<Video> findAll(int page, int pagesize) {
		EntityManager enma = JPAConfig.getEntityManager();
		TypedQuery<Video> query = enma.createNamedQuery("Video.findAll", Video.class);
		query.setFirstResult(page * pagesize);
		query.setMaxResults(pagesize);
		return query.getResultList();
	}

	@Override
	public List<Video> findByVideoname(String videname) {
		EntityManager enma = JPAConfig.getEntityManager();
	    String jpql = "SELECT c FROM Category c WHERE c.videname like :videname";
	    TypedQuery<Video> query = enma.createQuery(jpql, Video.class);
	    query.setParameter("videname","%"+ videname + "%");
	    return query.getResultList();
	}

}
