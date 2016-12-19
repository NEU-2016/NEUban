package hu.unideb.inf.rft.neuban.persistence.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import hu.unideb.inf.rft.neuban.persistence.annotations.JPARepositoryTest;
import hu.unideb.inf.rft.neuban.persistence.entities.CardEntity;
import hu.unideb.inf.rft.neuban.persistence.entities.ColumnEntity;

@RunWith(SpringRunner.class)
@JPARepositoryTest
public class ColumnRepositoryIT {
	
	@Autowired
	private ColumnRepository columnRepository;
	
	@Autowired
	private CardRepository cardRepository;

	@Test
    public void findParentColumnByCardIdShouldReturnNullWhenCardDoesNotExist() {
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumnByCardId(-1L);
        // Then
		assertThat(findParentColumn, nullValue());
    }
	
	@Test
    public void findParentColumnByCardIdShouldReturnNullWhenParamIsNull() {
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumnByCardId(null);
        // Then
		assertThat(findParentColumn, nullValue());
    }
	
	@Test
    public void findParentColumnByCardIdShouldReturnColumnWhenCardDoesExistsWithColumn() {
		
		CardEntity expectedCard = cardRepository.findOne(5L); 
		ColumnEntity expectedParentColumn = columnRepository.findOne(5L);
		
        // When
		ColumnEntity findParentColumn = this.columnRepository.findParentColumnByCardId(5L);
        // Then
		assertThat(findParentColumn, notNullValue());
		assertThat(findParentColumn, is(expectedParentColumn));
		
		assertThat(findParentColumn.getCards(), notNullValue());
		assertThat(findParentColumn.getCards().contains(expectedCard), is(true));
    }
	
}
