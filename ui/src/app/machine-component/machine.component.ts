import { Component, Input, OnInit } from '@angular/core';
import { GlobalThresholds } from '../globals/thresholds';
import { MachineModel } from '../model/machine.model';
import { MachineService } from '../service/machine.service';

@Component({
  selector: 'iot-machine',
  templateUrl: './machine.component.html',
  styleUrls: ['./machine.component.less']
})

export class MachineComponent {
  @Input() machine: MachineModel;

  public thresholds;

  constructor(private service: MachineService) {
    this.thresholds = GlobalThresholds;
  }

  private doPrint(): void {
    if (this.machine.printing) {
      this.service.printMachine(this.machine)
          .subscribe((response: MachineModel) => {
            this.machine = {...response, printing: this.machine.printing};
            setTimeout(() => {
              if (this.machine.quantity > 0) {
                this.doPrint();
              } else {
                this.machine.printing = false;
              }
            }, 600);
          });
    }
  }

  // HANDLERS //

  public onPrint(): void {
    this.machine.printing = true;
    this.doPrint();
  }

  public onPause(): void {
    this.machine.printing = false;
  }

  public onRefill(): void {
    this.service.refillMachine(this.machine)
        .subscribe((response: MachineModel) => this.machine = {...response, printing: this.machine.printing});
  }

  // GETTERS //

  get percent(): number {
    return (this.machine.quantity / this.machine.capacity) * 100;
  }

  get dangerWidth(): number {
    if (this.percent > 0) {
      return this.percent > this.thresholds.LOW_DANGER ? this.thresholds.LOW_DANGER : this.percent;
    } else {
      return 0;
    }
  }

  get warningWidth(): number {
    if (this.percent > this.thresholds.LOW_DANGER) {
      const width = this.percent - this.thresholds.LOW_DANGER;
      return width > this.thresholds.LOW_WARNING - this.thresholds.LOW_DANGER ? this.thresholds.LOW_WARNING - this.thresholds.LOW_DANGER : width;
    } else {
      return 0;
    }
  }

  get successWidth(): number {
    if (this.percent > this.thresholds.LOW_WARNING) {
      return this.percent - this.thresholds.LOW_WARNING;
    } else {
      return 0;
    }
  }
}
