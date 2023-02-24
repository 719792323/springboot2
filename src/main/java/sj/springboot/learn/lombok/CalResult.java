package sj.springboot.learn.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalResult {
    private Integer first;
    private Integer second;
    private Integer result;
}
