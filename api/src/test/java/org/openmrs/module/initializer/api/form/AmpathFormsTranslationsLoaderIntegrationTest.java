package org.openmrs.module.initializer.api.form;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.DatatypeService;
import org.openmrs.api.db.ClobDatatypeStorage;
import org.openmrs.module.initializer.DomainBaseModuleContextSensitiveTest;
import org.openmrs.module.initializer.api.loaders.AmpathFormsTranslationsLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class AmpathFormsTranslationsLoaderIntegrationTest extends DomainBaseModuleContextSensitiveTest {
	
	private static final String FORM_TRANSLATIONS_FOLDER_PATH = "src/test/resources/ampathformstranslations/";
	
	private static final String RESOURCE_UUID = "c5bf3efe-3798-4052-8dcb-09aacfcbabdc";
	
	@Autowired
	private AmpathFormsTranslationsLoader ampathFormsTranslationsLoader;
	
	@Autowired
	private DatatypeService datatypeService;
	
	@After
	public void clean() throws IOException {
		
		// Delete created form files
		FileUtils.deleteDirectory(new File(FORM_TRANSLATIONS_FOLDER_PATH));
		FileUtils.deleteQuietly(new File(
		        ampathFormsTranslationsLoader.getDirUtil().getDomainDirPath() + "/test_ampath_translations_updated.json"));
	}
	
	@Test
	public void load_shouldLoadFormTranslationsWithAllAttributesSpecified() throws Exception {
		
		// Replay
		ampathFormsTranslationsLoader.load();
		
		ClobDatatypeStorage clobTranslationsData = datatypeService.getClobDatatypeStorageByUuid(RESOURCE_UUID);
		
		// Verify clob
		Assert.assertNotNull(clobTranslationsData);
		Assert.assertEquals("c5bf3efe-3798-4052-8dcb-09aacfcbabdc", clobTranslationsData.getUuid());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(clobTranslationsData.getValue());
		Assert.assertEquals("\"c5bf3efe-3798-4052-8dcb-09aacfcbabdc\"", actualObj.get("uuid").toString());
		Assert.assertEquals("\"French Translations\"", actualObj.get("description").toString());
		Assert.assertEquals("\"fr\"", actualObj.get("language").toString());
		Assert.assertEquals("\"Encontre\"", actualObj.get("translations").get("Encounter").toString());
		Assert.assertEquals("\"Autre\"", actualObj.get("translations").get("Other").toString());
		Assert.assertEquals("\"Enfant\"", actualObj.get("translations").get("Child").toString());
		
	}
	
	@Test
	public void load_shouldLoadAndUpdateClob() throws Exception {
		
		// Test that initial version loads in with expected values
		// Replay
		ampathFormsTranslationsLoader.load();
		
		ClobDatatypeStorage clobTranslationsData = datatypeService.getClobDatatypeStorageByUuid(RESOURCE_UUID);
		
		// Verify clob
		Assert.assertNotNull(clobTranslationsData);
		Assert.assertEquals("c5bf3efe-3798-4052-8dcb-09aacfcbabdc", clobTranslationsData.getUuid());
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode ampathTranslations = mapper.readTree(clobTranslationsData.getValue());
		Assert.assertEquals("\"c5bf3efe-3798-4052-8dcb-09aacfcbabdc\"", ampathTranslations.get("uuid").toString());
		Assert.assertEquals("\"French Translations\"", ampathTranslations.get("description").toString());
		Assert.assertEquals("\"fr\"", ampathTranslations.get("language").toString());
		Assert.assertEquals("\"Encontre\"", ampathTranslations.get("translations").get("Encounter").toString());
		Assert.assertEquals("\"Autre\"", ampathTranslations.get("translations").get("Other").toString());
		Assert.assertEquals("\"Enfant\"", ampathTranslations.get("translations").get("Child").toString());
		
		String test_file_updated = "src/test/resources/testdata/testAmpathformstranslations/test_ampath_translations_updated.json";
		File srcFile = new File(test_file_updated);
		File dstFile = new File(
		        ampathFormsTranslationsLoader.getDirUtil().getDomainDirPath() + "/test_ampath_translations_updated.json");
		
		FileUtils.copyFile(srcFile, dstFile);
		
		// Replay
		ampathFormsTranslationsLoader.load();
		ClobDatatypeStorage clobTranslationsDataUpdated = datatypeService.getClobDatatypeStorageByUuid(RESOURCE_UUID);
		
		// Verify clob changed
		Assert.assertNotNull(clobTranslationsDataUpdated);
		ObjectMapper mapperUpdated = new ObjectMapper();
		JsonNode ampathTranslationsUpdated = mapperUpdated.readTree(clobTranslationsData.getValue());
		Assert.assertEquals("\"c5bf3efe-3798-4052-8dcb-09aacfcbabdc\"", ampathTranslationsUpdated.get("uuid").toString());
		Assert.assertEquals("\"French Translations Updated\"", ampathTranslationsUpdated.get("description").toString());
		Assert.assertEquals("\"fr\"", ampathTranslationsUpdated.get("language").toString());
		Assert.assertEquals("\"Tante\"", ampathTranslationsUpdated.get("translations").get("Aunt").toString());
		Assert.assertEquals("\"Oncle\"", ampathTranslationsUpdated.get("translations").get("Uncle").toString());
		Assert.assertEquals("\"Neveu\"", ampathTranslationsUpdated.get("translations").get("Nephew").toString());
	}
	
}