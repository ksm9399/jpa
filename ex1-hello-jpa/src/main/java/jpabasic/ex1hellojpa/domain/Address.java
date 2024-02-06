package jpabasic.ex1hellojpa.domain;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Address address = (Address) obj;
    return Objects.equals(getCity(), address.getCity()) &&
            Objects.equals(getStreet(), address.getStreet()) &&
            Objects.equals(getZipcode(), address.getZipcode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCity(), getStreet(), getZipcode());
  }
}
