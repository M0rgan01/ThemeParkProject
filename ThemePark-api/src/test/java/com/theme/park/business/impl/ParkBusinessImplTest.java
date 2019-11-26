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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkBusinessImplTest {


    @InjectMocks
    private ParkBusinessImpl parkBusiness;
    @Mock
    private ParkRepository parkRepository;

    private Park park;
    private List<Comment> comments;
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

        comments = new ArrayList<>();
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

    @Test
    public void testUpdateNotation2() throws NotFoundException {
        Comment comment3 = new Comment();
        comment3.setNotation(1);
        Comment comment4 = new Comment();
        comment4.setNotation(2);
        comments.add(comment3);
        comments.add(comment4);
        park.setComments(comments);

        Mockito.when(parkRepository.findById(1L)).thenReturn(Optional.of(park));

        parkBusiness.updateNotation(1L);

        ArgumentCaptor<Park> argument = ArgumentCaptor.forClass(Park.class);
        verify(parkRepository).save(argument.capture());

        assertEquals(argument.getValue().getGlobalNotation(), 2.8, 0.1);
    }


}
