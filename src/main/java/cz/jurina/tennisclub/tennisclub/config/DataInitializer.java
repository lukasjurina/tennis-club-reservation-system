package cz.jurina.tennisclub.tennisclub.config;

import cz.jurina.tennisclub.tennisclub.dao.CourtDao;
import cz.jurina.tennisclub.tennisclub.dao.CourtSurfaceDao;
import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Initializes default data for the tennis club system at startup.
 * If the external configuration property tennisclub.data.init=true is enabled:
 * Two default court surface types (Clay and Grass) and
 * four courts with the two surfaces are created
 *
 * To enable this initialization, include the following in application.properties:
 * <p>
 * tennisclub.data.init=true
 *
 * @author Lukas Jurina
 */
@Component
public class DataInitializer {

    private final CourtDao courtDao;
    private final CourtSurfaceDao courtSurfaceDao;

    @Value("${tennisclub.data.init:false}")
    private boolean initData;

    public DataInitializer(CourtDao courtDao, CourtSurfaceDao courtSurfaceDao) {
        this.courtDao = courtDao;
        this.courtSurfaceDao = courtSurfaceDao;
    }

    @PostConstruct
    public void init() {

        if (!initData) {
            return;
        }

        CourtSurface clay = new CourtSurface();
        clay.setSurfaceType("Clay");
        clay.setDeleted(false);
        clay.setPrice(3.5);
        courtSurfaceDao.save(clay);

        CourtSurface grass = new CourtSurface();
        grass.setSurfaceType("Grass");
        grass.setDeleted(false);
        grass.setPrice(3.0);
        courtSurfaceDao.save(grass);

        for (int i = 1; i <= 4; i++) {
            Court court = new Court();
            court.setCourtSurface(i % 2 == 0 ? clay : grass);
            court.setDeleted(false);
            courtDao.save(court);
        }
    }
}
