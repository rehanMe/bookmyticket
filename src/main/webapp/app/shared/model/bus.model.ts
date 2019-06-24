import { ITransport } from 'app/shared/model/transport.model';

export interface IBus {
  id?: number;
  busName?: string;
  busNumber?: string;
  bFare?: number;
  bTiming?: string;
  buses?: ITransport[];
}

export class Bus implements IBus {
  constructor(
    public id?: number,
    public busName?: string,
    public busNumber?: string,
    public bFare?: number,
    public bTiming?: string,
    public buses?: ITransport[]
  ) {}
}
