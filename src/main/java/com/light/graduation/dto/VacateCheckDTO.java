
package com.light.graduation.dto;

import lombok.Data;

import java.util.Date;

@Data
@SuppressWarnings("unused")
public class VacateCheckDTO {

    private String studentNumber;
    private String vacateDecision;
    private Date vacateTime;

}
