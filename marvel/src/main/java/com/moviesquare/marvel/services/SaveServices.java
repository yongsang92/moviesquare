package com.moviesquare.marvel.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.moviesquare.marvel.converter.MovieDTO;
import com.moviesquare.marvel.converter.StoryDTO;
import com.moviesquare.marvel.models.Categories;
import com.moviesquare.marvel.models.Movie;
import com.moviesquare.marvel.models.Story;
import com.moviesquare.marvel.models.Tag;
import com.moviesquare.marvel.repository.CategoriesRepo;
import com.moviesquare.marvel.repository.StoryRepo;
import com.moviesquare.marvel.repository.TagRepo;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveServices {

    private final StoryRepo storyRepo;
    private final TagRepo tagRepo;
    private final CategoriesRepo categoriesRepo;
    private final FileNameService fileNameService;
    private final Ffmpeg ffmpeg;
    @Transactional
    public void saveGif(MovieDTO dto, MultipartFile file) {

        
        String newFileName = fileNameService.createNewFileName(file.getOriginalFilename());
        File targetFile = fileNameService.createTargetFile(newFileName);

        String in = fileNameService.getInputPath(newFileName);
        String out = fileNameService.getOutputPath();

        Movie movieEntity = dto.toEntity();
        movieEntity.setImgPath(out);

        for (String tag : dto.getTags()) {

            Optional<Tag> optionalTag = tagRepo.findTagByTag(tag.trim());
            optionalTag.ifPresentOrElse(tagEntity -> {
                System.out.println(tagEntity.getTag());
                for (Categories c : tagEntity.getCategories()) {
                    System.out.println(c);
                }
                Categories categoriesEntity = new Categories();
                categoriesEntity.setTag(tagEntity);
                categoriesEntity.setMovie(movieEntity);
                categoriesRepo.save(categoriesEntity);
            }, () -> {
                Tag newTag = new Tag();
                newTag.setTag(tag.trim());
                Categories categoriesEntity = new Categories();
                categoriesEntity.setTag(newTag);
                categoriesEntity.setMovie(movieEntity);
                categoriesRepo.save(categoriesEntity);

            });

        }
        try {
            InputStream fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            e.printStackTrace();
            // TODO : 에러처리
        }
        try {
            ffmpeg.convertor(in, out);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO : 에러처리
        }

    }
    @Transactional
    public void saveStory(StoryDTO dto,MultipartFile[] files){
        String storyTitle=dto.getTitle();
        String[] gifComments=dto.getComments();
        String[] imgTitle=dto.getImgTitles();
        String[] tags=dto.getTags();

        List<Movie> movies = new ArrayList<>();
        Story story = new Story();
        story.setTitle(storyTitle);
        for (int i = 0; i < files.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(imgTitle[i].trim());
            movie.setComments(gifComments[i].trim());
            movie.setTags(tags[i].trim());
            movie.setImgPath(fileNameService.getOutputPath());
            movies.add(movie);
            System.out.println("movie info print: " + movie);
            String[] tagSplit=tags[i].split(",");
            System.out.println("movie's tags");
            for (String str : tagSplit) {
                String tag=str.trim();
                Optional<Tag> optionalTag = tagRepo.findTagByTag(tag);
                optionalTag.ifPresentOrElse(tagEntity -> {
                 
                    Categories categoriesEntity = new Categories();
                    categoriesEntity.setTag(tagEntity);
                    categoriesEntity.setMovie(movie);
                    categoriesRepo.save(categoriesEntity);
                }, () -> {
                    Tag newTag = new Tag();
                    newTag.setTag(tag);
                    Categories categoriesEntity = new Categories();
                    categoriesEntity.setTag(newTag);
                    categoriesEntity.setMovie(movie);
                    categoriesRepo.save(categoriesEntity);
                });
            }
        }
        story.setMainImgPath(movies.get(0).getImgPath());
        for (Movie m : movies) {
            story.addMovie(m);
        }
        storyRepo.save(story);

        for (int i = 0; i < files.length; i++) {
            String newFileName = fileNameService.createNewFileName(files[i].getOriginalFilename());
            File targetFile = fileNameService.createTargetFile(newFileName);

            String in = fileNameService.getInputPath(newFileName);
            String out = movies.get(i).getImgPath();
            try {
                InputStream fileStream = files[i].getInputStream();
                FileUtils.copyInputStreamToFile(fileStream, targetFile);
            } catch (IOException e) {
                FileUtils.deleteQuietly(targetFile);
                e.printStackTrace();
                // TODO : 에러처리
            }
            try {
                ffmpeg.convertor(in, out);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO : 에러처리
            }
        }

    }

   

}