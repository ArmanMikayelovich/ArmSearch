package project.dto;

public class UserDto {
    private int id;
    private String fullName;

    //TODO ANI  UserDto um petqa lini nayev heraxosahamar@  yev email@

    public UserDto(int id, String firstName, String lastName) {
        this.id = id;
        this.fullName = firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
