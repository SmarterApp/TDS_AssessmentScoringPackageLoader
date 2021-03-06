import {
  Component, EventEmitter, Input, OnDestroy, OnInit, Output, TemplateRef, ViewChild,
  ViewEncapsulation
} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {TestPackageJobService} from "./test-package-jobs.service";
import {TestPackageJob, StepStatus} from "./model/test-package-job.model";
import {TimerObservable} from "rxjs/observable/TimerObservable";
import 'rxjs/add/operator/takeWhile';
import {AuthGuard} from "../../../auth.component";

@Component({
  selector: 'test-package-jobs',
  templateUrl: './test-package-jobs.component.html',
  styleUrls: ['../../test-package.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TestPackageJobsComponent implements OnInit, OnDestroy {

  @ViewChild('dateTmpl') dateTmpl: TemplateRef<any>;
  @ViewChild('validateTmpl') validateTmpl: TemplateRef<any>;
  @ViewChild('systemTmpl') systemTmpl: TemplateRef<any>;

  // query: LoaderJobsQuery;
  StepStatuses = StepStatus; // Need to include the enum as a property to access it in template
  searchTerm: string = '';
  selectedJob: TestPackageJob;
  private alive: boolean; // used to unsubscribe from the TimerObservable when OnDestroy is called.
  @Output() selectedTestPackageJobChange: EventEmitter<TestPackageJob> = new EventEmitter<TestPackageJob>();
  filteredTestPackageJobs: TestPackageJob[];
  private _testPackageJobs: TestPackageJob[];
  selected = [];
  columns = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: TestPackageJobService,
              private authGuard: AuthGuard) {
    this.alive = true;
  }

  get testPackageJobs(): TestPackageJob[] {
    return this._testPackageJobs;
  }

  set testPackageJobs(loaderJobs: TestPackageJob[]) {
    this._testPackageJobs = loaderJobs;
    this.updateFilteredTestPackageJobs();
  }

  ngOnInit() {
    this.columns = [
        {prop: 'shortId', name: 'Job ID', width: 50},
        {prop: 'testPackageName', name: 'Test Package Name', width : 340 },
        {prop: 'createdAt', name: 'Date Created', cellTemplate: this.dateTmpl, width: 130},
        {prop: 'type', name: 'Job Type', width: 50},
        {prop: 'validationStepStatus', name: 'Validation', headerClass: "text-align-center", cellTemplate: this.validateTmpl, width: 30},
        {prop: 'tdsStepStatus', name: 'TDS', headerClass: "text-align-center", cellTemplate: this.systemTmpl, width: 20},
        {prop: 'artStepStatus', name: 'ART', headerClass: "text-align-center", cellTemplate: this.systemTmpl, width: 20},
        {prop: 'tisStepStatus', name: 'TIS', headerClass: "text-align-center", cellTemplate: this.systemTmpl, width: 20},
        {prop: 'thssStepStatus', name: 'THSS', headerClass: "text-align-center", cellTemplate: this.systemTmpl, width: 20},
    ];
    TimerObservable.create(0, 5000)
      .takeWhile(() => this.alive)
      .subscribe(() => {
        this.updateResults();
      });
  }

  ngOnDestroy() {
    this.alive = false;
  }

  updateResults() {
    this.service
      .getTestPackageJobs()
      .subscribe(loaderJobs => {
          this.testPackageJobs = loaderJobs;
        },
        error => {
          console.log("test-package-jobs.component got error getting data - refreshing auth")
          this.authGuard.updateUser();
        });
  }

  onSearchChange() {
    this.updateFilteredTestPackageJobs();
  }

  updateFilteredTestPackageJobs() {
    // Check if test package name or job id matches
    this.filteredTestPackageJobs = this.testPackageJobs
      .filter(x => x.testPackageName.toUpperCase().indexOf(this.searchTerm.toUpperCase()) >= 0
        || x.id.toUpperCase().indexOf(this.searchTerm.toUpperCase()) >= 0);
  }

  onRowSelect(event) {
    this.selectedJob = event['selected'][0];
    this.selectedTestPackageJobChange.emit(this.selectedJob);
  }

  rowClass = (rowData: TestPackageJob) => {
    if ( this.selectedJob && this.selectedJob.id === rowData.id) {
      return 'active';
    }

    return rowData.type == 'LOAD'
      && (rowData.validationStepStatus === 'FAIL'
      || rowData.tdsStepStatus === 'FAIL'
      || rowData.artStepStatus === 'FAIL'
      || rowData.tisStepStatus === 'FAIL'
      || rowData.thssStepStatus === 'FAIL')
      ? 'failed'
      : '';
  };

  uploadClick() {
    this.router.navigateByUrl('/loader/upload');
  }

  statusClick() {
    this.router.navigateByUrl('/loader/status');
  }
}
