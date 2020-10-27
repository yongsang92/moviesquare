package com.moviesquare.japan.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import com.moviesquare.japan.models.Movie;
import com.moviesquare.japan.models.Tag;
import com.moviesquare.japan.repository.CategoriesRepo;
import com.moviesquare.japan.repository.MovieRepo;
import com.moviesquare.japan.repository.TagRepo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServices {

    private final MovieRepo movieRepo;
    private final TagRepo tagRepo;
    private final CategoriesRepo categoriesRepo;

    /* 하나 조회 */
    @Transactional
    public Movie getMovie(Integer id) throws IOException {
        Movie Movie = movieRepo.findById(id).get();
        Movie.setCount(Movie.getCount() + 1L);
        String imgPath = Movie.getImgPath();
        byte[] imgbyteArry = Files.readAllBytes(Paths.get(imgPath));
        Movie.setImgbyteArray(imgbyteArry);

        return Movie;
    }

    /* 영화 전체 조회 */
    public List<Movie> getMovies(Integer page) throws IOException {
        Pageable paging = PageRequest.of(page, 6);
        List<Movie> result = movieRepo.findAll(paging);
        imgsTobyteArray(result);

        return result;

    }

    /* 전체 개수 획득 */
    public int getTotal() {
        return movieRepo.findAll().size();
    }

    /* 태그관련 영화 전체 조회 */
    @Transactional
    public List<Movie> getMovieByCategories(String tag, Integer page) throws IOException {
        Integer tagId = tagRepo.findIdByTag(tag);
        List<Movie> movies = Collections.emptyList();
        if (tagId != null) {
            Tag tagEntity = tagRepo.findById(tagId).get();
            tagEntity.setCount(tagEntity.getCount() + 1L);
            Pageable paging = PageRequest.of(page, 2);
            List<Integer> movieIds = categoriesRepo.findMovieIdByTag(tagId);

            movies = movieRepo.findAllWithPaging(movieIds, paging);

            imgsTobyteArray(movies);
        }
        return movies;
    }

    /* 태그관련 움짤 개수 반환 */
    public Integer getTagTotal(String tag) {
        Integer tagId = tagRepo.findIdByTag(tag);
        List<Integer> movieIds = categoriesRepo.findMovieIdByTag(tagId);
        return movieIds.size();
    }

    /* 이미지 -> byte[] 로 만들기 */
    public void imgsTobyteArray(List<Movie> movies) throws IOException {

        Iterator<Movie> iter = movies.iterator();
        while (iter.hasNext()) {
            Movie Movie = iter.next();
            String imgPath = Movie.getImgPath();
            byte[] imgbyteArry = Files.readAllBytes(Paths.get(imgPath));
            Movie.setImgbyteArray(imgbyteArry);
        }

    }

    /* 조회수 TOP2 움짤 반환 */
    public List<Movie> getTrending() throws IOException {
        List<Movie> result = movieRepo.findTop2ByOrderByCountDesc();
        imgsTobyteArray(result);
        setFrom(result);
        return result;
    }

    public void setFrom(List<Movie> movies){
        for(Movie movie: movies){
            movie.setFrom("japan");
        }

    }




}