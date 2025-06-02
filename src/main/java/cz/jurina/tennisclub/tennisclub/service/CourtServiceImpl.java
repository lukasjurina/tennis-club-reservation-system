package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dao.CourtDao;
import cz.jurina.tennisclub.tennisclub.dao.CourtSurfaceDao;
import cz.jurina.tennisclub.tennisclub.dto.CourtDto;
import cz.jurina.tennisclub.tennisclub.dto.CreateCourtDto;
import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtDao courtDao;
    private final CourtSurfaceDao courtSurfaceDao;

    @Override
    public void createCourt(CreateCourtDto dto) {
        List<CourtSurface> surfaces = courtSurfaceDao.findAll();
        String newSurface = dto.getSurfaceType();

        for (CourtSurface surface : surfaces) {
            if (surface.getSurfaceType().equals(newSurface)) {
                Court court = new Court();
                court.setCourtSurface(surface);
                courtDao.save(court);
                return;
            }
        }

        throw new IllegalArgumentException("Surface type does not exist");

    }

    @Override
    public List<CourtDto> getAllCourts() {
        return courtDao.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public CourtDto getCourtById(Long id) {

        testId(id);

        return entityToDto(courtDao.findById(id));
    }

    @Override
    public void deleteCourt(Long id) {

        testId(id);

        courtDao.deleteById(id);
    }

    @Override
    public void updateCourt(Long id, CreateCourtDto dto) {

        testId(id);

        List<CourtSurface> surfaces = courtSurfaceDao.findAll();
        String newSurface = dto.getSurfaceType();

        for (CourtSurface surface : surfaces) {
            if (surface.getSurfaceType().equals(newSurface)) {
                Court court = courtDao.findById(surface.getId());
                court.setCourtSurface(surface);
                courtDao.save(court);
                return;
            }
        }

        throw new IllegalArgumentException("Surface type does not exist");

    }

    private CourtDto entityToDto(Court c) {
        return new CourtDto(c.getId(), c.getCourtSurface().getSurfaceType(), c.getCourtSurface().getPrice());
    }

    private void testId(Long id) {
        if (courtDao.findById(id) == null){
            throw new IllegalArgumentException("Court with id " + id + " does not exist");
        }
    }
}
