import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MachineModel } from '../model/machine.model';

@Injectable()
export class MachineService {

  constructor(private http: HttpClient) {}

  public getMachines(): Observable<MachineModel[]> {
    return this.http.get<MachineModel[]>('/api/machines/');
  }

  public printMachine(machine: MachineModel): Observable<MachineModel> {
    return this.http.get<MachineModel>(`/api/machines/${machine.id}/print`);
  }

  public refillMachine(machine: MachineModel): Observable<MachineModel> {
    return this.http.get<MachineModel>(`/api/machines/${machine.id}/refill`);
  }
}
