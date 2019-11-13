package com.theme.park.business.impl;

import com.theme.park.doa.ParkRepository;
import com.theme.park.entities.Comment;
import com.theme.park.entities.Park;
import com.theme.park.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkBusinessImplTest {


    @InjectMocks
    private ParkBusinessImpl parkBusiness;
    @Mock
    private ParkRepository parkRepository;

    private Park park;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        park = new Park();

        Comment comment = new Comment();
        comment.setNotation(5);
        Comment comment1 = new Comment();
        comment1.setNotation(1);
        Comment comment2 = new Comment();
        comment2.setNotation(5);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment1);
        comments.add(comment2);

        park.setComments(comments);
    }

    @Test
    public void testUpdateNotation() throws NotFoundException {

        Mockito.when(parkRepository.findById(1L)).thenReturn(Optional.of(park));

        parkBusiness.updateNotation(1L);

        ArgumentCaptor<Park> argument = ArgumentCaptor.forClass(Park.class);
        verify(parkRepository).save(argument.capture());

        assertEquals(argument.getValue().getGlobalNotation(), 3.6, 0.1);
    }

}
