package hu.unideb.inf.rft.neuban.persistence.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.BoardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;

@RunWith(SpringRunner.class)
@JPARepositoryTest
@Sql(scripts = "classpath:sql/data-insert-user.sql")
public class BoardRepositoryIT {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ColumnRepository columnRepository;

	@Test
	public void findParentColumnShouldReturnNullWhenCardDoesNotExist() {
		// When
		BoardEntity findParentBoard = this.boardRepository.findParentBoardbyColumnId(-1L);
		// Then
		assertThat(findParentBoard, nullValue());
	}

	@Test
	public void findParentColumnShouldReturnNullWhenParamIsNull() {
		// When
		BoardEntity findParentBoard = this.boardRepository.findParentBoardbyColumnId(null);
		// Then
		assertThat(findParentBoard, nullValue());
	}

	@Test
    public void findParentColumnShouldReturnColumnWhenCardDoesExistsWithColumn() {
		
		ColumnEntity expectedColumn = columnRepository.findOne(3L); 
		BoardEntity expectedParentBoard= boardRepository.findOne(1L);
		
        // When
		BoardEntity findParentBoard = this.boardRepository.findParentBoardbyColumnId(3L);
        // Then
		assertThat(findParentBoard, notNullValue());
		assertThat(findParentBoard, is(expectedParentBoard));
		
		assertThat(findParentBoard.getColumns(), notNullValue());
		assertThat(findParentBoard.getColumns().contains(expectedColumn), is(true));
    }

}
