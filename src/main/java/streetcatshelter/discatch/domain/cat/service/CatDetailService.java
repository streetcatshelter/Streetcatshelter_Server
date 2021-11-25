package streetcatshelter.discatch.domain.cat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.aop.UpdateUserScore;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.cat.domain.Cat;
import streetcatshelter.discatch.domain.cat.domain.CatCalender;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.cat.domain.CatTag;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailRequestDto;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailUpdateRequestDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.CalendarResponseDto;
import streetcatshelter.discatch.domain.cat.repository.CatCalenderRepository;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatImageRepository;
import streetcatshelter.discatch.domain.cat.repository.CatRepository;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.LikedRepository;

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

    @UpdateUserScore
    @Transactional
    public void createCatDetail(CatDetailRequestDto requestDto, Long catId, User user) {

        Cat cat = getCat(catId);
        addCallender(requestDto,cat, user);
        CatDetail catDetail = new CatDetail(requestDto,cat,user);
        catDetailRepository.save(catDetail);
    }

    private Cat getCat(Long catId) {
        return catRepository.findById(catId).orElseThrow(()-> new IllegalArgumentException("catId가 존재하지 않습니다."));
    }

    @Transactional
    public Map<String,Long> addlike(Long catDetailId,User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such CatDetail")
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

    @UpdateUserScore
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
    public void addCallender(CatDetailRequestDto requestDto, Cat cat, User user) {

        LocalDate localDate = LocalDate.now();

        CatCalender catCalender = catCalenderRepository.findByLocalDateAndCatAndUser(localDate, cat, user);
        if(catCalender == null){
            catCalender = new CatCalender(localDate,requestDto.isFood(),requestDto.isSnack(),requestDto.isWater(),cat, user);
            catCalenderRepository.save(catCalender);
        }
        catCalender.update(requestDto);
    }


    @Transactional
    public void updateCatDetail(CatDetailUpdateRequestDto catDetailUpdateRequestDto, User user, Long catDetailId) {

        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such CatDetail")
        );

        List<CatTag> catTags = convertTag(catDetail, catDetailUpdateRequestDto.getCatTags());
        catDetail.updateCatDetail(catDetailUpdateRequestDto,catTags,user);
    }

    public List<CatTag> convertTag(CatDetail catDetail, List<String> catTagStringList) {
        List<CatTag> catTagList = new ArrayList<>();
        for (String tag : catTagStringList) {
            catTagList.add(new CatTag(catDetail, tag));
        }
        return catTagList;
    }
}
