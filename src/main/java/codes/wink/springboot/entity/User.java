package codes.wink.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    public Long id;    
    public String firstName;
    public String lastName;
    public String email;
    void set(String string, Long newIndex) {
    }
}