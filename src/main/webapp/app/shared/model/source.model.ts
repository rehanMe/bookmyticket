import { ITransport } from 'app/shared/model/transport.model';

export interface ISource {
  id?: number;
  sourceName?: string;
  sources?: ITransport[];
}

export class Source implements ISource {
  constructor(public id?: number, public sourceName?: string, public sources?: ITransport[]) {}
}
