package com.arnaugarcia.assessoriatorrelles.web.rest;

import com.arnaugarcia.assessoriatorrelles.AssessoriaTorrellesApp;

import com.arnaugarcia.assessoriatorrelles.domain.Location;
import com.arnaugarcia.assessoriatorrelles.repository.LocationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.assessoriatorrelles.domain.enumeration.RoadType;
/**
 * Test class for the LocationResource REST controller.
 *
 * @see LocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssessoriaTorrellesApp.class)
public class LocationResourceIntTest {

    private static final String DEFAULT_REF = "AAAAA";
    private static final String UPDATED_REF = "BBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAA";
    private static final String UPDATED_PROVINCE = "BBBBB";

    private static final String DEFAULT_TOWN = "AAAAA";
    private static final String UPDATED_TOWN = "BBBBB";

    private static final RoadType DEFAULT_TYPE_OF_ROAD = RoadType.STREET;
    private static final RoadType UPDATED_TYPE_OF_ROAD = RoadType.AVENUE;

    private static final String DEFAULT_NAME_ROAD = "AAAAA";
    private static final String UPDATED_NAME_ROAD = "BBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_APARTMENT = 1;
    private static final Integer UPDATED_APARTMENT = 2;

    private static final Integer DEFAULT_BUILDING = 1;
    private static final Integer UPDATED_BUILDING = 2;

    private static final Integer DEFAULT_DOOR = 1;
    private static final Integer UPDATED_DOOR = 2;

    private static final String DEFAULT_STAIR = "AAAAA";
    private static final String UPDATED_STAIR = "BBBBB";

    private static final String DEFAULT_URLGMAPS = "AAAAA";
    private static final String UPDATED_URLGMAPS = "BBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_CP = "AAAAA";
    private static final String UPDATED_CP = "BBBBB";

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLocationMockMvc;

    private Location location;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocationResource locationResource = new LocationResource();
        ReflectionTestUtils.setField(locationResource, "locationRepository", locationRepository);
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
                .ref(DEFAULT_REF)
                .province(DEFAULT_PROVINCE)
                .town(DEFAULT_TOWN)
                .typeOfRoad(DEFAULT_TYPE_OF_ROAD)
                .nameRoad(DEFAULT_NAME_ROAD)
                .number(DEFAULT_NUMBER)
                .apartment(DEFAULT_APARTMENT)
                .building(DEFAULT_BUILDING)
                .door(DEFAULT_DOOR)
                .stair(DEFAULT_STAIR)
                .urlgmaps(DEFAULT_URLGMAPS)
                .latitude(DEFAULT_LATITUDE)
                .longitude(DEFAULT_LONGITUDE)
                .cp(DEFAULT_CP);
        return location;
    }

    @Before
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locations.get(locations.size() - 1);
        assertThat(testLocation.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testLocation.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testLocation.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testLocation.getTypeOfRoad()).isEqualTo(DEFAULT_TYPE_OF_ROAD);
        assertThat(testLocation.getNameRoad()).isEqualTo(DEFAULT_NAME_ROAD);
        assertThat(testLocation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testLocation.getApartment()).isEqualTo(DEFAULT_APARTMENT);
        assertThat(testLocation.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testLocation.getDoor()).isEqualTo(DEFAULT_DOOR);
        assertThat(testLocation.getStair()).isEqualTo(DEFAULT_STAIR);
        assertThat(testLocation.getUrlgmaps()).isEqualTo(DEFAULT_URLGMAPS);
        assertThat(testLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocation.getCp()).isEqualTo(DEFAULT_CP);
    }

    @Test
    @Transactional
    public void checkProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setProvince(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTownIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setTown(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeOfRoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setTypeOfRoad(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameRoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setNameRoad(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setNumber(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setCp(null);

        // Create the Location, which fails.

        restLocationMockMvc.perform(post("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(location)))
                .andExpect(status().isBadRequest());

        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locations
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
                .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
                .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
                .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
                .andExpect(jsonPath("$.[*].typeOfRoad").value(hasItem(DEFAULT_TYPE_OF_ROAD.toString())))
                .andExpect(jsonPath("$.[*].nameRoad").value(hasItem(DEFAULT_NAME_ROAD.toString())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.[*].apartment").value(hasItem(DEFAULT_APARTMENT)))
                .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
                .andExpect(jsonPath("$.[*].door").value(hasItem(DEFAULT_DOOR)))
                .andExpect(jsonPath("$.[*].stair").value(hasItem(DEFAULT_STAIR.toString())))
                .andExpect(jsonPath("$.[*].urlgmaps").value(hasItem(DEFAULT_URLGMAPS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP.toString())));
    }

    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.typeOfRoad").value(DEFAULT_TYPE_OF_ROAD.toString()))
            .andExpect(jsonPath("$.nameRoad").value(DEFAULT_NAME_ROAD.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.apartment").value(DEFAULT_APARTMENT))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING))
            .andExpect(jsonPath("$.door").value(DEFAULT_DOOR))
            .andExpect(jsonPath("$.stair").value(DEFAULT_STAIR.toString()))
            .andExpect(jsonPath("$.urlgmaps").value(DEFAULT_URLGMAPS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findOne(location.getId());
        updatedLocation
                .ref(UPDATED_REF)
                .province(UPDATED_PROVINCE)
                .town(UPDATED_TOWN)
                .typeOfRoad(UPDATED_TYPE_OF_ROAD)
                .nameRoad(UPDATED_NAME_ROAD)
                .number(UPDATED_NUMBER)
                .apartment(UPDATED_APARTMENT)
                .building(UPDATED_BUILDING)
                .door(UPDATED_DOOR)
                .stair(UPDATED_STAIR)
                .urlgmaps(UPDATED_URLGMAPS)
                .latitude(UPDATED_LATITUDE)
                .longitude(UPDATED_LONGITUDE)
                .cp(UPDATED_CP);

        restLocationMockMvc.perform(put("/api/locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLocation)))
                .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locations.get(locations.size() - 1);
        assertThat(testLocation.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testLocation.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testLocation.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testLocation.getTypeOfRoad()).isEqualTo(UPDATED_TYPE_OF_ROAD);
        assertThat(testLocation.getNameRoad()).isEqualTo(UPDATED_NAME_ROAD);
        assertThat(testLocation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testLocation.getApartment()).isEqualTo(UPDATED_APARTMENT);
        assertThat(testLocation.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testLocation.getDoor()).isEqualTo(UPDATED_DOOR);
        assertThat(testLocation.getStair()).isEqualTo(UPDATED_STAIR);
        assertThat(testLocation.getUrlgmaps()).isEqualTo(UPDATED_URLGMAPS);
        assertThat(testLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocation.getCp()).isEqualTo(UPDATED_CP);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Get the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
