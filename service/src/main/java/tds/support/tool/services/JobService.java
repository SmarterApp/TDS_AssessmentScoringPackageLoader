package tds.support.tool.services;

import java.io.InputStream;
import java.util.List;

import tds.support.job.Job;
import tds.support.job.JobType;

/**
 * Service handling the {@link tds.support.job.Job}
 */
public interface JobService {
    /**
     * Starts the test package import job resulting in a job loaded into TDS, TIS, THSS, and ART
     *
     * @param packageName     the test package name
     * @param testPackage     the {@link java.io.InputStream} containing the test package
     * @param testPackageSize the size of the test package
     * @param includeScoring  {@code true} if scoring is to be included in the upload
     * @return the {@link tds.support.job.Job} created
     */
    Job startPackageImport(final String packageName, final InputStream testPackage, long testPackageSize, final boolean includeScoring);

    /**
     * Finds all jobs matching a specific {@link tds.support.job.JobType}
     *
     * @param jobType The type of jobs to fetch
     * @return A collection of all jobs matching
     */
    List<Job> findJobs(final JobType jobType);

    Job handleJobStep(final String jobId, final String stepName);
}
