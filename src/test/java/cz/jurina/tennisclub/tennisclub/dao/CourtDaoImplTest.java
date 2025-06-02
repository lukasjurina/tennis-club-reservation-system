package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CourtDaoImpl.class,  CourtSurfaceDaoImpl.class})
class CourtDaoImplTest {

    @Autowired
    private CourtDao courtDao;

    @Autowired
    private CourtSurfaceDao courtSurfaceDao;

    private CourtSurface surface;

    @BeforeEach
    void setUp() {
        surface = new CourtSurface();
        surface.setSurfaceType("Clay");
        surface.setPrice(150.0);
        surface.setDeleted(false);
        courtSurfaceDao.save(surface);
    }

    @Test
    void testSaveAndFindById() {
        Court court = new Court();
        court.setCourtSurface(surface);
        court.setDeleted(false);

        courtDao.save(court);

        Court found = courtDao.findById(court.getId());

        assertThat(found).isNotNull();
        assertThat(found.getCourtSurface().getSurfaceType()).isEqualTo("Clay");
        assertThat(found.getDeleted()).isFalse();
    }

    @Test
    void testFindAll() {
        Court court1 = new Court();
        court1.setCourtSurface(surface);
        court1.setDeleted(false);
        courtDao.save(court1);

        Court court2 = new Court();
        court2.setCourtSurface(surface);
        court2.setDeleted(false);
        courtDao.save(court2);

        List<Court> courts = courtDao.findAll();
        assertThat(courts).hasSize(2);
    }

    @Test
    void testDeleteByIdMarksAsDeleted() {
        Court court = new Court();
        court.setCourtSurface(surface);
        court.setDeleted(false);
        courtDao.save(court);

        courtDao.deleteById(court.getId());

        Court deletedCourt = courtDao.findById(court.getId());
        assertThat(deletedCourt).isNotNull();
        assertThat(deletedCourt.getDeleted()).isTrue();

        // findAll should exclude soft-deleted court
        List<Court> courts = courtDao.findAll();
        assertThat(courts).doesNotContain(deletedCourt);
    }
}