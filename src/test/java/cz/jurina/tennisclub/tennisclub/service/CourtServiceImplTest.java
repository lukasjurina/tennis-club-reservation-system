package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dao.CourtDao;
import cz.jurina.tennisclub.tennisclub.dao.CourtSurfaceDao;
import cz.jurina.tennisclub.tennisclub.dto.CourtDto;
import cz.jurina.tennisclub.tennisclub.dto.CreateCourtDto;
import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtServiceImplTest {

    @Mock
    private CourtDao courtDao;

    @Mock
    private CourtSurfaceDao courtSurfaceDao;

    @InjectMocks
    private CourtServiceImpl courtService;

    @Test
    void createCourtSuccess() {
        CreateCourtDto dto = new CreateCourtDto("Clay");

        CourtSurface claySurface = new CourtSurface();
        claySurface.setId(1L);
        claySurface.setSurfaceType("Clay");
        claySurface.setPrice(10.0);

        when(courtSurfaceDao.findAll()).thenReturn(List.of(claySurface));

        courtService.createCourt(dto);

        ArgumentCaptor<Court> courtCaptor = ArgumentCaptor.forClass(Court.class);
        verify(courtDao).save(courtCaptor.capture());

        Court savedCourt = courtCaptor.getValue();
        assertEquals(claySurface, savedCourt.getCourtSurface());
    }

    @Test
    void createCourtFailure() {
        CreateCourtDto dto = new CreateCourtDto("Unknown");

        when(courtSurfaceDao.findAll()).thenReturn(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                courtService.createCourt(dto));

        assertEquals("Surface type does not exist", exception.getMessage());
    }

    @Test
    void getCourtByIdSuccess() {
        Long courtId = 1L;

        CourtSurface surface = new CourtSurface();
        surface.setSurfaceType("Grass");
        surface.setPrice(15.0);

        Court court = new Court();
        court.setId(courtId);
        court.setCourtSurface(surface);

        when(courtDao.findById(courtId)).thenReturn(court);

        CourtDto result = courtService.getCourtById(courtId);

        assertEquals("Grass", result.getSurfaceType());
        assertEquals(15.0, result.getPrice());
        assertEquals(courtId, result.getId());
    }

    @Test
    void getCourtByIdFailure() {
        Long courtId = 100L;

        when(courtDao.findById(courtId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                courtService.getCourtById(courtId));

        assertEquals("Court with id 100 does not exist", exception.getMessage());
    }

    @Test
    void getAllCourts() {
        CourtSurface surface = new CourtSurface();
        surface.setSurfaceType("Hard");
        surface.setPrice(20.0);

        Court court = new Court();
        court.setId(1L);
        court.setCourtSurface(surface);

        when(courtDao.findAll()).thenReturn(List.of(court));

        List<CourtDto> result = courtService.getAllCourts();

        assertEquals(1, result.size());
        assertEquals("Hard", result.get(0).getSurfaceType());
    }

    @Test
    void deleteCourtSuccess() {
        Long courtId = 1L;

        Court court = new Court();
        court.setId(courtId);

        when(courtDao.findById(courtId)).thenReturn(court);

        courtService.deleteCourt(courtId);

        verify(courtDao).deleteById(courtId);
    }

    @Test
    void deleteCourtFailure() {
        Long courtId = 404L;

        when(courtDao.findById(courtId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                courtService.deleteCourt(courtId));

        assertEquals("Court with id 404 does not exist", exception.getMessage());
    }

    @Test
    void updateCourtSuccess() {
        Long courtId = 1L;
        CreateCourtDto dto = new CreateCourtDto("Clay");

        CourtSurface claySurface = new CourtSurface();
        claySurface.setId(2L);
        claySurface.setSurfaceType("Clay");
        claySurface.setPrice(10.0);

        Court existingCourt = new Court();
        existingCourt.setId(courtId);
        existingCourt.setCourtSurface(claySurface);

        when(courtDao.findById(courtId)).thenReturn(existingCourt);
        when(courtSurfaceDao.findAll()).thenReturn(List.of(claySurface));

        courtService.updateCourt(courtId, dto);

        verify(courtDao).save(existingCourt);
        assertEquals("Clay", existingCourt.getCourtSurface().getSurfaceType());
    }

    @Test
    void updateCourtFailure() {
        Long courtId = 2L;
        CreateCourtDto dto = new CreateCourtDto("Unknown");

        Court court = new Court();
        court.setId(courtId);

        when(courtDao.findById(courtId)).thenReturn(court);
        when(courtSurfaceDao.findAll()).thenReturn(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                courtService.updateCourt(courtId, dto));

        assertEquals("Surface type does not exist", exception.getMessage());
    }

}