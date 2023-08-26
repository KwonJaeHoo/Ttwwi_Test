package ttwwi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
@AllArgsConstructor
public class AuthorizedCodeDto 
{
    private String authorizationCode;

}
