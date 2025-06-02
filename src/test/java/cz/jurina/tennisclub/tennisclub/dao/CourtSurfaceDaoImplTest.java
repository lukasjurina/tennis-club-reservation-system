package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CourtSurfaceDaoImpl.class)
class CourtSurfaceDaoImplTest {

    @Autowired
    private CourtSurfaceDao courtSurfaceDao;

    @Test
    void testSaveAndFindById() {
        CourtSurface surface = new CourtSurface();
        surface.setSurfaceType("Grass");
        surface.setPrice(200.0);
        surface.setDeleted(false);

        courtSurfaceDao.save(surface);

        CourtSurface found = courtSurfaceDao.findById(surface.getId());

        assertThat(found).isNotNull();
        assertThat(found.getSurfaceType()).isEqualTo("Grass");
        assertThat(found.getPrice()).isEqualTo(200.0);
        assertThat(found.isDeleted()).isFalse();
    }

    @Test
    void testFindAll() {
        CourtSurface s1 = new CourtSurface();
        s1.setSurfaceType("Hard");
        s1.setPrice(100.0);
        s1.setDeleted(false);
        courtSurfaceDao.save(s1);

        CourtSurface s2 = new CourtSurface();
        s2.setSurfaceType("Clay");
        s2.setPrice(120.0);
        s2.setDeleted(false);
        courtSurfaceDao.save(s2);

        List<CourtSurface> surfaces = courtSurfaceDao.findAll();

        assertThat(surfaces).hasSize(2);
        assertThat(surfaces).extracting(CourtSurface::getSurfaceType).containsExactlyInAnyOrder("Hard", "Clay");
    }

    @Test
    void testDeleteByIdMarksAsDeleted() {
        CourtSurface surface = new CourtSurface();
        surface.setSurfaceType("Wood");
        surface.setPrice(180.0);
        surface.setDeleted(false);
        courtSurfaceDao.save(surface);

        courtSurfaceDao.deleteById(surface.getId());

        CourtSurface deletedSurface = courtSurfaceDao.findById(surface.getId());
        assertThat(deletedSurface).isNotNull();
        assertThat(deletedSurface.isDeleted()).isTrue();

        List<CourtSurface> allSurfaces = courtSurfaceDao.findAll();
        assertThat(allSurfaces).doesNotContain(deletedSurface);
    }
}
