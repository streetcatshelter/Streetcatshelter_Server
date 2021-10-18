package streetcatshelter.discatch.dto.responseDto;

import lombok.Data;

import java.util.List;


@Data
public class Result<t> {

    private List<t> date;

}
