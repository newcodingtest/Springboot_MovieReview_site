package org.yoon.moviereview.service;

import org.yoon.moviereview.dto.MovieDTO;
import org.yoon.moviereview.dto.MovieImageDTO;
import org.yoon.moviereview.dto.PageRequestDTO;
import org.yoon.moviereview.dto.PageResultDTO;
import org.yoon.moviereview.entity.Movie;
import org.yoon.moviereview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    default Map<String,Object> dtoToEntity(MovieDTO movieDTO){

        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        //MovieImageDTO 처리
        if (imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
               MovieImage movieImage = MovieImage.builder()
                       .path(movieImageDTO.getPath())
                       .imgName(movieImageDTO.getImgName())
                       .uuid(movieImageDTO.getUuid())
                       .movie(movie)
                       .build();
               return movieImage;

            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }
        return entityMap;
    }
    
    //목록 처리
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage>movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
           return MovieImageDTO.builder().imgName(movieImage.getImgName())
                   .path(movieImage.getPath())
                   .uuid(movieImage.getUuid())
                   .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }
}
