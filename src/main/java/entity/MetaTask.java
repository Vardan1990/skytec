package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaTask {

    private long id;

    private String status;

    private String type;

    private long clanId;

    private long gold;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated = LocalDateTime.now();
}
