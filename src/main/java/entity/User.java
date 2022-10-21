package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;

    private long clanId;

    private long totalAddedGold;

    private long lastAddedGold;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated = LocalDateTime.now();
}
