package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.CatDetailRequestDto;
import streetcatshelter.discatch.dto.responseDto.CalendarResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatDetailService {

    private final CatDetailRepository catDetailRepository;
    private final CatRepository catRepository;
    private final CommentRepository commentRepository;
    private final CatImageRepository catImageRepository;
    private final LikedRepository likedRepository;
    private final CatCalenderRepository catCalenderRepository;

    public List<CalendarResponseDto> getCalender(Long catId, int year, int month) {
        LocalDate startDate = LocalDate.of(year,month,1);
        LocalDate endDate = LocalDate.of(year,month,startDate.lengthOfMonth());
        List<CatCalender> catCalenders = catCalenderRepository.findALLByLocalDateBetweenAndCatId(startDate, endDate, catId);

        int lengthOfMonth = startDate.lengthOfMonth();

        List<CalendarResponseDto> calendarResponseDtos = new ArrayList<>();
        for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
            CalendarResponseDto calendarResponseDto = new CalendarResponseDto();
            calendarResponseDto.setLocalDate(date);
            for (CatCalender catCalender : catCalenders) {
                if(catCalender.getLocalDate().equals(date)){
                    calendarResponseDto.update(catCalender);
                }
            }
            calendarResponseDtos.add(calendarResponseDto);
        }

        return calendarResponseDtos;
    }


    @Transactional
    public void createCatDetail(CatDetailRequestDto requestDto, Long catId, UserPrincipal userPrincipal) {

        Cat cat = getCat(catId);
        addCallender(requestDto,cat);
        User user = userPrincipal.getUser();
        CatDetail catDetail = new CatDetail(requestDto,cat,user);


        catDetailRepository.save(catDetail);

    }

    private Cat getCat(Long catId) {
        return catRepository.findById(catId).orElseThrow(()-> new IllegalArgumentException("catId가 존재하지 않습니다."));
    }

    @Transactional
    public Map<String,Long> addlike(Long catDetailId,User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        Liked byCatDetailIdAndUser_userSeq = likedRepository.findByCatDetailIdAndUser_UserSeq(catDetailId, user.getUserSeq());

        if(byCatDetailIdAndUser_userSeq == null ){
            catDetail.getLikeds().add(new Liked(catDetail,user));
            Map<String,Long> map = new HashMap<>();
            map.put("UpdatedLikeCnt",catDetail.updateLikeCnt(1L));
            return map;
        }else{
            likedRepository.delete(byCatDetailIdAndUser_userSeq);
            Map<String,Long> map = new HashMap<>();
            map.put("UpdatedLikeCnt",catDetail.updateLikeCnt(-1L));
            return map;
        }
    }

    public void deleteCatDetail(Long catDetailId, User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("NO SUCH DATA")
        );
        if(catDetail.getUser().equals(user)){
            catDetailRepository.delete(catDetail);
        }else {
            throw new IllegalArgumentException("권한이 없습니다.!");
        }

    }

    @Transactional
    public void addCallender(CatDetailRequestDto requestDto, Cat cat) {

        LocalDate localDate = LocalDate.now();

        CatCalender catCalender = catCalenderRepository.findByLocalDate(localDate);
        if(catCalender == null){
            catCalender = new CatCalender(localDate,requestDto.isFood(),requestDto.isSnack(),requestDto.isWater(),cat);
            catCalenderRepository.save(catCalender);
        }
        catCalender.update(requestDto);
    }
}
