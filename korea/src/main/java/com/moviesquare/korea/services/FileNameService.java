package com.moviesquare.korea.services;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class FileNameService {

    // TODO : 수정하기
    private String uploadLocation = "C:\\Download\\ffmpeg\\upload\\"; // 이건 무조건 바꿔야 한다 위치를 하드 코딩하면 사단 난다

    public String createNewFileName(String originfileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(new Date());
        String fileExtendType = originfileName.substring(originfileName.length() - 4, originfileName.length());
        return time + getRandomString() + fileExtendType;
    }

    public File createTargetFile(String newFileName) {
        return new File(uploadLocation + newFileName);
    }

    public String getInputPath(String newFileName) {
        return uploadLocation + newFileName;
    }

    public String getOutputPath() {
        return uploadLocation + getTodayDate() + getRandomString() + ".mp4";
    }

    public String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    public String getRandomString() {
        char[] charaters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(charaters[rn.nextInt(charaters.length)]);
        }
        return sb.toString();
    }

}