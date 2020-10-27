package com.moviesquare.america.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import com.moviesquare.america.models.Tag;
import com.moviesquare.america.converter.MovieDTO;
import com.moviesquare.america.models.Categories;
import com.moviesquare.america.models.Movie;
import com.moviesquare.america.repository.CategoriesRepo;
import com.moviesquare.america.repository.TagRepo;
import com.moviesquare.america.repository.MovieRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class MovieServices {

    private final MovieRepo movieRepo;
    private final CategoriesRepo categoriesRepo;
    private final TagRepo tagRepo;

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

    /* 조회수 TOP2 움짤 반환 */
    public List<Movie> getTrending() throws IOException {
        List<Movie> result = movieRepo.findTop2ByOrderByCountDesc();
        imgsTobyteArray(result);
        setFrom(result);
        return result;
    }
    public void setFrom(List<Movie> movies){
        for(Movie movie: movies){
            movie.setFrom("usa");
        }

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
            Movie movie = iter.next();
            String imgPath = movie.getImgPath();
            byte[] imgbyteArry = Files.readAllBytes(Paths.get(imgPath));
            movie.setImgbyteArray(imgbyteArry);
        }

    }


 
    // 움짤 저장하기
    @Transactional
    public void saveEntity(MovieDTO dto, String imgPath) {

        Movie movieEntity = dto.toEntity();
        movieEntity.setImgPath(imgPath);
        movieRepo.save(movieEntity);

        for (String tag : dto.getTags()) {

            Optional<Tag> optionalTag = tagRepo.findTagByTag(tag); // 기존 태그? -> 태그 call 신생 태그? -> 태그 생성
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
                newTag.setTag(tag);
                tagRepo.save(newTag);
                Categories categoriesEntity = new Categories();
                categoriesEntity.setTag(newTag);
                categoriesEntity.setMovie(movieEntity);
                categoriesRepo.save(categoriesEntity);

            });

        }

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