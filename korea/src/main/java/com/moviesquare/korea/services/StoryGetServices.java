package com.moviesquare.korea.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import com.moviesquare.korea.models.Story;
import com.moviesquare.korea.repository.StoryRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryGetServices {

    private final StoryRepo storyRepo;

    public List<Story> getStories(Integer page) throws IOException {
        Pageable paging = PageRequest.of(page, 6);
        Page<Story> result = storyRepo.findAll(paging);
        imgsTobyteArray(result.getContent());

        return result.getContent();

    }

    public Story getStory(Integer id) throws IOException {
        return storyRepo.findById(id).get();
    }

    public int getTotal() {
        return storyRepo.findAll().size();
    }

    public void imgsTobyteArray(List<Story> movies) throws IOException {

        Iterator<Story> iter = movies.iterator();
        while (iter.hasNext()) {
            Story story = iter.next();
            String mainImgPath = story.getMainImgPath();
            byte[] imgbyteArry = Files.readAllBytes(Paths.get(mainImgPath));
            story.setImgbyteArray(imgbyteArry);
        }

    }
    
    public List<Story> getOldStories(Integer id) throws IOException {
        Pageable paging=PageRequest.of(0,5,Sort.Direction.ASC,"id");
        List<Story> result=storyRepo.findByIdLessThan(id,paging);
        imgsTobyteArray(result);
        return result;
    }
}