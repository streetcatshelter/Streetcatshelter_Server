package streetcatshelter.discatch.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserLevel {
    아깽이,
    냥린이,
    대장냥;
}
