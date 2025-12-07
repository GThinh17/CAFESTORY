package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Production;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO {
    Production production;
    int timeExpired;
}
