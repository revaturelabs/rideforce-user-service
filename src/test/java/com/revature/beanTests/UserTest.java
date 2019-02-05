package com.revature.beanTests;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.beans.forms.ChangeUserModel;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.json.Active;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private User u;

	@Before
    public void setupValidatorFactory () throws EmptyPasswordException {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
        u = new User();
		u.setId(100);
		u.setFirstName("first");
		u.setLastName("last");
		u.setEmail("j@gmail.com");
		u.setPassword("password");
		u.setPhotoUrl("test.jpg");
		u.setAddress("5125 Ven Ln.");
		u.setBatchEnd(Date.valueOf("2018-11-01"));
		u.setOffice(new Office());
		u.setCars(new HashSet<Car>());
		u.setContactInfo(new HashSet<ContactInfo>());
		u.setStartTime((float) 9.0);
		u.setActive(Active.ACTIVE);
		
		Assertions.assertThat(u.getFirstName()).isEqualTo("first");
		Assertions.assertThat(u.getLastName()).isEqualTo("last");
		Assertions.assertThat(u.getPassword()).isEqualTo("password");
    }
	
	@Test
	public void validUserTest() {
		
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(0);
	}
	
	@Test
	public void invalidUserIdTest() {
		u.setId(0);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserFirstNameTest() {
		u.setFirstName("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserLastNameTest() {
		u.setLastName("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	@Test
	public void emptyUserEmailTest() {
		u.setEmail("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserRoleTest() {
		u.setRole(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserOfficeTest() {
		u.setOffice(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserAddressTest() {
		u.setAddress("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	

	
	@Test
	public void nullUserCarsTest() {
		u.setCars(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserContactInfoTest() {
		u.setContactInfo(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void isEnabledTest() {
		Assertions.assertThat(this.u.isEnabled()).isFalse(); //method should just return false
	}
	
	@Test
	public void equalsWithSameObjectTest() {
		Assertions.assertThat(this.u.equals(this.u)).isTrue();
	}
	@Test
	public void equalsWithNullOtherObjectTest() {
		Assertions.assertThat(this.u.equals(null)).isFalse();
	}
	@Test
	public void equalsWithOtherClassIsFalseTest() {
		Assertions.assertThat(this.u.equals(new Object())).isFalse();
	}
	@Test
	public void equalsWithNullValuesOnOneSetValuesOnOtherTest() {
		User user = new User();
		Assertions.assertThat(user.equals(this.u)).isFalse();
	}
	
	@Test
	public void equalsRtnTrueForEqualUserObjectTest() {
		User user = this.u;
		Assertions.assertThat(user.equals(this.u)).isTrue();
	}
	@Test 
	public void equalsRtnFalseForPartsOfUserThatAreNullObjectTest() {
		User user = new User();
		Assertions.assertThat(user.equals(this.u)).isFalse();
	}
	@Test
	public void equalsRtnFalseForUnequalUserObjectTest() {
		Assertions.assertThat(this.u.equals(new User())).isFalse();
	}
	@Test
	public void equalsRtnFalseForComparisonToNullUserTest() {
		Assertions.assertThat(this.u.equals(null)).isFalse();
	}
	@Test
	public void equalsRtnFalseForDifferentClassesTest() {
		Object o = new Object();
		Assertions.assertThat(this.u.equals(o)).isFalse();
	}
	@Test
	public void hashCodeIsSameForTwoSameUsersTest() {
		Assertions.assertThat(this.u.hashCode() == this.u.hashCode()).isTrue();
	}
	@Test
	public void hashCodeIsSameForCopiedUserAndOriginalUserTest() {
		User user = this.u;
		Assertions.assertThat(this.u.hashCode() == user.hashCode()).isTrue();
	}
	@Test
	public void hashCodeDifferentForUserObjectsWithDifferentValuesTest() {
		Assertions.assertThat(this.u.hashCode() == new User().hashCode()).isFalse();
	}
	@Test
	public void toUriTest() throws URISyntaxException {
		Assertions.assertThat(this.u.toUri()).isEqualTo(new URI("/users/"+this.u.getId()));
	}
	@Test
	public void isCredentialsNonExpiredShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isCredentialsNonExpired()).isTrue();
	}
	@Test 
	public void isAccountNonLockedShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isAccountNonLocked()).isTrue();
	}
	@Test 
	public void isAccountNonExpiredShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isAccountNonExpired()).isTrue();
	}
	@Test 
	public void isNotATrainerShouldReturnFalseTest() {
		this.u.setRole(new UserRole(1, "ADMIN"));
		Assertions.assertThat(this.u.isTrainer()).isFalse();
	}
	@Test
	public void isATrainerShouldReturnTrueTest() {
		this.u.setRole(new UserRole(2, "TRAINER"));
		Assertions.assertThat(this.u.isTrainer()).isTrue();
	}
	
	@Test
	public void settersAndGettersMissed() {
		
	}
	
	@Test 
	public void equalsTest1() {
		User u2 = new User();
		u.setActive(null);
		u2.setActive(Active.ACTIVE);
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest2() {
		User u2 = new User();
		u.setActive(Active.ACTIVE);
		u2.setActive(Active.INACTIVE);
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest3() {
		User u2 = new User();
		u.setBatchEnd(null);
		u2.setBatchEnd(Date.valueOf("2018-1-1"));
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest4() {
		User u2 = new User();
		u.setBatchEnd(Date.valueOf("2018-6-2"));
		u2.setBatchEnd(Date.valueOf("2018-5-2"));
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest5() {
		User u2 = new User();
		u2.setContactInfo(new HashSet<ContactInfo>());
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest6() {
		User u2 = new User();
		u.setContactInfo(null);
		u2.setContactInfo(new HashSet<ContactInfo>());
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest7() {
		User u2 = new User();
		u2.setEmail("test@email.com");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest8() {
		User u2 = new User();
		u.setFirstName(null);
		u2.setFirstName("First");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest9() {
		User u2 = new User();
		u.setFirstName("FirstName");
		u2.setFirstName("First");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest10() {
		User u2 = new User();
		u.setLastName(null);
		u2.setLastName("Last");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest11() {
		User u2 = new User();
		u.setLastName("LastName");
		u2.setLastName("Last");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test
	public void equalsTest12() {
		User u2 = new User();
		u.setFirstName("First");
		u2.setFirstName("First");
		Assertions.assertThat(!u.equals(u2)).isTrue();
	}
	
	@Test 
	public void changeUserModelGetId() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		Assertions.assertThat(changeUserModel.getId() == 0).isTrue();
	}
	
	@Test 
	public void changeUserModelSetId() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setId(2);
		Assertions.assertThat(changeUserModel.getId() == 2).isTrue();
	}
	
	@Test 
	public void changeUserModelSetFirstName() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setFirstName("First");
		Assertions.assertThat(changeUserModel.getFirstName().equals("First")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetLastName() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setLastName("Last");
		Assertions.assertThat(changeUserModel.getLastName().equals("Last")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetEmail() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setEmail("email@gmail.com");
		Assertions.assertThat(changeUserModel.getEmail().equals("email@gmail.com")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetPhotoUrl() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setPhotoUrl("google.com");
		Assertions.assertThat(changeUserModel.getPhotoUrl().equals("google.com")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetAddress() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setAddress("123 Main St");
		Assertions.assertThat(changeUserModel.getAddress().equals("123 Main St")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetOffice() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		Office office = new Office();
		changeUserModel.setOffice(office);
		Assertions.assertThat(changeUserModel.getOffice().equals(office)).isTrue();
	}
	
	@Test 
	public void changeUserModelSetBatchEnd() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		Date date = new Date(100);
		changeUserModel.setBatchEnd(date);
		Assertions.assertThat(changeUserModel.getBatchEnd().equals(date)).isTrue();
	}
	
	@Test 
	public void changeUserModelSetRole() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setRole("ADMIN");
		Assertions.assertThat(changeUserModel.getRole().equals("ADMIN")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetActive() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setActive(Active.ACTIVE);
		Assertions.assertThat(changeUserModel.getActive().equals(Active.ACTIVE)).isTrue();
	}
	
	@Test 
	public void changeUserModelSetPassword() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setPassword("password");
		Assertions.assertThat(changeUserModel.getPassword().equals("password")).isTrue();
	}
	
	@Test 
	public void changeUserModelSetStartTime() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		changeUserModel.setStartTime(900);
		Assertions.assertThat(changeUserModel.getStartTime() == 900).isTrue();
	}
	
	@Test
	public void changeUserModelToStringTest() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		String toString = "ChangeUserModel [id=, firstName=, lastName=, email=, photoUrl=, address=, office=, batchEnd=, role=]";
		Assertions.assertThat(!changeUserModel.toString().equals(toString)).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest1() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setFirstName("OriginalFirst");
		changeUserModel.setFirstName("NewFirstName");
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getFirstName().equals("NewFirstName")).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest2() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setLastName("OriginalLast");
		changeUserModel.setLastName("NewLastName");
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getLastName().equals("NewLastName")).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest3() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setEmail("OriginalEmail");
		changeUserModel.setEmail("NewEmail");
		changeUserModel.changeUser(u);
		Assertions.assertThat(changeUserModel.getEmail().equals("NewEmail")).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest4() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setAddress("OriginalAddress");
		changeUserModel.setAddress("NewAddress");
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getAddress().equals("NewAddress")).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest5() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		Office office = new Office();
		u.setOffice(office);
		changeUserModel.setOffice(office);
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getOffice().equals(office)).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest6() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		Date date = new Date(100);
		u.setBatchEnd(date);
		changeUserModel.setBatchEnd(date);
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getBatchEnd().equals(date)).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest7() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setActive(Active.ACTIVE);
		changeUserModel.setActive(Active.INACTIVE);
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.isActive().equals(Active.INACTIVE)).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest8() throws EmptyPasswordException {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setPassword("OriginalPassword");
		changeUserModel.setPassword("NewPassword");
		changeUserModel.changeUser(u);
		Assertions.assertThat(!u.getPassword().equals("NewPassword")).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest9() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setStartTime(-1);
		changeUserModel.setStartTime(-2);
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getStartTime() == -2).isTrue();
	}
	
	@Test
	public void changeUserModelChangeUserTest10() {
		ChangeUserModel changeUserModel = new ChangeUserModel();
		u.setPhotoUrl("Original.com");
		changeUserModel.setPhotoUrl("New.com");
		changeUserModel.changeUser(u);
		Assertions.assertThat(u.getPhotoUrl().equals("New.com")).isTrue();
	}
}
