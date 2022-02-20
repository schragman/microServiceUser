package net.schrage.photoapp.api.users.photoappapiuser.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
  @NotNull(message = "First name must not be null")
  @Size(min=2, message = "Length of first name must not be less than 2 characters")
  private String firstName;

  @NotNull(message = "Last name must not be null")
  @Size(min=2, message = "Length of last name must not be less than 2 characters")
  private String lastName;

  @NotNull(message = "Password must not be null")
  @Size(min=8, max=16, message = "Length of password must not be less than 8 characters and not more than 16 characters")
  private String password;

  @NotNull(message = "Email must not be null")
  @Email
  private String email;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
