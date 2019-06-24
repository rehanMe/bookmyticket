import { ITransport } from 'app/shared/model/transport.model';

export interface ITrain {
  id?: number;
  trainName?: string;
  trainNumber?: string;
  tFare?: number;
  tTiming?: string;
  trains?: ITransport[];
}

export class Train implements ITrain {
  constructor(
    public id?: number,
    public trainName?: string,
    public trainNumber?: string,
    public tFare?: number,
    public tTiming?: string,
    public trains?: ITransport[]
  ) {}
}
