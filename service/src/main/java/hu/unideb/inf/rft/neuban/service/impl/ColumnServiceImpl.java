package hu.unideb.inf.rft.neuban.service.impl;

import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.repositories.ColumnRepository;
import hu.unideb.inf.rft.neuban.service.domain.BoardDto;
import hu.unideb.inf.rft.neuban.service.domain.ColumnDto;
import hu.unideb.inf.rft.neuban.service.exceptions.ColumnAlreadyExistsException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.BoardNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.ColumnNotFoundException;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.BoardService;
import hu.unideb.inf.rft.neuban.service.interfaces.ColumnService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataGetServiceBeanNameProvider.SINGLE_COLUMN_DATA_GET_SERVICE;
import static hu.unideb.inf.rft.neuban.service.provider.beanname.SingleDataUpdateServiceBeanNameProvider.SINGLE_COLUMN_DATA_UPDATE_SERVICE;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private BoardService boardService;
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier(SINGLE_COLUMN_DATA_GET_SERVICE)
    private SingleDataGetService<ColumnDto, Long> singleColumnDataGetService;

    @Autowired
    @Qualifier(SINGLE_COLUMN_DATA_UPDATE_SERVICE)
    private SingleDataUpdateService<ColumnDto> singleColumnDataUpdateService;

    @Transactional(readOnly = true)
    @Override
    public Optional<ColumnDto> get(final Long columnId) {
        return this.singleColumnDataGetService.get(columnId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ColumnDto> getAllByBoardId(final Long boardId) {
        final Optional<BoardDto> boardDtoOptional = this.boardService.get(boardId);

        if (boardDtoOptional.isPresent()) {
            return boardDtoOptional.get().getColumns();
        }
        return Lists.newArrayList();
    }

    @Transactional
    @Override
    public void save(final Long boardId, final ColumnDto columnDto) throws DataNotFoundException, ColumnAlreadyExistsException {
        Assert.notNull(columnDto);
        final BoardDto boardDto = this.boardService.get(boardId).orElseThrow(() -> new BoardNotFoundException(String.valueOf(boardId)));

        if (columnDto.getId() == null || boardDto.getColumns().stream().noneMatch(c -> c.getId().equals(columnDto.getId()))) {
            boardDto.getColumns().add(columnDto);
        } else {
            throw new ColumnAlreadyExistsException(String.valueOf(columnDto.getId()));
        }
        this.boardService.update(boardDto);
    }

    @Transactional
    @Override
    public void update(final ColumnDto columnDto) throws DataNotFoundException {
        this.singleColumnDataUpdateService.update(columnDto);
    }

    @Transactional
    @Override
    public void remove(final Long columnId) throws ColumnNotFoundException {
        Assert.notNull(columnId);

        Optional.ofNullable(this.columnRepository.findOne(columnId))
                .orElseThrow(() -> new ColumnNotFoundException(String.valueOf(columnId)));

        this.columnRepository.delete(columnId);
    }
}
