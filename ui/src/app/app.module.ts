import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { MachineComponent } from './machine-component/machine.component';
import { MachineService } from './service/machine.service';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    MachineComponent
  ],
  entryComponents: [],
  providers: [MachineService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
