package hu.unideb.inf.rft.neuban.persistence.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;

@RunWith(SpringRunner.class)
@JPARepositoryTest
@Sql(scripts = "classpath:sql/data-insert-user.sql")
public class ColumnRepositoryIT {
	
	@Autowired
	private ColumnRepository columnRepository;
	
	@Autowired
	private CardRepository cardRepository;

	@Test
    public void findParentColumnShouldReturnNullWhenCardDoesNotExist() {
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumn(-1L);
        // Then
		assertThat(findParentColumn, nullValue());
    }
	
	@Test
    public void findParentColumnShouldReturnNullWhenParamIsNull() {
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumn(null);
        // Then
		assertThat(findParentColumn, nullValue());
    }
	
	@Test
    public void findParentColumnShouldReturnColumnWhenCardDoesExistsWithColumn() {
		
		CardEntity expectedCard = cardRepository.findOne(5L); 
		ColumnEntity expectedParentColumn = columnRepository.findOne(5L);
		
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumn(5L);
        // Then
		assertThat(findParentColumn, notNullValue());
		assertThat(findParentColumn, is(expectedParentColumn));
		
		assertThat(findParentColumn.getCards().get(0), notNullValue());
		assertThat(findParentColumn.getCards().get(0), is(expectedCard));
    }
	
}
