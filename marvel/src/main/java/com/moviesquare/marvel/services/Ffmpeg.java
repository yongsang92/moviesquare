package com.moviesquare.marvel.services;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/* gif to mp4 converter */
@Component
public class Ffmpeg {
    private String ffmpegEXE="C:\\Download\\ffmpeg\\bin\\ffmpeg.exe"; // 이건 무조건 바꿔야 한다 위치를 하드 코딩하면 사단 난다

    

    public void convertor(String videoInputPath, String videoOutputPath) throws Exception {
        // commandline example : ffmpeg -i input.mp4 -y output.avi
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i"); //  input file url
        command.add(videoInputPath);
        command.add("-y"); // Overwrite output files without asking.
        command.add(videoOutputPath);

        for (String c : command) {
            System.out.print(c + " ");
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
        }

        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }

    }
}