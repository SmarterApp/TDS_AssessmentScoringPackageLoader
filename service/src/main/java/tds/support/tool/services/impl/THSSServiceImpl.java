package tds.support.tool.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import tds.support.tool.configuration.SupportToolProperties;
import tds.support.tool.services.THSSService;
import tds.teacherhandscoring.model.TeacherHandScoring;
import tds.teacherhandscoring.model.TeacherHandScoringApiResult;
import tds.teacherhandscoring.model.TeacherHandScoringConfiguration;
import tds.testpackage.model.Item;
import tds.testpackage.model.ItemGroup;
import tds.testpackage.model.Segment;
import tds.testpackage.model.TestPackage;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sends the Teacher Hand Scoring Configuration to THSS
 * <p>
 * Given a Test Specification Package,
 * this service sends the Teacher Hand Scoring Configuration portion of the
 * Test Specification Packages (configuration for individual items) to the
 * THSS .NET application.
 * <p>
 * THSS provides an endpoint to add items to it for configuration
 * prior to allow a teacher to hand score scoring.
 * <p>
 * A JSON file is POSTed to the endpoint as a file attachment:  http://host:28039/api/item/submit
 * <p>
 * This service performs the HTTP POST of the json data and returns the response.
 */
@Service
public class THSSServiceImpl implements THSSService {
    final private SupportToolProperties supportToolProperties;
    final private ObjectMapper objectMapper;

    @Autowired
    public THSSServiceImpl(final SupportToolProperties supportToolProperties, @Qualifier("thssObjectMapper") final ObjectMapper objectMapper) {
        this.supportToolProperties = supportToolProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public TeacherHandScoringApiResult loadTestPackage(final TestPackage testPackage) throws IOException {
        final String thssUrl = supportToolProperties.getThssApiUrl().orElseThrow(() -> new IOException("THSS api url property is not configured"));
        final UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(String.format("%s/item/submit", thssUrl));

        final List<TeacherHandScoringConfiguration> teacherHandScoringConfigurationList = getThssConfiguration(testPackage);
        final String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(teacherHandScoringConfigurationList);

        return postTeacherHandScoringConfiguration(builder.build().toUri(), json);
    }

    /**
     * Extracts all of the TeacherHandScoringConfiguration elements out of the TestPackage.
     */
    public static List<TeacherHandScoringConfiguration> getThssConfiguration(final TestPackage testPackage) {
        final List<TeacherHandScoring> teacherHandScoringList = testPackage.getAssessments().stream().
                flatMap(assessment -> assessment.getSegments().stream()).
                flatMap(THSSServiceImpl::getSegmentItems).
                map(Item::getTeacherHandScoring).
                filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        return teacherHandScoringList.stream().
                map(TeacherHandScoringConfiguration::new).
                collect(Collectors.toList());
    }

    /**
     * Sends the THSS JSON configuration data to THSS
     * Given the THSS endpoint and JSON data
     */
    private TeacherHandScoringApiResult postTeacherHandScoringConfiguration(final URI uri, final String json) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httppost = new HttpPost(uri);
            final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", json.getBytes("UTF-8"),
                    ContentType.APPLICATION_JSON, "thss-data.json");
            httppost.setEntity(builder.build());

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusLine().getStatusCode());
                if (!httpStatus.is2xxSuccessful()) {
                    throw new HttpClientErrorException(httpStatus, response.getStatusLine().getReasonPhrase(),
                            ByteStreams.toByteArray(response.getEntity().getContent()), StandardCharsets.UTF_8);
                }
                return objectMapper.readValue(response.getEntity().getContent(), TeacherHandScoringApiResult.class);
            }
        }
    }

    /**
     * Utility function to return the items of a segment, regardless if
     * the items are in the item pool or segment form.
     */
    private static Stream<Item> getSegmentItems(final Segment segment) {
        final Stream<ItemGroup> segmentFormItemGroups = segment.segmentForms().stream().
                flatMap(segmentForm -> segmentForm.itemGroups().stream());

        final Stream<ItemGroup> segmentPoolItemGroups = segment.pool().stream();

        return Stream.concat(segmentPoolItemGroups, segmentFormItemGroups).flatMap(itemGroup -> itemGroup.items().stream());
    }
}