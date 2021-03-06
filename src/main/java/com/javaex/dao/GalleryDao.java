package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GalleryVo> list() {
		System.out.println("[GalleryDao]:list");
		
		return sqlSession.selectList("gallery.selectList");
	}
	
	public int insert(GalleryVo galleryVo) {
		System.out.println("[GalleryDao]:insert");
		
		return sqlSession.insert("gallery.insert", galleryVo);
	}
	
	public int delete(int no) {
		System.out.println("[GalleryDao]:delete");
		
		return sqlSession.delete("gallery.delete", no);
	}
}
