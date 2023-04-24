package com.codesystem.matchingsystem.service.impl.utlis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchingPlayer {
    private Integer userId;
    private Integer rating;
    private Integer waitingTime;
    private Integer botId;
}
