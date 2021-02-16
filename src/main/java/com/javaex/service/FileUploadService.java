package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String restore(MultipartFile file) {
		System.out.println("FileUploadService:restore");
		System.out.println(file.getOriginalFilename());
		
/////	//중요한 역할 1: db에 저장할 정보 수집////////////////////////////////  파일 경로를 만들어 둔다.
		String saveDir = "C:\\javastudy\\upload"; 
		
		//이름 1: 오리지널 파일 이름
		String orgName = file.getOriginalFilename();
		System.out.println("orgName:"+ orgName);
		
		//확장자(옵션: 엑셀/파워포인트 확장자에 따라 다르게 보여준다)
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName="+ exName);
		
		//이름 2: 서버 저장파일 이름 : 관리하는 파일. 겹치지 않게
		String saveName = System.currentTimeMillis()+ UUID.randomUUID().toString()+ exName; //안겹치는 것을 넣어준다 + 겹치지 않는 값을 준다.
		System.out.println("saveName="+ saveName);
		
		//서버의 파일패스 --> 하드디스크의 저장경로 경로 + 파일이름까지 담으려고 한다.
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath="+ filePath);
		
		//파일 사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize="+fileSize);
		
/////   //중요한 역할 2: 서버의 하드디스크에 파일저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return saveName;
	}
}
