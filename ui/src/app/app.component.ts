import { Component, OnInit } from '@angular/core';
import { MachineModel } from './model/machine.model';
import { MachineService } from './service/machine.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {

  public machines: MachineModel[] = [];

  constructor(private service: MachineService) {
  }

  ngOnInit() {
    this.service.getMachines()
        .subscribe((response: MachineModel[]) => this.machines = response);
  }

}
