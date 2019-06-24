import { ITransport } from 'app/shared/model/transport.model';

export interface IDestination {
  id?: number;
  destinationName?: string;
  destinations?: ITransport[];
}

export class Destination implements IDestination {
  constructor(public id?: number, public destinationName?: string, public destinations?: ITransport[]) {}
}
