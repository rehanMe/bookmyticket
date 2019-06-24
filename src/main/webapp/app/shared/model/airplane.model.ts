import { ITransport } from 'app/shared/model/transport.model';

export interface IAirplane {
  id?: number;
  flightName?: string;
  flightNumber?: string;
  fFare?: number;
  fTiming?: string;
  airplanes?: ITransport[];
}

export class Airplane implements IAirplane {
  constructor(
    public id?: number,
    public flightName?: string,
    public flightNumber?: string,
    public fFare?: number,
    public fTiming?: string,
    public airplanes?: ITransport[]
  ) {}
}
