package dev.nhaiden.wdhbsp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "staff")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee implements Serializable {
    @Id
    @Length(max = 6)
    @NotBlank
    @Column(name = "id")
    private String id;

    @Column(name = "firstName")
    @NotBlank
    @Length(max = 255)
    @EqualsAndHashCode.Exclude
    private String firstName;

    @Column(name = "lastName")
    @NotBlank
    @Length(max=255)
    @EqualsAndHashCode.Exclude
    private String lastName;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();


}
