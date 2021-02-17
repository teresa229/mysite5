package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	
	//리스트
	public List<GalleryVo> list(){
		System.out.println("[GalleryService]:list");
		
		return galleryDao.list();
	}
	
	//등록
	public int upload(GalleryVo galleryVo, MultipartFile file) {
		System.out.println("[GalleryService]:upload");
		
		/* db에 저장할 정보 수집 */
		String saveDir = "c:\\javastudy\\upload";
		
		//이름 1:
		String orgName = file.getOriginalFilename();
		
		//확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		
		//서버 저장 이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		
		//서버 저장 경로
	    String filePath = saveDir + "\\" + saveName;
	    
	    //값 입력 전
	    System.out.println("[GalleryService]:upload 입력 전 "+ galleryVo);
	    System.out.println("[GalleryService]:upload 입력 전 "+ file.getOriginalFilename());
	    
	    //값 넣어주기
	    galleryVo.setSaveName(saveName);
	    galleryVo.setFilePath(filePath);  
	    galleryVo.setOrgName(file.getOriginalFilename());
	    galleryVo.setFileSize(file.getSize());
	    
	    System.out.println("[GalleryService]:upload 입력 후 "+ galleryVo);
	    System.out.println("[GalleryService]:upload 입력 후 "+ file.getOriginalFilename());
	 
	    //db에 갤러리 게시글 정보 저장
	    int dbResult = galleryDao.insert(galleryVo); 
	    
	    
	    /* 서버의 하드디스크에 저장 */
	    try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return dbResult;
	}

}
