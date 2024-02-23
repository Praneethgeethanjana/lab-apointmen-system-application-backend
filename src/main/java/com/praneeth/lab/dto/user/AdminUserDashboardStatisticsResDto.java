package com.praneeth.lab.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class AdminUserDashboardStatisticsResDto {
    private Long activeClients;
    private BigDecimal totalWithdrawal;
    private BigDecimal upcomingWithdrawal;
    private BigDecimal totalInvestment;
}
