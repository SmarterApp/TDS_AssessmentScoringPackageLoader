import { Component, OnInit } from '@angular/core';
import { TestPackageStatusService } from "./service/test-package-status.service";
import { TestPackageStatusRow } from "./model/test-package-status-row";
import { StepStatus } from "../jobs/model/test-package-job.model";
import { TargetSystem } from "./model/target-system.enum";
import { SortDirection } from "../../../shared/data/sort-direction.enum";
import { PageResponse } from "../../../shared/data/page-response";

/**
 * Controller for interacting with test package status data.
 */
@Component({
  templateUrl: './test-package-status.component.html',
  styleUrls: ['./test-package-status.component.css', '../../test-package.component.css']
})
export class TestPackageStatusComponent implements OnInit {
  // Initialize the model to some value
  private _testPackageStatusPage: PageResponse<TestPackageStatusRow> = {
    content: [],
    numberOfElements: 0,
    first: false,
    last: false,
    totalPages: 0,
    totalElements: 0,
    size: 0,
    number: 0,
    sort: []
  };

  private sortPreference = {
    sort: "uploadedAt",
    sortDir: SortDirection.Descending
  };

  constructor(private testPackageStatusService: TestPackageStatusService) {
  }

  /**
   * @return {TestPackageStatusRow[]} A collection of {TestPackageStatusRow}s that represent the state of each test
   * package managed by the Support Tool.
   */
  get testPackageStatusPage(): PageResponse<TestPackageStatusRow> {
    return this._testPackageStatusPage;
  }

  set testPackageStatusPage(value: PageResponse<TestPackageStatusRow>) {
    this._testPackageStatusPage = value;
  }

  /**
   * Get the first page of {TestPackageStatusRow}s for display, sorted by "Last Uploaded At" in Descending order.
   */
  ngOnInit() {
    // Create an event to fetch the first page of records.
    const initPaginatorEvent = {
      page: 0,
      rows: 2,
      first: 0,
      pageCount: 0
    };

    this.getNextPage(initPaginatorEvent);
  }

  /**
   * Get the next page of records.
   *
   * @param event The event fired by the {Paginator#onPageChange} method
   */
  getNextPage(event) {
    const nextPage = {
      page: event.page,
      size: event.rows,
      sort: this.sortPreference.sort,
      sortDir: this.sortPreference.sortDir
    };

    this.testPackageStatusService.getAll(nextPage)
      .subscribe(response => this.testPackageStatusPage = response);
  }

  /**
   * Set the sorting preference and update the page.
   */
  setSortPreference(event) {
    this.sortPreference = {
      sort: event.field,
      sortDir: event.order == 1 ? SortDirection.Ascending : SortDirection.Descending
    };

    this.getNextPage({
      page: this.testPackageStatusPage.number,
      rows: this.testPackageStatusPage.size,
      sort: this.sortPreference.sort,
      sortDir: this.sortPreference.sortDir
    });
  }

  /**
   * Get the appropriate CSS class for the specified {StepStatus}
   * <p>
   *     This method cannot be static; Angular views cannot access static methods
   * </p>
   *
   * @param {StepStatus} status The {StepStatus} to evaluate
   * @return {string} The CSS class that represents the specified {StepStatus}
   */
  getStatusIconCss(status: StepStatus): string {
    return TestPackageStatusRow.getStatusIconClass(status);
  }

  /**
   * Build a tooltip description message for the {TargetSystem}'s {StepStatus}
   * <p>
   *     This method cannot be static; Angular views cannot access static methods
   * </p>
   *
   * @param {StepStatus} status The target system's {StepStatus}
   * @param {TargetSystem} system The {TargetSystem}
   * @return {string} A message describing what the status icon means
   */
  getStatusDescription(status: StepStatus, system: TargetSystem): string {
    let statusMessage = `The test package was not loaded into ${ system }`;

    switch (status) {
      case StepStatus.Success:
        statusMessage = `The test package was loaded into ${ system } successfully`;
        break;
      case StepStatus.Fail:
        statusMessage = `The test package could not be loaded into ${ system }`;
        break;
      default:
        break;
    }

    return statusMessage;
  }

  /**
   * Delete a test package from all the systems it has been loaded into.
   *
   * @param {string} name The name of the test package to delete
   */
  deleteTestPackage(name: string) {
    const message = `Are you sure you want to delete the '${ name }' test package?`;
    if (window.confirm(message)) {
      // TODO: implement call to delete test package
      console.log("You must really wanna delete %s", name);
    }
  }
}
