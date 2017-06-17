package com.arnaugarcia.realstatecamp.web.rest;

import com.arnaugarcia.realstatecamp.RealStateCampApp;

import com.arnaugarcia.realstatecamp.domain.Property;
import com.arnaugarcia.realstatecamp.repository.PropertyRepository;

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

import com.arnaugarcia.realstatecamp.domain.enumeration.BuildingType;
import com.arnaugarcia.realstatecamp.domain.enumeration.ServiceType;
/**
 * Test class for the PropertyResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RealStateCampApp.class)
public class PropertyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BuildingType DEFAULT_BUILDING_TYPE = BuildingType.HOUSE;
    private static final BuildingType UPDATED_BUILDING_TYPE = BuildingType.FLAT;

    private static final ServiceType DEFAULT_SERVICE_TYPE = ServiceType.RENT;
    private static final ServiceType UPDATED_SERVICE_TYPE = ServiceType.SALE;

    private static final String DEFAULT_REF = "AAAAA";
    private static final String UPDATED_REF = "BBBBB";

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final Boolean DEFAULT_SOLD = false;
    private static final Boolean UPDATED_SOLD = true;

    private static final Boolean DEFAULT_TERRACE = false;
    private static final Boolean UPDATED_TERRACE = true;

    private static final Integer DEFAULT_M_2 = 1;
    private static final Integer UPDATED_M_2 = 2;

    private static final Integer DEFAULT_NUMBER_BEDROOM = 1;
    private static final Integer UPDATED_NUMBER_BEDROOM = 2;

    private static final Boolean DEFAULT_ELEVATOR = false;
    private static final Boolean UPDATED_ELEVATOR = true;

    private static final Boolean DEFAULT_FURNISHED = false;
    private static final Boolean UPDATED_FURNISHED = true;

    private static final Boolean DEFAULT_POOL = false;
    private static final Boolean UPDATED_POOL = true;

    private static final Boolean DEFAULT_GARAGE = false;
    private static final Boolean UPDATED_GARAGE = true;

    private static final Integer DEFAULT_NUMBER_WC = 1;
    private static final Integer UPDATED_NUMBER_WC = 2;

    private static final Boolean DEFAULT_AC = false;
    private static final Boolean UPDATED_AC = true;

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPropertyMockMvc;

    private Property property;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropertyResource propertyResource = new PropertyResource();
        ReflectionTestUtils.setField(propertyResource, "propertyRepository", propertyRepository);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .description(DEFAULT_DESCRIPTION)
                .buildingType(DEFAULT_BUILDING_TYPE)
                .serviceType(DEFAULT_SERVICE_TYPE)
                .ref(DEFAULT_REF)
                .visible(DEFAULT_VISIBLE)
                .sold(DEFAULT_SOLD)
                .terrace(DEFAULT_TERRACE)
                .m2(DEFAULT_M_2)
                .numberBedroom(DEFAULT_NUMBER_BEDROOM)
                .elevator(DEFAULT_ELEVATOR)
                .furnished(DEFAULT_FURNISHED)
                .pool(DEFAULT_POOL)
                .garage(DEFAULT_GARAGE)
                .numberWc(DEFAULT_NUMBER_WC)
                .ac(DEFAULT_AC);
        return property;
    }

    @Before
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property

        restPropertyMockMvc.perform(post("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(property)))
                .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = properties.get(properties.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getBuildingType()).isEqualTo(DEFAULT_BUILDING_TYPE);
        assertThat(testProperty.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testProperty.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testProperty.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testProperty.isSold()).isEqualTo(DEFAULT_SOLD);
        assertThat(testProperty.isTerrace()).isEqualTo(DEFAULT_TERRACE);
        assertThat(testProperty.getm2()).isEqualTo(DEFAULT_M_2);
        assertThat(testProperty.getNumberBedroom()).isEqualTo(DEFAULT_NUMBER_BEDROOM);
        assertThat(testProperty.isElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testProperty.isFurnished()).isEqualTo(DEFAULT_FURNISHED);
        assertThat(testProperty.isPool()).isEqualTo(DEFAULT_POOL);
        assertThat(testProperty.isGarage()).isEqualTo(DEFAULT_GARAGE);
        assertThat(testProperty.getNumberWc()).isEqualTo(DEFAULT_NUMBER_WC);
        assertThat(testProperty.isAc()).isEqualTo(DEFAULT_AC);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setName(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(property)))
                .andExpect(status().isBadRequest());

        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setRef(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(property)))
                .andExpect(status().isBadRequest());

        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the properties
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].buildingType").value(hasItem(DEFAULT_BUILDING_TYPE.toString())))
                .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
                .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
                .andExpect(jsonPath("$.[*].sold").value(hasItem(DEFAULT_SOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.booleanValue())))
                .andExpect(jsonPath("$.[*].m2").value(hasItem(DEFAULT_M_2)))
                .andExpect(jsonPath("$.[*].numberBedroom").value(hasItem(DEFAULT_NUMBER_BEDROOM)))
                .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.booleanValue())))
                .andExpect(jsonPath("$.[*].furnished").value(hasItem(DEFAULT_FURNISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.booleanValue())))
                .andExpect(jsonPath("$.[*].garage").value(hasItem(DEFAULT_GARAGE.booleanValue())))
                .andExpect(jsonPath("$.[*].numberWc").value(hasItem(DEFAULT_NUMBER_WC)))
                .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.booleanValue())));
    }

    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.buildingType").value(DEFAULT_BUILDING_TYPE.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.sold").value(DEFAULT_SOLD.booleanValue()))
            .andExpect(jsonPath("$.terrace").value(DEFAULT_TERRACE.booleanValue()))
            .andExpect(jsonPath("$.m2").value(DEFAULT_M_2))
            .andExpect(jsonPath("$.numberBedroom").value(DEFAULT_NUMBER_BEDROOM))
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.booleanValue()))
            .andExpect(jsonPath("$.furnished").value(DEFAULT_FURNISHED.booleanValue()))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.booleanValue()))
            .andExpect(jsonPath("$.garage").value(DEFAULT_GARAGE.booleanValue()))
            .andExpect(jsonPath("$.numberWc").value(DEFAULT_NUMBER_WC))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findOne(property.getId());
        updatedProperty
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .description(UPDATED_DESCRIPTION)
                .buildingType(UPDATED_BUILDING_TYPE)
                .serviceType(UPDATED_SERVICE_TYPE)
                .ref(UPDATED_REF)
                .visible(UPDATED_VISIBLE)
                .sold(UPDATED_SOLD)
                .terrace(UPDATED_TERRACE)
                .m2(UPDATED_M_2)
                .numberBedroom(UPDATED_NUMBER_BEDROOM)
                .elevator(UPDATED_ELEVATOR)
                .furnished(UPDATED_FURNISHED)
                .pool(UPDATED_POOL)
                .garage(UPDATED_GARAGE)
                .numberWc(UPDATED_NUMBER_WC)
                .ac(UPDATED_AC);

        restPropertyMockMvc.perform(put("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProperty)))
                .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = properties.get(properties.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getBuildingType()).isEqualTo(UPDATED_BUILDING_TYPE);
        assertThat(testProperty.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testProperty.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testProperty.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testProperty.isSold()).isEqualTo(UPDATED_SOLD);
        assertThat(testProperty.isTerrace()).isEqualTo(UPDATED_TERRACE);
        assertThat(testProperty.getm2()).isEqualTo(UPDATED_M_2);
        assertThat(testProperty.getNumberBedroom()).isEqualTo(UPDATED_NUMBER_BEDROOM);
        assertThat(testProperty.isElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testProperty.isFurnished()).isEqualTo(UPDATED_FURNISHED);
        assertThat(testProperty.isPool()).isEqualTo(UPDATED_POOL);
        assertThat(testProperty.isGarage()).isEqualTo(UPDATED_GARAGE);
        assertThat(testProperty.getNumberWc()).isEqualTo(UPDATED_NUMBER_WC);
        assertThat(testProperty.isAc()).isEqualTo(UPDATED_AC);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeDelete - 1);
    }
}
