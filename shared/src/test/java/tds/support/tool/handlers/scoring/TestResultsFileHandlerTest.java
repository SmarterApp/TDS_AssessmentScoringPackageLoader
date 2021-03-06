package tds.support.tool.handlers.scoring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tds.support.job.Error;
import tds.support.job.*;
import tds.support.tool.services.scoring.TestResultsService;

import java.io.InputStream;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestResultsFileHandlerTest {
    private TestResultsFileHandler fileHandler;

    @Mock
    private TestResultsService mockTestResultsService;

    @Mock
    private InputStream mockTestPackageStream;

    @Before
    public void setUp() {
        fileHandler = new TestResultsFileHandler(mockTestResultsService);
    }

    @Test
    public void shouldSaveTestRestResults() {
        final String username = "user1";
        TestResultsScoringJob job = new TestResultsScoringJob("trt-filename", username);
        Step stepToUpdate = new Step("update step", TargetSystem.Internal, "Update step description");
        Step step = fileHandler.handleTestResults(stepToUpdate, job, "packageName", mockTestPackageStream, 100L);

        verify(mockTestResultsService).saveTestResults(job, "packageName", mockTestPackageStream, 100L);

        assertThat(step.getStatus(), is(Status.SUCCESS));
        assertThat(step.getErrors(), empty());
    }

    @Test
    public void shouldReturnErrorsInStepDuringException() {
        final String username = "user1";
        TestResultsScoringJob job = new TestResultsScoringJob("trt-filename", username);
        Step stepToUpdate = new Step("update step", TargetSystem.Internal, "Update step description");
        when(mockTestResultsService.saveTestResults(job, "packageName", mockTestPackageStream, 100L))
                .thenThrow(new RuntimeException("Failed to upload file"));

        Step step = fileHandler.handleTestResults(stepToUpdate, job, "packageName", mockTestPackageStream, 100L);

        verify(mockTestResultsService).saveTestResults(job, "packageName", mockTestPackageStream, 100L);

        assertThat(step.getStatus(), is(Status.FAIL));
        assertThat(step.getErrors(), hasSize(1));

        Error error = step.getErrors().get(0);
        assertThat(error.getMessage(), is("Failed to upload file"));
        assertThat(error.getSeverity(), is(ErrorSeverity.CRITICAL));
    }
}