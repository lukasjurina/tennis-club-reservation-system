package cz.jurina.tennisclub.tennisclub.controller;

import cz.jurina.tennisclub.tennisclub.dto.CreateReservationDto;
import cz.jurina.tennisclub.tennisclub.dto.ReservationDto;
import cz.jurina.tennisclub.tennisclub.service.ReservationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Test
    void testGetAllReservations() {
        ReservationDto dto = new ReservationDto();
        dto.setId(1L);
        when(reservationService.getAllReservations()).thenReturn(List.of(dto));

        ResponseEntity<List<ReservationDto>> response = reservationController.getAllReservations();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
    }

    @Test
    void testGetReservationsByCourt() {
        ReservationDto dto = new ReservationDto();
        dto.setId(2L);
        when(reservationService.getReservationsByCourtId(5L)).thenReturn(List.of(dto));

        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationsByCourt(5L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2L, response.getBody().get(0).getId());
    }

    @Test
    void testGetReservationsByPhone() {
        ReservationDto dto = new ReservationDto();
        dto.setId(3L);
        when(reservationService.getReservationsByPhoneNumber("123456789", true)).thenReturn(List.of(dto));

        ResponseEntity<List<ReservationDto>> response = reservationController.getReservationsByPhone("123456789", true);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(3L, response.getBody().get(0).getId());
    }

    @Test
    void testUpdateReservation() {
        CreateReservationDto dto = new CreateReservationDto();
        dto.setCourtId(1L);
        dto.setCustomerPhoneNumber("123456789");
        dto.setStartTime(LocalDateTime.now());
        dto.setEndTime(LocalDateTime.now().plusHours(1));

        when(reservationService.updateReservation(10L, dto)).thenReturn(500.0);

        ResponseEntity<Double> response = reservationController.updateReservation(10L, dto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(500.0, response.getBody());
    }

    @Test
    void testDeleteReservation() {
        doNothing().when(reservationService).cancelReservation(8L);

        ResponseEntity<Void> response = reservationController.deleteReservation(8L);

        verify(reservationService, times(1)).cancelReservation(8L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testCreateReservation() {
        CreateReservationDto dto = new CreateReservationDto();
        dto.setCourtId(2L);
        dto.setCustomerPhoneNumber("987654321");
        dto.setStartTime(LocalDateTime.now());
        dto.setEndTime(LocalDateTime.now().plusMinutes(90));

        when(reservationService.createReservation(dto)).thenReturn(750.0);

        ResponseEntity<Double> response = reservationController.createReservation(dto);

        verify(reservationService).createReservation(dto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(750.0, response.getBody());
    }
}
