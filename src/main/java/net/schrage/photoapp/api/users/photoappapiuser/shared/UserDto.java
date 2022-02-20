package net.schrage.photoapp.api.users.photoappapiuser.shared;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Data Transfer Object, um das User-Objekt, welches vom Client (UI) geschickt wird, in eines zu transferieren
 * welches in die Datenbank gespeichert werden kann
 */
public class UserDto implements Serializable {
  private static long serialVersionUID = -1481506859374796547L;

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String userId;  //Der Client hat aus Sicherheitsgründen nur Zugriff auf die externe Id und nicht
  //die von der Datenbank generierten
  private String encryptedPassword;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }
}
