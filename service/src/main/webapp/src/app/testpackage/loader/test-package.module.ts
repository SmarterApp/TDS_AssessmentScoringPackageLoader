import { NgModule } from "@angular/core";
import { TestPackageComponent } from "./test-package.component";
import { TestPackageJobsModule } from "./jobs/test-package-jobs.module";
import { TestPackageJobsComponent } from "./jobs/test-package-jobs.component";
import { TestPackageJobDetailsComponent } from "./jobs/test-package-job-details.component";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { NgxDatatableModule } from "@swimlane/ngx-datatable";
import { DataTableModule } from "primeng/primeng";
import { UploadLoaderJobModule } from "./jobs/upload/upload-loader-job.module";

@NgModule({
  declarations: [
    TestPackageComponent,
    TestPackageJobsComponent,
    TestPackageJobDetailsComponent
  ],
  imports: [
    TestPackageJobsModule,
    BrowserModule,
    DataTableModule,
    NgxDatatableModule,
    FormsModule,
    UploadLoaderJobModule
  ],
  exports: [
    TestPackageComponent,
    TestPackageJobsComponent
  ]
})
export class TestPackageModule {
}
