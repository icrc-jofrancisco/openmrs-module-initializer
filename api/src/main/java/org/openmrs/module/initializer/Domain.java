package org.openmrs.module.initializer;

import org.apache.commons.lang.ArrayUtils;

public enum Domain {
	LIQUIBASE,
	JSON_KEY_VALUES,
	CONCEPT_CLASSES,
	CONCEPT_SOURCES,
	METADATASHARING,
	VISIT_TYPES,
	PATIENT_IDENTIFIER_TYPES,
	RELATIONSHIP_TYPES,
	LOCATION_TAGS,
	PRIVILEGES,
	ENCOUNTER_TYPES,
	ENCOUNTER_ROLES,
	ROLES,
	GLOBAL_PROPERTIES,
	ATTRIBUTE_TYPES,
	PROVIDER_ROLES,
	LOCATIONS,
	LOCATION_TAG_MAPS,
	BAHMNI_FORMS,
	OCL,
	CONCEPTS,
	CONCEPT_SETS,
	PROGRAMS,
	PROGRAM_WORKFLOWS,
	PROGRAM_WORKFLOW_STATES,
	PERSON_ATTRIBUTE_TYPES,
	IDGEN,
	AUTO_GENERATION_OPTIONS,
	DRUGS,
	ORDER_FREQUENCIES,
	ORDER_TYPES,
	APPOINTMENT_SPECIALITIES,
	APPOINTMENT_SERVICE_DEFINITIONS,
	APPOINTMENT_SERVICE_TYPES,
	DATAFILTER_MAPPINGS,
	METADATA_SETS,
	METADATA_SET_MEMBERS,
	METADATA_TERM_MAPPINGS,
	AMPATH_FORMS,
	AMPATH_TRANSLATIONS,
	HTML_FORMS;
	
	public int getOrder() {
		return ArrayUtils.indexOf(values(), this) + 1;
	}
	
	/**
	 * The name of the domain is also the name of its subfolder inside the configuration folder.
	 */
	public String getName() {
		return name().replace("_", "").toLowerCase();
	}
}
