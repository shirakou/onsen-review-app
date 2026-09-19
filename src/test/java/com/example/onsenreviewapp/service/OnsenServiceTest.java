package com.example.onsenreviewapp.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.repository.FavoriteRepository;
import com.example.onsenreviewapp.repository.OnsenRepository;
import com.example.onsenreviewapp.repository.ReviewRepository;

//JUnitでMockitoの@Mockと@InjectMocksを使用できるようにする
@ExtendWith(MockitoExtension.class)
public class OnsenServiceTest {
	
	@Mock
	private OnsenRepository onsenRepository;
	
	@Mock
	private FavoriteRepository favoriteRepository;
	
	@Mock
	private ReviewRepository reviewRepository;
	
	@InjectMocks
	private OnsenService onsenService;
	
    // 指定したIDの温泉が存在しない場合、
    // 削除に失敗してfalseが返り、削除や関連データの確認が行われないことを確認する
	@Test
	void doesNotDeleteNonexistentOnsen() {
		
		Mockito.when(onsenRepository.findById(1L)).
		thenReturn(Optional.empty());
		
		boolean deleted = onsenService.deleteOnsen(1L);
		
		Assertions.assertFalse(deleted);
		
		// どのOnsenに対してもdelete()が呼ばれていないことを確認する
		Mockito.verify(onsenRepository, Mockito.never())
				.delete(Mockito.any(Onsen.class));
		
		// お気に入りの存在確認まで処理が進んでいないことを確認する
		Mockito.verify(favoriteRepository, Mockito.never())
				.existsByOnsen_Id(1L);
		
		// レビューの存在確認まで処理が進んでいないことを確認する
		Mockito.verify(reviewRepository,  Mockito.never())
				.existsByOnsen_Id(1L);
	}
	
	
	// お気に入りが存在する場合、温泉を削除せずfalseを返すことを確認する
	@Test
	void doesNotDeleteOnsenWhenFavoriteExists() {
		
		Onsen onsen = new Onsen();
		
		Mockito.when(onsenRepository.findById(1L))
				.thenReturn(Optional.of(onsen));
		
		Mockito.when(favoriteRepository.existsByOnsen_Id(1L))
				.thenReturn(true);
		
		Mockito.when(reviewRepository.existsByOnsen_Id(1L))
				.thenReturn(false);
		
		boolean deleted = onsenService.deleteOnsen(1L);
		
		Assertions.assertFalse(deleted);
		
		Mockito.verify(onsenRepository, Mockito.never())
				.delete(Mockito.any(Onsen.class));
		
		Mockito.verify(favoriteRepository).existsByOnsen_Id(1L);
		
		Mockito.verify(reviewRepository).existsByOnsen_Id(1L);
	}
	
	
	// レビューが存在する場合、温泉を削除せずfalseを返すことを確認する
	@Test
	void doesNotDeleteOnsenWhenReviewExists() {
		
		Onsen onsen = new Onsen();
		
		Mockito.when(onsenRepository.findById(1L))
				.thenReturn(Optional.of(onsen));
		
		Mockito.when(favoriteRepository.existsByOnsen_Id(1L))
				.thenReturn(false);
		
		Mockito.when(reviewRepository.existsByOnsen_Id(1L))
				.thenReturn(true);
		
		boolean deleted = onsenService.deleteOnsen(1L);
		
		Assertions.assertFalse(deleted);
		
		Mockito.verify(favoriteRepository).existsByOnsen_Id(1L);
		
		Mockito.verify(reviewRepository).existsByOnsen_Id(1L);
		
		Mockito.verify(onsenRepository, Mockito.never())
				.delete(Mockito.any(Onsen.class));
	}
	
	
	// お気に入りとレビューが存在しない場合、温泉を削除してtrueを返すことを確認する
	@Test
	void deletesOnsenWhenNoRelatedDataExists() {
		
		Onsen onsen = new Onsen();
		
		Mockito.when(onsenRepository.findById(1L))
				.thenReturn(Optional.of(onsen));
		
		Mockito.when(favoriteRepository.existsByOnsen_Id(1L))
				.thenReturn(false);
		
		Mockito.when(reviewRepository.existsByOnsen_Id(1L))
				.thenReturn(false);
		
		boolean deleted = onsenService.deleteOnsen(1L);
		
		Assertions.assertTrue(deleted);
		
		Mockito.verify(favoriteRepository).existsByOnsen_Id(1L);
		
		Mockito.verify(reviewRepository).existsByOnsen_Id(1L);
		
		Mockito.verify(onsenRepository).delete(onsen);
	}
}
