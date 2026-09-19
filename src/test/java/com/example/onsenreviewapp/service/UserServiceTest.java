package com.example.onsenreviewapp.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	//一般ユーザーを利用停止できる
	@Test
	void deactivateActiveRegularUser() {
		
		User user = new User();
		
		user.setRole("ROLE_USER");
		user.setIsActive(true);
		
		// RepositoryがそのUserを返すように設定する
		Mockito.when(userRepository.findById(1L)).
		thenReturn(Optional.of(user));
		
		boolean deactivated = userService.deactivateUser(1L);
		
		//戻り値・変更後の状態・保存の呼び出しを検証
		Assertions.assertTrue(deactivated);
		Assertions.assertFalse(user.getIsActive());
		Mockito.verify(userRepository).save(user);
	}
	
	//管理者は利用停止にできない
	@Test
	void doesNotDeactivateAdminUser() {
		
		User user = new User();
		user.setRole("ROLE_ADMIN");
		user.setIsActive(true);
		
		Mockito.when(userRepository.findById(1L)).
		thenReturn(Optional.of(user));
		
		boolean deactivated = userService.deactivateUser(1L);
		
		Assertions.assertFalse(deactivated);
		Assertions.assertTrue(user.getIsActive());
		Mockito.verify(userRepository, Mockito.never()).save(user);
	}
	
	//存在しないユーザーを利用停止できない
	@Test
	void doesNotDeactivateNonexistentUser () {
		
		Mockito.when(userRepository.findById(1L)).
		thenReturn(Optional.empty());
		
		boolean deactivated = userService.deactivateUser(1L);
		
		Assertions.assertFalse(deactivated);
		Mockito.verify(userRepository, Mockito.never())
		.save(Mockito.any(User.class));
	}
	
	//利用停止中の一般ユーザーを有効化できる
	@Test
	void activatesInactiveRegularUser() {
		
		
		User user = new User();
		user.setRole("ROLE_USER");
		user.setIsActive(false);
		
		Mockito.when(userRepository.findById(1L)).
		thenReturn(Optional.of(user));
		
		boolean activated = userService.activateUser(1L);
		
		Assertions.assertTrue(activated);
		Assertions.assertTrue(user.getIsActive());
		Mockito.verify(userRepository).save(user);
	}
}
