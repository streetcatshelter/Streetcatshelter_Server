package streetcatshelter.discatch.service;

import streetcatshelter.discatch.domain.user.domain.User;

public class profileImageUrl {
    //유저 프로파일 이미지들을 매번 중복해서 쓸필요없이 static을 이용해서 어디서든 사용 할수 있게 만들어봄.
    public static String getProfileImageUrl(User user) {
        String profileImageUrl;
        if(user.getProfileUrl() == null) {
            profileImageUrl = user.getProfileImageUrl();
        } else {
            profileImageUrl = user.getProfileUrl();
        }
        return profileImageUrl;
    }
}
