package cz.jurina.tennisclub.tennisclub.controller;

import cz.jurina.tennisclub.tennisclub.dto.CourtDto;
import cz.jurina.tennisclub.tennisclub.dto.CreateCourtDto;
import cz.jurina.tennisclub.tennisclub.service.CourtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtControllerTest {

    @InjectMocks
    private CourtController courtController;

    @Mock
    private CourtService courtService;

    @Test
    void testCreateCourt() {
        CreateCourtDto dto = new CreateCourtDto("Clay");

        ResponseEntity<Void> response = courtController.createCourt(dto);

        verify(courtService, times(1)).createCourt(dto);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetAllCourts() {
        CourtDto dto = new CourtDto();
        dto.setId(1L);
        dto.setSurfaceType("Clay");

        when(courtService.getAllCourts()).thenReturn(List.of(dto));

        ResponseEntity<List<CourtDto>> response = courtController.getAllCourts();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Clay", response.getBody().get(0).getSurfaceType());
    }

    @Test
    void testGetCourtById() {
        CourtDto dto = new CourtDto();
        dto.setId(1L);
        dto.setSurfaceType("Grass");

        when(courtService.getCourtById(1L)).thenReturn(dto);

        ResponseEntity<CourtDto> response = courtController.getCourtById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Grass", response.getBody().getSurfaceType());
    }

    @Test
    void testDeleteCourt() {
        ResponseEntity<Void> response = courtController.deleteCourt(1L);

        verify(courtService, times(1)).deleteCourt(1L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateCourtSurface() {
        CreateCourtDto dto = new CreateCourtDto("Hard");

        ResponseEntity<Void> response = courtController.updateCourtSurface(1L, dto);

        verify(courtService, times(1)).updateCourt(1L, dto);
        assertEquals(200, response.getStatusCode().value());
    }
}
