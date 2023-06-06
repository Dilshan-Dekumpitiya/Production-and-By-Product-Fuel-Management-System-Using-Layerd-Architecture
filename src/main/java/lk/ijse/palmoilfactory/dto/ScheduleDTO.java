package lk.ijse.palmoilfactory.dto;

import lk.ijse.palmoilfactory.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleDTO implements SuperEntity {
    private String schId;
    private String timeRange;
}
