package hu.unideb.inf.rft.neuban.persistence.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.CommentEntity;

@RunWith(SpringRunner.class)
@JPARepositoryTest
@Sql(scripts = "classpath:sql/data-insert-user.sql")
public class CommentRepositoryIT {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void findByCardIdOrderByCreatedTimeDescShouldReturnEmptyListWhenParamCardIdDoesNotExists() {
		// When
		final List<CommentEntity> actualCommentList = this.commentRepository.findByCardIdOrderByCreatedTimeDesc(0L);
		// Then

		assertThat(actualCommentList.isEmpty(), is(true));
	}

	@Test
	public void findByCardIdOrderByCreatedTimeDescShouldReturnCommentsInOrder() {
		// Given

		final List<Long> expectedOrder = Arrays.asList(2L, 3L, 1L);

		// When
		final List<CommentEntity> actualCommentList = this.commentRepository.findByCardIdOrderByCreatedTimeDesc(1L);

		// Then
		assertThat(actualCommentList, notNullValue());
		assertThat(actualCommentList.isEmpty(), is(false));
		assertThat(actualCommentList.size(), is(3));
		final List<Long> actualOrder = Arrays.asList(actualCommentList.get(0).getId(), actualCommentList.get(1).getId(),
				actualCommentList.get(2).getId());

		assertThat(actualOrder, is(expectedOrder));

	}
}
