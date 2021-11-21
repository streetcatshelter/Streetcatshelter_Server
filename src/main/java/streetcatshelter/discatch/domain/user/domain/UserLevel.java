package streetcatshelter.discatch.domain.user.domain;

public enum UserLevel {
    고양이신(5, null),
    프로집사(4, 고양이신),
    대장냥(3, 프로집사),
    냥린이(2, 대장냥),
    아깽이(1, 냥린이)
    ;

    private final int value;
    private final UserLevel next;

    UserLevel(int value, UserLevel next){
        this.value = value;
        this.next = next;
    }

    public int value(){
        return value;
    }

    public UserLevel nextLevel(){
        return this.next;
    }
}