package tds.testpackage.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TestPackageSerializationTests {
	private ObjectMapper objectMapper;
	private XmlMapper xmlMapper;

	private String expectedJSON = "{\"publisher\":\"SBAC_PT\",\"publishDate\":\"2015-08-19T18:13:51.0\",\"subject\":\"MATH\",\"type\":\"summative\",\"version\":\"8185\",\"bankKey\":187,\"academicYear\":\"2017-2018\"," +
		"\"blueprint\":[{\"id\":\"SBAC-IRP-COMBINED-MATH-11\",\"type\":\"combined\"}]," +
		"\"assessments\":[{\"id\":\"SBAC-IRP-CAT-MATH-11\",\"label\":\"IRP CAT Grade 11 Math\",\"grades\":[{\"value\":\"11\"}]," +
		"\"segments\":[{\"id\":\"SBAC-IRP-Perf-MATH-11\",\"algorithmType\":\"fixedform\",\"algorithmImplementation\":\"FAIRWAY ROUNDROBIN\"," +
		"\"pool\":[{\"id\":\"id\",\"items\":[{\"id\":\"id\",\"type\":\"type\",\"presentations\":[\"ENU\"]," +
		"\"blueprintReferences\":[{\"idRef\":\"SBAC-IRP-CAT-MATH-11\"}]," +
		"\"itemScoreDimension\":{\"measurementModel\":\"IRT3PLn\",\"scorePoints\":\"1\",\"weight\":1.0," +
		"\"itemScoreParameters\":[{\"measurementParameter\":\"a\",\"value\":6.3}]}}]}]}]," +
		"\"tools\":[{\"name\":\"tool\",\"studentPackageFieldName\":\"TDSAcc\"," +
		"\"options\":[{\"code\":\"TDS_Other\",\"sortOrder\":0,\"dependencies\":[{\"ifToolType\":\"ifToolType\",\"ifToolCode\":\"ifToolCode\"}]}]}]}]}";

	@Before
	public void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new Jdk8Module());
	}

	@Test
	public void shouldSerializeToJson() throws JsonProcessingException {
		BlueprintElement blueprintElement = BlueprintElement.builder()
			.setId("SBAC-IRP-COMBINED-MATH-11")
			.setType("combined")
			.build();

		Grade grade = Grade.builder()
			.setValue("11")
			.build();

		BlueprintReference blueprintReference = BlueprintReference.builder()
			.setIdRef("SBAC-IRP-CAT-MATH-11")
			.build();

		ItemScoreParameter itemScoreParameter = ItemScoreParameter.builder()
			.setMeasurementParameter("a")
			.setValue(6.3)
			.build();

		ItemScoreDimension itemScoreDimension = ItemScoreDimension.builder()
			.setScorePoints("1")
			.setMeasurementModel("IRT3PLn")
			.setWeight(1)
			.setItemScoreParameters(Arrays.asList(itemScoreParameter))
			.build();

		Item item = Item.builder()
			.setPresentations(Arrays.asList("ENU"))
			.setBlueprintReferences(Arrays.asList(blueprintReference))
			.setId("id")
			.setType("type")
			.setItemScoreDimension(itemScoreDimension)
			.build();

		ItemGroup itemGroup = ItemGroup.builder()
			.setId("id")
			.setItems(Arrays.asList(item))
			.build();

		Segment segment = Segment.builder()
			.setId("SBAC-IRP-Perf-MATH-11")
			.setPosition(Optional.of(1))
			.setAlgorithmType("fixedform")
			.setAlgorithmImplementation("FAIRWAY ROUNDROBIN")
			.setPool(Arrays.asList(itemGroup))
			.build();

		Dependency dependency = Dependency.builder()
			.setIfToolType("ifToolType")
			.setIfToolCode("ifToolCode")
			.build();

		Option option = Option.builder()
			.setCode("TDS_Other")
			.setSortOrder(0)
			.setDependencies(Arrays.asList(dependency))
			.build();

		Tool tool = Tool.builder()
			.setName("tool")
			.setStudentPackageFieldName("TDSAcc")
			.setOptions(Arrays.asList(option))
			.build();

		Assessment assessment = Assessment.builder()
			.setId("SBAC-IRP-CAT-MATH-11")
			.setLabel("IRP CAT Grade 11 Math")
			.setGrades(Arrays.asList(grade))
			.setSegments(Arrays.asList(segment))
			.setTools(Arrays.asList(tool))
			.build();

		TestPackage testPackage = TestPackage.builder()
			.setPublisher("SBAC_PT")
			.setPublishDate("2015-08-19T18:13:51.0")
			.setSubject("MATH")
			.setType("summative")
			.setVersion("8185")
			.setBankKey(187)
			.setAcademicYear("2017-2018")
			.setBlueprint(Arrays.asList(blueprintElement))
			.setAssessments(Arrays.asList(assessment))
			.build();

		String json = objectMapper.writeValueAsString(testPackage);

		assertThatJson(json)
			.isEqualTo(expectedJSON);
	}

	@Test
	public void shouldDeserializeFromXml() throws IOException {
		InputStream inputStream = TestPackageSerializationTests.class.getClassLoader().getResourceAsStream("V2-(SBAC_PT)IRP-GRADE-11-MATH-EXAMPLE.xml");
		TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
		assertThat(testPackage.getPublisher()).isEqualTo("SBAC_PT");
		assertThat(testPackage.getSubject()).isEqualTo("MATH");
	}

	/**
	 * Ensure that optional values are not deserialized as null.
	 * 
	 * @throws IOException
	 */
	@Test
	public void shouldDeserializeWithoutNulls() throws IOException {
		InputStream inputStream = TestPackageSerializationTests.class.getClassLoader().getResourceAsStream("scoring-rules.xml");
		TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
		String json = objectMapper.writeValueAsString(testPackage);
		assertThat(json.indexOf("null")).isEqualTo(-1);
	}


	@Test
	public void shouldDeserializeFromXmlWithBooleanDefaults() throws IOException {
		InputStream inputStream = TestPackageSerializationTests.class.getClassLoader().getResourceAsStream("Dependencies-boolean-defaults.xml");
		List<Dependency> dependencies = xmlMapper.readValue(inputStream, new TypeReference<List<Dependency>>() {});

		assertThat(dependencies.size()).isEqualTo(7);
		assertThat(dependencies.get(0).enabled()).isEqualTo(false);
		assertThat(dependencies.get(1).enabled()).isEqualTo(true);
		assertThat(dependencies.get(3).enabled()).isEqualTo(false);
		assertThat(dependencies.get(4).enabled()).isEqualTo(true);

		assertThat(dependencies.get(0).defaultValue()).isEqualTo(false);
		assertThat(dependencies.get(2).defaultValue()).isEqualTo(true);
		assertThat(dependencies.get(5).defaultValue()).isEqualTo(true);
		assertThat(dependencies.get(6).defaultValue()).isEqualTo(false);
	}

	@Test
	public void shouldDeserializeFromJson() throws IOException {
		TestPackage testPackage = objectMapper.readValue(expectedJSON, TestPackage.class);

		assertThat(testPackage.getPublisher()).isEqualTo("SBAC_PT");
		assertThat(testPackage.getSubject()).isEqualTo("MATH");
	}

	/**
	 * Both the files below represent the same test package:
	 * V2-(SBAC_PT)IRP-GRADE-11-MATH-EXAMPLE.xml and V2-(SBAC_PT)IRP-GRADE-11-MATH-EXAMPLE.json
	 *
	 * Deserializing xml and json formats and comparing the in memory object model.
	 *
	 * @throws IOException
	 */
	@Test
	public void shouldDeserializeFromJsonAndXmlAsSameObject() throws IOException {
		InputStream xmlInputStream = TestPackageSerializationTests.class.getClassLoader().getResourceAsStream("V2-(SBAC_PT)IRP-GRADE-11-MATH-EXAMPLE.xml");
		InputStream jsonInputStream = TestPackageSerializationTests.class.getClassLoader().getResourceAsStream("V2-(SBAC_PT)IRP-GRADE-11-MATH-EXAMPLE.json");

		TestPackage testPackageXml = xmlMapper.readValue(xmlInputStream, TestPackage.class);
		TestPackage testPackageJson = objectMapper.readValue(jsonInputStream, TestPackage.class);

		assertThat(testPackageXml).isEqualTo(testPackageJson);
	}
}
