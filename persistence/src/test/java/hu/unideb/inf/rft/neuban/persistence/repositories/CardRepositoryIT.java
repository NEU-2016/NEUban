package hu.unideb.inf.rft.neuban.persistence.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;

@RunWith(SpringRunner.class)
@JPARepositoryTest
public class CardRepositoryIT {

	@Autowired
	private CardRepository cardRepository;

	@Test
	public void findByTitleContainingShouldReturnListOfCardIfCardContainsSearchString() {
		// Given

		final List<Long> expectedIds = Arrays.asList(9L, 10L);

		// When
		final List<CardEntity> actualCardList = this.cardRepository.findByTitleContaining("test");

		// Then
		assertThat(actualCardList, notNullValue());
		assertThat(actualCardList.isEmpty(), is(false));
		assertThat(actualCardList.size(), is(2));
		final List<Long> actualIds = Arrays.asList(actualCardList.get(0).getId(), actualCardList.get(1).getId());
		assertThat(actualIds, is(expectedIds));
	}

	@Test
	public void findByTitleContainingShouldReturnEmptyListIfNoCardContainsSearchStringInTitle() {
		// When
		final List<CardEntity> actualCardList = this.cardRepository
				.findByTitleContaining("None card have title like this");

		// Then
		assertThat(actualCardList, notNullValue());
		assertThat(actualCardList.isEmpty(), is(true));
	}

}
