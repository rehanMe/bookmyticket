import { ITransport } from 'app/shared/model/transport.model';
import { ICustomer } from 'app/shared/model/customer.model';

export const enum Status {
  CONFIRMED = 'CONFIRMED',
  CANCELLED = 'CANCELLED',
  TRANSACTIONFAILURE = 'TRANSACTIONFAILURE'
}

export interface IBookings {
  id?: number;
  cardNo?: string;
  validThru?: string;
  cvv?: number;
  name?: string;
  status?: Status;
  transport?: ITransport;
  customer?: ICustomer;
}

export class Bookings implements IBookings {
  constructor(
    public id?: number,
    public cardNo?: string,
    public validThru?: string,
    public cvv?: number,
    public name?: string,
    public status?: Status,
    public transport?: ITransport,
    public customer?: ICustomer
  ) {}
}
