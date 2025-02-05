package org.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    int id;
    int userId;
    String userCurrency;
    AddressEntity address;
    String email;
    float totalCost;
    int createAt;
}
