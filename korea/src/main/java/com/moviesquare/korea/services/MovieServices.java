package com.moviesquare.korea.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.moviesquare.korea.converter.MovieDTO;
import com.moviesquare.korea.models.Categories;
import com.moviesquare.korea.models.Movie;
import com.moviesquare.korea.models.Tag;
import com.moviesquare.korea.repository.CategoriesRepo;
import com.moviesquare.korea.repository.MovieRepo;
import com.moviesquare.korea.repository.TagRepo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class MovieServices {

    private final MovieRepo MovieRepo;
    private final TagRepo tagRepo;
    private final CategoriesRepo categoriesRepo;

    /* 하나 조회 */
    @Transactional
    public Movie getMovie(Integer id) throws IOException {
        Movie Movie = MovieRepo.findById(id).get();
        Movie.setCount(Movie.getCount() + 1L);
        String imgPath = Movie.getImgPath();
        byte[] imgbyteArry = Files.readAllBytes(Paths.get(imgPath));
        Movie.setImgbyteArray(imgbyteArry);

        return Movie;
    }

    /* 조회수 TOP2 움짤 반환 */
    public List<Movie> getTrending() throws IOException {
        List<Movie> result = MovieRepo.findTop2ByOrderByCountDesc();
        imgsTobyteArray(result);
        setFrom(result);
        return result;
    }
      /* 새로운 움짤 2개 반환 */
      public List<Movie> getBrandnew() throws IOException {
        List<Movie> result = MovieRepo.findTop2ByOrderByIdDesc();
        imgsTobyteArray(result);
        setFrom(result);
        return result;
    }
    public void setFrom(List<Movie> movies){
        for(Movie movie: movies){
            movie.setFrom("korea");
        }

    }

    /* 영화 전체 조회 */
    public List<Movie> getMovies(Integer page) throws IOException {
        Pageable paging = PageRequest.of(page, 6);
        List<Movie> result = MovieRepo.findAll(paging);
        imgsTobyteArray(result);

        return result;

    }

    /* 전체 개수 획득 */
    public int getTotal() {
        return MovieRepo.findAll().size();
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

            movies = MovieRepo.findAllWithPaging(movieIds, paging);

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

    // 움짤 저장하기
    @Transactional
    public void saveEntity(MovieDTO dto, String imgPath) {

        Movie movieEntity = dto.toEntity();
        movieEntity.setImgPath(imgPath);
        MovieRepo.save(movieEntity);

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



}