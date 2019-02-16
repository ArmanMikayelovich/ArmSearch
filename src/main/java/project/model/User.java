/**
 * User is the creator
 * of announcement
 */
package project.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority; //

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Index;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User  {
//    public User(String username, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, String fullName, String email, String phoneNumber, String password1, List<Item> itemList, String roleName) {
//        super(username, password, authorities);
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.fullName = fullName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.password = password1;
//        this.itemList = itemList;
//        this.roleName = roleName;
//    }
//
//    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, String fullName, String email, String phoneNumber, String password1, List<Item> itemList, String roleName) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.fullName = fullName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.password = password1;
//        this.itemList = itemList;
//        this.roleName = roleName;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;



    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList;

    @Column(nullable = false,length = 32)
    private String roleName = "USER";

}
