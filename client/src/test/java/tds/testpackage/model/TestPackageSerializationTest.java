package tds.testpackage.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
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

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TestPackageSerializationTest {
    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;

    private String expectedJSON = "{\"publisher\":\"SBAC_PT\",\"id\":\"SBAC-IRP-COMBINED-MATH-11\",\"publishDate\":\"2015-08-19T18:13:51.0\",\"subject\":\"MATH\",\"type\":\"summative\",\"version\":\"8185\",\"bankKey\":187,\"academicYear\":\"2017-2018\"," +
        "\"blueprint\":[{\"id\":\"SBAC-IRP-COMBINED-MATH-11\",\"type\":\"combined\"," +
        "\"scoring\":{\"rules\":[{\"name\":\"rule-name\",\"computationOrder\":1," +
        "\"parameters\":[{\"id\":\"id\",\"name\":\"parameter-name\",\"type\":\"type\",\"position\":1,\"values\":[{\"value\":\"value\"}]," +
        "\"properties\":[{\"name\":\"property-name\",\"value\":\"property-value\"}]}]}]," +
        "\"performanceLevels\":[{\"scaledLo\":1.0,\"scaledHi\":10.0,\"pLevel\":1}]}}," +
        "{\"id\":\"nested-parent\",\"type\":\"combined\",\"blueprintElements\":[{\"id\":\"nested-child\",\"type\":\"combined\"}]}]," +
        "\"assessments\":[{\"id\":\"SBAC-IRP-CAT-MATH-11\",\"label\":\"IRP CAT Grade 11 Math\",\"grades\":[{\"value\":\"11\"}]," +
        "\"segments\":[{\"id\":\"SBAC-IRP-Perf-MATH-11\",\"algorithmType\":\"fixedform\",\"algorithmImplementation\":\"FAIRWAY ROUNDROBIN\",\"position\":1," +
        "\"pool\":[{\"id\":\"id\",\"items\":[{\"id\":\"id\",\"type\":\"type\",\"presentations\":[{\"code\":\"ENU\", \"label\":\"English\"}]," +
        "\"blueprintReferences\":[{\"idRef\":\"SBAC-IRP-CAT-MATH-11\"},{\"idRef\":\"G11Math_DOK2\"}]," +
        "\"itemScoreDimensions\":[{\"measurementModel\":\"IRT3PLn\",\"scorePoints\":1,\"weight\":1.0," +
        "\"itemScoreParameters\":[{\"measurementParameter\":\"a\",\"value\":6.3}]}]}]}]," +
        "\"segmentForms\":[{\"id\":\"id\",\"cohort\":\"Cohort\",\"presentations\":[{\"code\":\"ENU\", \"label\":\"English\"}]," +
        "\"itemGroups\":[{\"id\":\"item-group-id2\",\"items\":[{\"id\":\"item-id2\",\"type\":\"type\",\"presentations\":[{\"code\":\"ENU\", \"label\":\"English\"}]," +
        "\"blueprintReferences\":[{\"idRef\":\"blueprintReference3\"}]," +
        "\"itemScoreDimensions\":[{\"measurementModel\":\"model2\",\"scorePoints\":1,\"weight\":0.0}]}]}]}]}]," +
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
        Value value = Value.builder()
            .setValue("value")
            .build();

        Property property = Property.builder()
            .setName("property-name")
            .setValue("property-value")
            .build();

        Parameter parameter = Parameter.builder()
            .setId("id")
            .setName("parameter-name")
            .setType("type")
            .setPosition(1)
            .setProperties(Arrays.asList(property))
            .setValues(Arrays.asList(value))
            .build();

        Rule rule = Rule.builder()
            .setName("rule-name")
            .setParameters(Arrays.asList(parameter))
            .setComputationOrder(1)
            .build();

        PerformanceLevel performanceLevel = PerformanceLevel.builder()
            .setPLevel(1)
            .setScaledHi(10)
            .setScaledLo(1)
            .build();

        Scoring scoring = Scoring.builder()
            .setPerformanceLevels(Arrays.asList(performanceLevel))
            .setRules(Arrays.asList(rule))
            .build();

        BlueprintElement blueprintElement = BlueprintElement.builder()
            .setId("SBAC-IRP-COMBINED-MATH-11")
            .setType("combined")
            .setScoring(scoring)
            .build();

        BlueprintElement nestedBlueprintElementChild = BlueprintElement.builder()
            .setId("nested-child")
            .setType("combined")
            .setScoring(Optional.empty())
            .build();

        BlueprintElement nestedBlueprintElementParent = BlueprintElement.builder()
            .setId("nested-parent")
            .setType("combined")
            .setScoring(Optional.empty())
            .setBlueprintElements(Arrays.asList(nestedBlueprintElementChild))
            .build();

        Grade grade = Grade.builder()
            .setValue("11")
            .build();

        BlueprintReference blueprintReference1 = BlueprintReference.builder()
            .setIdRef("SBAC-IRP-CAT-MATH-11")
            .build();

        BlueprintReference blueprintReference2 = BlueprintReference.builder()
            .setIdRef("G11Math_DOK2")
            .build();

        BlueprintReference blueprintReference3 = BlueprintReference.builder()
            .setIdRef("blueprintReference3")
            .build();

        ItemScoreParameter itemScoreParameter = ItemScoreParameter.builder()
            .setMeasurementParameter("a")
            .setValue(6.3)
            .build();

        ItemScoreDimension itemScoreDimension = ItemScoreDimension.builder()
            .setScorePoints(1)
            .setMeasurementModel("IRT3PLn")
            .setWeight(1)
            .setItemScoreParameters(Arrays.asList(itemScoreParameter))
            .build();

        ItemScoreDimension itemScoreDimension2 = ItemScoreDimension.builder()
            .setScorePoints(1)
            .setWeight(0.0)
            .setMeasurementModel("model2")
            .build();

        Presentation englishPresentation = Presentation.builder()
            .setCode("ENU")
            .setLabel("English")
            .build();

        Item item = Item.builder()
            .setPresentations(Arrays.asList(englishPresentation))
            .setBlueprintReferences(Arrays.asList(blueprintReference1, blueprintReference2))
            .setId("id")
            .setType("type")
            .setItemScoreDimensions(Collections.singletonList(itemScoreDimension))
            .build();

        ItemGroup itemGroup = ItemGroup.builder()
            .setId("id")
            .setItems(Arrays.asList(item))
            .build();

        Item item2 = Item.builder()
            .setPresentations(Arrays.asList(englishPresentation))
            .setBlueprintReferences(Arrays.asList(blueprintReference3))
            .setId("item-id2")
            .setType("type")
            .setItemScoreDimensions(Collections.singletonList(itemScoreDimension2))
            .build();

        ItemGroup itemGroup2 = ItemGroup.builder()
            .setId("item-group-id2")
            .setItems(Arrays.asList(item2))
            .build();

        SegmentForm segmentForm = SegmentForm.builder()
            .setId("id")
            .setCohort("Cohort")
            .setPresentations(Arrays.asList(englishPresentation))
            .setItemGroups(Arrays.asList(itemGroup2))
            .build();

        Segment segment = Segment.builder()
            .setId("SBAC-IRP-Perf-MATH-11")
            .setPosition(1)
            .setAlgorithmType("fixedform")
            .setAlgorithmImplementation("FAIRWAY ROUNDROBIN")
            .setPool(Arrays.asList(itemGroup))
            .setSegmentForms(Arrays.asList(segmentForm))
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
            .setId("SBAC-IRP-COMBINED-MATH-11")
            .setPublisher("SBAC_PT")
            .setPublishDate("2015-08-19T18:13:51.0")
            .setSubject("MATH")
            .setType("summative")
            .setVersion("8185")
            .setBankKey(187)
            .setAcademicYear("2017-2018")
            .setBlueprint(Arrays.asList(blueprintElement, nestedBlueprintElementParent))
            .setAssessments(Arrays.asList(assessment))
            .build();

        item.setItemGroup(itemGroup);
        item2.setItemGroup(itemGroup2);

        String json = objectMapper.writeValueAsString(testPackage);

        assertThatJson(json)
            .isEqualTo(expectedJSON);
    }

    @Test
    public void shouldRoundTripJsonSerializeTestPackage() throws Exception {

        Value value = Value.builder()
            .setValue("value")
            .build();

        Property property = Property.builder()
            .setName("property-name")
            .setValue("property-value")
            .build();

        Parameter parameter = Parameter.builder()
            .setId("id")
            .setName("parameter-name")
            .setType("type")
            .setPosition(1)
            .setProperties(Arrays.asList(property))
            .setValues(Arrays.asList(value))
            .build();

        Rule rule = Rule.builder()
            .setName("rule-name")
            .setParameters(Arrays.asList(parameter))
            .setComputationOrder(1)
            .build();

        PerformanceLevel performanceLevel = PerformanceLevel.builder()
            .setPLevel(1)
            .setScaledHi(10)
            .setScaledLo(1)
            .build();

        Scoring scoring = Scoring.builder()
            .setPerformanceLevels(Arrays.asList(performanceLevel))
            .setRules(Arrays.asList(rule))
            .build();

        BlueprintElement blueprintElement = BlueprintElement.builder()
            .setId("SBAC-IRP-COMBINED-MATH-11")
            .setType("combined")
            .setScoring(scoring)
            .build();

        BlueprintElement nestedBlueprintElementChild = BlueprintElement.builder()
            .setId("nested-child")
            .setType("combined")
            .setScoring(Optional.empty())
            .build();

        BlueprintElement nestedBlueprintElementParent = BlueprintElement.builder()
            .setId("nested-parent")
            .setType("combined")
            .setScoring(Optional.empty())
            .setBlueprintElements(Arrays.asList(nestedBlueprintElementChild))
            .build();

        Grade grade = Grade.builder()
            .setValue("11")
            .build();

        BlueprintReference blueprintReference1 = BlueprintReference.builder()
            .setIdRef("SBAC-IRP-CAT-MATH-11")
            .build();

        BlueprintReference blueprintReference2 = BlueprintReference.builder()
            .setIdRef("G11Math_DOK2")
            .build();

        BlueprintReference blueprintReference3 = BlueprintReference.builder()
            .setIdRef("blueprintReference3")
            .build();

        ItemScoreParameter itemScoreParameter = ItemScoreParameter.builder()
            .setMeasurementParameter("a")
            .setValue(6.3)
            .build();

        ItemScoreDimension itemScoreDimension = ItemScoreDimension.builder()
            .setScorePoints(1)
            .setMeasurementModel("IRT3PLn")
            .setWeight(1)
            .setItemScoreParameters(Arrays.asList(itemScoreParameter))
            .build();

        ItemScoreDimension itemScoreDimension2 = ItemScoreDimension.builder()
            .setScorePoints(1)
            .setWeight(0.0)
            .setMeasurementModel("model2")
            .build();

        Presentation englishPresentation = Presentation.builder()
            .setCode("ENU")
            .setLabel("English")
            .build();

        Item item = Item.builder()
            .setPresentations(Arrays.asList(englishPresentation))
            .setBlueprintReferences(Arrays.asList(blueprintReference1, blueprintReference2))
            .setId("id")
            .setType("type")
            .setItemScoreDimensions(Collections.singletonList(itemScoreDimension))
            .build();

        ItemGroup itemGroup = ItemGroup.builder()
            .setId("id")
            .setItems(Arrays.asList(item))
            .build();

        Item item2 = Item.builder()
            .setPresentations(Arrays.asList(englishPresentation))
            .setBlueprintReferences(Arrays.asList(blueprintReference3))
            .setId("item-id2")
            .setType("type")
            .setItemScoreDimensions(Collections.singletonList(itemScoreDimension2))
            .build();

        ItemGroup itemGroup2 = ItemGroup.builder()
            .setId("item-group-id2")
            .setItems(Arrays.asList(item2))
            .build();

        SegmentForm segmentForm = SegmentForm.builder()
            .setId("id")
            .setCohort("Cohort")
            .setPresentations(Arrays.asList(englishPresentation))
            .setItemGroups(Arrays.asList(itemGroup2))
            .build();

        Segment segment = Segment.builder()
            .setId("SBAC-IRP-Perf-MATH-11")
            .setPosition(1)
            .setAlgorithmType("fixedform")
            .setAlgorithmImplementation("FAIRWAY ROUNDROBIN")
            .setPool(Arrays.asList(itemGroup))
            .setSegmentForms(Arrays.asList(segmentForm))
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
            .setId("SBAC-IRP-COMBINED-MATH-11")
            .setPublisher("SBAC_PT")
            .setPublishDate("2015-08-19T18:13:51.0")
            .setSubject("MATH")
            .setType("summative")
            .setVersion("8185")
            .setBankKey(187)
            .setAcademicYear("2017-2018")
            .setBlueprint(Arrays.asList(blueprintElement, nestedBlueprintElementParent))
            .setAssessments(Arrays.asList(assessment))
            .build();

        item.setItemGroup(itemGroup);
        item2.setItemGroup(itemGroup2);


        String json = objectMapper.writeValueAsString(testPackage);

        TestPackage testPackage2 = objectMapper.readValue(json, TestPackage.class);
        assertThat(testPackage2).isEqualTo(testPackage);
    }


    @Test
    public void shouldRoundTripJsonSerializeSegment() throws Exception {
        Segment segment = Segment.builder().
            setAlgorithmImplementation("FAIRWAY ROUNDROBIN").
            setId("SBAC-IRP-Perf-MATH-11").
            setAlgorithmType("fixedform").
            setEntryApproval(true).
            setExitApproval(true).
            build();

        String json = objectMapper.writeValueAsString(segment);

        Segment segment2 = objectMapper.readValue(json, Segment.class);
        assertThat(segment2).isEqualTo(segment);
    }

    @Test
    public void shouldDeserializeFromXml() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        assertThat(testPackage.getPublisher()).isEqualTo("SBAC_PT");
        assertThat(testPackage.getSubject()).isEqualTo("MATH");
        assertThat(testPackage.getAssessments().get(0).getSegments().get(0).getPool().get(0).getItems().get(0).getBlueprintReferences().size()).isEqualTo(3);
    }

    @Test
    public void shouldDeserializeItemFromXmlWithAssessmentKey() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.addDeserializer(TestPackage.class, new TestPackageDeserializer());
        xmlMapper.registerModule(module);

        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        assertThat(testPackage.getPublisher()).isEqualTo("SBAC_PT");
        assertThat(testPackage.getSubject()).isEqualTo("MATH");
        assertThat(testPackage.getAssessments().get(0).getSegments().get(0).getPool().get(0).getItems().get(0).getBlueprintReferences().size()).isEqualTo(3);
        assertThat(testPackage.getAssessments().get(0).getKey()).isEqualTo("(SBAC_PT)SBAC-IRP-CAT-MATH-11-2017-2018");
        assertThat(testPackage.getAssessments().get(0).getSegments().get(0).getKey()).isEqualTo("(SBAC_PT)SBAC-IRP-CAT-MATH-11-2017-2018");
    }

    /**
     * Ensure that optional values are not deserialized as null.
     *
     * @throws IOException
     */
    @Test
    public void shouldDeserializeWithoutNulls() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("scoring-rules.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        String json = objectMapper.writeValueAsString(testPackage);
        assertThat(json.indexOf("null")).isEqualTo(-1);
    }

    @Test
    public void shouldHaveItemsScoreParameterWithDimension() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("scoring-rules.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        String dimension = testPackage.getAssessments().get(0).getSegments().get(0).
            getSegmentForms().get(0).getItemGroups().get(0).getItems().get(0).
            getItemScoreDimensions().get(0).getDimension().get();
        assertThat(dimension).isEqualTo("test-dimension");
    }

    @Test
    public void shouldDeserializePresentationsWithoutTrailingWhitespace() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        Presentation presentation = testPackage.getAssessments().get(0).getSegments().get(0).getPool().get(0).getItems().get(0).getPresentations().get(0);
        assertThat(presentation.getCode()).isEqualTo(presentation.getCode().trim());
    }

    @Test
    public void presentationsShouldHaveDefaultLabels() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);
        Presentation presentation = testPackage.getAssessments().get(0).getSegments().get(0).getPool().get(0).getItems().get(0).getPresentations().get(0);
        assertThat(presentation.label()).isEqualTo("English");
    }

    @Test
    public void shouldDeserializeFromXmlWithBooleanDefaults() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("dependencies-boolean-defaults.xml");
        List<Dependency> dependencies = xmlMapper.readValue(inputStream, new TypeReference<List<Dependency>>() {
        });

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
    public void shouldDeserializePresentationsFromXml() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("presentations.xml");
        List<Presentation> presentations = xmlMapper.readValue(inputStream, new TypeReference<List<Presentation>>() {
        });

        assertThat(presentations.size()).isEqualTo(2);
    }

    @Test
    public void shouldDeserializeSegmentBlueprintElementFromXmlWithEmptyListDefaults() throws IOException {
        InputStream inputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("segment-blueprint-element.xml");
        TestPackage testPackage = xmlMapper.readValue(inputStream, TestPackage.class);

        List<SegmentBlueprintElement> segmentBlueprint = testPackage.getAssessments().get(0).getSegments().get(0).getSegmentBlueprint();
        assertThat(segmentBlueprint.get(0).itemSelection().size()).isEqualTo(1);
        assertThat(segmentBlueprint.get(1).itemSelection().size()).isEqualTo(1);
        assertThat(segmentBlueprint.get(2).itemSelection().size()).isEqualTo(0);
    }

    @Test
    public void shouldDeserializeFromJson() throws IOException {
        TestPackage testPackage = objectMapper.readValue(expectedJSON, TestPackage.class);

        assertThat(testPackage.getPublisher()).isEqualTo("SBAC_PT");
        assertThat(testPackage.getSubject()).isEqualTo("MATH");
        BlueprintElement nestedChildBlueprintElement = testPackage.getBlueprint().get(1).blueprintElements().get(0);
        assertThat(nestedChildBlueprintElement.getId()).isEqualTo("nested-child");

        SegmentForm segmentForm = testPackage.getAssessments().get(0).getSegments().get(0).getSegmentForms().get(0);
        Item item = segmentForm.itemGroups().get(0).items().get(0);
        assertThat(item.getId()).isEqualTo("item-id2");

        Scoring scoring = testPackage.getBlueprint().get(0).getScoring().get();
        PerformanceLevel performanceLevel = scoring.performanceLevels().get(0);
        assertThat(performanceLevel.getPLevel()).isEqualTo(1);
        assertThat(performanceLevel.getScaledHi()).isEqualTo(10);
        assertThat(performanceLevel.getScaledLo()).isEqualTo(1);

        Rule rule = scoring.getRules().get(0);
        assertThat(rule.getName()).isEqualTo("rule-name");
        Parameter parameter = rule.getParameters().get(0);
        assertThat(parameter.getName()).isEqualTo("parameter-name");
        assertThat(parameter.getProperties().get(0).getName()).isEqualTo("property-name");
        assertThat(parameter.getProperties().get(0).getValue()).isEqualTo("property-value");
        assertThat(parameter.getValues().get(0).getValue()).isEqualTo("value");
    }

    /**
     * Both the files below represent the same test package:
     * test-specification-example-1.xml and test-specification-example-1.json
     * <p>
     * Deserializing xml and json formats and comparing the in memory object model.
     *
     * @throws IOException
     */
    @Test
    public void shouldDeserializeFromJsonAndXmlAsSameObject() throws IOException {
        InputStream xmlInputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.xml");
        InputStream jsonInputStream = TestPackageSerializationTest.class.getClassLoader().getResourceAsStream("test-specification-example-1.json");

        TestPackage testPackageXml = xmlMapper.readValue(xmlInputStream, TestPackage.class);
        TestPackage testPackageJson = objectMapper.readValue(jsonInputStream, TestPackage.class);

        assertThat(testPackageXml).isEqualTo(testPackageJson);
    }
}
