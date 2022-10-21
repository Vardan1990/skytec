package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clan {

    private long id;

    private String name;

    private long gold;

    private long previousGoldCount;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated = LocalDateTime.now();

}